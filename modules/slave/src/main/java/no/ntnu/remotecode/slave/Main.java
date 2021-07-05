package no.ntnu.remotecode.slave;

//import util.properties packages

//import simple producer packages

import com.google.gson.Gson;
import no.ntnu.remotecode.model.Template;
import no.ntnu.remotecode.model.docker.Container;
import no.ntnu.remotecode.model.enums.ContainerStatus;
import no.ntnu.remotecode.slave.messaging.KFConsumer;
import no.ntnu.remotecode.slave.messaging.KFProducer;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//import KafkaProducer packages

//import ProducerRecord packages


public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        List<String>             topics   = List.of("new_tasks");
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> KFProducer.run(topics), 10, TimeUnit.SECONDS);
        KFConsumer.run(topics);

        //                KFProducer.run(topics);
        //                System.out.println(ConsumerConfig.configDef().toEnrichedRst());


        //        manualDeployTest();
    }


    private static void manualDeployTest() {
        Template template = new Template();

        template.setCreatorId(1);
        template.setTemplateName("test-manual");
        template.setBuildDirName("test_tf_dir");
        template.setTemplateImageName("testi-imagename");
        template.setGitCloneRepo("https://github.com/Remote-run/remote-code-tf-test.git");

        Container container = new Container();

        container.setContainerTemplate(template);
        container.setContainerName("abc-test");
        container.setContainerOwnerId(2);
        container.setContainerStatus(ContainerStatus.REQUESTED);
        container.setDataDirName("test-dir-name");


        Gson gson = new Gson();

        System.out.println(gson.toJson(container));
        return;

        //        DockerContainerService containerService = new DockerContainerService();
        //
    }
}

/*
{"Id":0.0,"containerTemplate":{"Id":0.0,"creatorId":1.0,"templateName":"test-manual","buildDirName":"test_tf_dir","TemplateImageName":"testi-imagename","gitCloneRepo":"https://github.com/Remote-run/remote-code-tf-test.git"},"containerName":"abc-test","containerOwnerId":2.0,"containerStatus":"REQUESTED","dataDirName":"test-dir-name"}

 */
