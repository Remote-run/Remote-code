package no.ntnu.remotecode.master;


import no.ntnu.remotecode.model.Project;
import no.ntnu.remotecode.master.docker.Interface.DockerInterface;
import no.ntnu.remotecode.master.docker.Interface.IDockerBasicFunctions;
import no.ntnu.remotecode.master.docker.command.DockerFunctions;
import no.ntnu.remotecode.master.docker.service.DockerContainerService;

/*
1. starte co rutine som sjekker status på kjørende kontainere og cpu bruk osv.

 */
public class ContainerManager implements DockerInterface {

    public static DebugLogger dbl = new DebugLogger(true);
   
    private final DockerContainerService containerService = new DockerContainerService();
    private final IDockerBasicFunctions dockerFunctions = new DockerFunctions();

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
        dockerFunctions.deleteContainer(project.getContainerName());
        return true;
    }

    public void getRunningContainerInfo(Project project) {

    }


}
