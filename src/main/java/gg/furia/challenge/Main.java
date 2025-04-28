package gg.furia.challenge;

import gg.furia.challenge.chatbot.discord.DiscordBot;
import gg.furia.challenge.chatbot.telegram.TelegramBot;
import gg.furia.challenge.config.YamlUtil;
import gg.furia.challenge.openai.OpenAi;
import lombok.Getter;

import java.util.logging.Logger;

public class Main {
    @Getter
    private static OpenAi openAi;

    public static void main(String[] args) {
        YamlUtil.init("config.yaml");
        openAi = new OpenAi();

        DiscordBot.init();
        TelegramBot.init().start();
    }
}