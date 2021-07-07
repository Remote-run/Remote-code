package no.ntnu.remotecode.model.DTO.kafkamessage;

/**
 * Message from slaves -> master, containing info about container status cpu/ram usage.
 */
public class ContainerStatus {

    private String containerName;

    private ContainerStatus containerStatus;

    private float cpuUsage;

    private float ramUsage;
}
