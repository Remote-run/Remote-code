package no.ntnu.remotecode.slave;


import no.ntnu.remotecode.slave.service.IDockerHostFileSystemInterface;
import no.ntnu.remotecode.slave.service.HostInteraction;
import no.ntnu.remotecode.slave.util.FileUtils;
import no.ntnu.remotecode.master.model.Project;
import no.ntnu.remotecode.slave.service.DockerContainerService;

import java.io.File;

/*
1. starte co rutine som sjekker status på kjørende kontainere og cpu bruk osv.

 */
public class ContainerManager {

    public static DebugLogger dbl = new DebugLogger(true);

    private final DockerContainerService containerService = new DockerContainerService();

    private IDockerHostFileSystemInterface hostFs = new HostInteraction();

    /**
     * Starts an instance of the provided {@link Project}.
     * The container has been prevously run and stopped, it wil be started again.
     * If the container havent been run on this node before it wil be built.
     *
     * @param project the container to start
     * @return {@code true} if the start is sucsessfull {@code false} if not.
     */
    public boolean startContainer(Project project) {
        dbl.log("container start order issued");
        boolean suc = false;
        try {
            suc = containerService.startContainer(project);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suc;
    }

    public boolean stopContainer(Project project) {
        containerService.stopContainer(project);
        return true;
    }

    public boolean deleteContainer(Project project) {
        containerService.stopContainer(project);
        containerService.deleteContainer(project);

        File containerDataDir = new File(hostFs.getContainerDataDirContainer(), project.getDataDirName());

        FileUtils.deleteDir(containerDataDir);
        return true;
    }

    public void getRunningContainerInfo(Project project) {

    }


}
