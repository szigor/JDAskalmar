package me.igor.Commands.commands.music;

import me.igor.Commands.CommandContext;
import me.igor.Commands.ICommand;
import me.igor.LavaPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.URL;

public class PCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {

        final TextChannel channel = ctx.getEvent().getChannel(); //pobieranie kanalu gdzie sie pisze

        final Member self = ctx.getGuild().getSelfMember(); //w ktorym kanale jest bot
        final GuildVoiceState selfVoiceState = self.getVoiceState(); //w ktorym kanale jest bot

        final Member member = ctx.getEvent().getMember(); //w ktorym kanale jest osoba ktora pisze
        final GuildVoiceState memberVoiceState = member.getVoiceState(); //w ktorym kanale jest osoba ktora pisze

        final AudioManager audioManager = ctx.getGuild().getAudioManager(); //join
        final VoiceChannel memberChannel = memberVoiceState.getChannel(); //join - kanal osoby ktora pisze

        if (ctx.getArgs().isEmpty()) {
            channel.sendMessage("Podaj nazwe albo link piosenki po `!p`").queue();
            return;
        }

        if (selfVoiceState.inVoiceChannel()) {
            //channel.sendMessage("Przecież już jestem w innym kanale :neutral_face:").queue();
            //bot jest w kanale
            //dolacz do mojego kanalu i play
            join(channel, audioManager,memberChannel);
            play(channel, ctx);
            return;
        }

        if (!memberVoiceState.inVoiceChannel()) {
            //oosba ktora pisze nie jest w zadnym kanale
            channel.sendMessage("Musisz być w kanale żebym ci coś odpalił :unamused:").queue();
            return;
        }

        //wbija i puszcza
        join(channel, audioManager, memberChannel);
        play(channel, ctx);

    }

    private void join(TextChannel channel, AudioManager audioManager, VoiceChannel memberChannel) {
        audioManager.openAudioConnection(memberChannel);
        channel.sendMessage("Wbijam szefie").queue();
    }

    private void play(TextChannel channel, CommandContext ctx) {
        String link = String.join(" ", ctx.getArgs());

        if (!isUrl(link)) {
            link = "ytsearch:" + link;
        }

        PlayerManager.getInstance().loadAndPlay(channel, link);
    }

    @Override
    public String getName() {
        return "p";
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
