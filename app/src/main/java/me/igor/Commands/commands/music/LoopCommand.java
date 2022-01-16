package me.igor.Commands.commands.music;

import me.igor.Commands.CommandContext;
import me.igor.Commands.ICommand;
import me.igor.LavaPlayer.GuildMusicManager;
import me.igor.LavaPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class LoopCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {

        final TextChannel channel = ctx.getEvent().getChannel();
        final Member self = ctx.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Musisz być w kanale żebym zloopował :unamused:").queue();
            return;
        }

        final Member member = ctx.getEvent().getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Musisz być w kanale żebym zloopował :unamused:").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("Jak mam ci coś zloopować jak nie jesteśmy w tym samym kanale ??").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final boolean newLooping = !musicManager.scheduler.looping;

        musicManager.scheduler.looping = newLooping;

        channel.sendMessageFormat("Teraz leci **%s**", newLooping ? "w kółko" : "raz a porządnie").queue();

    }

    @Override
    public String getName() {
        return "loop";
    }
}
