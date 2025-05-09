package gg.furia.challenge.chatbot.discord.message;

import gg.furia.challenge.Main;
import gg.furia.challenge.chatbot.discord.util.MessageUtil;
import gg.furia.challenge.config.YamlUtil;
import gg.furia.challenge.exception.OpenAiException;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

@Slf4j
public class DirectMessageHandler {

    /**
     * Handle the direct message event
     *
     * @param event The event to handle
     */
    public static void handle(MessageReceivedEvent event) {
        MessageChannelUnion channel = event.getChannel();
        Message message = event.getMessage();
        chatbotHandler(channel, message);
    }

    /**
     * Handle the chatbot message
     *
     * @param channel The channel to send the message to
     * @param message The message to send
     */
    private static void chatbotHandler(MessageChannelUnion channel, Message message) {
        int maxLength = YamlUtil.getConfig().getOpenai().getMaxCharacters();
        if (message.getContentRaw().length() > maxLength) {
            message.reply("A mensagem é muito longa, tente novamente com uma mensagem menor.").queue();
            return;
        }

        channel.sendTyping().queue();
        List<MessageUtil.RoleMessage> roleMessage = MessageUtil.getMessages(channel, 10);

        try {
            String generated = Main.getOpenAi().generateText(roleMessage, message.getAuthor().getEffectiveName());
            message.reply(generated).queue();
        } catch (OpenAiException e) {
            message.reply(YamlUtil.getConfig().getChatbotText().getGenericError()).queue();
            log.error(e.getMessage());
        }
    }
}
