package no.ntnu.remotecode.slave.model;


/**
 * Represents an template for creating new containers.
 */
public class Template {

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
