package prisongame.prisongame;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Door;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import prisongame.prisongame.commands.staff.VanishCommand;
import prisongame.prisongame.lib.Config;
import prisongame.prisongame.lib.Keys;
import prisongame.prisongame.lib.ProfileKt;
import prisongame.prisongame.lib.Role;
import prisongame.prisongame.listeners.InventoryClickListener;

import java.text.DecimalFormat;

public class MyTask extends BukkitRunnable {

    public static Integer jobm = 1;
    static Boolean hasAlerted = true;

    static Integer timer1;
    static Integer timer2;

    public static BossBar bossbar = Bukkit.createBossBar(
            ChatColor.WHITE + "Morning",
            BarColor.WHITE,
            BarStyle.SOLID);

    @Override
    public void run() {
        PrisonGame.solitcooldown = PrisonGame.solitcooldown - 1;
        for (Player player : Bukkit.getOnlinePlayers()) {
            var profile = ProfileKt.getProfile(player);
            if (player.getPersistentDataContainer().getOrDefault(Keys.TRUST.key(), PersistentDataType.DOUBLE, 0.0) > 0) {
                player.getPersistentDataContainer().set(Keys.TRUST.key(), PersistentDataType.DOUBLE, 0.0);
            }
            DisguiseAPI.setActionBarShown(player, false);
            if (player.isSleeping()) {
                player.setNoDamageTicks(10);
            }
            if (player != PrisonGame.warden && profile.getWardenTime() != -1) {
                profile.setWardenTime(-1);
            }
            if (player.getPersistentDataContainer().has(Keys.HEAD_GUARD.key()))
                player.getPersistentDataContainer().remove(Keys.HEAD_GUARD.key());
            var timeSinceGuardKill = profile.getTimeSinceGuardKill();
            if (timeSinceGuardKill >= 0) {
                timeSinceGuardKill++;
                profile.setTimeSinceGuardKill(timeSinceGuardKill);
                if (timeSinceGuardKill / 20 / 60 >= 15) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + player.getName() + " only prison:whatuworried");
                    profile.setTimeSinceGuardKill(-1);
                }
            }
            if (PrisonGame.warden == player) {
                var wardenTime = profile.getWardenTime();
                wardenTime++;
                profile.setWardenTime(wardenTime);
                var advancement = Bukkit.getAdvancement(new NamespacedKey("prison", "dictatorship"));
                if (wardenTime / 20 / 60 >= 120 && !player.getAdvancementProgress(advancement).isDone()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + player.getName() + " only prison:dictatorship");
//                    PrisonGame.wardentime.put(p, Integer.MIN_VALUE);
                }
            }
            var solitaryTime = profile.getSolitaryTimer();
            if (solitaryTime >= 0) {
                solitaryTime--;
                profile.setSolitaryTimer(solitaryTime);
                if (solitaryTime <= 0 && player.getDisplayName().contains("SOLITARY")) {
                    PrisonGame.tptoBed(player);
                    MyListener.playerJoin(player, false);
                    player.sendMessage("You were released from solitary.");
                }
            }
            if (player.hasPotionEffect(PotionEffectType.DOLPHINS_GRACE)) {
                profile.setKiller(null);
            }
            if (PrisonGame.givepig && PrisonGame.warden != null) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + PrisonGame.warden.getName() + " only prison:piggo");
                PrisonGame.givepig = false;
            }
            if (player.getPersistentDataContainer().has(Keys.MUTED.key(), PersistentDataType.INTEGER)) {
                if (player.getPersistentDataContainer().get(Keys.MUTED.key(), PersistentDataType.INTEGER) <= 0) {
                    player.getPersistentDataContainer().remove(Keys.MUTED.key());
                } else {
                    player.getPersistentDataContainer().set(Keys.MUTED.key(), PersistentDataType.INTEGER, player.getPersistentDataContainer().get(Keys.MUTED.key(), PersistentDataType.INTEGER) - 1);
                }

            }
            if (PrisonGame.isInside(player, PrisonGame.nl("world", -50D, -53D, -109D, 0f, 0f), PrisonGame.nl("world", -48D, -59D, -107D, 0f, 0f))) {
                player.teleport(PrisonGame.nl("world", 70D, -59D, -69D, 180f, 0f));
            }
            if (player.getGameMode().equals(GameMode.ADVENTURE)) {
                player.getInventory().remove(Material.NETHERITE_SWORD);
                player.getInventory().remove(Material.SUGAR);
                if (profile.getHardMode()) {
                    player.getInventory().remove(Material.GOLDEN_APPLE);
                    player.getInventory().remove(Material.NETHERITE_HELMET);
                }
                player.getInventory().remove(Material.IRON_BARS);
            }
            if (profile.getRole() != Role.PRISONER && profile.getRole() != Role.WARDEN) {
                if (player.hasPotionEffect(PotionEffectType.UNLUCK)) {
                    player.sendMessage(ChatColor.RED + "You can't be in here!");
                    player.playSound(player, Sound.ENTITY_PILLAGER_AMBIENT, 1.5f, 0.75f);
                    player.damage(6);
                    player.removePotionEffect(PotionEffectType.UNLUCK);
                    player.teleport(PrisonGame.active.getBmout());
                }
            }
        }
        PrisonGame.swapcool -= 1;
        PrisonGame.lockdowncool -= 1;
        PrisonGame.wardenCooldown -= 1;
        if (Bukkit.getWorld("world").getTime() > 0 && Bukkit.getWorld("world").getTime() < 2000) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.removePotionEffect(PotionEffectType.BLINDNESS);
            }
            timer1 = 0;
            timer2 = 2500;
            bossbar.setTitle("ROLL CALL");
            hasAlerted = false;
        }
        if (MyTask.bossbar.getTitle().contains("ROLL CALL")) {
            Boolean allat = true;
            for (Player p : Bukkit.getOnlinePlayers()) {
                var profile = ProfileKt.getProfile(p);
                p.removePotionEffect(PotionEffectType.BAD_OMEN);
                p.getWorld().getWorldBorder().setWarningDistance(5);
                if (profile.getRole() != Role.PRISONER && profile.getHardMode()) {
                    if (!new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ()).getBlock().getType().equals(Material.LIGHT_BLUE_CONCRETE_POWDER)) {
                        p.sendTitle("", ChatColor.BLUE + "GET ONTO LIGHT BLUE POWDER OR GET FIRED!", 0, 5, 0);
                        p.addPotionEffect(PotionEffectType.HUNGER.createEffect(200, 0));
                        p.setCollidable(true);
                        p.addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 30, 0));
                        p.removePotionEffect(PotionEffectType.JUMP);
                    } else {
                        p.setFoodLevel(6);
                        if (p.hasPotionEffect(PotionEffectType.GLOWING) && p.getGameMode() != GameMode.SPECTATOR) {
                            p.sendMessage(ChatColor.GREEN + "You came to roll call!");
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
                        }
                        p.addPotionEffect(PotionEffectType.WEAKNESS.createEffect(20, 255));
                        p.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(20, 255));
                        p.removePotionEffect(PotionEffectType.HUNGER);
                        p.removePotionEffect(PotionEffectType.GLOWING);

                    }
                }
                if (profile.getRole() == Role.PRISONER && !profile.isEscaped()) {
                    if (!new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ()).getBlock().getType().equals(Material.RED_SAND)) {
                        p.sendTitle("", ChatColor.RED + "GET ONTO THE RED SAND OR YOU'LL BE KILLED!", 0, 5, 0);
                        p.addPotionEffect(PotionEffectType.HUNGER.createEffect(200, 0));
                        p.setCollidable(true);
                        allat = false;
                        p.addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 30, 0));
                        p.removePotionEffect(PotionEffectType.JUMP);
                        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                    } else {
                        if (profile.getRole() == Role.PRISONER && Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p) == Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals")) {
                            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners").addPlayer(p);
                        }
                        p.setHealth(p.getMaxHealth());
                        p.setFoodLevel(6);
                        if (p.hasPotionEffect(PotionEffectType.GLOWING) && p.getGameMode() != GameMode.SPECTATOR) {
                            p.sendMessage(ChatColor.GREEN + "You came to roll call!");
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
                        }
                        p.addPotionEffect(PotionEffectType.SLOW.createEffect(10, 255));
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
                PrisonGame.warden.sendMessage(ChatColor.GREEN + "All prisoners at roll call! +1k$!");
                PrisonGame.warden.getPersistentDataContainer().set(Keys.MONEY.key(), PersistentDataType.DOUBLE, PrisonGame.warden.getPersistentDataContainer().get(Keys.MONEY.key(), PersistentDataType.DOUBLE) + 1000.0);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                        p.sendTitle("", ChatColor.BOLD + bossbar.getTitle(), 20, 40, 20);
                    }, 4);
                    p.playSound(p, Sound.BLOCK_BELL_USE, 1, 1);
                    Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                        p.playSound(p, Sound.BLOCK_BELL_USE, 1, 1);
                    }, 4);
                    Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                        p.playSound(p, Sound.BLOCK_BELL_USE, 1, 1);
                    }, 8);
                }
                Bukkit.getWorld("world").setTime(timer2 + 1);
            }
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.setCollidable(true);
                p.removePotionEffect(PotionEffectType.JUMP);
            }
        }
        if (Bukkit.getWorld("world").getTime() == 2000) {
            if (!hasAlerted) {
                hasAlerted = true;
                Double taxcount = 0.0;
                DecimalFormat numberFormat = new DecimalFormat("#0.0");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    var profile = ProfileKt.getProfile(p);
                    if (p.hasPotionEffect(PotionEffectType.GLOWING) && !profile.isEscaped()) {
                        Bukkit.broadcastMessage(ChatColor.RED + p.getName() + ChatColor.GOLD + " didn't come to roll call! " + ChatColor.RED + "Kill them for 100 dollars!");
                        p.sendTitle("", ChatColor.RED + "COME TO ROLL CALL NEXT TIME!", 0, 60, 0);
                        if (profile.getHardMode()) {
                            p.damage(99999);
                            if (profile.getRole() != Role.PRISONER) {
                                if (PrisonGame.warden != null) {
                                    if (PrisonGame.warden.equals(p)) {
                                        PrisonGame.warden = null;
                                    }
                                }
                                profile.setRole(Role.PRISONER);
                            }
                        }
                        p.playSound(p, Sound.ENTITY_SILVERFISH_AMBIENT, 1, 0.25f);
                    }
                    if (!p.hasPotionEffect(PotionEffectType.GLOWING) && !profile.isEscaped() && profile.getAttendedLightsOut() == true) {
                        profile.setAttendedLightsOut(false);
                        profile.setAttendedRollCall(true);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:respect");
                    }
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() > 2000 && Bukkit.getWorld("world").getTime() < 4000) {
            timer1 = 2000;
            timer2 = 4000;
            bossbar.setTitle("Breakfast");
            for (Player p : Bukkit.getOnlinePlayers()) {
                var profile = ProfileKt.getProfile(p);
                if (!profile.getHardMode() && !profile.isEscaped() && profile.getRole() == Role.PRISONER)
                    p.addPotionEffect(PotionEffectType.SATURATION.createEffect(120, 0));
            }
        }
        if (Bukkit.getWorld("world").getTime() > 4000 && Bukkit.getWorld("world").getTime() < 7000) {
            timer1 = 4000;
            timer2 = 7000;
            bossbar.setTitle("Free Time");
            jobm = 1;
        }
        if (Bukkit.getWorld("world").getTime() > 7000 && Bukkit.getWorld("world").getTime() < 10000) {
            timer1 = 7000;
            timer2 = 10000;
            bossbar.setTitle("Job Time");
            jobm = 2;
        }
        if (Bukkit.getWorld("world").getTime() > 10000 && Bukkit.getWorld("world").getTime() < 13000) {
            timer1 = 10000;
            timer2 = 13000;
            bossbar.setTitle("Lunch");
            jobm = 1;
            for (Player p : Bukkit.getOnlinePlayers()) {
                var profile = ProfileKt.getProfile(p);
                if (!profile.getHardMode() && !profile.isEscaped() && profile.getRole() == Role.PRISONER)
                    p.addPotionEffect(PotionEffectType.SATURATION.createEffect(120, 0));
            }
        }
        if (Bukkit.getWorld("world").getTime() > 13000 && Bukkit.getWorld("world").getTime() < 15000) {
            hasAlerted = false;
            timer1 = 13000;
            timer2 = 15000;
            bossbar.setTitle("EVENING ROLL CALL");
            jobm = 1;
        }
        if (Bukkit.getWorld("world").getTime() == 15000) {
            if (!hasAlerted) {
                hasAlerted = true;
                Double taxcount = 0.0;
                DecimalFormat numberFormat = new DecimalFormat("#0.0");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    var profile = ProfileKt.getProfile(p);
                    if (profile.getSolitaryTimer() != -1)
                        continue;
                    if (p.hasPotionEffect(PotionEffectType.GLOWING) && !profile.isEscaped()) {
                        Bukkit.broadcastMessage(ChatColor.RED + p.getName() + ChatColor.GOLD + " didn't come to roll call! " + ChatColor.RED + "Kill them for 100 dollars!");
                        p.sendTitle("", ChatColor.RED + "COME TO ROLL CALL NEXT TIME!", 0, 60, 0);
                        if (profile.getHardMode()) {
                            p.damage(99999);
                            if (profile.getRole() != Role.PRISONER) {
                                if (PrisonGame.warden != null) {
                                    if (PrisonGame.warden.equals(p)) {
                                        PrisonGame.warden = null;
                                    }
                                }
                                profile.setRole(Role.PRISONER);
                            }
                        }
                        p.playSound(p, Sound.ENTITY_SILVERFISH_AMBIENT, 1, 0.25f);
                    }
                    if (!p.hasPotionEffect(PotionEffectType.GLOWING) && !profile.isEscaped() && profile.getAttendedLightsOut()) {
                        profile.setAttendedLightsOut(false);
                        profile.setAttendedRollCall(true);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:respect");
                    }
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() > 15000 && Bukkit.getWorld("world").getTime() < 16000) {
            timer1 = 15000;
            timer2 = 16000;
            bossbar.setTitle("Cell Time");
            jobm = 1;
        }
        if (Bukkit.getWorld("world").getTime() > 23300) {
            Bukkit.getWorld("world").setTime(0);
        }
        if (Bukkit.getWorld("world").getTime() == 16000) {
            Bukkit.broadcastMessage(ChatColor.RED + "All cells have been automatically closed! " + ChatColor.DARK_RED + "GET TO SLEEP!");
            LivingEntity le = (LivingEntity) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -40, -58, -973), EntityType.ZOMBIE);
            le.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(999999, 255));
            le.addPotionEffect(PotionEffectType.SPEED.createEffect(999999, 2));
            le.setPersistent(true);
            le.setInvulnerable(true);
            le.setSilent(true);
            PlayerDisguise playerDisguise = new PlayerDisguise("FreddyFazbearXXX");
            le.setCustomName("fredy");
            playerDisguise.setName("fredy");
            DisguiseAPI.disguiseEntity(le, playerDisguise);

            LivingEntity le2 = (LivingEntity) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -40, -58, -973), EntityType.ZOMBIE);
            le2.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(999999, 255));
            le2.addPotionEffect(PotionEffectType.SPEED.createEffect(999999, 2));
            le2.setPersistent(true);
            le2.setInvulnerable(true);
            le2.setSilent(true);
            PlayerDisguise playerDisguise2 = new PlayerDisguise("Freakedoutt");
            le2.setCustomName("boner");
            playerDisguise2.setName("boner");
            DisguiseAPI.disguiseEntity(le2, playerDisguise2);

            LivingEntity le3 = (LivingEntity) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -40, -58, -973), EntityType.ZOMBIE);
            le3.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(999999, 255));
            le3.addPotionEffect(PotionEffectType.SPEED.createEffect(999999, 2));
            le3.setPersistent(true);
            le3.setInvulnerable(true);
            le3.setSilent(true);
            PlayerDisguise playerDisguise3 = new PlayerDisguise("minekaufcraft");
            le3.setCustomName("chicker");
            playerDisguise3.setName("chicker");
            DisguiseAPI.disguiseEntity(le3, playerDisguise3);

            LivingEntity le4 = (LivingEntity) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -40, -58, -973), EntityType.ZOMBIE);
            le4.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(999999, 255));
            le4.addPotionEffect(PotionEffectType.SPEED.createEffect(999999, 2));
            le4.setPersistent(true);
            le4.setInvulnerable(true);
            le4.setSilent(true);
            PlayerDisguise playerDisguise4 = new PlayerDisguise("Paignton");
            le4.setCustomName("foxer");
            playerDisguise4.setName("foxer");
            DisguiseAPI.disguiseEntity(le4, playerDisguise4);

            switch (PrisonGame.active.getName()) {
                case "Gaeae Fort":
                    Location[] doors = {
                            new Location(Bukkit.getWorld("world"), 48, -59, -135),
                            new Location(Bukkit.getWorld("world"), 47, -59, -134),
                            new Location(Bukkit.getWorld("world"), 29, -59, -107),
                            new Location(Bukkit.getWorld("world"),  21, -60, -102),
                            new Location(Bukkit.getWorld("world"), 14, -60, -108),
                            new Location(Bukkit.getWorld("world"),  12, -60, -117),
                            new Location(Bukkit.getWorld("world"),23, -59, -120)
                    };
                    for (Location l : doors) {
                        BlockState state = l.getBlock().getState();
                        Door openable = (Door) state.getBlockData();
                        openable.setOpen(false);
                        state.setBlockData(openable);
                        state.update();
                        l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_CLOSE, 0.75f, 0.75f);
                    }
                    break;
                case "Hypertech":
                    Location[] doors2 = {
                            new Location(Bukkit.getWorld("world"), -18, -59, -996),
                            new Location(Bukkit.getWorld("world"), -14, -59, -996),
                            new Location(Bukkit.getWorld("world"), -14, -59, -991),
                            new Location(Bukkit.getWorld("world"), -14, -59, -1006),
                            new Location(Bukkit.getWorld("world"), -18, -59, -1006),
                            new Location(Bukkit.getWorld("world"), -14, -59, -1011),
                            new Location(Bukkit.getWorld("world"), -18, -59, -1011),
                            new Location(Bukkit.getWorld("world"), -18, -52, -996),
                            new Location(Bukkit.getWorld("world"), -14, -52, -996),
                            new Location(Bukkit.getWorld("world"), -14, -52, -991)
                    };
                    for (Location l : doors2) {
                        BlockState state = l.getBlock().getState();
                        Door openable = (Door) state.getBlockData();
                        openable.setOpen(false);
                        state.setBlockData(openable);
                        state.update();
                        l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_CLOSE, 0.75f, 0.75f);
                    }
                    break;
                case "Train":
                    Location[] doors3 = {
                            new Location(Bukkit.getWorld("world"), 92, -58, 961),
                            new Location(Bukkit.getWorld("world"), 91, -58, 961),
                            new Location(Bukkit.getWorld("world"), 92, -58, 964),
                            new Location(Bukkit.getWorld("world"), 91, -58, 964),
                            new Location(Bukkit.getWorld("world"), 92, -58, 969),
                            new Location(Bukkit.getWorld("world"), 91, -58, 969),
                            new Location(Bukkit.getWorld("world"), 92, -58, 972),
                            new Location(Bukkit.getWorld("world"), 91, -58, 972),
                            new Location(Bukkit.getWorld("world"), 92, -58, 980),
                            new Location(Bukkit.getWorld("world"), 91, -58, 980)
                    };
                    for (Location l : doors3) {
                        BlockState state = l.getBlock().getState();
                        Door openable = (Door) state.getBlockData();
                        openable.setOpen(false);
                        state.setBlockData(openable);
                        state.update();
                        l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_CLOSE, 0.75f, 0.75f);
                    }
                    break;
                case "Gladiator":
                    Location[] doors4 = {
                            new Location(Bukkit.getWorld("world"), -2049, -60, 1950),
                            new Location(Bukkit.getWorld("world"), -2043, -60, 1950),
                            new Location(Bukkit.getWorld("world"), -2037, -60, 1950),
                            new Location(Bukkit.getWorld("world"), -2021, -60, 1950),
                            new Location(Bukkit.getWorld("world"), -2015, -60, 1950),
                            new Location(Bukkit.getWorld("world"), -2009, -60, 1950),
                            new Location(Bukkit.getWorld("world"), -2049, -60, 2002),
                            new Location(Bukkit.getWorld("world"), -2043, -60, 2002),
                            new Location(Bukkit.getWorld("world"), -2037, -60, 2002),
                            new Location(Bukkit.getWorld("world"), -2021, -60, 2002),
                            new Location(Bukkit.getWorld("world"), -2015, -60, 2002),
                            new Location(Bukkit.getWorld("world"), -2009, -60, 2002)
                    };
                    for (Location l : doors4) {
                        BlockState state = l.getBlock().getState();
                        Door openable = (Door) state.getBlockData();
                        openable.setOpen(false);
                        state.setBlockData(openable);
                        state.update();
                        l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_CLOSE, 0.75f, 0.75f);
                    }
                    break;
                case "Island":
                    Location[] doors5 = {
                            new Location(Bukkit.getWorld("world"), 1955, -56, -1987),
                            new Location(Bukkit.getWorld("world"), 1962, -56, -1987),
                            new Location(Bukkit.getWorld("world"), 1955, -56, -1983),
                            new Location(Bukkit.getWorld("world"), 1962, -56, -1983),
                            new Location(Bukkit.getWorld("world"), 1955, -56, -1979),
                            new Location(Bukkit.getWorld("world"), 1962, -56, -1979),

                            new Location(Bukkit.getWorld("world"), 1955, -60, -1987),
                            new Location(Bukkit.getWorld("world"), 1962, -60, -1987),
                            new Location(Bukkit.getWorld("world"), 1955, -60, -1983),
                            new Location(Bukkit.getWorld("world"), 1962, -60, -1983),
                            new Location(Bukkit.getWorld("world"), 1955, -60, -1979),
                            new Location(Bukkit.getWorld("world"), 1962, -60, -1979)
                    };
                    for (Location l : doors5) {
                        BlockState state = l.getBlock().getState();
                        Door openable = (Door) state.getBlockData();
                        openable.setOpen(false);
                        state.setBlockData(openable);
                        state.update();
                        l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_CLOSE, 0.75f, 0.75f);
                    }
                    break;
                case "Santa's Workshop":
                    Location[] doors6 = {
                            new Location(Bukkit.getWorld("world"), 1964, -60, 1931),
                            new Location(Bukkit.getWorld("world"), 1958, -60, 1931),
                            new Location(Bukkit.getWorld("world"), 1964, -60, 1926),
                            new Location(Bukkit.getWorld("world"), 1958, -60, 1926),
                            new Location(Bukkit.getWorld("world"), 1964, -60, 1921),
                            new Location(Bukkit.getWorld("world"), 1958, -60, 1921),
                            new Location(Bukkit.getWorld("world"), 1964, -60, 1916),
                            new Location(Bukkit.getWorld("world"), 1958, -60, 1916),
                            new Location(Bukkit.getWorld("world"), 1964, -60, 1911),
                            new Location(Bukkit.getWorld("world"), 1958, -60, 1911),
                            new Location(Bukkit.getWorld("world"), 1964, -60, 1906),
                            new Location(Bukkit.getWorld("world"), 1958, -60, 1906),
                            new Location(Bukkit.getWorld("world"), 1964, -60, 1901),
                            new Location(Bukkit.getWorld("world"), 1958, -60, 1901)

                    };
                    for (Location l : doors6) {
                        BlockState state = l.getBlock().getState();
                        Door openable = (Door) state.getBlockData();
                        openable.setOpen(false);
                        state.setBlockData(openable);
                        state.update();
                        l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_CLOSE, 0.75f, 0.75f);
                    }
                    break;
                case "Volcano":
                    Location[] doors7 = {
                            new Location(Bukkit.getWorld("world"), -2019, -60, -1976),
                            new Location(Bukkit.getWorld("world"), -2020, -60, -1976)

                    };
                    for (Location l : doors7) {
                        BlockState state = l.getBlock().getState();
                        Door openable = (Door) state.getBlockData();
                        openable.setOpen(false);
                        state.setBlockData(openable);
                        state.update();
                        l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_CLOSE, 0.75f, 0.75f);
                    }
                    break;
            }
        }
        if (Bukkit.getWorld("world").getTime() < 16000) {
            for (Entity e : Bukkit.getWorld("world").getEntities()) {
                if (e.getCustomName() != null) {
                    if (e.getCustomName().equals("fredy") || e.getCustomName().equals("boner") || e.getCustomName().equals("foxer") || e.getCustomName().equals("chicker")) {
                        e.remove();
                    }
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() > 0 && Bukkit.getWorld("world").getTime() < 20) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (PrisonGame.isInside(p, new Location(Bukkit.getWorld("world"), -35, -55, -991), new Location(Bukkit.getWorld("world"), -59, -61, -968))) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:freder");
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() > 16000 && Bukkit.getWorld("world").getTime() < 24000) {
            timer1 = 16000;
            timer2 = 24000;
            bossbar.setTitle("LIGHTS OUT");
            for (Player p : Bukkit.getOnlinePlayers()) {
                var profile = ProfileKt.getProfile(p);
                if (profile.getHardMode()) {
                    if (p.hasPotionEffect(PotionEffectType.LUCK)) {
                        p.addPotionEffect(PotionEffectType.LUCK.createEffect(20 * 15, 0));
                        p.addPotionEffect(PotionEffectType.BAD_OMEN.createEffect(999999, 0));
                        p.sendTitle("", ChatColor.RED + "You will be respawned at roll call!", 0, 20 * 3, 0);
                    }
                }
                if (profile.getRole() == Role.PRISONER && !profile.isEscaped()) {
                    p.getWorld().getWorldBorder().setWarningDistance(Integer.MAX_VALUE);
                    if (p.getWorld().getName().equals("endprison")) {
                        if (!new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ()).getBlock().getType().equals(Material.JIGSAW)) {
                            if (!p.hasPotionEffect(PotionEffectType.LUCK)) {
                                p.sendTitle("", ChatColor.RED + "GET TO A CELL OR YOU'LL BE KILLED!", 0, 20 * 3, 0);
                                p.addPotionEffect(PotionEffectType.HUNGER.createEffect(200, 0));
                                p.addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 30, 0));
                                if (profile.getHardMode()) {
                                    if (p.getHealth() > 1) {
                                        p.setHealth(1);
                                    }
                                }
                                Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                            } else {
                                Bukkit.getWorld("world").setTime(Bukkit.getWorld("world").getTime() + 2);
                                if (profile.getRole() == Role.PRISONER && Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p) == Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals")) {
                                    Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners").addPlayer(p);
                                }
                                p.removePotionEffect(PotionEffectType.HUNGER);
                                p.removePotionEffect(PotionEffectType.GLOWING);
                            }
                        }
                    } else {
                        if (!p.isSleeping()) {
                            if (!p.hasPotionEffect(PotionEffectType.LUCK)) {
                                p.sendTitle("", ChatColor.RED + "GET TO SLEEP IN A BED OR YOU'LL BE KILLED!", 0, 20 * 3, 0);
                                p.addPotionEffect(PotionEffectType.HUNGER.createEffect(200, 0));
                                p.addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 30, 0));
                                if (profile.getHardMode()) {
                                    if (p.getHealth() > 1) {
                                        p.setHealth(1);
                                    }
                                }
                                Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                                profile.setAttendedLightsOut(false);
                            } else {
                                profile.setAttendedLightsOut(true);
                                Bukkit.getWorld("world").setTime(Bukkit.getWorld("world").getTime() + 2);
                                if (profile.getRole() == Role.PRISONER && Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p) == Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals")) {
                                    Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners").addPlayer(p);
                                }
                                p.removePotionEffect(PotionEffectType.HUNGER);
                                p.removePotionEffect(PotionEffectType.GLOWING);

                            }
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
        bossbar.setTitle(ChatColor.BOLD + bossbar.getTitle());
        bossbar.setProgress(Math.max(0, Math.min(1, ((float) Bukkit.getWorld("world").getTime() - (float) timer1) / ((float) timer2 - (float) timer1))));
        if (PrisonGame.active.getName().equals("Barreled")) {
            bossbar.setTitle(bossbar.getTitle() + ChatColor.GREEN + " Power: " + PrisonGame.BBpower);
            if (Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"),-1023,-57,-994)).getType().equals(Material.AIR)) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    var profile = ProfileKt.getProfile(p);
                    if (profile.getRole().equals(Role.PRISONER)) {
                        p.addPotionEffect(PotionEffectType.SPEED.createEffect(20*5,0));
                    }
                }
            } else {

            }
        }
        if (Bukkit.getWorld("world").getTime() == timer2) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                    p.sendTitle("", ChatColor.BOLD + bossbar.getTitle(), 20, 40, 20);
                }, 4);
                p.playSound(p, Sound.BLOCK_BELL_USE, 1, 1);
                Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                    p.playSound(p, Sound.BLOCK_BELL_USE, 1, 1);
                }, 4);
                Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                    p.playSound(p, Sound.BLOCK_BELL_USE, 1, 1);
                }, 8);
            }
        }

        var format = new DecimalFormat("#0.0");
        var timer = PrisonGame.mm.deserialize(PrisonGame.warden == null
                ? "(No Warden!)"
                : ProfileKt.getProfile(PrisonGame.warden).getWardenTime() / (20 * 60) + "m");

        var wardenDisplay = PrisonGame.warden == null
                ? PrisonGame.mm.deserialize("No warden!")
                : PrisonGame.mm.deserialize(
                        "<name> <ping>\n<red>(<health> HP <aqua><damage></aqua>) for <time>",
                        Placeholder.component("damage", PrisonGame.mm.deserialize(String.format(
                                "[%ss of No-Damage]",
                                PrisonGame.warden.getNoDamageTicks() / 20
                        ))),
                        Placeholder.component("name", PrisonGame.warden.displayName()),
                        Placeholder.component("ping", PrisonGame.getPingDisplay(PrisonGame.warden)),
                        Placeholder.component("health", PrisonGame.mm.deserialize("" + Math.round(PrisonGame.warden.getHealth()))),
                        Placeholder.component("time", timer)
                );

        var guardCount = 0;
        var prisonerCount = 0;

        var guards = Component.empty();
        var prisoners = Component.empty();

        for (var player : Bukkit.getOnlinePlayers()) {
            var profile = ProfileKt.getProfile(player);
            var role = profile.getRole();

            switch (role) {
                case NURSE, GUARD, SWAT -> {
                    if (!player.getPersistentDataContainer().has(VanishCommand.VANISHED)) {
                        guards = guards.append(player
                                .displayName()
                                .append(Component.space())
                                .append(PrisonGame.getPingDisplay(player))
                                .append(Component.newline()));
                    }
                    guardCount++;
                }
                case PRISONER -> {
                    if (player.isDead() || player.hasPotionEffect(PotionEffectType.LUCK)) {
                        prisoners = prisoners.append(PrisonGame.mm.deserialize(
                                "<dark_red>☠ <gray><player>\n",
                                Placeholder.component("player", player.name())
                        ));
                        prisonerCount++;
                        break;
                    }
                    if (!player.getPersistentDataContainer().has(VanishCommand.VANISHED)) {
                        prisoners = prisoners.append(player
                                .displayName()
                                .append(Component.space())
                                .append(PrisonGame.getPingDisplay(player))
                                .append(Component.newline()));
                    }
                    prisonerCount++;
                }
            }
        }

        var tab = PrisonGame.mm.deserialize("""
                <gray>---
                <yellow>PrisonButBad</yellow> - <white>made by agmass!</white>
                <green>Players: <player-count></green>
                <red><warden></red>
                ---
                
                <aqua>Guards (<guard-count>):</aqua>
                
                <guards>
                
                ---
                
                <gold>Prisoners (<prisoner-count>):</gold>
                
                <prisoners>
                <padding>
                """,
                Placeholder.component("player-count", PrisonGame.mm.deserialize("" + Bukkit.getOnlinePlayers().size())),
                Placeholder.component("warden", wardenDisplay),
                Placeholder.component("guard-count", PrisonGame.mm.deserialize("" + guardCount)),
                Placeholder.component("guards", guards),
                Placeholder.component("prisoner-count", PrisonGame.mm.deserialize("" + prisonerCount)),
                Placeholder.component("prisoners", prisoners),
                Placeholder.component("padding", PrisonGame.mm.deserialize("\n".repeat(50)))
        );
        String civs = "";

        Integer pris = 0;
        Integer guard = 0;

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (PrisonGame.isInside(p, new Location(Bukkit.getWorld("world"), -2011, -53, -1929), new Location(Bukkit.getWorld("world"), -2009, -57, -1931))) {
                if (PrisonGame.warden.equals(p)) {
                    InventoryClickListener.switchMap(Config.prisons.get("nether"));
                } else {
                    p.sendTitle("", ChatColor.RED + "YOU MUST BE WARDEN!", 0, 50, 0);
                }
            }
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            var profile = ProfileKt.getProfile(p);
            if (p.getName().contains("RennArpent")) {
                p.setDisplayName(p.getDisplayName().replace("FUNDER", "fund deez nuts"));
                p.setDisplayName(p.getDisplayName().replace("WARDEN", "nah fuck this dude"));
                p.setDisplayName(p.getDisplayName().replace("GUARD", "worst guard"));
                p.setDisplayName(p.getDisplayName().replace("NURSE", "L nurse"));
                p.setDisplayName(p.getDisplayName().replace("SWAT", "who gave him swat"));
                p.setDisplayName(p.getDisplayName().replace("PRISONER", "idiot -->"));
                p.setDisplayName(p.getDisplayName().replace("SOLITARY", "deserved"));
                p.setDisplayName(p.getDisplayName().replace("VISITOR", "i wish he was a prisoner"));
                p.setDisplayName(p.getDisplayName().replace("CRIMINAL", "*snore* mimimimimi"));
            }
            if (p.getLocation().getBlockY() == -60 && PrisonGame.active.getName().equals("Train") && PrisonGame.isInside(p, PrisonGame.nl("world", 27D, -61D, 920D, 0f, 0f), PrisonGame.nl("world", 129D, 8D, 1041D, 0f, 0f))) {
                p.damage(999);
            }
            if (p.getLocation().getBlockY() == -61 && PrisonGame.active.getName().equals("Island") && p.isInWater()) {
                p.damage(4);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:dewater");
            }
            if (p.getLocation().getBlockY() <= -53 && PrisonGame.active.getName().equals("Boat") && p.isInWater()) {
                p.damage(1);
                p.setNoDamageTicks(1);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:dewater");
            }
            if (p.getLocation().getY() < 118 && p.getWorld().getName().equals("endprison")) {
                p.damage(999);
            }
            if (p.getPersistentDataContainer().has(Keys.NIGHT_VISION.key(), PersistentDataType.INTEGER)) {
                p.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(99999, 255));
            } else {
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }
            if (p.getGameMode().equals(GameMode.SURVIVAL))
                p.setGameMode(GameMode.ADVENTURE);
            if (p.hasPotionEffect(PotionEffectType.LUCK) || p.hasPotionEffect(PotionEffectType.BAD_OMEN)) {
                if (p.getGameMode().equals(GameMode.SPECTATOR)) {
                    if (PrisonGame.warden != null) {
                        p.setSpectatorTarget(PrisonGame.warden);
                        p.sendActionBar(new TextComponent(ChatColor.GRAY + "CURRENTLY SPECTATING THE WARDEN"));
                    } else {
                        PrisonGame.tptoBed(p);
                        p.sendActionBar(new TextComponent(ChatColor.GRAY + "NO WARDEN TO SPECTATE!"));
                    }
                }
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
            if (profile.getHardMode()) {
                if (p.isSprinting() && !profile.isEscaped() && profile.getRole() == Role.PRISONER) {
                    p.setFoodLevel(p.getFoodLevel() - 1);
                    p.sendTitle("", ChatColor.RED + "You can only sprint when you've escaped! [HARD MODE]", 0, 5, 0);
                }
            }

            if (p.getInventory().getHelmet() != null) {
                if (!p.getInventory().getHelmet().getType().equals(Material.YELLOW_WOOL)) {
                    p.setMaxHealth(20);
                } else {
                    p.addPotionEffect(PotionEffectType.SPEED.createEffect(20, 2));
                }
            } else {
                p.setMaxHealth(20);
            }


            if (p.getPersistentDataContainer().has(Keys.SEMICLOAK.key(), PersistentDataType.INTEGER)) {
                p.getPersistentDataContainer().set(Keys.MONEY.key(), PersistentDataType.DOUBLE, p.getPersistentDataContainer().get(Keys.MONEY.key(), PersistentDataType.DOUBLE)+1000);
                p.sendMessage(ChatColor.RED + "Your semicloak ascension has been refunded and removed as ascensions have been removed!");
                p.getPersistentDataContainer().remove(Keys.SEMICLOAK.key());
            }
            if (p.getPersistentDataContainer().has(Keys.REINFORCEMENT.key(), PersistentDataType.INTEGER)) {
                p.getPersistentDataContainer().set(Keys.MONEY.key(), PersistentDataType.DOUBLE, p.getPersistentDataContainer().get(Keys.MONEY.key(), PersistentDataType.DOUBLE)+1000);
                p.sendMessage(ChatColor.RED + "Your Reinforcement ascension has been refunded and removed as ascensions have been removed!");
                p.getPersistentDataContainer().remove(Keys.REINFORCEMENT.key());
            }
            if (p.getPersistentDataContainer().has(Keys.SPAWN_PROTECTION.key(), PersistentDataType.INTEGER)) {
                p.getPersistentDataContainer().set(Keys.MONEY.key(), PersistentDataType.DOUBLE, p.getPersistentDataContainer().get(Keys.MONEY.key(), PersistentDataType.DOUBLE)+1000);
                p.sendMessage(ChatColor.RED + "Your protspawn ascension has been refunded and removed as ascensions have been removed!");
                p.getPersistentDataContainer().remove(Keys.SPAWN_PROTECTION.key());
            }
            if (p.getPersistentDataContainer().has(Keys.RANDOM_ITEMS.key(), PersistentDataType.INTEGER)) {
                p.sendMessage(ChatColor.RED + "Your Random item ascension has been refunded and removed as ascensions have been removed!");
                p.getPersistentDataContainer().set(Keys.MONEY.key(), PersistentDataType.DOUBLE, p.getPersistentDataContainer().get(Keys.MONEY.key(), PersistentDataType.DOUBLE)+1000);
                p.getPersistentDataContainer().remove(Keys.RANDOM_ITEMS.key());
            }
            if (p.getInventory().getItemInMainHand().getItemMeta() != null) {
                var mainHand = p.getInventory().getItemInMainHand();
                var meta = mainHand.getItemMeta();
                if (meta != null && !meta.getDisplayName().equals(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]")) {
                    p.setWalkSpeed(0.2f);
                    p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4.4);
                } else {
                    p.getInventory().getItemInMainHand().setDurability((short) 3);
                    p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(9999);
                }
            }

            for (Player pe : Bukkit.getOnlinePlayers()) {
                if (!p.canSee(pe) && p.isOnline()) {
                    if (!pe.isInsideVehicle() && pe.isOnline() && !pe.getPersistentDataContainer().has(VanishCommand.VANISHED)) {
                        p.showPlayer(PrisonGame.getPlugin(PrisonGame.class), pe);
                    }
                }
            }

            if (p.hasPotionEffect(PotionEffectType.DOLPHINS_GRACE)) {
                if (p.isInsideVehicle() && p.isOnline()) {
                    if (p.getVehicle() instanceof Player) {
                        ((Player) p.getVehicle()).setCooldown(Material.IRON_SHOVEL, 20 * 5);
                        ((Player) p.getVehicle()).hidePlayer(PrisonGame.getPlugin(PrisonGame.class), p);
                    }
                }
                p.setNoDamageTicks(20);
                p.addPotionEffect(PotionEffectType.WEAKNESS.createEffect(20, 255));
            }
            if (p.hasPotionEffect(PotionEffectType.DOLPHINS_GRACE)) {
                p.removePotionEffect(PotionEffectType.GLOWING);
            }

            if (!profile.getHardMode()) {
                if (PrisonGame.api.getPlayerAdapter(Player.class).getUser(p).getCachedData().getMetaData().getPrefix() != null) {
                    if (!p.getDisplayName().contains(PrisonGame.api.getPlayerAdapter(Player.class).getUser(p).getCachedData().getMetaData().getPrefix())) {
                        p.setCustomName(ChatColor.GRAY + "[" + PrisonGame.api.getPlayerAdapter(Player.class).getUser(p).getCachedData().getMetaData().getPrefix() + ChatColor.GRAY + "] " + p.getDisplayName());
                        p.setPlayerListName(ChatColor.GRAY + "[" + PrisonGame.api.getPlayerAdapter(Player.class).getUser(p).getCachedData().getMetaData().getPrefix() + ChatColor.GRAY + "] " + p.getDisplayName());
                        p.setDisplayName(ChatColor.GRAY + "[" + PrisonGame.api.getPlayerAdapter(Player.class).getUser(p).getCachedData().getMetaData().getPrefix() + ChatColor.GRAY + "] " + p.getDisplayName());
                    }
                }
            } else {
                p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "HARD MODE" + ChatColor.GRAY + "] " + ChatColor.GRAY + p.getName());
            }
            if (!p.getPersistentDataContainer().has(Keys.OLD_TAB.key())) {
                p.sendPlayerListHeader(tab);
            } else {
                p.sendPlayerListHeaderAndFooter(Component.empty(), PrisonGame.mm.deserialize("Imagine using old tab. Actual pussy move ngl"));
            }
            /*if (p.hasPotionEffect(PotionEffectType.GLOWING)) {
                if (PrisonGame.type.get(p) == 0 && Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p) == Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners")) {
                    Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                }
            }*/
            bossbar.addPlayer(p);
            if (p.getInventory().getItemInMainHand().getType().equals(Material.COOKED_CHICKEN)) {
                p.kickPlayer("");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " 5m Abuse of Illegals [AUTO]");
            }
            if (p.getInventory().getItemInMainHand().hasItemMeta()) {
                if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]")) {
                    if (profile.getRole() == Role.PRISONER) {
                        p.getInventory().remove(p.getInventory().getItemInMainHand());
                    }
                }
            }
            if (p.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.DAMAGE_ALL)) {
                if (p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_SWORD)) {
                    if (profile.getRole() != Role.WARDEN) {
                        p.sendMessage("no illegal 4 u!");
                        p.getInventory().getItemInMainHand().removeEnchantment(Enchantment.DAMAGE_ALL);
                        p.kickPlayer("");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " 5m Abuse of Illegals [AUTO]");
                    }
                }
            }
            if (p.getLocation().getBlock().getType().equals(Material.VOID_AIR)) {
                if (profile.getRole().equals(Role.PRISONER)) {
                    if (!p.hasPotionEffect(PotionEffectType.GLOWING)) {
                        Boolean yesdothat = true;
                        if (p.getInventory().getChestplate() != null) {
                            if (p.getInventory().getChestplate().getItemMeta() != null) {
                                if (p.getInventory().getChestplate().getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "Cloak")) {
                                    yesdothat = false;
                                }
                            }
                        }
                        if (yesdothat) {
                            for (ItemStack i : p.getInventory()) {
                                if (i != null) {
                                    if (i.getItemMeta().getDisplayName().contains("[CONTRABAND]") || i.getType().equals(Material.STONE_SWORD) || i.getType().equals(Material.IRON_SWORD) || i.getType().equals(Material.IRON_HELMET) || i.getType().equals(Material.IRON_CHESTPLATE) || i.getType().equals(Material.IRON_LEGGINGS) || i.getType().equals(Material.IRON_BOOTS)) {
                                        if (!p.isInsideVehicle() && p.isOnline()) {
                                            p.addPotionEffect(PotionEffectType.GLOWING.createEffect(1200, 0));
                                            p.sendMessage(ChatColor.RED + "You were caught with contraband!");
                                            for (Player g : Bukkit.getOnlinePlayers()) {
                                                var profile2 = ProfileKt.getProfile(g);
                                                if (profile2.getRole() != Role.PRISONER) {
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
                }
            }
            if (profile.getRole() == Role.NURSE) {
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
        DecimalFormat numberFormat = new DecimalFormat("#0.0");
        DecimalFormat numberFormat2 = new DecimalFormat("#0.000");
        DecimalFormat whole = new DecimalFormat("#");
        if (PrisonGame.warden == null) {
            String wardentime = ChatColor.RED + "None! Use '/warden' to become the prison warden!";

            if (PrisonGame.wardenCooldown > 0) {
                wardentime = ChatColor.RED + "None! Warden command is on cooldown! " + net.md_5.bungee.api.ChatColor.GRAY + "[" + Math.round((float) PrisonGame.wardenCooldown / 20f) + "]";
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.getDisplayName().contains("ASCENDING")) {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + PrisonGame.formatBalance(Keys.MONEY.get(p, 0.0)) + "$" + ChatColor.GRAY + " || " + ChatColor.GRAY + "Current Warden: " + wardentime));
                } else {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent((ChatColor.AQUA + whole.format(p.getPersistentDataContainer().getOrDefault(Keys.ASCENSION_COINS.key(), PersistentDataType.DOUBLE, 0.0)) + " ascension coins.")));
                }
            }
        } else {
            if (!PrisonGame.warden.isOnline()) {
                PrisonGame.warden = null;
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                var profile = ProfileKt.getProfile(p);
                if (!p.getDisplayName().contains("ASCENDING") || !p.getGameMode().equals(GameMode.SPECTATOR)) {
                    if (p != PrisonGame.warden) {
                        Role role = profile.getRole();
                        String acbar = "";
                        if (p.getDisplayName().contains("SOLITARY"))
                            acbar = "||" + ChatColor.DARK_GRAY + " SOLITARY [ " + numberFormat.format(profile.getSolitaryTimer() / 20) + " seconds left. ]";
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + numberFormat.format(p.getPersistentDataContainer().getOrDefault(Keys.MONEY.key(), PersistentDataType.DOUBLE, 0.0)) + "$" + ChatColor.GRAY + acbar + ChatColor.GRAY + " || Current Warden: " + ChatColor.DARK_RED + PrisonGame.warden.getName()));
                    } else {
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + numberFormat.format(p.getPersistentDataContainer().getOrDefault(Keys.MONEY.key(), PersistentDataType.DOUBLE, 0.0)) + "$" + ChatColor.GRAY + " || " + ChatColor.GRAY + "Current Warden: " + ChatColor.GREEN + "You! Use \"/warden help\"!"));
                    }
                }
                if (p.getDisplayName().contains("ASCENDING")) {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent((ChatColor.AQUA + "you have " + whole.format(p.getPersistentDataContainer().getOrDefault(Keys.ASCENSION_COINS.key(), PersistentDataType.DOUBLE, 0.0)) + " ascension coins.")));
                }
            }
        }
    }
}
