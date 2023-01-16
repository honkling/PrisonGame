package prisongame.prisongame.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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
                if (event.getPlayer().isOnGround() || event.getPlayer().isSprinting()) {
                    event.getPlayer().sendMessage(ChatColor.GREEN + "You threw a player (You can throw players with handcuffs by sprint or jump)");
                    p.sendTitle("THROWN!", "");
                    p.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(20 * 15, 10));
                    p.addPotionEffect(PotionEffectType.SLOW.createEffect(20 * 15, 2));
                    p.setCooldown(Material.IRON_DOOR, 20 * 15);
                }
            }
        }
    }
}
