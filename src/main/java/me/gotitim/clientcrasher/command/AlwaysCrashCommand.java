package me.gotitim.clientcrasher.command;

import me.gotitim.clientcrasher.ClientCrasher;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class AlwaysCrashCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact server administrators if you believe that this is in error.");
            return true;
        }
        if(args.length == 0) return false;
        List<String> alwaysCrash = ClientCrasher.getInstance().getConfig().getStringList("always-crash");
        if(alwaysCrash.contains(args[0])) {
            alwaysCrash.remove(args[0]);
            sender.sendMessage(ChatColor.GREEN + "Removed " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " from AlwaysCrash list.");
        } else {
            alwaysCrash.add(args[0]);
            sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " to AlwaysCrash list.");
        }
        ClientCrasher.getInstance().getConfig().set("always-crash", alwaysCrash);
        return true;
    }
}
