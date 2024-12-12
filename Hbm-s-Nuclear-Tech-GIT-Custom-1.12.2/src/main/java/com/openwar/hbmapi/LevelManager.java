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
    public static boolean isItemLeveled(Item item){
        return levelsMap.containsKey(item);
    }
    public static void registerLevels(){
        registered=true;
        registerItem(missile_generic, 22);
        registerItem(missile_incendiary, 22);
        registerItem(missile_cluster, 22);
        registerItem(missile_buster, 22);
        registerItem(missile_strong, 30);
        registerItem(missile_incendiary_strong, 30);
        registerItem(missile_cluster_strong, 30);
        registerItem(missile_buster_strong, 30);
        registerItem(missile_burst, 38);
        registerItem(missile_inferno, 38);
        registerItem(missile_rain, 38);
        registerItem(missile_drill, 38);
        registerItem(missile_endo, 45);
        registerItem(missile_exo, 45);
        registerItem(missile_emp, 46);
        registerItem(missile_emp_strong, 50);
        registerItem(missile_n2, 46);
        registerItem(missile_doomsday, 60);
        registerItem(missile_nuclear, 70);
        registerItem(missile_nuclear_cluster, 75);
        registerItem(mp_warhead_10_he, 22);
        registerItem(mp_warhead_10_incendiary, 22);
        registerItem(mp_warhead_10_buster, 22);
        registerItem(mp_warhead_10_nuclear, 40);
        registerItem(mp_warhead_10_nuclear_large, 45);
        registerItem(mp_warhead_15_incendiary, 30);
        registerItem(mp_warhead_15_he, 38);
        registerItem(mp_warhead_15_n2, 55);
        registerItem(mp_warhead_15_nuclear, 71);
        registerItem(mp_warhead_15_mirv, 74);
        registerItem(mp_warhead_15_thermo, 76);
        registerItem(Item.getItemFromBlock(ModBlocks.railgun_plasma), 45);
        registerItem(sat_laser, 55);
    }
    public static void regiseterIfNot(){
        if(!registered){
            registerLevels();
        }
    }
}
