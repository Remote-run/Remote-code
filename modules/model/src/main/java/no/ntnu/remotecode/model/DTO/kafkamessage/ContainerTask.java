package no.ntnu.remotecode.model.DTO.kafkamessage;

import lombok.Data;
import no.ntnu.remotecode.model.Project;
import no.ntnu.remotecode.model.enums.ContainerAction;


/**
 * Messages from the master to the slaves about a change to preform on a container.
 */
@Data
public class ContainerTask {

    private Project project;

    private ContainerAction action;
}
