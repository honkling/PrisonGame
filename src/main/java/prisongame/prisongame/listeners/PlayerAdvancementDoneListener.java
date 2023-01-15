package prisongame.prisongame.listeners;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class PlayerAdvancementDoneListener implements Listener {
    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
        if (!event.getAdvancement().getRoot().getKey().toString().equals("prison:root")) {
            event.message(null);
        } else {
            event.message(event.message().color(TextColor.color(0, 200, 0)));
        }
    }
}
