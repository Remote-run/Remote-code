package no.ntnu.remotecode.master.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.ntnu.remotecode.master.model.enums.ContainerStatus;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "container")
public class Project {


    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    /**
     * The uuid key for this container.
     */
    @JsonbTransient
    @Column(unique = true)
    private UUID projectKey;


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
    @JsonbTransient
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


    /**
     * The last registered time the container was used.
     */
    private Instant lastRegisteredLogon;

    /**
     * The compute node providing resources for this container to run
     */
    @ManyToOne
    @JsonbTransient
    private ComputeNode hostingNode;

    /**
     * The key used to access the vs code instance
     * TODO: this should be hashed probably, but have to check what kind of hashing alg is used in the vs-code server
     */
    private String accessesKey;


}
