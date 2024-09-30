package com.ffms.discordmodbot;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;
import discord4j.core.GatewayDiscordClient;


// This class is used in order to figure out which command the user sent, if any.
// It will ignore messages sent by a bot, then those that aren't a command, and
// then finally it will check what command was sent. If the command doesn't exist,
// then the program will simply do nothing.
public class CommandHandler {
    private Mono<Message> messageResponse;
    private Mono<Void> actionResponse;

    public Mono<?> getMessageResponse() {
        if (messageResponse != null) {
            return messageResponse;
        } else if (actionResponse != null) {
            return actionResponse;
        } else {
            return Mono.empty();
        }
    }

    public CommandHandler(GatewayDiscordClient gateway, Message message, DeletedMessages deletedMessages) {
        User sender = new User(gateway, message.getUserData());

        if(sender.isBot()) {
            System.out.println("Sender was a bot. Ignoring...");
        } else {
            // The message that was passed must be broken down in multiple stages to make its contents readable.
            // The current implementation restricts the use of spaces and "!"s within a command's arguments
            // as these characters are used to differentiate between elements like the command marker, command arguments,
            // and the command's identity.
            String messageStr = message.getContent();
            String[] messageStrArray = messageStr.split(" ");
            messageStrArray[0] = messageStrArray[0].toLowerCase();
            String[] commandIdentifier = messageStrArray[0].split("!");

            // The only way commandIdentifier[0] can be empty here is if the very first character was either
            // a space or an !.
            if(commandIdentifier[0].isEmpty()) {
                switch (messageStrArray[0]) {
                    case "!parsenumbers":
                        ParseNumbers parseNumbers = new ParseNumbers(messageStrArray, message);
                        messageResponse = parseNumbers.getCommand();
                        break;
                    case "!delete":
                        DeleteMessage deleteMessage = new DeleteMessage(message);
                        actionResponse = deleteMessage.getCommand();
                        break;
                    case "!snipe":
                        int index;
                        try {
                            index = Integer.parseInt(messageStrArray[1]);
                        }
                        catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            index = 9;
                        }
                        SnipeMessage snipeMessage = new SnipeMessage(index, deletedMessages, message);
                        messageResponse = snipeMessage.getCommand();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
