package prisongame.prisongame;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

public class CommandKit implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (PrisonGame.warden == null && sender instanceof Player) {
                Player nw = (Player) sender;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (PrisonGame.type.get(p) != 0) {
                        MyListener.playerJoin(p);
                    }
                    PrisonGame.type.put(p, 0);
                    p.playSound(p, Sound.BLOCK_END_PORTAL_SPAWN, 1, 1);
                    p.sendTitle("", ChatColor.RED + nw.getName() + ChatColor.GREEN + " is the new warden!");

                }
                PrisonGame.type.put(nw, -1);
                PrisonGame.warden = nw;
                nw.teleport(new Location(Bukkit.getWorld("world"), -3, -59, -129));
                nw.setCustomName(ChatColor.GRAY + "[" + ChatColor.RED + "WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + nw.getName());
                nw.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + nw.getName());
                nw.setDisplayName(ChatColor.GRAY + "[" + ChatColor.RED + "WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + nw.getName());

                if (nw.getName().equals("agmass")) {
                    nw.setCustomName(ChatColor.GRAY + "[" + ChatColor.RED + "OWNER" + ChatColor.GRAY + "] " + nw.getDisplayName());
                    nw.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "OWNER" + ChatColor.GRAY + "] " + nw.getDisplayName());
                    nw.setDisplayName(ChatColor.GRAY + "[" + ChatColor.RED + "OWNER" + ChatColor.GRAY + "] " + nw.getDisplayName());
                }

                if (nw.getName().equals("ClownCaked") || nw.getName().equals("4950")) {
                    nw.setCustomName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + nw.getDisplayName());
                    nw.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + nw.getDisplayName());
                    nw.setDisplayName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + nw.getDisplayName());
                }


                nw.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                nw.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                nw.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                nw.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));

                ItemStack wardenSword = new ItemStack(Material.IRON_SWORD);
                wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                wardenSword.addEnchantment(Enchantment.DURABILITY, 2);

                nw.getInventory().addItem(wardenSword);
                nw.getInventory().addItem(new ItemStack(Material.BOW));
                nw.getInventory().addItem(new ItemStack(Material.ARROW, 64));
                nw.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));

                ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
                ItemMeta cardm = card.getItemMeta();
                cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
                card.setItemMeta(cardm);
                nw.getInventory().addItem(card);

            } else {
                sender.sendMessage(ChatColor.RED + "Someone else is already the warden!");
            }
        } else if (PrisonGame.warden.equals(sender)) {
            if (args[0].equals("guard")) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    Player g = Bukkit.getPlayer(args[1]);
                    if (g.isOnline() && g != sender && PrisonGame.type.get(g) == 0) {
                        PrisonGame.type.put(g, 1);
                        g.sendMessage(ChatColor.BLUE + "You were promoted by " + ChatColor.DARK_GRAY + " Warden " + ChatColor.RED + sender.getName());
                        Bukkit.broadcastMessage(ChatColor.BLUE + g.getName() + " was promoted to a guard!");

                        g.setCustomName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                        g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                        g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());

                        if (g.getName().equals("agmass")) {
                            g.setCustomName(ChatColor.GRAY + "[" + ChatColor.RED + "OWNER" + ChatColor.GRAY + "] " + g.getDisplayName());
                            g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "OWNER" + ChatColor.GRAY + "] " + g.getDisplayName());
                            g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.RED + "OWNER" + ChatColor.GRAY + "] " + g.getDisplayName());
                        }

                        if (g.getName().equals("ClownCaked") || g.getName().equals("4950")) {
                            g.setCustomName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + g.getDisplayName());
                            g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + g.getDisplayName());
                            g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + g.getDisplayName());
                        }

                        ItemStack orangechest = new ItemStack(Material.LEATHER_CHESTPLATE);
                        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        LeatherArmorMeta chestmeta = (LeatherArmorMeta) orangechest.getItemMeta();
                        chestmeta.setColor(Color.fromRGB(126, 135, 245));
                        orangechest.setItemMeta(chestmeta);

                        ItemStack orangeleg = new ItemStack(Material.LEATHER_LEGGINGS);
                        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeleg.getItemMeta();
                        orangelegItemMeta.setColor(Color.fromRGB(126, 135, 245));
                        orangeleg.setItemMeta(orangelegItemMeta);

                        ItemStack orangeboot = new ItemStack(Material.LEATHER_BOOTS);
                        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        LeatherArmorMeta orangebootItemMeta = (LeatherArmorMeta) orangeboot.getItemMeta();
                        orangebootItemMeta.setColor(Color.fromRGB(126, 135, 245));
                        orangeboot.setItemMeta(orangebootItemMeta);

                        g.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
                        g.getInventory().setChestplate(orangechest);
                        g.getInventory().setLeggings(orangeleg);
                        g.getInventory().setBoots(orangeboot);

                        ItemStack wardenSword = new ItemStack(Material.STONE_SWORD);
                        wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                        wardenSword.addEnchantment(Enchantment.DURABILITY, 1);

                        g.getInventory().addItem(wardenSword);

                        g.getInventory().addItem(new ItemStack(Material.CROSSBOW));
                        g.getInventory().addItem(new ItemStack(Material.ARROW, 16));
                        g.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));

                        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
                        card.setItemMeta(cardm);
                        g.getInventory().addItem(card);



                    } else {
                        sender.sendMessage(ChatColor.BLUE + "We had troubles promoting this player.");
                    }
                }
            }
            if (args[0].equals("nurse")) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    Player g = Bukkit.getPlayer(args[1]);
                    if (g.isOnline() && g != sender && PrisonGame.type.get(g) == 0) {
                        PrisonGame.type.put(g, 2);
                        g.sendMessage(ChatColor.LIGHT_PURPLE + "You were promoted by " + ChatColor.DARK_GRAY + " Warden " + ChatColor.RED + sender.getName());
                        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + g.getName() + " was promoted to a nurse!");

                        g.setCustomName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                        g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                        g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());

                        if (g.getName().equals("agmass")) {
                            g.setCustomName(ChatColor.GRAY + "[" + ChatColor.RED + "OWNER" + ChatColor.GRAY + "] " + g.getDisplayName());
                            g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "OWNER" + ChatColor.GRAY + "] " + g.getDisplayName());
                            g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.RED + "OWNER" + ChatColor.GRAY + "] " + g.getDisplayName());
                        }

                        if (g.getName().equals("ClownCaked") || g.getName().equals("4950")) {
                            g.setCustomName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + g.getDisplayName());
                            g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + g.getDisplayName());
                            g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + g.getDisplayName());
                        }

                        ItemStack orangechest = new ItemStack(Material.LEATHER_CHESTPLATE);
                        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        LeatherArmorMeta chestmeta = (LeatherArmorMeta) orangechest.getItemMeta();
                        chestmeta.setColor(Color.PURPLE);
                        orangechest.setItemMeta(chestmeta);

                        ItemStack orangeleg = new ItemStack(Material.LEATHER_LEGGINGS);
                        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeleg.getItemMeta();
                        orangelegItemMeta.setColor(Color.PURPLE);
                        orangeleg.setItemMeta(orangelegItemMeta);

                        ItemStack orangeboot = new ItemStack(Material.LEATHER_BOOTS);
                        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        LeatherArmorMeta orangebootItemMeta = (LeatherArmorMeta) orangeboot.getItemMeta();
                        orangebootItemMeta.setColor(Color.PURPLE);
                        orangeboot.setItemMeta(orangebootItemMeta);

                        g.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                        g.getInventory().setChestplate(orangechest);
                        g.getInventory().setLeggings(orangeleg);
                        g.getInventory().setBoots(orangeboot);

                        ItemStack wardenSword = new ItemStack(Material.WOODEN_SWORD);
                        wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                        wardenSword.addEnchantment(Enchantment.DURABILITY, 1);

                        g.getInventory().addItem(wardenSword);

                        g.getInventory().addItem(new ItemStack(Material.CROSSBOW));
                        g.getInventory().addItem(new ItemStack(Material.ARROW, 16));
                        g.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));

                        ItemStack pot = new ItemStack(Material.SPLASH_POTION);
                        PotionMeta potionMeta = (PotionMeta) pot.getItemMeta();
                        potionMeta.addCustomEffect(PotionEffectType.HEAL.createEffect(10, 2), true);
                        pot.setItemMeta(potionMeta);

                        g.getInventory().addItem(pot);

                        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
                        card.setItemMeta(cardm);
                        g.getInventory().addItem(card);


                    } else {
                        sender.sendMessage(ChatColor.BLUE + "We had troubles promoting this player.");
                    }
                }
            }
            if (args[0].equals("resign")) {
                MyListener.playerJoin(PrisonGame.warden);
                Bukkit.broadcastMessage(ChatColor.GREEN + "The warden has resigned!");
                PrisonGame.warden = null;
            }
            if (args[0].equals("fire")) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    Player g = Bukkit.getPlayer(args[1]);
                    if (g.isOnline() && g != sender && PrisonGame.type.get(g) != 0) {
                        Bukkit.broadcastMessage(ChatColor.GOLD + g.getName() + " was fired.");
                        MyListener.playerJoin(g);
                    }
                }
            }
            if (args[0].equals("help")) {
                if (PrisonGame.warden.equals(sender)) {
                    Player p = (Player) sender;
                    p.sendMessage(ChatColor.DARK_GRAY + "-=-=-=-=-=-=-=-");
                    p.sendMessage(ChatColor.BLUE + "/warden help" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Shows you this menu.");
                    p.sendMessage(ChatColor.BLUE + "/warden guard [name]" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Makes another player a guard.");
                    p.sendMessage(ChatColor.BLUE + "/warden nurse [name]" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Makes another player a nurse.");
                    p.sendMessage(ChatColor.BLUE + "/warden fire [guard name]" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Fires a guard from their job");
                    p.sendMessage(ChatColor.BLUE + "/warden resign" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Resigns you from your job.");
                    p.sendMessage(ChatColor.DARK_GRAY + "-=-=-=-=-=-=-=-");

                }
            }
        }
        return true;
    }
}