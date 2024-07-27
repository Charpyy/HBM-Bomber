package com.hbm.core;
import java.util.HashMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Blob;

import static com.hbm.items.ModItems.*;
import com.hbm.blocks.ModBlocks;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
public class HbmDatabaseOpenwar {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/openwar";
    private static final String DB_USER = "chap";
    private static final String DB_PASSWORD = "Openwar10@Chap";

    public static Connection connection;
	public static HashMap<Item,Integer> levelsMap = new HashMap<>();

    public HbmDatabaseOpenwar() throws SQLException {
		if (connection==null){
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		}
    }

    public int fetchLevelForPlayer(String uuid) {
        int level = 0;
        String query = "SELECT value FROM openwar WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "levels::" + uuid.toLowerCase());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Blob value = resultSet.getBlob("value");
                level = value.getBytes(value.length(), 1)[0];
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du niveau pour le joueur " + uuid + " : " + e.getMessage());
        }

        return level;
    }

    public static int getPlayerLevel(String uuid) {

		try {
			HbmDatabaseOpenwar fetcher = new HbmDatabaseOpenwar();
			int level = fetcher.fetchLevelForPlayer(uuid);
			return level;
		} catch (SQLException e) {
			System.err.println("Erreur lors de la récupération du niveau pour le pseudo " + uuid);
			e.printStackTrace();
			return -1;
		}
	}
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
	public static boolean canUseItem(EntityPlayer player,Item item){
		if(isItemLeveled(item)){
			return isPlayerLevelAbove(player,getItemLevel(item),true);
		}
		return true;
	}
	public static boolean isPlayerLevelAbove(EntityPlayer player,int level,boolean sendMessage){
		boolean res=getPlayerLevel(player.getUniqueID().toString())>=level;
		if((!res)&&sendMessage){
			player.sendMessage(new TextComponentString("§8\u00bb §c You need to be level: "+level+" to do that !"));
		}
		return res;
	}
}