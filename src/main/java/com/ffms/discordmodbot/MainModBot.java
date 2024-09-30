// @author Maxwell Stoecklein

package com.ffms.discordmodbot;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.MessageDeleteEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

import java.util.Optional;

// This program runs a discord bot intended for use as a moderation tool, with some extra features.
// I would've added some essentials like a ban command, however I don't have an alt to repeatedly invite and ban.
// Having a friend as my guinea pig for this would also waste their time. The command would be extremely easy to
// implement, so I decided to forgo making it.

public class MainModBot {
    public static void main(String[] args) {
        // If the following line's string is set as "token", the bot will fail to run. Token should be replaced with
        // a valid Discord bot token.
        DiscordClient client = DiscordClient.create("");
        DeletedMessages deletedMessages = new DeletedMessages();

        Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) -> {
            Mono<Void> printOnLogin = gateway.on(ReadyEvent.class, event ->
                    Mono.fromRunnable(() -> {
                        final User self = event.getSelf();
                        System.out.printf("Logged in as %s\n", self.getUsername());
                    })).then();

            // Listens for messages. The commandHandler object it creates will check if the message is a valid command.
            Mono<?> commandListener = gateway.on(MessageCreateEvent.class, event -> {
                Message message = event.getMessage();
                CommandHandler commandHandler = new CommandHandler(gateway, message, deletedMessages);
                return commandHandler.getMessageResponse();
            }).then();

            // Listens for messages being deleted. The main limitation of this event is that it doesn't seem
            // to allow the bot to retrieve any deleted messages that had originally been SENT prior to when
            // the bot's current active session was created. I.E: If someone turns on this bot as you read this
            // and then someone deletes a message that was sent a month ago, the bot cannot retrieve that message.
            Mono<?> deletionListener = gateway.on(MessageDeleteEvent.class, event -> {
                Optional<Message> maybeMessage = event.getMessage();
                if(maybeMessage.isPresent()) {
                    Message message = maybeMessage.get();
                    deletedMessages.addDeletedMessage(message);
                }
                else {
                    System.out.println("A message deletion was detected, but could not be retrieved...");
                }
                return Mono.empty();
            }).then();
            return printOnLogin.and(commandListener.and(deletionListener));
        });
        login.block();
    }
}
