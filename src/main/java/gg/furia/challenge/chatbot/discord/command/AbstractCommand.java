package gg.furia.challenge.chatbot.discord.command;

import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

/**
 * AbstractCommand is an abstract class that represents a command for JDA.
 * It provides a template for creating commands with a name, description, and execution logic.
 */
@Getter
public abstract class AbstractCommand {
    protected final String name;
    protected final String description;

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void execute(SlashCommandInteractionEvent event);

    public SlashCommandData getSlashCommandData() {
        return Commands.slash(name, description);
    }
}
