package no.ntnu.remotecode.slave.docker.Interface;

import java.util.List;

public interface IDockerBasicFunctions {

    /**
     * Returns a list of the names fot the images in the local docker repository
     *
     * @return the names of the images in the local docker repo
     */
    List<String> getLocalImageNames();

    void stopContainer(String containerName);

    /**
     * Deletes the container with the provided name from the local docker repo
     *
     * @param containerName the name of the container to delete
     */
    void deleteContainer(String containerName);

    /**
     * Deletes the image with the provided name from the local docker repo
     *
     * @param containerImage
     */
    void deleteImage(String containerImage);
}
