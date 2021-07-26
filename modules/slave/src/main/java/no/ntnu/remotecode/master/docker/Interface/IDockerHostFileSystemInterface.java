package no.ntnu.remotecode.master.docker.Interface;

import java.io.File;

public interface IDockerHostFileSystemInterface {

    File getHostFileLocation(File localFile);


    File getContainerDataDirHost();

    File getTemplateDirHost();

    File getContainerDataDirContainer();

    File getTemplateDirContainer();

}
