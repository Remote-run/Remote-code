package no.ntnu.remotecode.slave.docker;


import no.ntnu.remotecode.slave.docker.command.DockerGenericCommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class DockerEngineInterfaceImpl implements DockerEngineInterface {


    /**
     * Returns a list of the names fot the images in the local docker repository
     *
     * @return the names of the images in the local docker repo
     */
    @Override
    public List<String> getLocalImageNames() {
        ArrayList<String> idList = new ArrayList<>();

        DockerGenericCommand command = new DockerGenericCommand("docker image ls --format \"{{.Repository}}\"");

        command.setBlocking(true);
        command.setKeepOutput(true);
        Process process = command.run();


        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                idList.add(line);
            }
            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idList;
    }


    @Override
    public void stopContainer(String containerName) {
        DockerGenericCommand command = new DockerGenericCommand(String.format("docker container stop %s",
                                                                              containerName));
        command.run();
    }

    /**
     * Deletes the container with the provided name from the local docker repo
     *
     * @param containerName the name of the container to delete
     */
    @Override
    public void deleteContainer(String containerName) {
        DockerGenericCommand command = new DockerGenericCommand(String.format("docker container rm %s", containerName));
        command.run();
    }

    /**
     * Deletes the image with the provided name from the local docker repo
     *
     * @param containerImage
     */
    @Override
    public void deleteImage(String containerImage) {
        DockerGenericCommand command = new DockerGenericCommand(String.format("docker image rm %s", containerImage));
        command.run();
    }

    /**
     * Starts the container with the provided name if it exists.
     * returns {@code true} if the container exists and started sucsessfully returns {@code false} if not
     *
     * @param containerName the name of the container to delete
     * @return {@code true} if sucsess {@code false} if failure
     */
    @Override
    public boolean startContainer(String containerName) {
        DockerGenericCommand command = new DockerGenericCommand(String.format("docker contaner start %s",
                                                                              containerName));
        Process process  = command.run();
        int     exitCode = 1;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException ignored) {

        }
        return exitCode == 0;
    }
}
