package prisongame.prisongame.listeners;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import prisongame.prisongame.MyListener;
import prisongame.prisongame.Prison;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Role;

import static prisongame.prisongame.MyListener.reloadBert;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().getType().equals(InventoryType.PLAYER)) {
            if (event.getCurrentItem() != null) {
                if (event.getCurrentItem().getItemMeta() != null) {
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "2x Income")) {
                        if (event.getWhoClicked().getPersistentDataContainer().get(PrisonGame.ascendcoins, PersistentDataType.DOUBLE) >= 25) {
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.doubincome, PersistentDataType.INTEGER, 1);
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.ascendcoins, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().get(PrisonGame.ascendcoins, PersistentDataType.DOUBLE) - 25);
                            event.getWhoClicked().closeInventory();
                        }
                        event.setCancelled(true);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "Tax Evasion")) {
                        if (event.getWhoClicked().getPersistentDataContainer().get(PrisonGame.ascendcoins, PersistentDataType.DOUBLE) >= 25) {
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.taxevasion, PersistentDataType.INTEGER, 1);
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.ascendcoins, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().get(PrisonGame.ascendcoins, PersistentDataType.DOUBLE) - 25);
                            event.setCancelled(true);
                            event.getWhoClicked().closeInventory();
                        }
                        event.setCancelled(true);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "Semi Cloak")) {
                        if (event.getWhoClicked().getPersistentDataContainer().get(PrisonGame.ascendcoins, PersistentDataType.DOUBLE) >= 5) {
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.semicloak, PersistentDataType.INTEGER, 1);
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.ascendcoins, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().get(PrisonGame.ascendcoins, PersistentDataType.DOUBLE) - 5);
                            event.setCancelled(true);
                            event.getWhoClicked().closeInventory();
                        }
                        event.setCancelled(true);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "Reinforcements")) {
                        if (event.getWhoClicked().getPersistentDataContainer().get(PrisonGame.ascendcoins, PersistentDataType.DOUBLE) >= 30) {
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.reinforcement, PersistentDataType.INTEGER, 1);
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.ascendcoins, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().get(PrisonGame.ascendcoins, PersistentDataType.DOUBLE) - 30);
                            event.setCancelled(true);
                            event.getWhoClicked().closeInventory();
                        }
                        event.setCancelled(true);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "ProtSpawn")) {
                        if (event.getWhoClicked().getPersistentDataContainer().get(PrisonGame.ascendcoins, PersistentDataType.DOUBLE) >= 10) {
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.protspawn, PersistentDataType.INTEGER, 1);
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.ascendcoins, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().get(PrisonGame.ascendcoins, PersistentDataType.DOUBLE) - 10);
                            event.setCancelled(true);
                            event.getWhoClicked().closeInventory();
                        }
                        event.setCancelled(true);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "Random Items")) {
                        if (event.getWhoClicked().getPersistentDataContainer().get(PrisonGame.ascendcoins, PersistentDataType.DOUBLE) >= 3) {
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.randomz, PersistentDataType.INTEGER, 1);
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.ascendcoins, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().get(PrisonGame.ascendcoins, PersistentDataType.DOUBLE) - 3);
                            event.setCancelled(true);
                            event.getWhoClicked().closeInventory();
                        }
                        event.setCancelled(true);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.YELLOW + "Get Coords [CUSTOM]")) {
                        event.setCancelled(true);
                        event.getWhoClicked().sendMessage("nl(\"" + event.getWhoClicked().getWorld().getName() + "\", " + event.getWhoClicked().getLocation().getX() + "D," + event.getWhoClicked().getLocation().getY() + "D," + event.getWhoClicked().getLocation().getZ() + "D," + event.getWhoClicked().getLocation().getYaw() + "f," + event.getWhoClicked().getLocation().getPitch() + "f)");
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().contains("[CMD]")) {
                        if (event.getWhoClicked().hasPermission("minecraft.command.gamemode")) {
                            if (event.getCurrentItem().getItemMeta().getLore() != null) {
                                event.setCancelled(true);
                                Bukkit.dispatchCommand(event.getWhoClicked(), event.getCurrentItem().getItemMeta().getLore().get(0));
                            }
                        }
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "epic bertude night vision")) {
                        event.setCancelled(true);
                        if (!event.getWhoClicked().getPersistentDataContainer().has(PrisonGame.nightvis, PersistentDataType.INTEGER)) {
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.nightvis, PersistentDataType.INTEGER, 1);
                            event.getWhoClicked().sendMessage("ok i changed that for u lol");
                            Player p = (Player) event.getWhoClicked();
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                        } else {
                            event.getWhoClicked().getPersistentDataContainer().remove(PrisonGame.nightvis);
                            event.getWhoClicked().sendMessage("ok i changed that for u lol");
                            Player p = (Player) event.getWhoClicked();
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                        }
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "old tab")) {
                        event.setCancelled(true);
                        if (!event.getWhoClicked().getPersistentDataContainer().has(PrisonGame.tab, PersistentDataType.INTEGER)) {
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.tab, PersistentDataType.INTEGER, 1);
                            event.getWhoClicked().sendMessage("ok i changed that for u lol");
                            Player p = (Player) event.getWhoClicked();
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                        } else {
                            event.getWhoClicked().getPersistentDataContainer().remove(PrisonGame.tab);
                            event.getWhoClicked().sendMessage("ok i changed that for u lol");
                            Player p = (Player) event.getWhoClicked();
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                        }
                    }

                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "-1 dollar")) {
                        if (!event.getWhoClicked().hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                            event.setCancelled(true);
                            event.getWhoClicked().damage(999999);
                            Bukkit.broadcastMessage(event.getWhoClicked().getName() + " was robbed by bertrude (L)");
                        } else {
                            event.getWhoClicked().sendMessage("Bertrude was nice today and decided not to rob you :D");
                        }
                    }

                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "no warden spaces")) {
                        event.setCancelled(true);
                        if (!event.getWhoClicked().getPersistentDataContainer().has(PrisonGame.whiff, PersistentDataType.INTEGER)) {
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.whiff, PersistentDataType.INTEGER, 1);
                            event.getWhoClicked().sendMessage("ok i changed that for u lol");
                            Player p = (Player) event.getWhoClicked();
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                        } else {
                            event.getWhoClicked().getPersistentDataContainer().remove(PrisonGame.whiff);
                            event.getWhoClicked().sendMessage("ok i changed that for u lol");
                            Player p = (Player) event.getWhoClicked();
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                        }
                    }

                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick2(InventoryClickEvent event) {
        if (!PrisonGame.escaped.get(event.getWhoClicked()) && PrisonGame.roles.get(event.getWhoClicked()) == Role.PRISONER) {
            if (event.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().sendMessage(ChatColor.RED + "You can't take armor off till you've escaped!");
            }
        }
    }

    @EventHandler
    public void onInventoryClick3(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            if (event.getCurrentItem().getType().equals(Material.RED_STAINED_GLASS_PANE) || event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
                event.setCancelled(true);
            }
            if (event.getCurrentItem().getItemMeta() != null) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Not Drugs")) {
                    if (event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 30.0) {
                        event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)  - 30.0);
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                    }
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Scrap Metal")) {
                    if (event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 150.0) {
                        event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)  - 150.0);
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.RAW_IRON));
                    }
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Dagger")) {
                    if (event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 1000.0) {
                        event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)  - 1000.0);
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                    }
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Chainmail Helmet")) {
                    if (event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 300.0) {
                        event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)  - 300.0);
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.CHAINMAIL_HELMET));
                    }
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Soup")) {
                    if (event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 2.0) {
                        event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)  - 2.0);
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.BEETROOT_SOUP));
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Supreme Stick")) {
                    if (event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 50.0) {
                        event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)  - 50.0);
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.STICK));
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Coal")) {
                    if (event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 30.0) {
                        event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)  - 30.0);
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.COAL));
                    }
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "SWAT Guards")) {
                    if (event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 2500.0) {
                        event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)  - 2.0);
                        PrisonGame.swat = true;
                        Bukkit.broadcastMessage(ChatColor.GREEN + event.getWhoClicked().getName() + " has enabled SWAT guards!");
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Prot 1")) {
                    if (event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 500.0) {
                        Boolean shouldpay = false;
                        if (event.getWhoClicked().getInventory().getHelmet() != null) {
                            if (event.getWhoClicked().getInventory().getHelmet().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                                event.getWhoClicked().getInventory().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                                shouldpay = true;
                            }
                        }
                        if (event.getWhoClicked().getInventory().getChestplate() != null) {
                            if (event.getWhoClicked().getInventory().getChestplate().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                                event.getWhoClicked().getInventory().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                                shouldpay = true;
                            }
                        }
                        if (event.getWhoClicked().getInventory().getLeggings() != null) {
                            if (event.getWhoClicked().getInventory().getLeggings().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                                event.getWhoClicked().getInventory().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                                shouldpay = true;
                            }
                        }
                        if (event.getWhoClicked().getInventory().getBoots() != null) {
                            if (event.getWhoClicked().getInventory().getBoots().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                                event.getWhoClicked().getInventory().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                                shouldpay = true;
                            }
                        }
                        if (shouldpay)
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)  - 30.0);
                    }
                }
                if (PrisonGame.warden != null) {
                    if (PrisonGame.swapcool <= 0 && PrisonGame.warden.equals(event.getWhoClicked())) {
                        System.out.println(event.getCurrentItem().getItemMeta().getDisplayName());

                        Prison prison = switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
                            case "§5The End?" -> PrisonGame.endmap;
                            case "§9Boat" -> PrisonGame.boat;
                            case "§7Fortress Of Gaeae" -> PrisonGame.gaeae;
                            case "§fHypertech" -> PrisonGame.hyper;
                            case "§eTrain" -> PrisonGame.train;
                            case "§fGladiator" -> PrisonGame.gladiator;
                            case "§6Island" -> PrisonGame.island;
                            case "§lSanta's Workshop" -> PrisonGame.santa;
                            case "§cVolcano" -> PrisonGame.volcano;
                            case "§7Skeld" -> PrisonGame.amongus;
                            case "§aRocksNGrass" -> PrisonGame.rag;
                            case "§8Maximum Security" -> PrisonGame.ms;
                            default -> null;
                        };

                        if (prison == null)
                            return;

                        event.setCancelled(true);
                        switchMap(prison);
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Rock")) {
                    event.setCancelled(true);
                    if (event.getWhoClicked().getInventory().containsAtLeast(new ItemStack(Material.STONE_BUTTON), 9)) {
                        event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.STONE_BUTTON, 9));
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.COBBLESTONE));
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Paper")) {
                    event.setCancelled(true);
                    if (event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 15.0) {
                        if (event.getWhoClicked().getInventory().contains(Material.COAL) && event.getWhoClicked().getInventory().contains(Material.RAW_IRON)) {
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)- 15.0);
                            event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.COAL, 1));
                            event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.RAW_IRON, 1));
                            event.getWhoClicked().getInventory().addItem(new ItemStack(Material.PAPER));
                        }
                    } else {
                        event.getWhoClicked().sendMessage(ChatColor.RED + "Not enough money!");
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Fake Card")) {
                    event.setCancelled(true);
                    if (event.getWhoClicked().getInventory().containsAtLeast(new ItemStack(Material.PAPER), 3) && event.getWhoClicked().getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                        event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.PAPER, 3));
                        event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.STICK, 2));
                        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
                        card.setItemMeta(cardm);
                        event.getWhoClicked().getInventory().addItem(card);
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GRAY + "WireCutters")) {
                    event.setCancelled(true);
                    if (event.getWhoClicked().getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE), 1) &&  event.getWhoClicked().getInventory().containsAtLeast(new ItemStack(Material.RAW_IRON), 4) && event.getWhoClicked().getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                        event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.RAW_IRON, 4));
                        event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.STICK, 2));
                        event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.COBBLESTONE, 1));
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + event.getWhoClicked().getName() + " iron_pickaxe{Damage:245,display:{Name:'[{\"text\":\"WireCutters\",\"italic\":false,\"color\":\"gray\"},{\"text\":\" \"},{\"text\":\"[CONTRABAND]\",\"color\":\"red\"}]'},Enchantments:[{id:efficiency,lvl:5}],HideFlags:1,CanDestroy:[iron_bars]} 1");
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "Cloak")) {
                    event.setCancelled(true);
                    if (event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 15.0) {
                        if (event.getWhoClicked().getInventory().contains(Material.COAL)) {
                            event.getWhoClicked().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getWhoClicked().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)- 15.0);
                            event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.COAL, 1));
                            ItemStack orangeboot = new ItemStack(Material.LEATHER_CHESTPLATE);

                            LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeboot.getItemMeta();
                            orangelegItemMeta.setDisplayName(ChatColor.DARK_GRAY + "Cloak Chestplate");
                            orangelegItemMeta.setColor(Color.BLACK);
                            orangeboot.setItemMeta(orangelegItemMeta);
                            event.getWhoClicked().getInventory().setChestplate(orangeboot);
                        }
                    } else {
                        event.getWhoClicked().sendMessage(ChatColor.RED + "Not enough money!");
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Normal Crafting")) {
                    event.getWhoClicked().openWorkbench(null, true);
                }
            }
        }
    }

    private void switchMap(Prison prison) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getDisplayName().contains("ASCENDING"))
                continue;

            player.teleport(prison.mapSwitch);
        }

        PrisonGame.active = prison;
        PrisonGame.swapcool = 20 * 60 * 5;

        reloadBert();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (PrisonGame.roles.get(player) != Role.WARDEN) {
                MyListener.playerJoin(player, true);
                player.sendTitle("New prison!", prison.name.toUpperCase());
                continue;
            }

            player.teleport(prison.getWardenspawn());

            Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                player.teleport(prison.getWardenspawn());
            }, 5);

            if (!player.getDisplayName().contains("ASCENDING"))
                player.sendTitle("New prison!", prison.name.toUpperCase());
        }
    }

    @EventHandler
    public void onInventoryClick4(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (PrisonGame.roles.get(player) != Role.WARDEN)
            return;

        player.sendMessage(ChatColor.RED + "Wardens cannot interact with containers.");
        event.setCancelled(true);
    }
}
