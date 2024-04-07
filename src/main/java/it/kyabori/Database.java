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
        String user = Main.getInstance().getConfig().getString("database.username");
        String password = Main.getInstance().getConfig().getString("database.password");
        Connection connection = DriverManager.getConnection(url, user, password);

        this.connection = connection;
        return connection;
    }

    public void initializeDatabase() throws SQLException {

        Statement statement = getConnection().createStatement();
        //Create the link table if it doesn't exist
        statement.execute("CREATE TABLE IF NOT EXISTS golive_link (username varchar(16) primary key, link varchar(255))");
        statement.close();
    }

    public void setLink (String playerName, String link) throws SQLException {
        //check if the player already has a link
        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM golive_link WHERE username = ?");
        statement.setString(1, playerName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            //update the link
            statement = getConnection().prepareStatement("UPDATE golive_link SET link = ? WHERE username = ?");
            statement.setString(1, link);
            statement.setString(2, playerName);
            statement.executeUpdate();

            statement.close();
        } else {
            //insert the link
            statement = getConnection().prepareStatement("INSERT INTO golive_link (username, link) VALUES (?, ?)");
            statement.setString(1, playerName);
            statement.setString(2, link);
            statement.executeUpdate();

            statement.close();
        }
    }

    public String getLink (String playerName) throws SQLException {
        //get the link of the player
        PreparedStatement statement = getConnection().prepareStatement("SELECT link FROM golive_link WHERE username = ?");
        statement.setString(1, playerName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("link");
        } else {
            return null;
        }
    }
}