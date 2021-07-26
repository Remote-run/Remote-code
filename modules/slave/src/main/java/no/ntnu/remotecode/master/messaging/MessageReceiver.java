package no.ntnu.remotecode.master.messaging;

import no.ntnu.remotecode.model.ContainerTask;
import no.ntnu.remotecode.master.ContainerManager;

public class MessageReceiver {

    ContainerManager containerManager = new ContainerManager();


    public void containerTaskMessage(ContainerTask task) {

        switch (task.getAction()) {
            case START -> containerManager.startContainer(task.getProject());
            case STOP -> containerManager.stopContainer(task.getProject());
            case DELETE -> containerManager.deleteContainer(task.getProject());
        }


    }


}
