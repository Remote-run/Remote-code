package no.ntnu.remotecode.model;


import lombok.Data;
import lombok.NoArgsConstructor;

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
    private double creatorId;


    /**
     * The name of the template used.
     */
    private String templateName;

    /**
     * The docker file to build new template instances from.
     */
    private String buildFileName;


}
