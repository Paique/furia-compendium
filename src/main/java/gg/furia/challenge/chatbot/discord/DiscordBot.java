package gg.furia.challenge.chatbot.discord;

import gg.furia.challenge.chatbot.discord.message.JdaEvents;
import gg.furia.challenge.config.Config;
import gg.furia.challenge.config.YamlUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

@Getter
@Slf4j
public class DiscordBot {
    @Getter
    private static JDA jda;

    public static void init() {
        if (jda != null) return;
        Config config = YamlUtil.getConfig();
        Config.DiscordConfig.StatusConfig statusConfig = config.getDiscord().getStatus();

        jda = JDABuilder.createDefault(config.getDiscord().getToken(),
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.DIRECT_MESSAGE_TYPING,
                        GatewayIntent.DIRECT_MESSAGE_POLLS,
                        GatewayIntent.MESSAGE_CONTENT
                )
                .setActivity(Activity.of(statusConfig.getActivity(), statusConfig.getText()))
                .setStatus(OnlineStatus.ONLINE)
                .build();

        jda.addEventListener(new JdaEvents());
        jda.setAutoReconnect(true);

        CommandRegistry commandRegistry = CommandRegistry.create(jda);
        commandRegistry.registerAllCommands();
        log.info("Discord Bot initialized");
    }
}
