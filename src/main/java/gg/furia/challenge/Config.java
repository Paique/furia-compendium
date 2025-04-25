package gg.furia.challenge;

import gg.furia.challenge.openai.ModelTypes;
import lombok.Getter;

//Todo: Environment variables seems to be too strict, change to a yaml file
@Getter
public class Config {
    private static final String discordToken = System.getenv("DISCORD_TOKEN");
    private static final String openAiToken = System.getenv("OPENAI_API_KEY");

    @Getter
    private static final ModelTypes openAiModel = initModelType();

    private static ModelTypes initModelType() {
        String modelEnv = System.getenv("OPENAI_MODEL");
        ModelTypes model = ModelTypes.fromString(modelEnv);
        return model != null ? model : ModelTypes.GPT_4_O_MINI_SEARCH_PREVIEW;
    }

    public static String getDiscordToken() {
        if (discordToken == null) {
            throw new IllegalArgumentException("Discord token is not set in environment variables.");
        }
        return discordToken;
    }

    public static String getOpenAiToken() {
        if (openAiToken == null) {
            throw new IllegalArgumentException("OpenAI token is not set in environment variables.");
        }
        return openAiToken;
    }
}
