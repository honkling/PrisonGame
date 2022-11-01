package prisongame.prisongame;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class MyTask extends BukkitRunnable {

    static Integer jobm = 1;

    static BossBar bossbar = Bukkit.createBossBar(
            ChatColor.WHITE + "Morning",
            BarColor.WHITE,
            BarStyle.SOLID);;

    @Override
    public void run(){
        if (Bukkit.getWorld("world").getTime() > 0 && Bukkit.getWorld("world").getTime() < 1000) {
            bossbar.setTitle("ROLL CALL");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (PrisonGame.type.get(p) == 0 && !PrisonGame.escaped.get(p)) {
                    if (!new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ()).getBlock().getType().equals(Material.RED_SAND))
                    {
                        p.sendTitle("", ChatColor.RED + "GET ONTO THE YARD'S RED SAND!", 0, 20 * 3, 0);
                        p.addPotionEffect(PotionEffectType.SPEED.createEffect(200, 0));
                        p.addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 30, 0));
                        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                    } else {
                        if (PrisonGame.type.get(p) == 0 && Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p) == Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals")) {
                            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners").addPlayer(p);
                        }
                        p.removePotionEffect(PotionEffectType.SPEED);
                        p.removePotionEffect(PotionEffectType.GLOWING);

                    }
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() > 1000 && Bukkit.getWorld("world").getTime() < 3000) {
            bossbar.setTitle("Breakfast (Hunger Regen)");
            for (Player p :Bukkit.getOnlinePlayers()) {
                p.addPotionEffect(PotionEffectType.SATURATION.createEffect(120, 0));
            }
        }
        if (Bukkit.getWorld("world").getTime() > 3000 && Bukkit.getWorld("world").getTime() < 6000) {
            bossbar.setTitle("Free Time");
            jobm = 1;
        }
        if (Bukkit.getWorld("world").getTime() > 6000 && Bukkit.getWorld("world").getTime() < 10000) {
            bossbar.setTitle("Job Time");
            jobm = 2;
        }
        if (Bukkit.getWorld("world").getTime() > 10000 && Bukkit.getWorld("world").getTime() < 13000) {
            bossbar.setTitle("Cell Time");
            jobm = 1;
        }
        if (Bukkit.getWorld("world").getTime() > 13000 && Bukkit.getWorld("world").getTime() < 24000) {
            bossbar.setTitle("LIGHTS OUT");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (PrisonGame.type.get(p) == 0 && !PrisonGame.escaped.get(p)) {
                    if (!p.isSleeping()) {
                        p.sendTitle("", ChatColor.RED + "GET TO SLEEP!", 0, 20 * 3, 0);
                        p.addPotionEffect(PotionEffectType.SPEED.createEffect(200, 0));
                        p.addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 30, 0));
                        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                    } else {
                        if (PrisonGame.type.get(p) == 0 && Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p) == Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals")) {
                            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners").addPlayer(p);
                        }
                        p.removePotionEffect(PotionEffectType.SPEED);
                        p.removePotionEffect(PotionEffectType.GLOWING);

                    }
                }
            }
            Bukkit.getWorld("world").setTime(Bukkit.getWorld("world").getTime() + 2);
            bossbar.setColor(BarColor.RED);
            bossbar.addFlag(BarFlag.DARKEN_SKY);
            bossbar.addFlag(BarFlag.CREATE_FOG);
        } else {
            bossbar.setColor(BarColor.WHITE);
            bossbar.removeFlag(BarFlag.DARKEN_SKY);
            bossbar.removeFlag(BarFlag.CREATE_FOG);
        }
        for (Player p :Bukkit.getOnlinePlayers()) {
            if (p.getGameMode().equals(GameMode.SPECTATOR) && p.hasPotionEffect(PotionEffectType.DARKNESS)) {
                p.teleport(PrisonGame.active.nursebed);
                if (p.getName().equals("Jacco100") && !p.getPlayerListName().contains("REPORTER")) {
                    p.setCustomName(ChatColor.GRAY + "[" + ChatColor.GREEN + "REPORTER" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.GREEN + "REPORTER" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.GREEN + "REPORTER" + ChatColor.GRAY + "] " + p.getDisplayName());
                }
                if (p.hasPotionEffect(PotionEffectType.GLOWING)) {
                    if (PrisonGame.type.get(p) == 0 && Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p) == Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners")) {
                        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                    }
                }
                bossbar.addPlayer(p);
                if (p.getLocation().getBlock().getType().equals(Material.VOID_AIR)) {
                    if (PrisonGame.type.get(p).equals(0)) {
                        if (!p.hasPotionEffect(PotionEffectType.GLOWING)) {
                            for (ItemStack i : p.getInventory()) {
                                if (i != null) {
                                    if (i.getItemMeta().getDisplayName().contains("[CONTRABAND]") || i.getType().equals(Material.STONE_SWORD) || i.getType().equals(Material.IRON_SWORD) || i.getType().equals(Material.IRON_HELMET) || i.getType().equals(Material.IRON_CHESTPLATE) || i.getType().equals(Material.IRON_LEGGINGS) || i.getType().equals(Material.IRON_BOOTS)) {
                                        p.addPotionEffect(PotionEffectType.GLOWING.createEffect(1200, 0));
                                        p.sendMessage(ChatColor.RED + "You were caught with contraband!");
                                        for (Player g : Bukkit.getOnlinePlayers()) {
                                            if (PrisonGame.type.get(g) != 0) {
                                                g.playSound(g, Sound.ENTITY_SILVERFISH_DEATH, 1, 0.5f);
                                                g.sendMessage(ChatColor.RED + p.getName() + ChatColor.DARK_RED + " was caught with contraband!");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!PrisonGame.type.containsKey(p)) {
                PrisonGame.type.put(p, 0);
                MyListener.playerJoin(p);
            }
            if (!PrisonGame.st.containsKey(p)) {
                PrisonGame.st.put(p, 0.0);
            }
            if (!PrisonGame.sp.containsKey(p)) {
                PrisonGame.sp.put(p, 0.0);
            }
            if (!PrisonGame.escaped.containsKey(p)) {
                PrisonGame.escaped.put(p, false);
            }
            if (PrisonGame.type.get(p) == 2) {
                if (!p.getInventory().contains(Material.SPLASH_POTION)) {
                    ItemStack pot = new ItemStack(Material.SPLASH_POTION);
                    PotionMeta potionMeta = (PotionMeta) pot.getItemMeta();
                    potionMeta.addCustomEffect(PotionEffectType.HEAL.createEffect(10, 0), true);
                    pot.setItemMeta(potionMeta);

                    p.getInventory().addItem(pot);
                    p.setCooldown(Material.SPLASH_POTION, 40);
                }
            }
        }
        if (PrisonGame.warden == null) {
            for (Player p :Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0).toString() + "$" + ChatColor.GRAY + " || "  + ChatColor.GRAY + "Current Warden: " + ChatColor.RED + " None! Use '/warden' to become the prison warden!"));
            }
        } else {
            if (!PrisonGame.warden.isOnline()) {
                PrisonGame.warden = null;
            }
            for (Player p :Bukkit.getOnlinePlayers()) {
                if (p != PrisonGame.warden) {
                    if (p.getInventory().contains(Material.STRUCTURE_VOID)) {
                        p.getInventory().remove(Material.STRUCTURE_VOID);
                    }
                    switch (PrisonGame.type.get(p)) {
                        case 0:
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0).toString() + "$" + ChatColor.GRAY + " || " + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + " || Current Warden: " + ChatColor.DARK_RED + PrisonGame.warden.getName()));
                            break;
                        case 1:
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0).toString() + "$" + ChatColor.GRAY + " || " + ChatColor.BLUE  + "GUARD" + ChatColor.GRAY +  " (/resign to resign)" + ChatColor.GRAY + " || Current Warden: " + ChatColor.DARK_RED + PrisonGame.warden.getName()));
                            break;
                        case 2: p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0).toString() + "$" + ChatColor.GRAY + " || " + ChatColor.LIGHT_PURPLE  + "NURSE" + ChatColor.GRAY +  " (/resign to resign)" + ChatColor.GRAY + " || Current Warden: " + ChatColor.DARK_RED + PrisonGame.warden.getName()));
                    }
                } else {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0).toString() + "$" + ChatColor.GRAY + " || " + ChatColor.GRAY + "Current Warden: " + ChatColor.GREEN + "You! Use '/warden help' to see warden commands!"));
                }
            }
        }
    }
}