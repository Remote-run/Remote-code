package no.ntnu.remotecode.slave;

//import util.properties packages

//import simple producer packages


import no.ntnu.remotecode.slave.messaging.MessageReceiver;
import no.ntnu.remotecode.master.model.Template;
import no.ntnu.remotecode.master.model.Project;
import no.ntnu.remotecode.master.model.enums.ContainerStatus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//import KafkaProducer packages

//import ProducerRecord packages


public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        //        List<String>             topics   = List.of("new_tasks");
        //        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        //        executor.schedule(() -> KFProducer.run(topics), 10, TimeUnit.SECONDS);
        //        KFConsumer.run(topics);

        //                KFProducer.run(topics);
        //                System.out.println(ConsumerConfig.configDef().toEnrichedRst());

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        MessageReceiver receiver        = new MessageReceiver(executorService);

        receiver.run();

    }


    private static void manualDeployTest() {
        Template template = new Template();

        template.setCreatorId(1);
        template.setTemplateName("test-manual");
        template.setBuildDirName("test_tf_dir");
        template.setTemplateImageName("testi-imagename");
        template.setGitCloneRepo("https://github.com/Remote-run/remote-code-tf-test.git");

        Project project = new Project();

        project.setContainerTemplate(template);
        project.setContainerName("abc-test");
        project.setContainerOwnerId(2);
        project.setContainerStatus(ContainerStatus.REQUESTED);
        project.setDataDirName("test-dir-name");


        //        DockerContainerService containerService = new DockerContainerService();
        //
    }
}

/*
{"Id":0.0,"containerTemplate":{"Id":0.0,"creatorId":1.0,"templateName":"test-manual","buildDirName":"test_tf_dir","TemplateImageName":"testi-imagename","gitCloneRepo":"https://github.com/Remote-run/remote-code-tf-test.git"},"containerName":"abc-test","containerOwnerId":2.0,"containerStatus":"REQUESTED","dataDirName":"test-dir-name"}

 */
