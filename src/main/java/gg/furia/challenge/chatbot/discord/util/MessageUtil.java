package gg.furia.challenge.chatbot.discord.util;

import gg.furia.challenge.chatbot.discord.DiscordBot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MessageUtil {
    public record RoleMessage(String content, boolean isFromBot) {
    }

    /**
     * Get the last messages from a channel and return them in a list of RoleMessage instances.
     *
     * @param channel The channel to get the messages from
     * @return A map with the messages and if they are from this bot or not
     */
    public static List<RoleMessage> getMessages(MessageChannelUnion channel, int limit) {
        return getMessages(channel, limit, 900, false, false).stream()
                .map(msg -> new RoleMessage(
                                msg.getContentRaw(),
                                msg.getAuthor().equals(DiscordBot.getJda().getSelfUser())
                        )
                )
                .toList();
    }

    /**
     * Get the last messages from a channel and return them in a list of Message instances.
     *
     * @param channel    The channel to get the messages from
     * @param limit      The maximum number of messages to fetch
     * @param maxChar    Drop any message whose raw content length exceeds this
     * @param ignoreBots If true, drop messages sent by any bot
     * @param onlySelf   If true, keep *only* messages sent by this bot
     * @return An ordered list of messages, from oldest to newest
     */
    public static List<Message> getMessages(MessageChannelUnion channel, int limit, int maxChar, boolean ignoreBots, boolean onlySelf) {
        List<Message> fetched = channel.getHistory()
                .retrievePast(limit)
                .complete();

        List<Message> filtered = fetched.stream()
                .filter(msg -> msg.getContentRaw().length() <= maxChar)
                .filter(msg -> !ignoreBots || !msg.getAuthor().isBot())
                .filter(msg -> !onlySelf || msg.getAuthor().equals(DiscordBot.getJda().getSelfUser()))
                .collect(Collectors.toList());

        Collections.reverse(filtered);
        return filtered;
    }
}
