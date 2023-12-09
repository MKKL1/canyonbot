package com.mkkl.canyonbot.music.player.queue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.object.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

//TODO change name to something more descriptive
@Getter
@AllArgsConstructor
public class TrackQueueElement {
    private final AudioTrack audioTrack;
    private final User user;
}
