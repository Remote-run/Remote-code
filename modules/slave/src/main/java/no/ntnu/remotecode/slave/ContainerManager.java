package no.ntnu.remotecode.slave;


import no.ntnu.remotecode.model.docker.Container;
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

    public boolean startContainer(Container container) {
        try {
            containerService.startContainer(container);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean stopContainer(Container container) {
        dockerFunctions.stopContainer(container.getContainerName());
        return true;
    }

    public void deleteContainer(Container container) {
        dockerFunctions.deleteContainer(container.getContainerName());
    }

    public void getRunningContainerInfo(Container container) {
        
    }


}
