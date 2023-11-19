package prisongame.prisongame.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Role;

public class PlayerDropItemListener implements Listener {
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        var player = event.getPlayer();
        var drop = event.getItemDrop();
        var item = drop.getItemStack();
        var meta = item.getItemMeta();
        var type = item.getType();

        if (
                type == Material.BOWL ||
                type == Material.STONE_BUTTON ||
                type == Material.WOODEN_SWORD ||
                (meta != null && meta.getDisplayName().contains("Prisoner Uniform")))
            pseudoCancel(player, drop);

        if (PrisonGame.roles.get(player) != Role.PRISONER) {
            if (!type.equals(Material.TRIPWIRE_HOOK)) {
                pseudoCancel(player, drop);
            } else {
                event.setCancelled(true);
            }
        }
    }

    private void pseudoCancel(Player player, Item drop) {
        player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1, 1);
        drop.setItemStack(new ItemStack(Material.AIR));
    }
}
