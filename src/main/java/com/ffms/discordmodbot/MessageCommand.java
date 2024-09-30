package com.ffms.discordmodbot;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public class MessageCommand {
    protected Mono<Message> command;
    public Mono<Message> getCommand() {
        if (command != null) {
            return command;
        } else{
            return Mono.empty();
        }
    }
}
