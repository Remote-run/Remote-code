package no.ntnu.remotecode.master.model.enums;

public enum ContainerAction {

    /**
     * Request that the container in question should be started on a node.
     * If the container does not exist on the node it should be created
     */
    START,

    /**
     * Stops but does not delete the container in question from a node
     */
    STOP,

    DELETE,
}
