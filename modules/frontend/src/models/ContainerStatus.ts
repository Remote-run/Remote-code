/**
 * The different states a container running in the system can have.
 * <p>
 * The reason RUNNING and IDLE are separate states is for load balancing.
 */
export enum ContainerStatus {

  /**
   * the container is requested, but not created yet
   */
  REQUESTED = 'REQUESTED',

  /**
   * The container is currently running, and maintaining some level of cpu usage
   */
  RUNNING = 'RUNNING',

  /**
   * The container is currently running, but not using a significant amount of cpu
   */
  IDLE = 'IDLE',

  /**
   * The container has been turned off because of inactivity
   */
  OFF = 'OFF',

  /**
   * The container has been deleted becuse of beeing inactive for too long
   */
  DELETED = 'DELETED',
}
