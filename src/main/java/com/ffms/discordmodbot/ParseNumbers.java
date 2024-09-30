// @author Maxwell Stoecklein

package com.ffms.discordmodbot;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.util.Arrays;

public class ParseNumbers {
    private Mono<Message> command;
    public Mono<Message> getCommand() {
        if (command != null) {
            return command;
        } else {
            return Mono.empty();
        }
    }

    public ParseNumbers(String[] messageStrArray, Message message) {
        messageStrArray[0] = "";
        String messageStr = String.join("", messageStrArray);
        messageStrArray = messageStr.split(",");
        int[] messageNumbers = new int[messageStrArray.length];

        // This loop converts the numbers from string form to integer form so that
        // they may be used in logic if necessary. This is simply an example function
        // so not much is done with them.
        int i = 0;
        for (String str : messageStrArray) {
            try {
                messageNumbers[i++] = Integer.parseInt(str);
            }
            catch (NumberFormatException e) {
                String errorStr = String.format("Could not parse number: %s. " +
                        "Please separate numbers using commas only.", str);
                System.out.println(errorStr);
                command = message.getChannel().flatMap(channel ->
                        channel.createMessage(errorStr));
            }
        }
        int sumNumbers = Arrays.stream(messageNumbers).sum(); // Only logic done with inputs converted to int.

        i = 0;
        for (int number : messageNumbers) {
            messageStrArray[i++] = String.valueOf(number);
        }
        String finalMessageStr = String.format("Parsed Integers: %s\nSum: %d",
                String.join(", ", messageStrArray), sumNumbers);
        try {
            System.out.println(finalMessageStr);
        }
        catch (NumberFormatException e) {
            System.out.println("Could not print that string.");
        }
        command = message.getChannel().flatMap(channel -> channel.createMessage(finalMessageStr));
    }

}
