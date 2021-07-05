package no.ntnu.remotecode.slave.docker.Interface;

import no.ntnu.remotecode.model.docker.Container;

public interface DockerInterface {

    /**
     * Starts an instance of the provided {@link Container}.
     * The container has been prevously run and stopped, it wil be started again.
     * If the container havent been run on this node before it wil be built.
     *
     * @param container the container to start
     * @return {@code true} if the start is sucsessfull {@code false} if not.
     */
    boolean startContainer(Container container);


    boolean stopContainer(Container container);
}
