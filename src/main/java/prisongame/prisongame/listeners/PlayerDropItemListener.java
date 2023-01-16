package prisongame.prisongame.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Role;

public class PlayerDropItemListener implements Listener {
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (PrisonGame.roles.get(event.getPlayer()) != Role.PRISONER) {
            if (!event.getItemDrop().getItemStack().getType().equals(Material.TRIPWIRE_HOOK)) {
                event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                event.getItemDrop().setItemStack(new ItemStack(Material.AIR));
            } else {
                event.setCancelled(true);
            }
        }
    }
}
