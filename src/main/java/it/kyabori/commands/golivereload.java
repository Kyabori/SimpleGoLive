package it.kyabori.commands;

import it.kyabori.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class golivereload implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        String prefix = Main.getMsg("prefix").replaceAll("&", "§");
        if (player.hasPermission("simplegolive.golivereload")) {
            //reload the config
            Main.getInstance().reloadConfig();
            String msg = Main.getMsg("reload").replaceAll("&", "§");
            commandSender.sendMessage(prefix + msg);
            return true;
        } else {
            String msg = Main.getMsg("ConsoleError").replaceAll("&", "§");
            commandSender.sendMessage(msg);
            return true;
        }
    }
}
