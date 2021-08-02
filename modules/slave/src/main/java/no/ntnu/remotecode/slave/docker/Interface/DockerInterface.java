package no.ntnu.remotecode.slave.docker.Interface;

import no.ntnu.remotecode.master.model.Project;

public interface DockerInterface {

    /**
     * Starts an instance of the provided {@link Project}.
     * The container has been prevously run and stopped, it wil be started again.
     * If the container havent been run on this node before it wil be built.
     *
     * @param project the container to start
     * @return {@code true} if the start is sucsessfull {@code false} if not.
     */
    boolean startContainer(Project project);


    boolean stopContainer(Project project);
}
