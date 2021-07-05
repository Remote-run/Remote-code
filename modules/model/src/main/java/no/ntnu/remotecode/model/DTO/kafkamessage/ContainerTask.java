package no.ntnu.remotecode.model.DTO.kafkamessage;

import lombok.Data;
import no.ntnu.remotecode.model.docker.Container;
import no.ntnu.remotecode.model.enums.ContainerAction;


/**
 * Messages from the master to the slaves about a change to preform on a container.
 */
@Data
public class ContainerTask {

    private Container container;

    private ContainerAction action;
}
