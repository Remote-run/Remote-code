package no.ntnu.remotecode.master.model.enums;

public enum TaskStatus {

    /**
     * The task is created but not sent
     */
    NOT_SENT,

    /**
     * The task is recived on a node
     */
    RECEIVED,

    /**
     * The task is currently working
     */
    WORKING,

    /**
     * The task is complete
     */
    COMPLETE,

    /**
     * Some error has occurred
     */
    ERROR

}
