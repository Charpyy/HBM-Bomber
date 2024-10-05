package com.openwar.hbmapi.Discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBot extends ListenerAdapter {
    static JDA jda;
    public void startBot(String token) throws Exception {
        JDABuilder builder = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        builder.setActivity(Activity.playing("Console OpenWar"));
        builder.addEventListeners(this);
        builder.addEventListeners(new BotListener());
        jda = builder.build();
        jda.awaitReady();
    }
    public static void sendChannelMessage(String msg) {
        MessageChannel channelPL = jda.getTextChannelById("1292041500124774441");
        assert channelPL != null;
        channelPL.sendMessage(msg).queue();
    }
}
