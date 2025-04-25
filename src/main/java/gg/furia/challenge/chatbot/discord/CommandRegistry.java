package gg.furia.challenge.chatbot.discord;
import gg.furia.challenge.chatbot.discord.command.AbstractCommand;
import gg.furia.challenge.chatbot.discord.command.ClearMessagesCommand;
import gg.furia.challenge.chatbot.discord.command.CreateDirectMessageCommand;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;

import java.util.HashMap;
import java.util.Map;

/**
 * CommandRegistry is a singleton class that manages the registration of slash commands for JDA.
 */
public class CommandRegistry {
    @Getter
    private static final Map<String, AbstractCommand> commands = new HashMap<>();
    private static CommandRegistry instance;
    private static boolean registered = false;

    public static final AbstractCommand CREATE_DM = register(new CreateDirectMessageCommand());
    public static final AbstractCommand CLEAR_MESSAGES = register(new ClearMessagesCommand());


    private final JDA jda;
    private CommandRegistry(JDA jda) {
        this.jda = jda;
    }

    /**
     * Registers all commands to the JDA instance.
     * This method should be called only once, as it will throw an exception if called again.
     */
    public void registerAllCommands() {
        if (registered) return;
        jda.updateCommands().addCommands(commands.values().stream().map(AbstractCommand::getSlashCommandData).toList()).queue();
        registered = true;
    }

    /**
     * Registers a new command to the command list.
     * @param command The command to register.
     * @return The registered command.
     */
    private static AbstractCommand register(AbstractCommand command) {
        if (registered) throw new IllegalStateException("Commands already registered");
        System.out.println("Adding command " + command.getName() + " to registry");
        commands.put(command.getName(), command);
        return command;
    }

    /**
     * Creates a new CommandRegistry instance if it doesn't exist, or returns the existing one.
     *
     * @param jda The JDA instance to use for command registration.
     * @return The CommandRegistry instance.
     */
    public static CommandRegistry create(JDA jda) {
        if (instance == null) instance = new CommandRegistry(jda);
        return instance;
    }
}
