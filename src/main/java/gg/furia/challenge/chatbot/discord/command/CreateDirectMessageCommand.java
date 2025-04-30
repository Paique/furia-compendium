package gg.furia.challenge.chatbot.discord.command;

import gg.furia.challenge.config.YamlUtil;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * CreateDirectMessageCommand is a command that creates a direct message channel with the user.
 * It extends the AbstractCommand class and implements the execute method to send a message to the user.
 */
public class CreateDirectMessageCommand extends AbstractCommand {

    public CreateDirectMessageCommand() {
        super("iniciar", "Cria uma DM com o bot.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        User user = event.getUser();
        user.openPrivateChannel().queue((channel) -> {
            channel.sendMessage(YamlUtil.getConfig().getChatbotText().getStartMessage()).queue();
        });
        event.reply("A DM foi enviada! ✉️⬆️").setEphemeral(true).queue();
    }
}
