package gg.furia.challenge.openai;

import lombok.Getter;

@Getter
public enum ModelTypes {
    // Does not support web search
    GPT_4_1("gpt-4.1"),
    O_4_MINI("o4-mini"),
    GPT_4_O("gpt-4o"),

    // Supports web search
    GPT_4_O_MINI("gpt-4o-mini"),
    GPT_4_O_SEARCH_PREVIEW("gpt-4o-search-preview"),
    GPT_4_O_MINI_SEARCH_PREVIEW("gpt-4o-mini-search-preview");


    private final String modelName;

    ModelTypes(String modelName) {
        this.modelName = modelName;
    }

    public static ModelTypes fromString(String modelName) {
        for (ModelTypes modelType : ModelTypes.values()) {
            if (modelType.getModelName().equalsIgnoreCase(modelName)) {
                return modelType;
            }
        }
        return null;
    }
}
