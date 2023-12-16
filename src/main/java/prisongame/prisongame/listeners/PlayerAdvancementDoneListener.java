package prisongame.prisongame.listeners;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import prisongame.prisongame.discord.listeners.Messages;
import prisongame.prisongame.lib.Config;

public class PlayerAdvancementDoneListener implements Listener {
    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
        if (!event.getAdvancement().getRoot().getKey().toString().equals("prison:root")) {
            event.message(null);
        } else {
            if (!Config.dev)
                Messages.INSTANCE.onGrantAdvancement(event.getPlayer(), event.getAdvancement());
            event.message(event.message().color(TextColor.color(0, 200, 0)));
        }
    }
}
