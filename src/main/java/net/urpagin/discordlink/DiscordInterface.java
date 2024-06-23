package net.urpagin.discordlink;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.urpagin.discordlink.discord.listeners.DiscordCommandListener;
import net.urpagin.discordlink.discord.listeners.DiscordMessageListener;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

import static org.bukkit.Bukkit.getLogger;

// Provides interface with the Discord bot.
public class DiscordInterface extends ListenerAdapter {
    private final String token;
    private final long channelId;
    private static JDA api;
    private static JavaPlugin plugin;

    // Max amount of characters the bot can send.
    public static final int DISCORD_MAX_MESSAGE_LENGTH = 2000;


    DiscordInterface(JavaPlugin plugin, String token, long channelId) {
        this.token = token;
        this.channelId = channelId;
        DiscordInterface.plugin = plugin;
        initializeBot();
    }

    public void initializeBot() {
        try {
            api = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS).build().awaitReady();

            // Add event listeners!
            api.addEventListener(new DiscordMessageListener(channelId), this);
            api.addEventListener(new DiscordCommandListener(), this);

            // Add slash commands!
            api.updateCommands().addCommands(Commands.slash("playing", "Display connected players!"), Commands.slash("deaths", "Display the deaths leaderboard!"), Commands.slash("become_a_god", "Would you hold any regrets in becoming divine?")).queue();

            updateActivityPlaying();
        } catch (InterruptedException e) {
            Bukkit.getLogger().severe("Failed to initialize Discord bot: " + Arrays.toString(e.getStackTrace()));
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted during bot setup", e);
        }
    }

    public static void updateActivityPlaying() {
        Server server = Bukkit.getServer(); // Get server instance
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            int onlinePlayers = server.getOnlinePlayers().size();
            int maxPlayers = server.getMaxPlayers();
            String plural = (onlinePlayers > 1) ? "players" : "player";
            String activityMessage = String.format("\uD83C\uDF1F %d/%d %s online!", onlinePlayers, maxPlayers, plural);
            api.getPresence().setActivity(Activity.customStatus(activityMessage));
        }, 20L); // Delay the task by 20 ticks (1 second)
        // When a player quits it's not registered when no waiting is done.
    }

    public boolean sendMessageToChannel(String content) {
        if (content.length() > DISCORD_MAX_MESSAGE_LENGTH) return false;
        content = content.strip();
        if (content.isBlank()) return false;

        try {
            TextChannel channel = api.getTextChannelById(this.channelId);
            if (channel != null) channel.sendMessage(content).queue();
            else getLogger().severe("Channel not found: " + this.channelId);
            return true;
        } catch (Exception e) {
            getLogger().severe(e.getMessage());
        }

        return false;
    }

}
