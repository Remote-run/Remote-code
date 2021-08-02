package no.ntnu.remotecode.master.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import no.ntnu.remotecode.master.model.enums.TemplateStatus;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.UUID;

/**
 * Represents an template for creating new containers.
 */

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "container_template")
public class Template {


    @JsonbTransient
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(nullable = false)
    @JsonbTransient
    @Enumerated(EnumType.STRING)
    private TemplateStatus templateStatus;


    /**
     * The uuid key for this container.
     */
    @Column(unique = true, nullable = false)
    private UUID templateKey;


    /**
     * The id of the admin user who created the template.
     */
    @JsonbTransient
    @Column(nullable = false)
    private double creatorId;


    /**
     * The name of the template used.
     */
    private String templateName;

    /**
     * The name of the template dir containing the build docker file
     */
    private String buildDirName;

    /**
     * The name of the built image used for this template.
     */
    private String TemplateImageName;


    /**
     * The repo to clone in to the projects built from this template
     */
    private String gitCloneRepo;

}
