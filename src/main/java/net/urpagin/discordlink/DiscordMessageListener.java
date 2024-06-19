package net.urpagin.discordlink;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static org.bukkit.Bukkit.getServer;

public class DiscordMessageListener extends ListenerAdapter {

    // Discord ChannelID to which messages will be sent.
    public static long channelId;

    // Max length of a Minecraft chat message.
    private static final int MINECRAFT_CHAT_MAX_MSG_LENGTH = 256;

    // Formatting set to messages coming from Discord to the MC Chat.
    private static final String MINECRAFT_BOUND_FORMATTING = "§l§b"; // bold aqua

    private static final String DISCORD_ERROR_REPLY_REACTION_EMOJI = "❌"; // Red cross emoji (X)

    DiscordMessageListener(long channelId_) {
        channelId = channelId_;
    }

    private void discordErrorReply(MessageReceivedEvent event, String errorMessage) {
        Emoji crossEmoji = Emoji.fromUnicode(DISCORD_ERROR_REPLY_REACTION_EMOJI);
        event.getMessage().addReaction(crossEmoji).queue();
        event.getMessage().reply(errorMessage).queue();
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel().getIdLong() != channelId) return;
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getContentRaw().isBlank()) {
            discordErrorReply(event, "The message content is empty! Cannot send to MC Chat.");
            return;
        }

        String authorName = event.getAuthor().getName();
        String authorMessage = event.getMessage().getContentRaw();

        if (authorMessage.length() > MINECRAFT_CHAT_MAX_MSG_LENGTH) {
            discordErrorReply(event, "The message length is greater than " + MINECRAFT_CHAT_MAX_MSG_LENGTH + " characters! Cannot send to MC Chat.");
            return;
        }

        String messageToSend = String.format("%s[ %s ] %s", MINECRAFT_BOUND_FORMATTING, authorName, authorMessage);
        getServer().broadcastMessage(messageToSend);
    }
}
