package prisongame.prisongame.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;
import prisongame.prisongame.MyTask;

public class PlayerBedEnterEvent implements Listener {
    @EventHandler
    public void onPlayerBedLeave(org.bukkit.event.player.PlayerBedEnterEvent event) {
        for (Entity e : event.getPlayer().getPassengers()) {
            e.leaveVehicle();
            if (e instanceof Player) {
                Player p = (Player) e;
                p.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
                p.removePotionEffect(PotionEffectType.WEAKNESS);
                p.removePotionEffect(PotionEffectType.BLINDNESS);
                p.sleep(event.getBed().getLocation(), true);
                event.setCancelled(true);
            }
        }
    }
}
