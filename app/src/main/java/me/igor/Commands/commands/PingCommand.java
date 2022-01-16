package me.igor.Commands.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PingCommand extends ListenerAdapter {

    public void onGuildMessageReceived( GuildMessageReceivedEvent event) {

        String messageSent = event.getMessage().getContentRaw();

        if (messageSent.equalsIgnoreCase("dj!ping")) {
            event.getChannel().sendMessage("Mój ping: **" + event.getJDA().getGatewayPing() + "ms**").queue();
        }

    }
}
