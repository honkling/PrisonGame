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

import java.text.DecimalFormat;

public class MyTask extends BukkitRunnable {

    static Integer jobm = 1;
    static Boolean hasAlerted = true;

    static Integer timer1;
    static Integer timer2;

    static BossBar bossbar = Bukkit.createBossBar(
            ChatColor.WHITE + "Morning",
            BarColor.WHITE,
            BarStyle.SOLID);;

    @Override
    public void run(){
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.isSleeping()) {
                p.setNoDamageTicks(10);
            }
            if (!PrisonGame.type.containsKey(p)) {
                PrisonGame.type.put(p, 0);
                MyListener.playerJoin(p, false);
            }
            if (!PrisonGame.st.containsKey(p)) {
                PrisonGame.st.put(p, 0.0);
            }
            if (!PrisonGame.sp.containsKey(p)) {
                PrisonGame.sp.put(p, 0.0);
            }
            if (!PrisonGame.lastward.containsKey(p)) {
                PrisonGame.lastward.put(p, 20 * 10);
            }
            PrisonGame.lastward.put(p, PrisonGame.lastward.get(p) + 1);
            if (!PrisonGame.lastward2.containsKey(p)) {
                PrisonGame.lastward2.put(p, 0);
            }
            if (!PrisonGame.wardenban.containsKey(p)) {
                PrisonGame.wardenban.put(p, 0);
            }
            if (PrisonGame.lastward.get(p) > 20 * 5) {
                PrisonGame.lastward2.put(p, 0);
            }
            if (!PrisonGame.escaped.containsKey(p)) {
                PrisonGame.escaped.put(p, false);
            }
            p.getInventory().remove(Material.NETHERITE_SWORD);
            if (PrisonGame.type.get(p) != 0 && PrisonGame.type.get(p) != -1) {
                if (p.hasPotionEffect(PotionEffectType.UNLUCK)) {
                    p.sendMessage(ChatColor.RED + "You can't be in here!");
                    p.playSound(p, Sound.ENTITY_PILLAGER_AMBIENT, 1.5f, 0.75f);
                    p.damage(6);
                    p.removePotionEffect(PotionEffectType.UNLUCK);
                    p.teleport(PrisonGame.active.getBmout());
                }
            }
        }
        PrisonGame.swapcool -= 1;
        PrisonGame.lockdowncool -= 1;
        if (Bukkit.getWorld("world").getTime() > 0 && Bukkit.getWorld("world").getTime() < 2500) {
            timer1 = 0;
            timer2 = 2500;
            bossbar.setTitle("ROLL CALL");
            hasAlerted = false;
            Boolean allat = true;
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.getWorld().getWorldBorder().setWarningDistance(5);
                if (PrisonGame.type.get(p) == 0 && !PrisonGame.escaped.get(p)) {
                    if (!new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ()).getBlock().getType().equals(Material.RED_SAND))
                    {
                        p.sendTitle("", ChatColor.RED + "GET ONTO THE RED SAND OR YOU'LL BE KILLED!", 0, 5, 0);
                        p.addPotionEffect(PotionEffectType.HUNGER.createEffect(200, 0));
                        p.setCollidable(true);
                        allat = false;
                        p.addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 30, 0));
                        p.removePotionEffect(PotionEffectType.JUMP);
                        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                    } else {
                        if (PrisonGame.type.get(p) == 0 && Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p) == Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals")) {
                            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners").addPlayer(p);
                        }
                        if (p.hasPotionEffect(PotionEffectType.GLOWING)) {
                            p.sendMessage(ChatColor.GREEN + "You came to roll call!");
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
                        }
                        p.addPotionEffect(PotionEffectType.SLOW.createEffect(10, 255));
                        if (p.isOnGround())
                            p.addPotionEffect(PotionEffectType.JUMP.createEffect(20, -25));
                        p.addPotionEffect(PotionEffectType.WEAKNESS.createEffect(20, 255));
                        p.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(20, 255));
                        p.setCollidable(false);
                        p.removePotionEffect(PotionEffectType.HUNGER);
                        p.removePotionEffect(PotionEffectType.GLOWING);

                    }
                }
            }
            if (allat && PrisonGame.warden != null) {
                PrisonGame.warden.sendTitle("",  ChatColor.GREEN + "All prisoner at roll call! +0.05$!", 0, 5, 0);
                PrisonGame.warden.getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, PrisonGame.warden.getPersistentDataContainer().get(PrisonGame.mny, PersistentDataType.DOUBLE) + 0.05);
            }
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.setCollidable(true);
                p.removePotionEffect(PotionEffectType.JUMP);
            }
        }
        if (Bukkit.getWorld("world").getTime() == 2500) {
            if (!hasAlerted) {
                hasAlerted = true;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPotionEffect(PotionEffectType.GLOWING) && !PrisonGame.escaped.get(p)) {
                        Bukkit.broadcastMessage(ChatColor.RED + p.getName() + ChatColor.GOLD + " didn't come to roll call! " + ChatColor.RED + "Kill them for 100 dollars!");
                        p.sendTitle("", ChatColor.RED + "COME TO ROLL CALL NEXT TIME!", 0, 60, 0);
                        p.playSound(p, Sound.ENTITY_SILVERFISH_AMBIENT, 1, 0.25f);
                    }
                    DecimalFormat numberFormat = new DecimalFormat("#.00");
                    p.sendMessage(ChatColor.GREEN + numberFormat.format(p.getPersistentDataContainer().get(PrisonGame.mny,  PersistentDataType.DOUBLE) * 0.1) + "$ was taxed towards the prison.");
                    p.getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) * 0.9);
                    PrisonGame.warden.getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, PrisonGame.warden.getPersistentDataContainer().get(PrisonGame.mny, PersistentDataType.DOUBLE) + p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) * 0.1);
                }
                PrisonGame.warden.sendMessage(ChatColor.GREEN + "You were given your taxes!");
            }
        }
        if (Bukkit.getWorld("world").getTime() > 2500 && Bukkit.getWorld("world").getTime() < 3000) {
            timer1 = 2500;
            timer2 = 3000;
            bossbar.setTitle("Breakfast (Hunger Regen)");
            for (Player p :Bukkit.getOnlinePlayers()) {
                p.addPotionEffect(PotionEffectType.SATURATION.createEffect(120, 0));
            }
        }
        if (Bukkit.getWorld("world").getTime() > 3000 && Bukkit.getWorld("world").getTime() < 6000) {
            timer1 = 3000;
            timer2 = 6000;
            bossbar.setTitle("Free Time");
            jobm = 1;
        }
        if (Bukkit.getWorld("world").getTime() > 6000 && Bukkit.getWorld("world").getTime() < 10000) {
            timer1 = 6000;
            timer2 = 10000;
            bossbar.setTitle("Job Time");
            jobm = 2;
        }
        if (Bukkit.getWorld("world").getTime() > 10000 && Bukkit.getWorld("world").getTime() < 13000) {
            timer1 = 10000;
            timer2 = 13000;
            bossbar.setTitle("Cell Time");
            jobm = 1;
        }
        if (Bukkit.getWorld("world").getTime() > 23800 ) {
            Bukkit.getWorld("world").setTime(Bukkit.getWorld("world").getTime() + 20);
        }
        if (Bukkit.getWorld("world").getTime() > 13000 && Bukkit.getWorld("world").getTime() < 24000) {
            timer1 = 13000;
            timer2 = 24000;
            bossbar.setTitle("LIGHTS OUT");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (PrisonGame.type.get(p) == 0 && !PrisonGame.escaped.get(p)) {
                    p.getWorld().getWorldBorder().setWarningDistance(Integer.MAX_VALUE);
                    if (p.getWorld().getName().equals("endprison")) {
                        if (!new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ()).getBlock().getType().equals(Material.JIGSAW)) {
                            p.sendTitle("", ChatColor.RED + "GET TO A CELL OR YOU'LL BE KILLED!", 0, 20 * 3, 0);
                            p.addPotionEffect(PotionEffectType.HUNGER.createEffect(200, 0));
                            p.addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 30, 0));
                            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                        } else {
                            Bukkit.getWorld("world").setTime(Bukkit.getWorld("world").getTime() + 2);
                            if (PrisonGame.type.get(p) == 0 && Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p) == Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals")) {
                                Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners").addPlayer(p);
                            }
                            p.removePotionEffect(PotionEffectType.HUNGER);
                            p.removePotionEffect(PotionEffectType.GLOWING);

                        }
                    } else {
                        if (!p.isSleeping()) {
                            p.sendTitle("", ChatColor.RED + "GET TO SLEEP IN A BED OR YOU'LL BE KILLED!", 0, 20 * 3, 0);
                            p.addPotionEffect(PotionEffectType.HUNGER.createEffect(200, 0));
                            p.addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 30, 0));
                            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                        } else {
                            Bukkit.getWorld("world").setTime(Bukkit.getWorld("world").getTime() + 2);
                            if (PrisonGame.type.get(p) == 0 && Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p) == Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals")) {
                                Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners").addPlayer(p);
                            }
                            p.removePotionEffect(PotionEffectType.HUNGER);
                            p.removePotionEffect(PotionEffectType.GLOWING);

                        }
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
            if (!PrisonGame.active.getName().equals("Island"))
                bossbar.removeFlag(BarFlag.CREATE_FOG);
        }
        if (PrisonGame.active.getName().equals("Island")) {
            bossbar.addFlag(BarFlag.CREATE_FOG);
        }
        bossbar.setProgress(((float) Bukkit.getWorld("world").getTime() - (float) timer1) / ((float) timer2 - (float) timer1));
        if (Bukkit.getWorld("world").getTime() == timer2) {
            for (Player p :Bukkit.getOnlinePlayers()) {
                Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> { p.sendTitle("", ChatColor.BOLD + bossbar.getTitle(), 20, 40, 20);}, 4);
                p.playSound(p, Sound.BLOCK_BELL_USE, 1, 1);
                Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> { p.playSound(p, Sound.BLOCK_BELL_USE, 1, 1);}, 4);
                Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> { p.playSound(p, Sound.BLOCK_BELL_USE, 1, 1);}, 8);
            }
        }
        for (Player p :Bukkit.getOnlinePlayers()) {
            //if (!p.getPersistentDataContainer().has(PrisonGame.rank, PersistentDataType.INTEGER)) {
            //    p.getPersistentDataContainer().set(PrisonGame.rank, PersistentDataType.INTEGER, 0);
            //}
            if (p.getLocation().getBlockY() == -60 && PrisonGame.active.getName().equals("Train")) {
                p.damage(999);
            }
            if (p.getLocation().getBlockY() == -61 && PrisonGame.active.getName().equals("Island") && p.getLocation().getBlock().getType().equals(Material.WATER)) {
                p.damage(4);
            }
            if (p.getLocation().getY() < 118 && p.getWorld().getName().equals("endprison")) {
                p.damage(999);
            }
            p.setWalkSpeed(0.2f);
            if (p.getPersistentDataContainer().has(PrisonGame.nightvis, PersistentDataType.INTEGER)) {
                p.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(99999, 255));
            } else {
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }
            if (p.getGameMode().equals(GameMode.SURVIVAL))
                p.setGameMode(GameMode.ADVENTURE);
            if (p.hasPotionEffect(PotionEffectType.LUCK)) {
                p.setGameMode(GameMode.SPECTATOR);
                p.teleport(PrisonGame.active.getNursebed());
            } else {
                if (p.getGameMode().equals(GameMode.SPECTATOR)) {
                    p.setGameMode(GameMode.ADVENTURE);
                }
            }
            /*if (p.getPersistentDataContainer().getOrDefault(PrisonGame.rank, PersistentDataType.INTEGER, 0) == 1) {
                p.setCustomName(ChatColor.GRAY + "[" + ChatColor.GOLD + "SUPPORTER" + ChatColor.GRAY + "] " + p.getDisplayName());
                p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.GOLD + "SUPPORTER" + ChatColor.GRAY + "] " + p.getDisplayName());
                p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.GOLD + "SUPPORTER" + ChatColor.GRAY + "] " + p.getDisplayName());
            }*/
            if (p.getName().equals("Jacco100") && !p.getPlayerListName().contains("REPORTER") || p.getName().equals("Goodgamer121") && !p.getPlayerListName().contains("REPORTER") || p.getName().equals("Evanbeer") && !p.getPlayerListName().contains("REPORTER") || p.getName().equals("teuli") && !p.getPlayerListName().contains("REPORTER")) {
                p.setCustomName(ChatColor.GRAY + "[" + ChatColor.GREEN + "REPORTER" + ChatColor.GRAY + "] " + p.getDisplayName());
                p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.GREEN + "REPORTER" + ChatColor.GRAY + "] " + p.getDisplayName());
                p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.GREEN + "REPORTER" + ChatColor.GRAY + "] " + p.getDisplayName());
            }
            if (p.getName().equals("Evanbeer") && !p.getPlayerListName().contains("BUILDER")) {
                p.setCustomName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + p.getDisplayName());
                p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + p.getDisplayName());
                p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + p.getDisplayName());
            }
            if (p.getName().equals("noahbt787") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("ATee_") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("Kyrris") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("adam214") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("JuleczkaOwO") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("RennArpent") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("Kingdarksword") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("Susanne_h") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("Sanan1010") && !p.getPlayerListName().contains("BUILD HELP")) {
                p.setCustomName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILD HELP" + ChatColor.GRAY + "] " + p.getDisplayName());
                p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILD HELP" + ChatColor.GRAY + "] " + p.getDisplayName());
                p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILD HELP" + ChatColor.GRAY + "] " + p.getDisplayName());
            }
            /*if (p.hasPotionEffect(PotionEffectType.GLOWING)) {
                if (PrisonGame.type.get(p) == 0 && Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p) == Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners")) {
                    Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                }
            }*/
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
                                    break;
                                }
                            }
                        }
                    }
                }
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
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        if (PrisonGame.warden == null) {
            for (Player p :Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + numberFormat.format(p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)) + "$" + ChatColor.GRAY + " || "  + ChatColor.GRAY + "Current Warden: " + ChatColor.RED + " None! Use '/warden' to become the prison warden!"));
            }
        } else {
            if (!PrisonGame.warden.isOnline()) {
                PrisonGame.warden = null;
            }
            for (Player p :Bukkit.getOnlinePlayers()) {
                if (p != PrisonGame.warden) {
                    switch (PrisonGame.type.get(p)) {
                        case 0:
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + numberFormat.format(p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0))+ "$" + ChatColor.GRAY + " || " + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + " || Current Warden: " + ChatColor.DARK_RED + PrisonGame.warden.getName() + ChatColor.RED + " (" + Math.round(PrisonGame.warden.getHealth()) + " HP)"));
                            break;
                        case 1:
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + numberFormat.format(p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)) + "$" + ChatColor.GRAY + " || " + ChatColor.BLUE  + "GUARD" + ChatColor.GRAY +  " (/resign to resign)" + ChatColor.GRAY + " || Current Warden: " + ChatColor.DARK_RED + PrisonGame.warden.getName() + ChatColor.RED + " (" + Math.round(PrisonGame.warden.getHealth()) + " HP)"));
                            break;
                        case 3:
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + numberFormat.format(p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)) + "$" + ChatColor.GRAY + " || " + ChatColor.DARK_GRAY  + "SWAT" + ChatColor.GRAY +  " (/resign to resign)" + ChatColor.GRAY + " || Current Warden: " + ChatColor.DARK_RED + PrisonGame.warden.getName() + ChatColor.RED + " (" + Math.round(PrisonGame.warden.getHealth()) + " HP)"));
                            break;
                        case 2: p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + numberFormat.format(p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)) + "$" + ChatColor.GRAY + " || " + ChatColor.LIGHT_PURPLE  + "NURSE" + ChatColor.GRAY +  " (/resign to resign)" + ChatColor.GRAY + " || Current Warden: " + ChatColor.DARK_RED + PrisonGame.warden.getName() + ChatColor.RED + " (" + Math.round(PrisonGame.warden.getHealth()) + " HP)"));
                    }
                } else {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + numberFormat.format(p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)) + "$" + ChatColor.GRAY + " || " + ChatColor.GRAY + "Current Warden: " + ChatColor.GREEN + "You! Use '/warden help' to see warden commands!"));
                }
            }
        }
    }
}