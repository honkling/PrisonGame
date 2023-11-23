package prisongame.prisongame;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import prisongame.prisongame.lib.ProfileKt;
import prisongame.prisongame.lib.Role;

import java.util.Random;

public class MyListener implements Listener {

    public static void reloadBert() {

        for (Entity e : Bukkit.getWorld("world").getEntities()) {
            if (e.getType().equals(EntityType.VILLAGER))
                e.remove();
        }

        if (PrisonGame.bertrude != null) {
            PrisonGame.bertrude.remove();
            PrisonGame.bertrude = null;
        }
        PrisonGame.bertrude = (LivingEntity) Bukkit.getWorld("world").spawnEntity(PrisonGame.active.bert, EntityType.VILLAGER);
        PrisonGame.bertrude.setAI(false);
        PrisonGame.bertrude.setGravity(false);
        PrisonGame.bertrude.setCustomName("bertrude (real settings)");
        PrisonGame.bertrude.setInvulnerable(true);
    }

    public static void playerJoinignoreAsc(Player player, Boolean dontresetshit) {
        var profile = ProfileKt.getProfile(player);
        if (!dontresetshit) {
            player.getOpenInventory().getTopInventory().clear();
            player.getOpenInventory().getBottomInventory().clear();
            player.getOpenInventory().close();
            player.getInventory().clear();
        }
        if (!dontresetshit)
            profile.setEscaped(false);
        player.playSound(player, Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1, 0.75f);

        if (!dontresetshit) {
            player.setCustomName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + player.getName());
            player.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + player.getName());
            player.setDisplayName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + player.getName());

        }

        if (!dontresetshit) {
            ItemStack orangechest = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta chestmeta = (LeatherArmorMeta) orangechest.getItemMeta();
            chestmeta.setColor(Color.fromRGB(208, 133, 22));
            chestmeta.setDisplayName("Prisoner Uniform");
            orangechest.setItemMeta(chestmeta);

            ItemStack orangeleg = new ItemStack(Material.LEATHER_LEGGINGS);
            LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeleg.getItemMeta();
            orangelegItemMeta.setColor(Color.fromRGB(208, 133, 22));
            orangelegItemMeta.setDisplayName("Prisoner Uniform");
            orangeleg.setItemMeta(orangelegItemMeta);

            ItemStack orangeboot = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta orangebootItemMeta = (LeatherArmorMeta) orangeboot.getItemMeta();
            orangebootItemMeta.setColor(Color.fromRGB(40, 20, 2));
            //if (p.getPersistentDataContainer().getOrDefault(PrisonGame.rank, PersistentDataType.INTEGER, 0) == 1) {
            //    orangebootItemMeta.setColor(Color.YELLOW);
            //}
            orangebootItemMeta.setDisplayName("Prisoner Uniform");
            orangeboot.setItemMeta(orangebootItemMeta);

            player.getInventory().setChestplate(orangechest);
            player.getInventory().setLeggings(orangeleg);
            player.getInventory().setBoots(orangeboot);
        }
        if (!dontresetshit) {
            profile.setRole(Role.PRISONER);
        }
        Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
            player.teleport(PrisonGame.active.getSpwn());
        }, 5L);
        Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
            player.teleport(PrisonGame.active.getSpwn());
        }, 5L);
        if (!profile.getHardMode())
            profile.setHardMode(false);
        if (profile.getHardMode()) {
            var prisonerNumber = profile.getHardModeIdentifier();
            PlayerDisguise playerDisguise = new PlayerDisguise("pdlCAMERA");
            playerDisguise.setName("Prisoner " + prisonerNumber);
            playerDisguise.setKeepDisguiseOnPlayerDeath(true);
            DisguiseAPI.disguiseToAll(player, playerDisguise);
            player.setCustomName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + "Prisoner " + prisonerNumber);
            player.setDisplayName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + "Prisoner " + prisonerNumber);
            player.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "HARD MODE" + ChatColor.DARK_GRAY + "] " + player.getName());
        }
        if (!dontresetshit)
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners").addPlayer(player);
    }

    public static void playerJoin(Player p, Boolean dontresetshit) {
        if (!p.getDisplayName().contains("ASCENDING")) {
            playerJoinignoreAsc(p, dontresetshit);
        }
    }


}