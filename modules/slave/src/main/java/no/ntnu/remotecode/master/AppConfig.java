package no.ntnu.remotecode.master;

import lombok.Getter;

public class AppConfig {

    private static AppConfig instance = getInstance();

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    private AppConfig() {
    }

    @Getter
    private final String workerID = "1";

    @Getter
    private final String masterUrl = "kafka:9092";

}
