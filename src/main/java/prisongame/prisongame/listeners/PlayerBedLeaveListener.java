package prisongame.prisongame.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import prisongame.prisongame.MyTask;

public class PlayerBedLeaveListener implements Listener {
    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        if (MyTask.bossbar.getTitle().equals("LIGHTS OUT")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Color.fromRGB(255, 59, 98) + "You can't wake up until roll call!");
        }
    }
}
