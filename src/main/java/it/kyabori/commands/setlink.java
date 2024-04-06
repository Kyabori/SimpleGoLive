package it.kyabori.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import it.kyabori.Database;
import it.kyabori.Main;

public class setlink implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("simplegolive.setlink")) {
                if (strings.length == 1) {
                    //save in database
                    try {
                        Database database = new Database();
                        database.setLink(player.getName(), strings[0]);
                        player.sendMessage(Main.getMsg("Success"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        player.sendMessage(Main.getMsg("Error"));
                    }
                } else {
                    player.sendMessage("Usage: /setlink <link>");
                }
            } else {
                commandSender.sendMessage(Main.getMsg("ConsoleError"));
            }
            return true;
        }
        return false;
    }
}