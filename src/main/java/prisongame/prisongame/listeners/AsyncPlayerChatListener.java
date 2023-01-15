package prisongame.prisongame.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.persistence.PersistentDataType;
import prisongame.prisongame.FilteredWords;
import prisongame.prisongame.PrisonGame;

public class AsyncPlayerChatListener implements Listener {
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (PrisonGame.warden == event.getPlayer()) {
            if (!PrisonGame.word.get(event.getPlayer()).equals(event.getMessage())) {
                Bukkit.getLogger().info(event.getPlayer().getDisplayName() + ChatColor.RED + ": " + FilteredWords.filtermsg(event.getMessage()));
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!p.getPersistentDataContainer().has(PrisonGame.whiff, PersistentDataType.INTEGER)) {
                        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
                    }


                    //if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.rank, PersistentDataType.INTEGER, 0) != 1)
                    //    p.sendMessage(event.getPlayer().getPlayerListName() + ChatColor.RED + ": " + ChatColor.RED + FilteredWords.filtermsg(event.getMessage()));
                    //else {

                    try {
                        event.setFormat(event.getPlayer().getDisplayName() + ChatColor.RED + ": " + ChatColor.RED + ChatColor.translateAlternateColorCodes('&', FilteredWords.filtermsg(event.getMessage())) + " ");
                    } catch (Exception e)  {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage("hey idk why but typing the letter % COMPLETELY fucks up the chat so lol");
                    }
                    //}
                }
            } else {
                event.getPlayer().sendMessage(ChatColor.RED + "Do not spam!");
                event.setCancelled(true);
            }
        } else {
            if (!event.getPlayer().getDisplayName().contains("SOLITARY")) {
                if (PrisonGame.warden != event.getPlayer())
                    //if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.rank, PersistentDataType.INTEGER, 0) != 1)
                    //    Bukkit.broadcastMessage(event.getPlayer().getPlayerListName() + ChatColor.GRAY + ": " + ChatColor.GRAY + ChatColor.GRAY + FilteredWords.filtermsg(event.getMessage()));
                    //else {
                    if (event.getMessage().toLowerCase().equals("piggopet reference")) {
                        PrisonGame.givepig = true;
                    }
                if (!PrisonGame.word.getOrDefault(event.getPlayer(), "").equals(event.getMessage())) {
                    try {
                        event.setFormat(event.getPlayer().getDisplayName() + ChatColor.GRAY + ": " + ChatColor.GRAY + ChatColor.GRAY + FilteredWords.filtermsg(event.getMessage()));
                    } catch (Exception e) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage("hey idk why but typing the letter % COMPLETELY fucks up the chat so lol");
                    }
                    PrisonGame.word.put(event.getPlayer(), event.getMessage());
                } else {
                    event.getPlayer().sendMessage(ChatColor.RED + "Do not spam!");
                    event.setCancelled(true);
                }
            } else {
                if (PrisonGame.warden != event.getPlayer()) {
                    event.setCancelled(true);
                    Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + ChatColor.GRAY + ": " + ChatColor.DARK_GRAY + "*silenced by solitary*");
                }
            }
        }
    }
}
