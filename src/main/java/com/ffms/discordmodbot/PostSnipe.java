package com.ffms.discordmodbot;

import discord4j.core.object.entity.Message;
import discord4j.core.spec.MessageCreateSpec;
import reactor.core.publisher.Mono;

import java.io.InputStream;

public class PostSnipe {
    private Mono<Message> command;
    public Mono<Message> getCommand() {
        if (command != null) {
            return command;
        } else {
            return Mono.empty();
        }
    }

    // Posts a picture of a snipe. Added per request of a friend.
    public PostSnipe(Message message) {
        InputStream snipeFile = getClass().getResourceAsStream("/images/Snipes-Flight.png");
        if (snipeFile != null) {
            command = message.getChannel().flatMap(channel -> channel.createMessage(MessageCreateSpec.builder()
                    .addFile("snipe.png", snipeFile).build()));
        }
    }
}
