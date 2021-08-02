package no.ntnu.remotecode.master.model.DTO.kafkamessage;

import no.ntnu.remotecode.master.model.enums.ContainerStatus;

/**
 * Message from slaves -> master, containing info about container status cpu/ram usage.
 */
public class ContainerResouceStatus {

    private String containerName;

    private ContainerStatus containerResouceStatus;

    private float cpuUsage;

    private float ramUsage;
}
