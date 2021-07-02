package no.ntnu.remotecode.model;

import no.ntnu.remotecode.model.docker.Container;

import java.time.Instant;

public class RunningInstance {

    private Container container;

    /**
     * The last registered time the container was used.
     */
    private Instant lastRegisteredLogon;

    /**
     * The compute node providing resources for this container to run
     */
    private ComputeNode hostingNode;


}
