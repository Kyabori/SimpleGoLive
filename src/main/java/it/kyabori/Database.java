package it.kyabori;

import java.sql.*;

public class Database {

    private Connection connection;

    public Connection getConnection() throws SQLException {

        if(connection != null){
            return connection;
        }

        //Try to connect to my MySQL database running locally
        //complete string example: "jdbc:mysql://host/db_name
        String urlStart = "jdbc:mysql://";
        String host = Main.getInstance().getConfig().getString("database.host");
        String port = Main.getInstance().getConfig().getString("database.port");
        String database = Main.getInstance().getConfig().getString("database.database");
        String url = urlStart + host + ":" + port + "/" + database;
        String user = Main.getInstance().getConfig().getString("database.user");
        String password = Main.getInstance().getConfig().getString("database.password");

        Connection connection = DriverManager.getConnection(url, user, password);

        this.connection = connection;

        System.out.println("Connected to database.");

        return connection;
    }

    public void initializeDatabase() throws SQLException {

        Statement statement = getConnection().createStatement();

        //Create the player_stats table
        statement.execute("CREATE DATABASE IF NOT EXISTS 'EasyGoLive'");
        statement.execute("CREATE TABLE IF NOT EXISTS link (username varchar(16) primary key, link varchar(255))");
        statement.close();
    }

    public void setLink (String playerName, String link) throws SQLException {
        //check if the player already has a link
        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM link WHERE username = ?");
        statement.setString(1, playerName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            //update the link
            statement = getConnection().prepareStatement("UPDATE link SET link = ? WHERE username = ?");
            statement.setString(1, link);
            statement.setString(2, playerName);
            statement.executeUpdate();

            statement.close();
        } else {
            //insert the link
            statement = getConnection().prepareStatement("INSERT INTO link (username, link) VALUES (?, ?)");
            statement.setString(1, playerName);
            statement.setString(2, link);
            statement.executeUpdate();

            statement.close();
        }
    }

    public String getLink (String playerName) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM link WHERE username = ?");
        statement.setString(1, playerName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("link");
        } else {
            return null;
        }
    }
}