package it.kyabori.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import it.kyabori.Database;
import it.kyabori.Main;

public class setlink implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] link) {
        if (commandSender instanceof Player) {
            String prefix = Main.getMsg("prefix").replaceAll("&", "§");
            Player player = (Player) commandSender;
            if (player.hasPermission("simplegolive.setlink")) {
                if (link.length == 1) {
                    //try to save the link in the database or update it if it already exists
                    try {
                        Database database = new Database();
                        database.setLink(player.getName(), link[0]);
                        String msg = Main.getMsg("success").replaceAll("&", "§");
                        player.sendMessage(prefix + msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                        String msg = Main.getMsg("Error").replaceAll("&", "§");
                        player.sendMessage(prefix + msg);
                    }
                } else {
                    player.sendMessage(prefix + Main.getMsg("setLinkUsage").replaceAll("&", "§"));
                }
            } else {
                String msg = Main.getMsg("ConsoleError").replaceAll("&", "§");
                commandSender.sendMessage(msg);
            }
            return true;
        }
        return false;
    }
}