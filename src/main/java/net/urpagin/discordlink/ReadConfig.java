package net.urpagin.discordlink;

import org.bukkit.plugin.java.JavaPlugin;

public class ReadConfig {
    private static final String DEFAULT_TOKEN = "your_discord_bot_token_here";
    private static final long DEFAULT_CHANNEL_ID = -1;

    private final JavaPlugin plugin;
    private String botToken;
    private long channelId;

    public ReadConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        loadConfig();
    }

    private void loadConfig() {
        botToken = plugin.getConfig().getString("discord_bot_token", DEFAULT_TOKEN);
        channelId = plugin.getConfig().getLong("discord_channel_id", DEFAULT_CHANNEL_ID);

        boolean configInvalid = false;

        if (DEFAULT_TOKEN.equals(botToken)) {
            plugin.getLogger().severe("Please set 'discord_bot_token' in the plugin's 'config.yml'");
            configInvalid = true;
        }

        if (channelId == DEFAULT_CHANNEL_ID) {
            plugin.getLogger().severe("Please set 'discord_channel_id' in the plugin's 'config.yml'");
            configInvalid = true;
        }

        if (configInvalid) disablePlugin();

    }

    private void disablePlugin() {
        plugin.getLogger().severe("Error in the plugin's config. Disabling plugin.");
        plugin.getServer().getPluginManager().disablePlugin(plugin);
    }

    public String getBotToken() {
        return botToken;
    }

    public long getDiscordChannelId() {
        return channelId;
    }
}
