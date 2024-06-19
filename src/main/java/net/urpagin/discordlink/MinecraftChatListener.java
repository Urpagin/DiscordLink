package net.urpagin.discordlink;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MinecraftChatListener implements Listener {

    // the prefix used to make an MC Chat message send to Discord.
    private static final char DISCORD_PREFIX = ';';

    // Formatting set to messages sent to Discord from the MC Chat.
    private static final String DISCORD_BOUND_FORMATTING = "§l"; // bold

    // Formatting set to messages that could not be sent to Discord.
    private static final String DISCORD_BOUND_FORMATTING_ERROR = "§c"; // red


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String playerName = event.getPlayer().getName();
        String message = event.getMessage();

        if (message.charAt(0) != DISCORD_PREFIX) return;

        // Removes the discordPrefix from the message.
        message = message.substring(1);

        if (message.isBlank()) return;

        // ---- now process chat message ----

        String discordMessage = String.format("**[ %s ]** %s", playerName, message);

        boolean isMessageSentSuccessfully = DiscordLink.discord.sendMessageToChannel(discordMessage);

        if (isMessageSentSuccessfully) event.setMessage(DISCORD_BOUND_FORMATTING + message);
        else event.setMessage(DISCORD_BOUND_FORMATTING_ERROR + message);
    }
}
