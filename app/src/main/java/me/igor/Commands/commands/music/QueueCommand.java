package me.igor.Commands.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.igor.Commands.CommandContext;
import me.igor.Commands.ICommand;
import me.igor.LavaPlayer.GuildMusicManager;
import me.igor.LavaPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {

        final TextChannel channel = ctx.getEvent().getChannel();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());

        final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

        if (queue.isEmpty()) {
            channel.sendMessage("Kolejka jest pusta\njak twój łeb xdd").queue();
            return;
        }

        final int trackCount = Math.min(queue.size(), 20);
        final List<AudioTrack> trackList = new ArrayList<>(queue);

        final MessageAction messageAction = channel.sendMessage("**Bieżąca kolejka**\n");

        for (int i = 0; i < trackCount; i++) {
            final AudioTrack track = trackList.get(i);
            final AudioTrackInfo info = track.getInfo();

            messageAction.append("`#`")
                    .append(String.valueOf(i + 1))
                    .append(" `")
                    .append(info.title)
                    .append(" od ")
                    .append(info.author)
                    .append("` [`")
                    .append(formatTime(track.getDuration()))
                    .append("`]\n");
        }

        if (trackList.size() > trackCount) {
            messageAction.append("i jeszcze `")
                    .append(String.valueOf(trackList.size() - trackCount))
                    .append("` inne...");
        }

        messageAction.queue();

    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public String getName() {
        return "queue";
    }
}
