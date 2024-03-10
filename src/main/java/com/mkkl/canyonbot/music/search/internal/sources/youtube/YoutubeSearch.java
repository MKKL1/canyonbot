package com.mkkl.canyonbot.music.search.internal.sources.youtube;

import com.mkkl.canyonbot.music.search.internal.sources.SearchSource;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;

public class YoutubeSearch implements SearchSource {
    @Override
    public String name() {
        return "Youtube search";
    }
    @Override
    public String logoUrl() {
        return "https://cdn.discordapp.com/attachments/1168970395861397624/1168970843947286649/logo-youtube.png";
    }

    @Override
    public String searchIdentifier() {
        return "ytsearch";
    }
}
