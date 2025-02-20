package com.mkkl.canyonbot.music.player.event.lavalink;

import com.mkkl.canyonbot.event.GuildEvent;
import com.mkkl.canyonbot.music.player.event.LavaLinkEvent;
import dev.arbjerg.lavalink.client.LavalinkNode;
import dev.arbjerg.lavalink.protocol.v4.PlayerState;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PlayerUpdateEvent implements LavaLinkEvent, GuildEvent {
    private final LavalinkNode node;
    private final long guildId;
    private final PlayerState state;
    public PlayerUpdateEvent(LavalinkNode node, long guildId, PlayerState state) {
        this.node = node;
        this.guildId = guildId;
        this.state = state;
    }
    public PlayerUpdateEvent(dev.arbjerg.lavalink.client.event.PlayerUpdateEvent oldUpdateEvent) {
        this(oldUpdateEvent.getNode(), oldUpdateEvent.getGuildId(), oldUpdateEvent.getState());
    }
}
