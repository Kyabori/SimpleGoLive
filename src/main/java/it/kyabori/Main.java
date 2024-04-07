package it.kyabori;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import it.kyabori.commands.golive;
import it.kyabori.commands.setlink;
import it.kyabori.Database;

import java.util.Objects;

public class Main extends JavaPlugin {
    @Getter
    static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Objects.requireNonNull(getCommand("setlink")).setExecutor(new setlink());
        Objects.requireNonNull(getCommand("golive")).setExecutor(new golive());
        getLogger().info(Main.getMsg(ChatColor.GREEN + "SimpleGoLive enabled!"));
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static String getMsg(String key) {
        String s = Objects.requireNonNull(Main.getInstance().getConfig().getString("messages." + key)).replaceAll("&", "§");
        return s;
    }
}
