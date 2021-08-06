package no.ntnu.remotecode.slave.docker;

import no.ntnu.remotecode.slave.DebugLogger;
import no.ntnu.remotecode.slave.docker.command.DockerGenericCommand;
import no.ntnu.remotecode.slave.docker.container.DockerContainerState;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public class DockerContainerStatusWatcherImpl implements DockerContainerStatusWatcher {

    private static DockerContainerStatusWatcherImpl instance;

    public static DebugLogger dbl = new DebugLogger(true);

    public static DockerContainerStatusWatcherImpl getInstance() {
        if (instance == null) {
            instance = new DockerContainerStatusWatcherImpl();
        }
        return instance;
    }

    private DockerContainerStatusWatcherImpl() {
        scheduledExecutorService.scheduleAtFixedRate(this::refreshContainerStatuses, 0, 2, TimeUnit.SECONDS);
    }

    private Lock stateLock = new ReentrantLock();
    private Lock onChangeLock = new ReentrantLock();

    private Map<String, DockerContainerState> containerStates = new HashMap<>();

    private Map<String, Function<DockerContainerState, Boolean>> onChangeMap = new HashMap<>();


    private ExecutorService executorService = Executors.newCachedThreadPool();
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();


    /**
     * Returns the state of the container with the given name
     *
     * @param containerName The name of the container to chek
     * @return The state of the container with the given name.
     */
    @Override
    public DockerContainerState getContainerState(String containerName) {
        DockerContainerState ret;
        try {
            stateLock.lock();
            ret = containerStates.get(containerName);
        } finally {
            stateLock.unlock();
        }

        return (ret == null) ? DockerContainerState.NOT_FOUND : ret;
    }

    /**
     * Register a function to be run when the system registers a change in the state of the
     * container with the provided name.
     * <p>
     * The {@code onStateChange} function recives  new container state and returns {@code true}
     * it should keep watching for changes or {@code false} if not.
     *
     * @param containerName  The name of the container to watch
     * @param onStateChanged The function to run when the state changes
     */
    @Override
    public void onStateChange(
            String containerName, Function<DockerContainerState, Boolean> onStateChanged) {
        dbl.log("new state change watcher added for ", containerName);
        try {
            onChangeLock.lock();
            onChangeMap.put(containerName, onStateChanged);
        } finally {
            onChangeLock.unlock();
        }
    }

    private void refreshContainerStatuses() {
        Map<String, DockerContainerState> newStates = this.getCurrentContainersState();

        try {
            stateLock.lock();
            onChangeLock.lock();

            // for each of the containers we are asked to watch
            onChangeMap.keySet().parallelStream().forEach(containerName -> {
                // if the new values are different then the old ones
                // note this works with the null values of new keys
                if (newStates.get(containerName) != containerStates.get(containerName)) {
                    dbl.log("found state change in ",
                            containerName,
                            " old state ",
                            containerStates.get(containerName),
                            "new state is ",
                            newStates.get(containerName));
                    DockerContainerState newState = newStates.get(containerName);
                    triggerContainerOnChange(containerName,
                                             newState == null ? DockerContainerState.NOT_FOUND : newState);
                }
            });

            containerStates = newStates;

        } finally {
            onChangeLock.unlock();
            stateLock.unlock();
        }
    }

    private void triggerContainerOnChange(String containerName, DockerContainerState containerState) {
        // if a change is detected submitt the on change task
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> onChangeMap.get(containerName)
                                                                                           .apply(containerState),
                                                                          executorService);
        // if the task is done watching remove it from the watching map
        future.thenAcceptAsync(continueWatching -> {
            if (!continueWatching) {
                removeOnStateChange(containerName);
            }
        }, executorService);
    }

    private void removeOnStateChange(String containerName) {
        dbl.log("state change watcher removed for ", containerName);
        try {
            onChangeLock.lock();
            onChangeMap.remove(containerName);
        } finally {
            onChangeLock.unlock();
        }
    }


    private HashMap<String, DockerContainerState> getCurrentContainersState() {
        HashMap<String, DockerContainerState> statusMap = new HashMap<>();
        DockerGenericCommand command = new DockerGenericCommand(
                "docker container ls -a --format \"{{.Names}}:{{.Status}}\"");

        command.setBlocking(true);
        command.setKeepOutput(true);
        Process process = command.run();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] nameStatusPair = line.split(":");

                if (nameStatusPair.length < 2) {
                    // todo: this means docker error, shold be handeled
                } else {
                    String  containerName   = nameStatusPair[0];
                    String  containerStatus = nameStatusPair[1];
                    boolean isUp            = containerStatus.startsWith("Up");
                    statusMap.put(containerName, isUp ? DockerContainerState.RUNNING : DockerContainerState.OFF);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statusMap;
    }
}
