package no.ntnu.remotecode.model;

import no.ntnu.remotecode.model.enums.ContainerStatus;

import java.time.Instant;

public class Container {

    /**
     * The template used to create this container.
     */
    private Template containerTemplate;

    /**
     * The name of the container.
     * This is used in the url to reach it.
     */
    private String containerName;

    /**
     * The user id of the user who created the container.
     */
    private double containerOwnerId;

    /**
     * The current run status of the container.
     */
    private ContainerStatus containerStatus;

    /**
     * The name of the directory where containers active data is stored.
     */
    private String dataDirName;


}
