package no.ntnu.remotecode.slave.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

    public static final File executionDir = new File("").getAbsoluteFile();
    public static final File systemTmpDir = new File(System.getProperty("java.io.tmpdir"));

    /**
     * Cheks whther or not the given file is a child of the given directory.
     * if ether is null false is returned
     *
     * @param file   The file to check if is in the directory
     * @param parent The dir to check if the file is in
     * @return true if the file is in any of the dirs sub directorys
     */
    public static boolean isFileChildOfDir(File file, File parent) {
        boolean isChild = false;
        try {
            if (file.getCanonicalPath().startsWith(parent.getCanonicalPath() + File.separator)) {
                isChild = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isChild;
    }

    /**
     * Deletes a directory recursivly, symbolic links are not followed
     *
     * @param file The dir to delete.
     */
    public static void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (!Files.isSymbolicLink(f.toPath())) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
    }

    /**
     * Utility method for returning a file with one less ending than the one provided, that is:
     * File(/bip/bop/abc.tar.gz) -> File(/bip/bop/abc.tar)
     * <p>
     * If the file provided have no ending the input object is returned
     *
     * @param file the file to remove the ending from
     * @return the file object representing a file with one less ending
     */
    public static File getFileWithOneLessEnding(File file) {
        int lastDot = file.getAbsolutePath().lastIndexOf(".");
        if (lastDot > 0) {
            return new File(file.getAbsolutePath().substring(0, lastDot));
        } else {
            return file;
        }

    }
}
