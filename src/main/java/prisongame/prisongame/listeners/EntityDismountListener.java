package prisongame.prisongame.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.event.entity.EntityDismountEvent;

public class EntityDismountListener implements Listener {
    @EventHandler
    public void onEntityDismount(EntityDismountEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (p.hasPotionEffect(PotionEffectType.DOLPHINS_GRACE)) {
                p.sendTitle(ChatColor.RED + "", "YOU'RE HANDCUFFED!", 20, 20, 20);
                event.setCancelled(true);
            }
        }
    }
}
