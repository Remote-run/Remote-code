package no.ntnu.remotecode.slave.docker.command;

import no.ntnu.remotecode.slave.docker.Interface.IDockerHostFileSystemInterface;

import java.io.File;

public class HostInteraction implements IDockerHostFileSystemInterface {

    private static final String nfsDirHostPath = "/home/trygve/Development/sommer_last/Remote-code/NFS_SHARED_FILE/";
    private static final String containerNfsMountPoint = "/home/trygve/Development/sommer_last/Remote-code/NFS_SHARED_FILE/";
    //    private static String containerNfsMountPoint = "/remote_code_files/";

    private static final String containerDirName = "container_dirs";
    private static final String templateDirName = "templates";


    @Override
    public File getHostFileLocation(File localFile) {
        String absPath = localFile.getAbsolutePath();
        String subPath = absPath.substring(containerNfsMountPoint.length()); // ehhh this is not gr8

        return new File(nfsDirHostPath, subPath);
    }

    @Override
    public File getContainerDataDirHost() {
        return new File(nfsDirHostPath, containerDirName);
    }

    @Override
    public File getTemplateDirHost() {
        return new File(nfsDirHostPath, templateDirName);
    }

    @Override
    public File getContainerDataDirContainer() {
        return new File(containerNfsMountPoint, containerDirName);
    }

    @Override
    public File getTemplateDirContainer() {
        return new File(containerNfsMountPoint, templateDirName);
    }


}
