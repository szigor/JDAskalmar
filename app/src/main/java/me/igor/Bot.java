package me.igor;

import me.igor.Commands.commands.HelpCommand;
import me.igor.Commands.commands.PingCommand;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Bot {
    private Bot() throws LoginException {

        JDABuilder.createDefault(
                        Config.get("token"),
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_VOICE_STATES
                )
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(
                        new Listener(),
                        new HelpCommand(),
                        new PingCommand())
                //.setActivity(Activity.listening("dj!help - komendy"))
                .build();
    }
    public static void main(String[] args) throws LoginException {
        new Bot();
    }
}
