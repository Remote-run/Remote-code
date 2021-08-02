package no.ntnu.remotecode.slave.docker.Interface;

import java.util.HashMap;
import java.util.List;

public interface IDockerBasicFunctions {

    /**
     * Returns a list of the names fot the images in the local docker repository
     *
     * @return the names of the images in the local docker repo
     */
    List<String> getLocalImageNames();

    /**
     * Returns a map with all the current containers names and wether or not they are running.
     *
     * @return a map with all the current containers names and wether or not they are running
     */
    HashMap<String, Boolean> getContainerStatuses();


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

    /**
     * Starts the container with the provided name if it exists.
     * returns {@code true} if the container exists and started sucsessfully returns {@code false} if not
     *
     * @param containerName the name of the container to delete
     * @return {@code true} if sucsess {@code false} if failure
     */
    boolean startContainer(String containerName);
}
