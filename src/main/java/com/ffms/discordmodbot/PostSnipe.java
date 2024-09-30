package com.ffms.discordmodbot;

import discord4j.core.object.entity.Message;
import discord4j.core.spec.MessageCreateSpec;

import java.io.InputStream;

public class PostSnipe extends MessageCommand {

    // Posts a picture of a snipe. Added per request of a friend.
    public PostSnipe(Message message) {
        InputStream snipeFile = getClass().getResourceAsStream("/images/Snipes-Flight.png");
        if (snipeFile != null) {
            command = message.getChannel().flatMap(channel -> channel.createMessage(MessageCreateSpec.builder()
                    .addFile("snipe.png", snipeFile).build()));
        }
    }
}
