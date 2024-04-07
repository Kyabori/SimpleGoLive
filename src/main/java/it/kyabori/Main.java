package it.kyabori;

import it.kyabori.commands.golivereload;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import it.kyabori.commands.golive;
import it.kyabori.commands.setlink;

import java.util.Objects;

public class Main extends JavaPlugin {
    @Getter
    static Main instance;


    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Database database = new Database();
        database.initializeDatabase();
        Objects.requireNonNull(getCommand("setlink")).setExecutor(new setlink());
        Objects.requireNonNull(getCommand("golive")).setExecutor(new golive());
        Objects.requireNonNull(getCommand("golivereload")).setExecutor(new golivereload());
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static String getMsg(String key) {
        String s = instance.getConfig().getString("messages." + key);
        return s;
    }
}
