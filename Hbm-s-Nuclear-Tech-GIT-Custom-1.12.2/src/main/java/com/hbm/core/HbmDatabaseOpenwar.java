package com.hbm.core;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.hbm.items.ModItems.*;

public class HbmDatabaseOpenwar {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/openwar";
    private static final String DB_USER = "chap";
    private static final String DB_PASSWORD = "Openwar10@Chap";

    public static Connection connection;

    public HbmDatabaseOpenwar() throws SQLException {
		if (connection==null){
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		}
    }

    public int fetchLevelForPlayer(String username) {
        int level = 0;
        String query = "SELECT value FROM openwar WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "levels::" + username.toLowerCase());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String hexValue = resultSet.getString("value");
                level = Integer.parseInt(hexValue, 16);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du niveau pour le pseudo " + username + " : " + e.getMessage());
        }

        return level;
    }

    public static int getPlayerLevel(String username) {

		try {
			HbmDatabaseOpenwar fetcher = new HbmDatabaseOpenwar();
			int level = fetcher.fetchLevelForPlayer(username);
			System.out.println("Le niveau du joueur est : " + level);
			return level;
		} catch (SQLException e) {
			System.err.println("Erreur lors de la récupération du niveau pour le pseudo " + username);
			e.printStackTrace();
			return -1;
		}
	}
	public static void registerLevels(){
		
	}
}