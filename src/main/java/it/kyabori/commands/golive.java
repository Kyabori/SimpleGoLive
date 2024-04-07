package it.kyabori.commands;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import it.kyabori.Database;
import it.kyabori.Main;

public class golive implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            String prefix = Main.getMsg("prefix");
            if (player.hasPermission("simplegolive.golive")) {
                try {
                    Database database = new Database();
                    String link = database.getLink(player.getName());
                    if (link != null) {
                        //send a broadcast message with the player's link with a String list
                        List<String> msg = Main.getInstance().getConfig().getStringList("messages.golive");
                        for (String string : msg) {
                            string = string.replaceAll("%player%", player.getName()).replaceAll("%link%", link).replaceAll("&", "§");
                            Main.getInstance().getServer().broadcastMessage(string);
                        }
                    } else {
                        String msg = Main.getMsg("noLink");
                        player.sendMessage(prefix + msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    String msg = Main.getMsg("Error");
                    player.sendMessage(prefix + msg);
                }
                return true;
            } else {
                String msg = Main.getMsg("ConsoleError");
                commandSender.sendMessage(prefix + msg);
                return true;
            }
        }
        return false;
    }
}