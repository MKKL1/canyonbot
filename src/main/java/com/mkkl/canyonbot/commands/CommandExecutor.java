package com.mkkl.canyonbot.commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputAutoCompleteEvent;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class CommandExecutor {
    private final GatewayDiscordClient gatewayDiscordClient;
    private final CommandRegistry commandRegistry;

    @Autowired
    public CommandExecutor(GatewayDiscordClient gatewayDiscordClient, CommandRegistry commandRegistry) {
        this.gatewayDiscordClient = gatewayDiscordClient;
        this.commandRegistry = commandRegistry;
        this.register()
                .subscribe(); // TODO move?
    }

    public Mono<Void> register() {
        return gatewayDiscordClient.on(ChatInputInteractionEvent.class, event -> {
            //TODO localize
            return commandRegistry.getCommandByName(event.getCommandName())
                .map(botCommand -> botCommand.execute(event)
                        .onErrorResume(throwable -> botCommand.getErrorHandler().handle(throwable, event)))
                .orElseGet(() -> event.reply("Command not found"));
            }).then()
            .and(gatewayDiscordClient.on(ChatInputAutoCompleteEvent.class, event -> {
                Optional<AutoCompleteCommand> optionalCommand = commandRegistry.getAutoCompleteCommandByName(event.getCommandName());
                if (optionalCommand.isEmpty()) {
                    return Mono.empty();
                }
                return optionalCommand.get()
                        .autoComplete(event);
            }));
    }
}
