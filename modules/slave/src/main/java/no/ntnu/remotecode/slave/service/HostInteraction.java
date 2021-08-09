package no.ntnu.remotecode.slave.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class HostInteraction implements IDockerHostFileSystemInterface {

    private static String nfsDirHostPath = "";
    private static String containerNfsMountPoint = "/application_storage";

    private static final String containerDirName = "container_dirs";
    private static final String templateDirName = "templates";

    private static final String envVarNameContainerNfsPath = "HOST_NFS_PATH";

    @Override
    public File getHostFileLocation(File localFile) {

        nfsDirHostPath = getHostNfsPathFromEnv();
        String absPath = localFile.getAbsolutePath();
        String subPath = absPath.substring(containerNfsMountPoint.length()); // ehhh this is not gr8

        return new File(nfsDirHostPath, subPath);
    }

    private String getHostNfsPathFromEnv() {
        Map<String, String> env = System.getenv();

        String hopefullyValue = env.get(envVarNameContainerNfsPath);

        if (hopefullyValue != null) {
            return hopefullyValue;
        } else {
            throw new RuntimeException("HOST NFS PATH NOT PRESENT");
        }

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
