package gg.furia.challenge.config;

import gg.furia.challenge.exception.EmptyTokenException;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public class EnvConfig {
    public static void applyEnvironmentOverrides() throws EmptyTokenException {
        Config config = YamlUtil.getConfig();

        applyEnvOverride(
                "DISCORD_TOKEN",
                config.getDiscord()::getToken,
                config.getDiscord()::setToken
        );

        applyEnvOverride(
                "TELEGRAM_TOKEN",
                config.getTelegram()::getToken,
                config.getTelegram()::setToken
        );

        applyEnvOverride(
                "OPENAI_TOKEN",
                config.getOpenai()::getToken,
                config.getOpenai()::setToken
        );
    }

    private static void applyEnvOverride(String envKey, Supplier<String> getter, Consumer<String> setter) throws EmptyTokenException {
        String configValue = getter.get();

        if (!isNullOrEmpty(configValue)) {
            log.warn("{} is set in config file, which is not recommended for security reasons", envKey);
            return;
        }

        try {
            setter.accept(getEnv(envKey));
            log.info("Using {} from environment variables", envKey);
        } catch (IllegalArgumentException e) {
            throw new EmptyTokenException(envKey + " is not set or is empty.");
        }
    }

    private static String getEnv(String key) {
        String env = System.getenv(key);
        if (env == null || env.isEmpty()) {
            throw new IllegalArgumentException("Environment variable " + key + " is not set or is empty.");
        }
        return env;
    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
