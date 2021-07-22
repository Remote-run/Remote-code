package no.ntnu.remotecode.slave.docker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DockerFileBuilder {

    private String sourceImage;


    private List<String> lines;

    private final List<String> allowedCommandTypes = List.of("RUN", "ENV");

    public DockerFileBuilder(String sourceImage) {
        this.sourceImage = sourceImage;
    }

    public DockerFileBuilder addLines(List<String> lines) {

        lines.forEach(cmdLine -> {
            if (allowedCommandTypes.stream().anyMatch(cmdLine::startsWith)) {
                lines.add(cmdLine);
            }
        });
        return this;
    }

    public DockerFileBuilder addRunLine(String command) {
        String runCommand = (command.startsWith("RUN ")) ? command : "RUN " + command;

        lines.add(runCommand);
        return this;
    }

    public DockerFileBuilder addEnvLine(String command) {
        String envCommand = (command.startsWith("ENV ")) ? command : "ENV " + command;

        lines.add(envCommand);
        return this;
    }

    public File writeToFile(File file) throws IOException {

        if (file.exists()) {
            boolean suc = file.delete();
            if (! suc) {
                throw new IOException();
            }
        }


        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

        String fromCommand = String.format("FROM %s", sourceImage);

        bufferedWriter.write(fromCommand);
        bufferedWriter.newLine();

        for (String commandLine : lines) {
            bufferedWriter.write(commandLine);
            bufferedWriter.newLine();
        }

        bufferedWriter.flush();
        bufferedWriter.close();

        return file;
    }

}
