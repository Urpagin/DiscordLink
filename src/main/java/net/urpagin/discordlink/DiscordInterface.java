package net.urpagin.discordlink;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Arrays;

import static org.bukkit.Bukkit.getLogger;

public class DiscordInterface extends ListenerAdapter {
    private final String token;
    private final long channelId;
    private JDA api;

    // Max amount of characters the bot can send.
    private static final int DISCORD_MAX_MESSAGE_LENGTH = 2000;


    DiscordInterface(String token, long channelId) {
        this.token = token;
        this.channelId = channelId;
        initializeBot();
    }

    public void initializeBot() {
        this.api = JDABuilder.createDefault(token).build();

        try {
            this.api = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS).build().awaitReady();
            this.api.awaitReady(); // block until JDA is ready!
            api.addEventListener(new DiscordMessageListener(channelId), this);
        } catch (InterruptedException e) {
            getLogger().severe(Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }

    public boolean sendMessageToChannel(String content) {
        if (content.length() > DISCORD_MAX_MESSAGE_LENGTH) return false;
        if (content.isBlank()) return false;

        try {
            TextChannel channel = this.api.getTextChannelById(this.channelId);
            if (channel != null) channel.sendMessage(content).queue();
            else getLogger().severe("Channel not found: " + this.channelId);
            return true;
        } catch (Exception e) {
            getLogger().severe(e.getMessage());
        }

        return false;
    }

}
