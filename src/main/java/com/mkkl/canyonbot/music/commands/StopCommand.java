package com.mkkl.canyonbot.music.commands;

import com.mkkl.canyonbot.commands.BotCommand;
import com.mkkl.canyonbot.commands.DefaultErrorHandler;
import com.mkkl.canyonbot.commands.RegisterCommand;
import com.mkkl.canyonbot.commands.exceptions.BotExternalException;
import com.mkkl.canyonbot.music.exceptions.GuildMusicBotNotCreated;
import com.mkkl.canyonbot.music.player.LinkContext;
import com.mkkl.canyonbot.music.player.LinkContextRegistry;
import com.mkkl.canyonbot.music.player.TrackScheduler;
import dev.arbjerg.lavalink.libraries.discord4j.Discord4JUtils;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.voice.VoiceConnection;
import reactor.core.publisher.Mono;

@RegisterCommand
public class StopCommand extends BotCommand {
    private final LinkContextRegistry linkContextRegistry;
    private final GatewayDiscordClient gatewayDiscordClient;
    public StopCommand(DefaultErrorHandler errorHandler, LinkContextRegistry linkContextRegistry, GatewayDiscordClient gatewayDiscordClient) {
        super(ApplicationCommandRequest.builder()
                .name("stop")
                .description("Stops playing music")
                .build(), errorHandler);
        this.linkContextRegistry = linkContextRegistry;
        this.gatewayDiscordClient = gatewayDiscordClient;
    }

    @Override
    public Mono<Void> execute(ChatInputInteractionEvent event) {
        return event.getInteraction()
                .getGuild()
                //TODO there is no check for player not being used at all at given guild
                // it could lead to unnecessary resource usage
                .flatMap(guild -> {
                    if (!linkContextRegistry.isCached(guild.getId().asLong()))
                        return Mono.error(new GuildMusicBotNotCreated(guild));
                    return Discord4JUtils.leave(gatewayDiscordClient, guild.getId())
                            .then(event.reply("Disconnected bot"));
                });
    }

}
