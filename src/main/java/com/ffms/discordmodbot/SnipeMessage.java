package com.ffms.discordmodbot;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public class SnipeMessage {
    private Mono<Message> command;
    public Mono<Message> getCommand() {
        if (command != null) {
            return command;
        } else {
            return Mono.empty();
        }
    }

    public SnipeMessage(int index, DeletedMessages deletedMessages, Message message) {
        if (index >= 3 || index < 0) {
            command = message.getChannel().flatMap(channel -> channel.createMessage(
                    "Snipe parameters are invalid. Please enter a number between 0 and 2 after the command " +
                            "to indicate which of the 3 most recently deleted messages should be displayed.\n" +
                            "(0 being most recent)"
            ));
        } else {

            if(deletedMessages.getDeletedMessages().get(message.getChannelId()) != null &&
                    index + 1 <= deletedMessages.getDeletedMessages().get(message.getChannelId()).size()) {
                // Index List
                // 0: Username.
                // 1: Time original message was sent.
                // 2: The contents of the message.
                String[] responseElements = {
                        deletedMessages.getDeletedMessages().get(message.getChannelId())
                                .get(index).getUserData().username(),
                        deletedMessages.getDeletedMessages().get(message.getChannelId())
                                .get(index).getTimestamp().toString(),
                        deletedMessages.getDeletedMessages().get(message.getChannelId())
                                .get(index).getContent()
                };

                String responseString = "From: " + responseElements[0] + " | Sent @" + responseElements[1] + "\n" +
                        "> " + responseElements[2];
                command = message.getChannel().flatMap(channel -> channel.createMessage(responseString));
            } else {
                String responseString = "No deleted messages could be found.";
                command = message.getChannel().flatMap(channel -> channel.createMessage(responseString));
            }
        }
    }
}
