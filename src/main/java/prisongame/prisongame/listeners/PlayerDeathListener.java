package prisongame.prisongame.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import prisongame.prisongame.MyListener;
import prisongame.prisongame.PrisonGame;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath2(PlayerDeathEvent event) {
        Player p = event.getEntity();
        if(p.isDead()) {
            if (p.getKiller() != null) {
                p.getKiller().playSound(p.getKiller(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 2);
                if (p.hasPotionEffect(PotionEffectType.GLOWING)) {
                    p.getKiller().sendMessage(ChatColor.GREEN + "You gained a little bit of money for killing a criminal.");
                    p.getKiller().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,p.getKiller().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) + 100.0);
                } else {
                    if (PrisonGame.type.get(p.getKiller()) == 0) {
                        p.getKiller().addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 5, 0));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            if (!event.getEntity().getKiller().equals(event.getEntity())) {
                event.getEntity().getKiller().getInventory().getItemInMainHand();
                if (event.getEntity().getKiller().getInventory().getItemInMainHand().getItemMeta() != null) {
                    if (event.getEntity().getKiller().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]")) {
                        if (!event.getEntity().getKiller().hasCooldown(Material.IRON_SHOVEL)) {
                            event.setCancelled(true);
                            event.getEntity().addPotionEffect(PotionEffectType.WEAKNESS.createEffect(20 * 30, 255));
                            event.getEntity().addPotionEffect(PotionEffectType.BLINDNESS.createEffect(20 * 30, 255));
                            event.getEntity().addPotionEffect(PotionEffectType.DOLPHINS_GRACE.createEffect(20 * 30, 255));
                            event.getEntity().sendTitle(ChatColor.RED + "HANDCUFFED!", "", 20, 160, 20);
                            Player p = event.getEntity();
                            p.getKiller().addPassenger(p);
                            event.getEntity().getKiller().sendMessage(ChatColor.GREEN + "Shift to drop players.");
                            return;
                        } else {
                            event.setCancelled(true);
                            event.getEntity().getKiller().sendMessage(ChatColor.GRAY + "Your handcuffs are on cooldown/in use!");
                            return;
                        }
                    }
                }
            }
            PrisonGame.killior.put(event.getEntity(), event.getEntity().getKiller());
        }
        event.getDrops().removeIf(i -> i.getType() == Material.TRIPWIRE_HOOK);
        event.getDrops().removeIf(i -> i.getType() == Material.WOODEN_AXE);
        event.getDrops().removeIf(i -> i.getType() == Material.WOODEN_SWORD);
        event.getDrops().removeIf(i -> i.getType() == Material.CARROT_ON_A_STICK);
        event.getDrops().removeIf(i -> i.getType() == Material.IRON_DOOR);
        if (Bukkit.getWorld("world").getTime() > 16000 && Bukkit.getWorld("world").getTime() < 24000) {
            if (event.getPlayer().getKiller() != null) {
                if (event.getPlayer().getKiller().getInventory().getItemInMainHand().getType().equals(Material.TORCH)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:lightsin");
                }
            }
        }
        if (PrisonGame.type.get(event.getEntity()) == 3) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getName() + " only prison:invincible");
        }
        if (PrisonGame.type.get(event.getEntity()) != 0) {
            if (event.getEntity().getKiller() != null) {

                if (event.getEntity().getKiller().getInventory().getItemInMainHand().getType().equals(Material.WOODEN_AXE)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:killstaff");
                    PrisonGame.axekills.put(event.getEntity().getKiller(), PrisonGame.axekills.get(event.getEntity().getKiller()) + 1);
                    PrisonGame.worryachieve.put(event.getEntity().getKiller(), 0);
                    if (PrisonGame.axekills.get(event.getEntity().getKiller()) == 5) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:oneman");
                    }
                }
            }
        }
        if (PrisonGame.type.get(event.getEntity()) == 0) {
            if (event.getEntity().getKiller() != null) {
                if (PrisonGame.type.get(event.getEntity().getKiller()) == -1) {
                    if (event.getEntity().getKiller().getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:yoink");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getName() + " only prison:skillissue");
                    }
                }
            }
        }
        if (PrisonGame.warden != null) {
            if (PrisonGame.warden.equals(event.getEntity())) {
                if (event.getEntity().getKiller() != null) {
                    if (PrisonGame.type.get(event.getEntity().getKiller()) != 0) {
                        if (PrisonGame.type.get(event.getEntity().getKiller()) == 2) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:incorrectmogus");
                        }
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getName() + " only prison:dieguard");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:killwardenguard");
                        if (PrisonGame.active.getName().equals("Skeld")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:impostor");
                        }
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:killwarden");
                }
                if (PrisonGame.wardentime.get(event.getEntity()) < 0) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getName() + " only prison:afinishedstory");
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:pbb");
                    }
                }
                PrisonGame.wardenCooldown = 40;
                event.getDrops().clear();
                PrisonGame.warden = null;
                PrisonGame.type.put(event.getEntity(), 0);
                MyListener.playerJoin(event.getEntity(), false);
            }
        }
        if (PrisonGame.type.get(event.getEntity()) == 0) {
            event.setDeathMessage(ChatColor.GRAY + event.getDeathMessage());
            if (event.getEntity().getKiller() != null) {
                if (PrisonGame.type.get(event.getEntity().getKiller()) == -1) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getName() + " only prison:badluck");
                }
                if (PrisonGame.escaped.get(event.getEntity().getKiller())) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:nmng");
                }
            }
        }
        if (PrisonGame.type.get(event.getEntity()) != 0) {
            event.setDeathMessage(ChatColor.GOLD + event.getDeathMessage());
        }
    }
}