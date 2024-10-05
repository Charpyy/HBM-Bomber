package com.openwar.hbmapi;

import com.openwar.hbmapi.Discord.DiscordBot;
import com.openwar.hbmapi.proxy.CommonProxy;
import com.openwar.hbmapi.utils.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid =  Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class Main {

    public static SimpleNetworkWrapper network;
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;
    private DiscordBot bot;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        network = new SimpleNetworkWrapper(Reference.MOD_ID);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public static void PostInit(FMLPostInitializationEvent event) {
    }
    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent evt) {
        bot = new DiscordBot();
        try {
            bot.startBot("MTI1MDA0ODA2NjAzMTk3NjUyMA.GPLfy1.O8iq54lcnsDuaoQR0J9nzLTbhwU81E-yIAaVtU");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }}
