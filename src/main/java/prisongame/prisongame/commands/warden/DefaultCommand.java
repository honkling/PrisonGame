package prisongame.prisongame.commands.warden;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.MyListener;
import prisongame.prisongame.PrisonGame;

public class DefaultCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (PrisonGame.warden != null || !(sender instanceof Player) || PrisonGame.wardenCooldown > 0) {
            sender.sendMessage(ChatColor.RED + "Someone else is already the warden!");
            return true;
        }

        Player player = (Player) sender;

        if (player.getGameMode() == GameMode.SPECTATOR)
            return true;

        Player nw = (Player) sender;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + sender.getName() + " only prison:mprison");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + sender.getName() + " only prison:guard");
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (PrisonGame.type.get(p) != 0) {
                MyListener.playerJoin(p, false);
            }
            PrisonGame.type.put(p, 0);
            PrisonGame.askType.put(p, 0);
            p.playSound(p, Sound.BLOCK_END_PORTAL_SPAWN, 1, 1);
            p.sendTitle("", ChatColor.RED + nw.getName() + ChatColor.GREEN + " is the new warden!");
            PrisonGame.wardenCooldown = 20 * 6;
        }
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Warden").addPlayer(nw);
        PrisonGame.type.put(nw, -1);
        PrisonGame.warden = nw;
        PrisonGame.swat = false;
        nw.teleport(PrisonGame.active.getWardenspawn());
        nw.setCustomName(ChatColor.GRAY + "[" + ChatColor.RED + "WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + nw.getName());
        nw.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + nw.getName());
        nw.setDisplayName(ChatColor.GRAY + "[" + ChatColor.RED + "WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + nw.getName());

        ItemStack card2 = new ItemStack(Material.IRON_SHOVEL);
        ItemMeta cardm2 = card2.getItemMeta();
        cardm2.setDisplayName(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]");
        cardm2.addEnchant(Enchantment.KNOCKBACK, 1, true);
        card2.setItemMeta(cardm2);
        nw.getInventory().addItem(card2);

        nw.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        nw.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        nw.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        nw.getInventory().setBoots(new ItemStack(Material.NETHERITE_BOOTS));

        ItemStack wardenSword = new ItemStack(Material.DIAMOND_SWORD);
        wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        wardenSword.addEnchantment(Enchantment.DURABILITY, 2);

        if (nw.getPersistentDataContainer().has(PrisonGame.reinforcement, PersistentDataType.INTEGER)) {
            nw.getInventory().addItem(new ItemStack(Material.DIAMOND_AXE));
            nw.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        }

        nw.getInventory().addItem(wardenSword);
        nw.getInventory().addItem(new ItemStack(Material.BOW));
        nw.getInventory().addItem(new ItemStack(Material.ARROW, 64));
        nw.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));

        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta cardm = card.getItemMeta();
        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
        card.setItemMeta(cardm);
        nw.getInventory().addItem(card);

        if (nw.getPersistentDataContainer().has(PrisonGame.protspawn, PersistentDataType.INTEGER)) {
            if (nw.getInventory().getHelmet() != null)
                nw.getInventory().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getChestplate() != null)
                nw.getInventory().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getLeggings() != null)
                nw.getInventory().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getBoots() != null)
                nw.getInventory().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        }

        return true;
    }
}