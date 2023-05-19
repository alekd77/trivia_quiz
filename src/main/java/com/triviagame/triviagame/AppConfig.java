package com.triviagame.triviagame;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {
    private static final String APP_RESOURCES_DIR_PATH = "src/main/resources";
    private static final String INITIAL_CONFIG_FILE = "config.properties";
    private static AppConfig instance;
    private final Properties config;

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    private AppConfig() {
        this.config = new Properties();
        loadInitConfig();
    }

    public String getAppResourcesDirectoryPath() {
        return APP_RESOURCES_DIR_PATH;
    }

    public boolean isDebugModeEnabled() {
        return Boolean.parseBoolean(this.config.getProperty("debug"));
    }

    public String getEncodingType() {
        return config.getProperty("encoding");
    }

    public String getTestTriviaFile() {
        return config.getProperty("test_trivia_file");
    }

    private void loadInitConfig() {
        String initialConfigFilePath = String.format("%s/%s", APP_RESOURCES_DIR_PATH, INITIAL_CONFIG_FILE);

        try {
            FileInputStream input = new FileInputStream(initialConfigFilePath);
            this.config.load(input);

            String loggerMessage = String.format(
                    "Loading initial configuration from file \"%s\" successful",
                    INITIAL_CONFIG_FILE
            );
            AppLogger.info(loggerMessage);

        } catch (IOException e) {
            String loggerMessage = String.format(
                    "Loading initial configuration from file \"%s\" failed\n\tDescription:\t%s",
                    INITIAL_CONFIG_FILE, e.getMessage()
            );

            AppLogger.error(loggerMessage);

            throw new RuntimeException(loggerMessage);
        }
    }
}
