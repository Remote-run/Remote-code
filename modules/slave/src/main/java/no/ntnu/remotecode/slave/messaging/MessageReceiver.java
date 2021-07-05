package no.ntnu.remotecode.slave.messaging;

import no.ntnu.remotecode.model.DTO.kafkamessage.ContainerTask;
import no.ntnu.remotecode.slave.docker.DockerController;

public class MessageReceiver {

    DockerController dockerController = new DockerController();


    public void containerTaskMessage(ContainerTask task) {

        switch (task.getAction()) {
            case CREATE -> dockerController.buildContainer(task.getContainer());
            case START -> dockerController.startContainer(task.getContainer());
            case STOP -> dockerController.stopContainer(task.getContainer());
            case DELETE -> dockerController.deleteContainer(task.getContainer());
        }


    }


}
