package me.igor.Commands.commands.music;

import me.igor.Commands.CommandContext;
import me.igor.Commands.ICommand;
import me.igor.LavaPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.net.URL;

public class PlayCommand implements ICommand {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void handle(CommandContext ctx) {

        final TextChannel channel = ctx.getEvent().getChannel();

        if (ctx.getArgs().isEmpty()) {
            channel.sendMessage("Podaj nazwe albo link do piosenki po `!play`").queue();
            return;
        }

        final Member self = ctx.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Muszę być w kanale żeby coś odpalić :unamused:").queue();
            return;
        }

        final Member member = ctx.getEvent().getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Musisz być w kanale żebym ci coś odpalił :unamused:").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("Jak mam ci coś odpalić jak nie jesteśmy w tym samym kanale ??").queue();
            return;
        }

        String link = String.join(" ", ctx.getArgs());

        if (!isUrl(link)) {
            link = "ytsearch:" + link;
        }

        PlayerManager.getInstance().loadAndPlay(channel, link);
    }

    @Override
    public String getName() {
        return "play";
    }

    private boolean isUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
