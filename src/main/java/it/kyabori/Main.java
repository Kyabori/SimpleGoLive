package it.kyabori;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import it.kyabori.commands.golive;
import it.kyabori.commands.setlink;

import java.util.Objects;

public class Main extends JavaPlugin {
    @Getter
    static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        try {
            Database database = new Database();
            database.initializeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(getCommand("setlink")).setExecutor(new setlink());
        Objects.requireNonNull(getCommand("golive")).setExecutor(new golive());
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static String getMsg(String key) {
        String s = Main.getInstance().getConfig().getString("messages." + key);
        return s;
    }
}
