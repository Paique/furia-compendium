package gg.furia.challenge.chatbot.discord.message;

import gg.furia.challenge.chatbot.discord.command.AbstractCommand;
import gg.furia.challenge.chatbot.discord.CommandRegistry;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class JdaEvents extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        if (event.getAuthor().isBot()) return;
        if (event.isFromType(ChannelType.PRIVATE)) DirectMessageHandler.handle(event);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
        if (event.getUser().isBot()) return;

        AbstractCommand command = CommandRegistry.getCommands().get(event.getName());
        if (command == null) return;
        command.execute(event);
    }
}
