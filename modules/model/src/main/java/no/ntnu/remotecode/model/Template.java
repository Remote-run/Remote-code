package no.ntnu.remotecode.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;
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

    /**
     * The uuid key for this container.
     */
    @Column(unique = true)
    private UUID templateKey;


    /**
     * The id of the admin user who created the template.
     */
    @JsonbTransient
    private double creatorId;


    /**
     * The name of the template used.
     */
    private String templateName;

    /**
     * The name of the template dir containing the build docker file
     */
    @JsonbTransient
    private String buildDirName;

    /**
     * The name of the built image used for this template.
     */
    @JsonbTransient
    private String TemplateImageName;


    /**
     * The repo to clone in to the projects built from this template
     */
    private String gitCloneRepo;

}
