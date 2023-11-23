package prisongame.prisongame.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.ProfileKt;

public class PlayerMoveListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        var profile = ProfileKt.getProfile(event.getPlayer());
        if (PrisonGame.isInside(event.getPlayer(), PrisonGame.active.getRunpoint1(), PrisonGame.active.getRunpoint2()) && PrisonGame.active.getRunpoint1().getY() > event.getPlayer().getLocation().getY()) {
            var track = profile.getTrack();
            track += 0.5;
            profile.setTrack(track);
            event.getPlayer().sendTitle("", ChatColor.GREEN + Double.toString(track) + "/120", 0, 10, 10);
            if (track >= 120) {
                profile.setTrack(0.0);
                if (event.getPlayer().hasPotionEffect(PotionEffectType.SPEED)) {
                    event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(event.getPlayer().getPotionEffect(PotionEffectType.SPEED).getDuration() + 20 * 25, 0));
                } else {
                    event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(20 * 30, 0));
                }
            }
        }
    }
}
