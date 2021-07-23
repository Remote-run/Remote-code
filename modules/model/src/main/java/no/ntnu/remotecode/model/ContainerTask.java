package no.ntnu.remotecode.model;

import lombok.Data;
import no.ntnu.remotecode.model.ComputeNode;
import no.ntnu.remotecode.model.Project;
import no.ntnu.remotecode.model.enums.ContainerAction;
import no.ntnu.remotecode.model.enums.TaskStatus;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.UUID;


/**
 * Messages from the master to the slaves about a change to preform on a container.
 */
@Data
@Entity
public class ContainerTask {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Project project;

    @Enumerated(EnumType.STRING)
    private ContainerAction action;

    @JsonbTransient
    private long receiverId;

    @JsonbTransient
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
}
