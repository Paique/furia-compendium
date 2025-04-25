package gg.furia.challenge.chatbot.discord;

import gg.furia.challenge.Config;
import gg.furia.challenge.chatbot.discord.message.JdaEvents;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

@Getter
public class DiscordBot {
    @Getter
    private static JDA jda;

    public static void initJDA() {
        if (jda != null) return;

        jda = JDABuilder.createDefault(Config.getDiscordToken(),
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.DIRECT_MESSAGE_TYPING,
                        GatewayIntent.DIRECT_MESSAGE_POLLS,
                        GatewayIntent.MESSAGE_CONTENT
                )
                .setActivity(Activity.customStatus("Sua fonte de informações sobre a Furia"))
                .setActivity(Activity.of(Activity.ActivityType.WATCHING, "Mount on Twitch"))
                .setStatus(OnlineStatus.ONLINE)
                .build();

        jda.addEventListener(new JdaEvents());
        jda.setAutoReconnect(true);

        CommandRegistry commandRegistry = CommandRegistry.create(jda);
        commandRegistry.registerAllCommands();
    }
}
