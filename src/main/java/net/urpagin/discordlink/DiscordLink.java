package net.urpagin.discordlink;

import net.urpagin.discordlink.minecraft.listeners.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class DiscordLink extends JavaPlugin implements Listener {

    public static DiscordInterface discord;

    private static String get_delta_rounded(long start, long end) {
        // Calculate duration in seconds
        double deltaTimeSeconds = (end - start) / 1_000_000_000.0;  // Convert nanoseconds to seconds
        String format = "%." + 1 + "f";
        return String.format(format, deltaTimeSeconds);
    }

    @Override
    public void onEnable() {
        long startTime = System.nanoTime();

        // Is this ok to do?
        new ReadConfig(this); // Initialize the class for static use.

        // Register event listeners
        getServer().getPluginManager().registerEvents(new MinecraftChatListener(), this);
        getServer().getPluginManager().registerEvents(new MinecraftPlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new MinecraftPlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new MinecraftPlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new MinecraftPlayerConnectionTracker(), this);

        discord = new DiscordInterface(this, ReadConfig.getBotToken(), ReadConfig.getDiscordChannelId());

        long endTime = System.nanoTime();

        getLogger().info("Plugin successfully loaded in " + get_delta_rounded(startTime, endTime) + "s!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
