package net.urpagin.discordlink.minecraft.listeners;

import net.urpagin.discordlink.DiscordInterface;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MinecraftPlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        DiscordInterface.updateActivityPlaying();
    }
}
