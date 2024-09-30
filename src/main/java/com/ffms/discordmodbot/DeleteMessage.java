// @author Maxwell Stoecklein

package com.ffms.discordmodbot;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public class DeleteMessage {
    private Mono<Void> command;
    public Mono<Void> getCommand() {
        if (command != null) {
            return command;
        } else{
            return Mono.empty();
        }
    }

    //
    public DeleteMessage(Message message) {
        System.out.println(message.getContent());
        // The following will produce an "Error while handling MessageCreateEvent" warning
        // if the bot cannot delete the message. This is usually due to a lack of Manage Messages permissions,
        // or if the message was sent in DMs which the bot simply cannot delete.
        command = message.delete();
    }
}
