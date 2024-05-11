package me.gotitim.clientcrasher.command;

import me.gotitim.clientcrasher.ClientCrasher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CrashCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) return false;
        Player player = Bukkit.getPlayer(args[0]);
        if(player == null) {
            sender.sendMessage(ChatColor.RED + "Could not find player '" + args[0] + "'");
            return true;
        }

        ClientCrasher.crashPlayer(player);
        sender.sendMessage(ChatColor.GREEN + "Crashed " + ChatColor.AQUA + player.getName() + ChatColor.GREEN + "'s client.");
        return true;
    }
}
