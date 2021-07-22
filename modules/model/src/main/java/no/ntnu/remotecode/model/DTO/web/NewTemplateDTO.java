package no.ntnu.remotecode.model.DTO.web;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class NewTemplateDTO {

    /**
     * The name of the new template
     */
    @NotBlank
    private String templateName;

    /**
     * The link to the github repo to be cloned in the container
     */
    @NotBlank
    private String githubLink;

    /**
     * The string to be converted to the build dockerfile
     */
    @NotBlank
    private List<String> dockerBuildSteps;

}
