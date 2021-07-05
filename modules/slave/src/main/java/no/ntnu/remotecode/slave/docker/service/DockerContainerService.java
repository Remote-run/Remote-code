package no.ntnu.remotecode.slave.docker.service;

import no.ntnu.remotecode.model.docker.Container;
import no.ntnu.remotecode.slave.DebugLogger;
import no.ntnu.remotecode.slave.docker.Interface.IDockerBasicFunctions;
import no.ntnu.remotecode.slave.docker.Interface.IDockerHostFileSystemInterface;
import no.ntnu.remotecode.slave.docker.command.DockerFunctions;
import no.ntnu.remotecode.slave.docker.command.DockerRunCommand;
import no.ntnu.remotecode.slave.docker.command.HostInteraction;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.List;

public class DockerContainerService {

    private static final String containerVolumeMountPath = "/root/remote-code-project";
    DebugLogger dbl = new DebugLogger(true);

    IDockerBasicFunctions dockerBasicFunctions = new DockerFunctions();
    IDockerHostFileSystemInterface hostFs = new HostInteraction();

    DockerImageService dockerImageService = new DockerImageService();


    public boolean startContainer() {
        return true;
    }

    public void createContainer(Container container) throws FileSystemException, FileNotFoundException, InterruptedException {
        /*
        1. sjekk om tmplate dir sitt bilde e laget om ikke legg in en laging i køen
        2. sjekk om container diren e laget om ikke lag en, og putt inn template repo
         */


        File hostDataDir = new File(hostFs.getContainerDataDirContainer(), container.getDataDirName());

        if (!hostDataDir.exists()) {
            hostDataDir.mkdir();
        }
        dbl.log("made dir");
        dockerImageService.buildImage(container.getContainerTemplate());
        dbl.log("built template");
        DockerRunCommand runCommand = new DockerRunCommand(container.getContainerTemplate().getTemplateImageName(),
                                                           container.getContainerName());

        runCommand.setResourceAllocationParts(List.of("--gpus all"));
        runCommand.setNetwork("remote_code_net");

        runCommand.addEnvVariable("GIT_BASE_REPO", container.getContainerTemplate().getGitCloneRepo());

        String hostdirP = new File(hostFs.getContainerDataDirHost(), container.getDataDirName()).toString();


        runCommand.addVolume(hostdirP, containerVolumeMountPath);

        runCommand.setDetached(true);
        //        runCommand.setDumpIO(true);

        runCommand.run();
        dbl.log("started command");
    }

}
