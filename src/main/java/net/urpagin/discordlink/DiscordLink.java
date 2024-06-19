package net.urpagin.discordlink;

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
        ReadConfig config = new ReadConfig(this);

        // Register event listener
        getServer().getPluginManager().registerEvents(new MinecraftChatListener(), this);
        discord = new DiscordInterface(config.getBotToken(), config.getDiscordChannelId());

        long endTime = System.nanoTime();

        getLogger().info("Plugin successfully loaded in " + get_delta_rounded(startTime, endTime) + "s!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
