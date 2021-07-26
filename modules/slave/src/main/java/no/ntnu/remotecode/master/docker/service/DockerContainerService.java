package no.ntnu.remotecode.master.docker.service;

import no.ntnu.remotecode.model.Project;
import no.ntnu.remotecode.master.DebugLogger;
import no.ntnu.remotecode.master.docker.Interface.IDockerBasicFunctions;
import no.ntnu.remotecode.master.docker.Interface.IDockerHostFileSystemInterface;
import no.ntnu.remotecode.master.docker.command.DockerFunctions;
import no.ntnu.remotecode.master.docker.command.DockerRunCommand;
import no.ntnu.remotecode.master.docker.command.HostInteraction;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.List;
import java.util.Map;

public class DockerContainerService {

    private static final String containerVolumeMountPath = "/root/remote-code-project";
    DebugLogger dbl = new DebugLogger(true);

    IDockerBasicFunctions dockerBasicFunctions = new DockerFunctions();
    IDockerHostFileSystemInterface hostFs = new HostInteraction();

    DockerImageService dockerImageService = new DockerImageService();


    public boolean startContainer(Project project) {
        /*
        1. check if the container is on this computer at all.
            - if it is and is running return OK
            - if it is and not running try to start, if sucsess return ok
        2. If it is not on this computer build, start the container
         */
        String               containerName = project.getContainerName();
        Map<String, Boolean> statusMap     = dockerBasicFunctions.getContainerStatuses();

        if (statusMap.containsKey(containerName)) {
            if (statusMap.get(containerName)) {
                // if container exist and is running nothing have to be done so return ok
                return true;
            } else {
                return dockerBasicFunctions.startContainer(containerName);
            }
        } else {
            try {
                return createContainer(project);

            } catch (FileSystemException | FileNotFoundException | InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private boolean createContainer(Project project) throws FileSystemException, FileNotFoundException, InterruptedException {
        /*
        1. sjekk om tmplate dir sitt bilde e laget om ikke legg in en laging i køen
        2. sjekk om container diren e laget om ikke lag en, og putt inn template repo
         */


        File hostDataDir = new File(hostFs.getContainerDataDirContainer(), project.getDataDirName());

        if (!hostDataDir.exists()) {
            hostDataDir.mkdir();
        }
        dbl.log("made dir");
        dockerImageService.buildImage(project.getContainerTemplate());
        dbl.log("built template");
        DockerRunCommand runCommand = new DockerRunCommand(project.getContainerTemplate().getTemplateImageName(),
                                                           project.getContainerName());

        runCommand.setResourceAllocationParts(List.of("--gpus all"));
        runCommand.setNetwork("remote_code_net");

        runCommand.addEnvVariable("GIT_BASE_REPO", project.getContainerTemplate().getGitCloneRepo());

        String hostdirP = new File(hostFs.getContainerDataDirHost(), project.getDataDirName()).toString();


        runCommand.addVolume(hostdirP, containerVolumeMountPath);

        runCommand.setDetached(true);
        //        runCommand.setDumpIO(true);
        Process process  = runCommand.run();
        int     exitCode = 1;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dbl.log("started command");
        return exitCode == 0;
    }

}
