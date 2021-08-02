package no.ntnu.remotecode.slave;


import no.ntnu.remotecode.slave.docker.Interface.IDockerHostFileSystemInterface;
import no.ntnu.remotecode.slave.docker.command.HostInteraction;
import no.ntnu.remotecode.slave.util.FileUtils;
import no.ntnu.remotecode.master.model.Project;
import no.ntnu.remotecode.slave.docker.Interface.DockerInterface;
import no.ntnu.remotecode.slave.docker.Interface.IDockerBasicFunctions;
import no.ntnu.remotecode.slave.docker.command.DockerFunctions;
import no.ntnu.remotecode.slave.docker.service.DockerContainerService;

import java.io.File;

/*
1. starte co rutine som sjekker status på kjørende kontainere og cpu bruk osv.

 */
public class ContainerManager implements DockerInterface {

    public static DebugLogger dbl = new DebugLogger(true);

    private final DockerContainerService containerService = new DockerContainerService();
    private final IDockerBasicFunctions dockerFunctions = new DockerFunctions();

    private IDockerHostFileSystemInterface hostFs = new HostInteraction();

    public boolean startContainer(Project project) {
        dbl.log("container start order issued");
        try {
            containerService.startContainer(project);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean stopContainer(Project project) {
        dockerFunctions.stopContainer(project.getContainerName());
        return true;
    }

    public boolean deleteContainer(Project project) {
        dockerFunctions.stopContainer(project.getContainerName());
        dockerFunctions.deleteContainer(project.getContainerName());

        File containerDataDir = new File(hostFs.getContainerDataDirContainer(), project.getDataDirName());

        FileUtils.deleteDir(containerDataDir);
        return true;
    }

    public void getRunningContainerInfo(Project project) {

    }


}
