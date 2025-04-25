package gg.furia.challenge.chatbot.discord.command;

import gg.furia.challenge.chatbot.discord.util.MessageUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.List;

/**
 * ClearMessagesCommand is a command that clears all bot messages in the channel.
 * It is intended for use in direct messages only.
 */
public class ClearMessagesCommand extends AbstractCommand {

    public ClearMessagesCommand() {
        super("limpar", "Limpa todas as mensagens do bot no canal (Não limpa as suas mensagens).");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (event.getChannel().getType() != ChannelType.PRIVATE) {
            event.reply("This command can only be used in direct messages.").setEphemeral(true).queue();
            return;
        }

        List<Message> messages = MessageUtil.getMessages(event.getChannel(), 50, Integer.MAX_VALUE, false, true);
        event.getChannel().purgeMessages(messages);
        event.reply("Todas as mensagens do bot foram limpas.").setEphemeral(true).queue();
    }
}
