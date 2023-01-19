package prisongame.prisongame.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import prisongame.prisongame.MyListener;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Role;

import static prisongame.prisongame.MyListener.reloadBert;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        if (PrisonGame.isInside(event.getPlayer(), new Location(Bukkit.getWorld("world"), 2141, -57, -2084), new Location(Bukkit.getWorld("world"), 2137, -62, -2080))) {
            if (event.getMessage().equals("1775182") && PrisonGame.warden.equals(event.getPlayer())) {
                event.setCancelled(true);
                event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 2141, -60, -2087));
                event.getPlayer().sendMessage(ChatColor.GREEN + "Access granted.");
                for (Player pe : Bukkit.getOnlinePlayers()) {
                    pe.teleport(new Location(Bukkit.getWorld("world"), -2062, -50, 1945));
                }
                PrisonGame.active = PrisonGame.boat;
                PrisonGame.swapcool = (20 * 60) * 5;
                reloadBert();
                for (Player pe : Bukkit.getOnlinePlayers()) {
                    if (PrisonGame.roles.get(pe) != Role.WARDEN) {
                        MyListener.playerJoin(pe, true);
                        pe.sendTitle("New prison!", "BOAT");
                    } else {
                        pe.teleport(PrisonGame.active.getWardenspawn());
                        if (!pe.getDisplayName().contains("ASCENDING"))
                            pe.sendTitle("New prison!", "BOAT");
                    }
                }
            }
        }
    }
}
