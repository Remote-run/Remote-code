package no.ntnu.remotecode.master.control;

import no.ntnu.remotecode.master.model.ComputeNode;
import no.ntnu.remotecode.master.model.ContainerTask;
import no.ntnu.remotecode.master.model.Project;
import no.ntnu.remotecode.master.model.enums.ContainerAction;
import no.ntnu.remotecode.master.model.enums.ContainerStatus;
import no.ntnu.remotecode.master.model.enums.TaskStatus;
import no.ntnu.remotecode.master.kafka.MasterMessagingService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Singleton
public class ContainerStateManager {


    @Inject
    MasterMessagingService messagingService;

    @PersistenceContext
    EntityManager entityManager;


    //    private static final String FIND_LEAST_POPULATED_NODE = "select proj.hostingNode.id, count(proj) from Project as proj where proj.containerStatus = :stat group by po.hostingNode.id order by count(proj)";

    private static final String FIND_LEAST_POPULATED_NODE = "SELECT computenode.id, count(computenode.id)\n" + "FROM computenode \n" + String.format(
            "left join (SELECT * from container where container.containerstatus='%s') c\n",
            ContainerStatus.RUNNING) + " on computenode.id = c.hostingnode_id group by computenode.id order by count(computenode.id)";


    List<ComputeNode> workerNodes = new ArrayList<>();

    //TODO: replace with a node registering system
    @PostConstruct
    void initNodes() {
        Optional<ComputeNode> mabyNode = Optional.ofNullable(entityManager.find(ComputeNode.class, 1L));

        if (mabyNode.isEmpty()) {
            var a = new ComputeNode();
            a.setId(1);
            a.setNumGpus(1);
            entityManager.persist(a);
        }

    }


    public Optional<ComputeNode> getLeastPopulatedNode() {
        try {

            List<Object[]> shittySolution = (List<Object[]>) entityManager.createNativeQuery(FIND_LEAST_POPULATED_NODE)
                                                                          .getResultList();

            if (shittySolution.size() == 0) {
                return Optional.empty();
            }

            Integer key                 = (Integer) shittySolution.get(0)[0];
            Long    key2electricBogaloo = Long.valueOf(key);

            return Optional.ofNullable(entityManager.find(ComputeNode.class, key2electricBogaloo));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }


    }

    public void RequestContainerAction(Project project, ContainerAction containerAction) {

        //TODO: if no nodes are pressent the system shold idle untill a new node registers
        ComputeNode node = getLeastPopulatedNode().get();

        ContainerTask newTask = new ContainerTask();

        newTask.setProject(project);
        newTask.setAction(containerAction);
        newTask.setReceiverId(node.getId());
        newTask.setTaskStatus(TaskStatus.NOT_SENT);

        entityManager.persist(newTask);
        entityManager.flush();

        messagingService.sendTask(newTask);
    }

}
