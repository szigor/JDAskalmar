package me.igor.Commands.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String messageSent = event.getMessage().getContentRaw();

        if (messageSent.equalsIgnoreCase("dj!help")) {
            event.getChannel().sendMessage(
                                      "__**Lista wszystkich moich komend:**__\n" +
                            "`!join` - Wchodze na głosówke\n" +
                            "`!play` - Odpalam muze ***[!play*** <***link*** lub ***wyszukiwana fraza***>***]***\n" +
                            "`!stop` - stopuje utwór i zeruje kolejke\n" +
                            "`!skip` - skipuje nute\n" +
                            "`!loop` - puszczam nute w kółko/raz\n" +
                            "`!now` - nazwa bieżącej nuty\n" +
                            "`!queue` - pokazuje utwory, które są w kolejce\n" +
                            "`!dc` - rozłączam się z głosówki\n" +
                            "`!p` - dołączam i od razu puszczam nute\n" + "         " +
                            "***[!p*** <***link*** lub ***wyszukiwana fraza***>***]***"
            ).queue();
        }

    }
}
