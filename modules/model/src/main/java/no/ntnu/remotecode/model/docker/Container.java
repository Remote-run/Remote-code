package no.ntnu.remotecode.model.docker;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.ntnu.remotecode.model.Template;
import no.ntnu.remotecode.model.enums.ContainerStatus;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "container")
public class Container {


    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private double Id;

    /**
     * The template used to create this container.
     */
    @ManyToOne
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
    @Enumerated(EnumType.STRING)
    private ContainerStatus containerStatus;

    /**
     * The name of the directory where containers active data is stored.
     */
    private String dataDirName;


}
