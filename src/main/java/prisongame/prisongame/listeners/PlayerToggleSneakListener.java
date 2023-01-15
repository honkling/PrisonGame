package prisongame.prisongame.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerToggleSneakListener implements Listener {
    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        for (Entity e : event.getPlayer().getPassengers()) {
            e.leaveVehicle();
            if (e instanceof Player) {
                Player p = (Player) e;
                p.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
                p.removePotionEffect(PotionEffectType.WEAKNESS);
                p.removePotionEffect(PotionEffectType.BLINDNESS);
            }
        }
    }
}
