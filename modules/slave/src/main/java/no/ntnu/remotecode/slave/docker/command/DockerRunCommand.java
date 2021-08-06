package no.ntnu.remotecode.slave.docker.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The default run command for a container. Can be configured
 */
public class DockerRunCommand extends SystemCommand {

    private final HashMap<String, String> volumes = new HashMap<>();
    private final HashMap<String, String> envVariables = new HashMap<>();
    private final HashMap<String, String> labels = new HashMap<>();

    private List<String> resourceAllocationParts;
    private String image;
    private final String containerName;
    private String network;

    private boolean detached = false;

    private boolean rmWhenStopped = true;

    /**
     * Creates a new runCommand
     *
     * @param image         the image to build from
     * @param containerName the name to give the container while running
     */
    public DockerRunCommand(String image, String containerName) {
        this.image = image;
        this.containerName = containerName;
        this.network = "bridge";
    }


    /**
     * Sets whether or not the container will be detached when run.
     *
     * @param detached whether or not the container will be detached when run.
     */
    public void setDetached(boolean detached) {
        this.detached = detached;
    }


    /**
     * Adds a volume to the run command.
     *
     * @param from the absolute path on the host machine or the volume to bind from
     * @param to   the absolute path on the container to mount the from dir/volume
     */
    public void addVolume(String from, String to) {
        volumes.put(from, to);
    }

    /**
     * Adds an environment variable to the run command
     *
     * @param varName  the name of the env variable to set
     * @param varValue the value the the env variable to set
     */
    public void addEnvVariable(String varName, String varValue) {
        envVariables.put(varName, varValue);
    }

    /**
     * Sets the network used by the container.
     *
     * @param network The network used by the container.
     */
    public void setNetwork(String network) {
        this.network = network;
    }


    /**
     * Sets the resource allocation parts of the command
     *
     * @param commandParts the resource allocation parts of the command to set.
     */
    public void setResourceAllocationParts(List<String> commandParts) {
        this.resourceAllocationParts = commandParts;
    }


    /**
     * Sets the image to build from.
     *
     * @param image The image to build from.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Sets whether or not to rm the container tmp volumes when stopped
     * (default: <code>true</code>:
     *
     * @param rmWhenStopped
     */
    public void setRmWhenStopped(boolean rmWhenStopped) {
        this.rmWhenStopped = rmWhenStopped;
    }

    /**
     * Adds a label to set when running the container. if the label value is <code>null</code>
     * an empty label string will be substituted.
     *
     * @param labelKey   The key of the label to add.
     * @param labelValue The value to associate with the key
     */
    public void addContainerLabel(String labelKey, String labelValue) {
        this.labels.put(labelKey, labelValue);
    }

    /**
     * Builds the docker command and returns a list of the command parts.
     *
     * @return A list of all the command parts for the command to run.
     */
    protected ArrayList<String> buildCommand() {
        ArrayList<String> commandParts = new ArrayList<>();

        commandParts.add("docker");
        commandParts.add("container run");

        if (this.rmWhenStopped) {
            commandParts.add("--rm");

        }

        commandParts.add("--name " + this.containerName);

        if (this.detached) {
            if (super.isBlocking() || super.isKeepError() || super.isKeepOutput()) {
                throw new RuntimeException("Container set to keep operate on output but is set as blocking");
            } else {
                commandParts.add("-d");
            }
        }


        // compute resources
        if (this.resourceAllocationParts != null) {
            commandParts.addAll(this.resourceAllocationParts);
        }


        // network
        if (network != null) {
            commandParts.add("--net=" + this.network);
        }

        // volumes
        for (Map.Entry<String, String> volume : this.volumes.entrySet()) {
            commandParts.add(String.format("-v %s:%s", volume.getKey(), volume.getValue()));
        }

        // env vars
        for (Map.Entry<String, String> var : this.envVariables.entrySet()) {
            commandParts.add(String.format("-e %s=\"%s\"", var.getKey(), var.getValue()));
        }

        // labels
        for (Map.Entry<String, String> label : this.labels.entrySet()) {
            if (label.getValue() == null) {
                commandParts.add(String.format("-l %s", label.getKey()));
            } else {
                commandParts.add(String.format("-l %s=\"%s\"", label.getKey(), label.getValue()));
            }
        }

        commandParts.add(this.image);

        return commandParts;
    }


}
