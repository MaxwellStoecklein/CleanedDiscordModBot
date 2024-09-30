package com.ffms.discordmodbot;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;

import java.util.HashMap;
import java.util.LinkedList;

public class DeletedMessages {

    private HashMap<Snowflake, LinkedList<Message>> deletedMessages;
    public HashMap<Snowflake, LinkedList<Message>> getDeletedMessages(){
        return deletedMessages;
    }

    public DeletedMessages() {
        deletedMessages = new HashMap<>();
    }

    public void addDeletedMessage(Message message) {
        deletedMessages.putIfAbsent(message.getChannelId(), new LinkedList<>());
        if (deletedMessages.get(message.getChannelId()).size() < 3) {
            deletedMessages.get(message.getChannelId()).addFirst(message);
        } else {
            deletedMessages.get(message.getChannelId()).removeLast();
            deletedMessages.get(message.getChannelId()).addFirst(message);
        }
    }
}
