package no.ntnu.remotecode.model.enums;

public enum KafkaTopic {

    /**
     * This is where the master relays messages to all the slaves about what they should do.
     */
    CONTAINER_TASK,

    TASK_ACK,

}
