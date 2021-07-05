package no.ntnu.remotecode.slave.messaging;

import no.ntnu.remotecode.model.DTO.kafkamessage.ContainerTask;
import no.ntnu.remotecode.slave.ContainerManager;

public class MessageReceiver {

    ContainerManager containerManager = new ContainerManager();


    public void containerTaskMessage(ContainerTask task) {

        switch (task.getAction()) {
            case START -> containerManager.startContainer(task.getContainer());
            case STOP -> containerManager.stopContainer(task.getContainer());
            case DELETE -> containerManager.deleteContainer(task.getContainer());
        }


    }


}
