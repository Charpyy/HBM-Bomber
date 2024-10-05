package com.openwar.hbmapi;

import java.util.UUID;
import com.openwar.hbmapi.Discord.DiscordBot;
public class RaidAction {
    public void askAction(UUID playeUUID, int pointNeeded){
        DiscordBot.sendChannelMessage("factionraid "+playeUUID.toString()+" "+pointNeeded);
    }
}
