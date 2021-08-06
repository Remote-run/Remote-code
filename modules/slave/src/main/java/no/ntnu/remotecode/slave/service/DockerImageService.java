package no.ntnu.remotecode.slave.service;

import no.ntnu.remotecode.master.model.Template;
import no.ntnu.remotecode.slave.docker.DockerEngineInterface;
import no.ntnu.remotecode.slave.docker.command.DockerImageBuildCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class DockerImageService {


    DockerEngineInterface dockerEngineInterface = DockerEngineInterface.getDefault();
    IDockerHostFileSystemInterface hostFs = new HostInteraction();

    private final ConcurrentHashMap<String, Semaphore> buildMap = new ConcurrentHashMap<>();


    public void buildImage(Template template) throws InterruptedException, FileSystemException, FileNotFoundException {
        boolean doImageExist = doImageExistLocally(template.getTemplateImageName());
        if (!doImageExist) {
            if (buildMap.containsKey(template.getTemplateImageName())) {
                Semaphore lock = buildMap.get(template.getTemplateImageName());

                lock.acquire();
                lock.release();

                if (!lock.hasQueuedThreads()) {
                    buildMap.remove(template.getTemplateImageName());
                }
                //                synchronized (lock) {
                //                    lock.wait();
                //                }
            } else {
                Semaphore lock = new Semaphore(0, false);
                buildMap.put(template.getTemplateImageName(), lock);
                buildTemplate(template);
                lock.release();

                //                synchronized (lock) {
                //                    notifyAll();
                //                }
            }
        }
    }

    // todo: improve
    private void buildTemplate(Template template) throws FileSystemException, FileNotFoundException {
        //        File hostTemplateBuildDir = hostFs
        //                .getHostFileLocation(new File(hostFs.getTemplateDirHost(), template.getBuildDirName()));
        File containerTemplateDir = hostFs.getHostFileLocation(new File(hostFs.getTemplateDirContainer(),
                                                                        template.getBuildDirName()));

        File abc = new File(hostFs.getTemplateDirContainer(), template.getBuildDirName());
        DockerImageBuildCommand buildCommand = new DockerImageBuildCommand(template.getTemplateImageName(),
                                                                           abc,
                                                                           new File(abc, "Dockerfile"));

        buildCommand.setBlocking(true);
        buildCommand.run();

    }

    private boolean doImageExistLocally(String imageName) {
        List<String> localImages = dockerEngineInterface.getLocalImageNames();

        return localImages.contains(imageName);
    }

}
