package com.openwar.hbmapi.Discord;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.UUID;

public class BotListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(net.dv8tion.jda.api.events.message.MessageReceivedEvent event) {
        Message message = event.getMessage();
        if (message.getAuthor().isBot()) {
            if (message.getChannel().getId().equals("1292041500124774441")) {
                String msgContent = message.getContentDisplay();
                if (msgContent.contains("hbm")) {
                    String[] msgParts = msgContent.split(" ");
                    if (msgParts.length > 1) {
                        String bool = msgParts[1];
                        try {
                            if (bool.contains("true")) {
                                DiscordBot.sendChannelMessage("log received true.");
                            }
                            if (bool.contains("false")) {
                                DiscordBot.sendChannelMessage("log received false.");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("error parsing");

                        }
                    }
                }
            }
        }
    }
}
