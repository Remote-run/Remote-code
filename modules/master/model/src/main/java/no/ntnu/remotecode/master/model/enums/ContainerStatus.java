package no.ntnu.remotecode.master.model.enums;

/**
 * The different states a container running in the system can have.
 * <p>
 * The reason RUNNING and IDLE are separate states is for load balancing.
 */
public enum ContainerStatus {
    /**
     * the container is requested, but not created yet
     */
    REQUESTED,

    /**
     * The container is currently running, and maintaining some level of cpu usage
     */
    RUNNING,

    /**
     * The container is currently running, but not using a significant amount of cpu
     */
    IDLE,

    /**
     * The container has been turned off because of inactivity
     */
    OFF,

    /**
     * The container has been deleted because of being inactive for too long
     */
    DELETED,

    /**
     * Some error has occurred and the container is unable to operate
     */
    ERROR,
}
