package com.ffms.discordmodbot;

import reactor.core.publisher.Mono;

public class ActionCommand {
    protected Mono<Void> command;
    public Mono<Void> getCommand() {
        if (command != null) {
            return command;
        } else{
            return Mono.empty();
        }
    }
}
