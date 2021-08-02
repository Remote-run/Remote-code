package no.ntnu.remotecode.master.model;

import lombok.Data;
import no.ntnu.remotecode.master.model.enums.ContainerAction;
import no.ntnu.remotecode.master.model.enums.TaskStatus;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;


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
