package no.ntnu.remotecode.slave;


import no.ntnu.remotecode.model.Project;
import no.ntnu.remotecode.slave.docker.Interface.DockerInterface;
import no.ntnu.remotecode.slave.docker.Interface.IDockerBasicFunctions;
import no.ntnu.remotecode.slave.docker.command.DockerFunctions;
import no.ntnu.remotecode.slave.docker.service.DockerContainerService;

/*
1. starte co rutine som sjekker status på kjørende kontainere og cpu bruk osv.

 */
public class ContainerManager implements DockerInterface {

    private final DockerContainerService containerService = new DockerContainerService();
    private final IDockerBasicFunctions dockerFunctions = new DockerFunctions();

    public boolean startContainer(Project project) {
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

    public void deleteContainer(Project project) {
        dockerFunctions.deleteContainer(project.getContainerName());
    }

    public void getRunningContainerInfo(Project project) {

    }


}
