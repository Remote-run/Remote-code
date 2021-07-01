package no.ntnu.remotecode.slave.model.enums;

/**
 * The different states a container running in the system can have.
 * <p>
 * The reason RUNNING and IDLE are separate states is for load balancing.
 */
public enum ContainerStatus {
    /**
     * the container is requested, but is currnetly under construction
     */
    BUILDING,

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
     * The container has been deleted becuse of beeing inactive for too long
     */
    DELETED,
}
