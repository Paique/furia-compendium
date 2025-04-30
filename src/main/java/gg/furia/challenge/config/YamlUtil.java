package gg.furia.challenge.config;

import gg.furia.challenge.exception.EmptyTokenException;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * YamlConfig is a singleton class that handles the loading and creation of a YAML configuration file.
 * It loads the configuration into a Config object.
 * @see Config
 */
@Slf4j
public class YamlUtil {
    private static YamlUtil instance;
    private final File yamlFile;

    @Getter
    public static Config config;

    public YamlUtil(String filePath) {
        this.yamlFile = new File(filePath);
        loadYaml();
    }

    /**
     * Initialize the YamlConfig singleton instance.
     * This method should be called once at the start of the application.
     */
    public static void init(String filePath) {
        if (instance == null) {
            instance = new YamlUtil(filePath);
            log.info("Loaded config file: {}", filePath);
        }
    }

    /**
     * Create a new config file if it does not exist.
     * @param destination The destination file to create.
     */
    @SneakyThrows
    private void createConfig(File destination) {
        try (InputStream in = YamlUtil.class.getClassLoader().getResourceAsStream("config.yaml")) {
            if (in == null) throw new FileNotFoundException("Resource not found: config.yaml");
            Files.copy(in, destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * Load the YAML configuration file into a Config object.
     * If the file does not exist, call createConfig() to create it.
     */
    private void loadYaml() {
        if (!this.yamlFile.exists()) createConfig(yamlFile);
        try (InputStream in = Files.newInputStream(yamlFile.toPath())) {
            Yaml yaml = new Yaml();
            config = yaml.loadAs(in, Config.class);
            EnvConfig.applyEnvironmentOverrides();
        } catch (IOException | EmptyTokenException e) {
            log.error(e.getMessage());
            System.exit(1);
        }
    }
}
