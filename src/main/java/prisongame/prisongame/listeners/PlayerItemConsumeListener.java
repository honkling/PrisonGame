package prisongame.prisongame.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerItemConsumeListener implements Listener {
    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if (event.getPlayer().hasCooldown(event.getItem().getType()))
            event.setCancelled(true);
        if (event.getItem().getType().equals(Material.GOLDEN_APPLE)) {
            event.getPlayer().setCooldown(Material.GOLDEN_APPLE, 20 * 60);
        }
    }
}
