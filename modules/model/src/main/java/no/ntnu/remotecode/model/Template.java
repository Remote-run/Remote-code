package no.ntnu.remotecode.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

/**
 * Represents an template for creating new containers.
 */

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "container_template")
public class Template {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private double Id;

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


    private String gitCloneRepo;


    private String templateLink;

}
