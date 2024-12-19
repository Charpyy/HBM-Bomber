package com.openwar.hbmapi;

import com.hbm.blocks.ModBlocks;
import net.minecraft.item.Item;

import java.util.HashMap;

import static com.hbm.items.ModItems.*;
import static com.hbm.items.ModItems.sat_laser;

public class LevelManager {
    public static HashMap<Item,Integer> levelsMap = new HashMap<>();
    public static boolean registered=false;
    public static void registerItem(Item item, int level){
        levelsMap.put((Item)item, level);
    }
    public static int getItemLevel(Item item){
        return levelsMap.get(item);
    }
    public static boolean isTeam(Item item){
        return item.equals(mp_warhead_15_nuclear);
    }
    public static boolean isItemLeveled(Item item){
        return levelsMap.containsKey(item);
    }
    public static void registerLevels(){
        registered=true;
        registerItem(missile_emp, 47);
        registerItem(missile_emp_strong, 50);
        registerItem(mp_warhead_10_he, 32);
        registerItem(mp_warhead_10_incendiary, 34);
        registerItem(mp_warhead_10_buster, 36);
        registerItem(mp_warhead_15_he, 40);
        registerItem(mp_warhead_15_incendiary, 42);
        registerItem(mp_warhead_15_nuclear, 20);
        registerItem(missile_soyuz0,40);
        registerItem(sat_radar, 46);
        registerItem(sat_laser, 56);
    }
    public static void regiseterIfNot(){
        if(!registered){
            registerLevels();
        }
    }
}
