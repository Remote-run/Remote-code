package no.ntnu.remotecode.master.control;

import lombok.extern.java.Log;
import no.ntnu.remotecode.master.model.ContainerTask;
import no.ntnu.remotecode.master.model.DTO.kafkamessage.TaskAck;
import no.ntnu.remotecode.master.model.Project;
import no.ntnu.remotecode.master.model.enums.ContainerStatus;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Log
@Transactional
public class KafkaReceiverService {


    @PersistenceContext
    EntityManager entityManager;

    public void handleAckMessage(TaskAck taskAck) {
        log.info("Handeling ack message");
        ContainerTask task = entityManager.find(ContainerTask.class, taskAck.getId());

        if (task == null) {
            throw new RuntimeException("KAFKA MESSAGE ERROR");
        }


        log.info("Task old state: " + task.getTaskStatus());
        Project project = task.getProject();

        switch (taskAck.getTaskStatus()) {
            case NOT_SENT:
            case WORKING:
            case RECEIVED:
                break;
            case COMPLETE:
                switch (task.getAction()) {
                    case START:
                        project.setContainerStatus(ContainerStatus.RUNNING);
                        break;
                    case STOP:
                        project.setContainerStatus(ContainerStatus.OFF);
                        break;
                    case DELETE:
                        project.setContainerStatus(ContainerStatus.DELETED);
                        break;
                }
                break;
            case ERROR:
                project.setContainerStatus(ContainerStatus.ERROR);
                break;
        }

        entityManager.persist(project);

        task.setTaskStatus(taskAck.getTaskStatus());
        entityManager.persist(task);

        log.info("Task new state: " + task.getTaskStatus());

    }

}
