package gg.furia.challenge.chatbot.discord.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

/**
 * AbstractCommand is an abstract class that represents a command for JDA.
 * It provides a template for creating commands with a name, description, and execution logic.
 */
@Getter
@RequiredArgsConstructor
public abstract class AbstractCommand {
    protected final String name;
    protected final String description;

    public abstract void execute(SlashCommandInteractionEvent event);

    public SlashCommandData getSlashCommandData() {
        return Commands.slash(name, description);
    }
}
