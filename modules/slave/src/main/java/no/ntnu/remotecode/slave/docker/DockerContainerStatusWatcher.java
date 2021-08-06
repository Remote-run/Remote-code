package no.ntnu.remotecode.slave.docker;

import no.ntnu.remotecode.slave.docker.container.DockerContainerState;

import java.util.function.Function;

public interface DockerContainerStatusWatcher {

    static DockerContainerStatusWatcher defaultInstanceInstance() {
        return DockerContainerStatusWatcherImpl.getInstance();
    }

    /**
     * Returns the state of the container with the given name
     *
     * @param containerName The name of the container to chek
     * @return The state of the container with the given name.
     */
    DockerContainerState getContainerState(String containerName);

    /**
     * Register a function to be run when the system registers a change in the state of the
     * container with the provided name.
     * <p>
     * The {@code onStateChange} function recives  new container state and returns {@code true}
     * it should keep watching for changes or {@code false} if not.
     *
     * @param containerName  The name of the container to watch
     * @param onStateChanged The function to run when the state changes
     */
    void onStateChange(String containerName, Function<DockerContainerState, Boolean> onStateChanged);

}
