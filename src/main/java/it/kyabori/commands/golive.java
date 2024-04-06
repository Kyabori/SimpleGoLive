package it.kyabori.commands;

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
            //send the player's link to the global chat
            if (player.hasPermission("simplegolive.golive")) {
                try {
                    Database database = new Database();
                    String link = database.getLink(player.getName());
                    if (link != null) {
                        player.sendMessage(Main.getMsg("livemessage").replace("%link%", link));
                    } else {
                        player.sendMessage(Main.getMsg("noLink"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    player.sendMessage(Main.getMsg("Error"));
                }
                return true;
            } else {
                commandSender.sendMessage(Main.getMsg("ConsoleError"));
                return true;
            }
        }
        return false;
    }
}