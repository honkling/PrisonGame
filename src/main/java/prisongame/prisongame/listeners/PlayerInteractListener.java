package prisongame.prisongame.listeners;

import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Door;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import prisongame.prisongame.MyTask;
import prisongame.prisongame.PrisonGame;

import static prisongame.prisongame.MyListener.playerJoin;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            if (PrisonGame.active.getName().equals("The End?"))
                event.getPlayer().sendMessage("Wow! You managed to interact with a block in survival mode! This means the server is completely fucking broken, or it's reloading. Please tell agmass. Please.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract2(PlayerInteractEvent event) {
        if (event.getItem() != null) {
            if (event.getItem().getType().equals(Material.IRON_DOOR)) {
                if (!event.getPlayer().hasCooldown(Material.IRON_DOOR)) {
                    if (event.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) {
                        event.getPlayer().setCooldown(Material.IRON_DOOR, 20);
                        if (PrisonGame.warden != event.getPlayer()) {
                            event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 1924, -60, -2027));
                        }
                    }
                }
            }
        }
        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().getType().equals(Material.COARSE_DIRT)) {
                if (event.getItem() != null) {
                    if (event.getItem().getType().equals(Material.WOODEN_SHOVEL)) {
                        if (!event.getPlayer().hasCooldown(Material.WOODEN_SHOVEL)) {
                            event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) + 8.0 * MyTask.jobm);
                            event.getClickedBlock().setType(Material.BEDROCK);
                            event.getPlayer().setCooldown(Material.WOODEN_SHOVEL, 10);
                            Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                                event.getClickedBlock().setType(Material.COARSE_DIRT);
                            }, 20 * 10);
                        }

                    }
                }
            }
            if (event.getClickedBlock().getType().equals(Material.GRINDSTONE)) {
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType().equals(Material.TRAPPED_CHEST)) {
                if (PrisonGame.prisonerlevel.getOrDefault(event.getPlayer(), 0) != 1) {
                    event.getPlayer().getInventory().addItem(new ItemStack(Material.COD));
                    event.setCancelled(true);
                } else {
                    event.getPlayer().sendMessage(ChatColor.GOLD + "As an F-CLASS, You can only mine!");
                }
            }
            if (event.getClickedBlock().getType().equals(Material.BLAST_FURNACE)) {
                event.setCancelled(true);
                if (event.getItem() != null) {
                    if (event.getItem().getType().equals(Material.COD)) {
                        if (!event.getPlayer().hasCooldown(Material.COD)) {
                            event.getItem().setAmount(event.getItem().getAmount() - 1);
                            event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_BLASTFURNACE_FIRE_CRACKLE, 1, 1);
                            event.getPlayer().setCooldown(Material.COD, 2);
                            Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                                event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) + 2.0 * MyTask.jobm);
                            }, 20 * 4);
                        }
                    }
                }
            }
            if (event.getClickedBlock().getType().equals(Material.ENDER_CHEST) || event.getClickedBlock().getType().equals(Material.SMOKER) || event.getClickedBlock().getType().equals(Material.FURNACE) || event.getClickedBlock().getType().equals(Material.BARREL) || event.getClickedBlock().getType().equals(Material.CHEST) || event.getClickedBlock().getType().equals(Material.HOPPER) || event.getClickedBlock().getType().equals(Material.DROPPER) || event.getClickedBlock().getType().equals(Material.DISPENSER)) {
                if (PrisonGame.roles.get(event.getPlayer()) != 0 || PrisonGame.hardmode.get(event.getPlayer())) {
                    event.getPlayer().sendMessage(ChatColor.RED + "You can't access this!");
                    event.setCancelled(true);
                } else {
                    PrisonGame.worryachieve.put(event.getPlayer(), -1);
                }
            }
            if (event.getClickedBlock().getType().equals(Material.COBBLESTONE) || event.getClickedBlock().getType().equals(Material.STONE) || event.getClickedBlock().getType().equals(Material.ANDESITE)) {
                event.getPlayer().getInventory().addItem(new ItemStack(Material.STONE_BUTTON));
            }
            if (event.getClickedBlock().getType().equals(Material.DISPENSER)) {
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType().equals(Material.OAK_WALL_SIGN)) {
                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) event.getClickedBlock().getState();
                if (sign.getLine(1).equals("Leave")) {
                    event.getPlayer().stopAllSounds();
                    playerJoin(event.getPlayer(), false);
                    event.getPlayer().sendMessage(ChatColor.GREEN + "Welcome back!");
                }
                if (sign.getLine(1).equals("ASCENSION")) {
                    Inventory inv = Bukkit.createInventory(null, 9, ChatColor.AQUA + "ASCENSION");
                    if (!event.getPlayer().getPersistentDataContainer().has(PrisonGame.semicloak, PersistentDataType.INTEGER))
                        inv.addItem(PrisonGame.createGuiItem(Material.LEATHER_CHESTPLATE, ChatColor.DARK_AQUA + "Semi Cloak", ChatColor.GREEN + "You will not warn guards for breaking bars/opening doors", ChatColor.AQUA + "5 Ascension Coins"));
                    if (!event.getPlayer().getPersistentDataContainer().has(PrisonGame.reinforcement, PersistentDataType.INTEGER))
                        inv.addItem(PrisonGame.createGuiItem(Material.DIAMOND_AXE, ChatColor.DARK_AQUA + "Reinforcements", ChatColor.GREEN + "Gives you a diamond axe and iron helmet on FIRST SPAWN as warden", ChatColor.AQUA + "30 Ascension Coins"));
                    if (!event.getPlayer().getPersistentDataContainer().has(PrisonGame.protspawn, PersistentDataType.INTEGER))
                        inv.addItem(PrisonGame.createGuiItem(Material.DIAMOND_CHESTPLATE, ChatColor.DARK_AQUA + "ProtSpawn", ChatColor.GREEN + "Spawns you with PROT 1 as a warden, guard, nurse or swat.", ChatColor.AQUA + "10 Ascension Coins"));
                    if (!event.getPlayer().getPersistentDataContainer().has(PrisonGame.randomz, PersistentDataType.INTEGER))
                        inv.addItem(PrisonGame.createGuiItem(Material.SPLASH_POTION, ChatColor.DARK_AQUA + "Random Items", ChatColor.GREEN + "Gives you 3 random items when spawning.", ChatColor.AQUA + "3 Ascension Coins"));
                    event.getPlayer().openInventory(inv);
                }
                if (sign.getLine(0).equals("ASCEND")) {
                    if (event.getPlayer().equals(PrisonGame.warden)) {
                        if (PrisonGame.warden.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 10000) {
                            PrisonGame.warden.getPersistentDataContainer().set(PrisonGame.ascendcoins, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) / 2000);
                            PrisonGame.warden.getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0);
                            Bukkit.broadcastMessage(ChatColor.GREEN + "The warden has ascended!");
                            PrisonGame.roles.put(PrisonGame.warden, 0);
                            PrisonGame.warden.getInventory().clear();
                            PrisonGame.wardenCooldown = 60 + (20 * 4);
                            PrisonGame.warden.teleport(PrisonGame.nl("world", 1999.5D, -14D, -2049.9D, 180f, 0f));
                            PrisonGame.warden.playSound(PrisonGame.warden, Sound.ENTITY_ENDER_DRAGON_DEATH, 1, 1);
                            PrisonGame.warden.playSound(PrisonGame.warden, Sound.MUSIC_DISC_OTHERSIDE, 1, 0.5f);
                            PrisonGame.warden.addPotionEffect(PotionEffectType.SLOW_FALLING.createEffect(20 * 5, 0));
                            Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                                event.getPlayer().setDisplayName(ChatColor.GRAY + "[" + ChatColor.AQUA + "ASCENDING" + ChatColor.GRAY + "] " + ChatColor.AQUA + PrisonGame.warden.getName());
                                event.getPlayer().setCustomName(ChatColor.GRAY + "[" + ChatColor.AQUA + "ASCENDING" + ChatColor.GRAY + "] " + ChatColor.AQUA + PrisonGame.warden.getName());
                                event.getPlayer().setPlayerListName(ChatColor.GRAY + "[" + ChatColor.AQUA + "ASCENDING" + ChatColor.GRAY + "] " + ChatColor.AQUA + PrisonGame.warden.getName());
                                PrisonGame.warden = null;
                            }, 4);

                        } else {
                            PrisonGame.warden.sendMessage(ChatColor.RED + "you don't have enough money!");
                        }
                    }
                }
                if (sign.getLine(1).equals("+1 time tick")) {
                    Bukkit.getWorld("world").setTime(Bukkit.getWorld("world").getTime() + 1);
                }
            }
            if (event.getClickedBlock().getType().equals(Material.MANGROVE_WALL_SIGN)) {
                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) event.getClickedBlock().getState();
                if (sign.getLine(1).equals("LOCKDOWN")) {
                    if (PrisonGame.lockdowncool < 1) {
                        Bukkit.getWorld("world").setTime(16000);
                        PrisonGame.lockdowncool = (20 * 60) * 10;
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "That's on cooldown! " + ChatColor.YELLOW + PrisonGame.lockdowncool / 20 + " seconds left");
                    }
                }
            }
            if (event.getItem() != null) {
                if (event.getItem().getType().equals(Material.WOLF_SPAWN_EGG)) {
                    event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.WOLF);
                    event.getPlayer().getInventory().removeItem(new ItemStack(Material.WOLF_SPAWN_EGG, 1));
                }
            }
            if (event.getClickedBlock().getType().equals(Material.BIRCH_WALL_SIGN)) {
                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) event.getClickedBlock().getState();
                if (sign.getLine(1).equals("TP To Island")) {
                    if (PrisonGame.roles.get(event.getPlayer()) != 0 && PrisonGame.roles.get(event.getPlayer()) != -1 && PrisonGame.warden != event.getPlayer()) {
                        event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 1924, -60, -2027));
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You're not a guard/You can't come here as warden");
                    }
                }
                if (sign.getLine(1).equals("Market Shop")) {
                    event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                    Inventory inv = Bukkit.createInventory(null, 9, "Shop");
                    inv.addItem(PrisonGame.createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ""));
                    inv.addItem(PrisonGame.createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ""));
                    inv.addItem(PrisonGame.createGuiItem(Material.BEETROOT_SOUP, ChatColor.YELLOW + "Soup", ChatColor.GREEN + "$2"));
                    inv.addItem(PrisonGame.createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ""));
                    inv.addItem(PrisonGame.createGuiItem(Material.STICK, ChatColor.YELLOW + "Supreme Stick", ChatColor.GREEN + "$50"));
                    inv.addItem(PrisonGame.createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ""));
                    inv.addItem(PrisonGame.createGuiItem(Material.COAL, ChatColor.YELLOW + "Coal", ChatColor.GRAY + "$30"));
                    inv.addItem(PrisonGame.createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ""));
                    inv.addItem(PrisonGame.createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ""));
                    event.getPlayer().openInventory(inv);
                }
                if (sign.getLine(1).equals("Switch Maps")) {
                    if (event.getPlayer() == PrisonGame.warden && event.getPlayer().getPassengers().size() == 0) {
                        if (PrisonGame.swapcool <= 0) {
                            Inventory inv = Bukkit.createInventory(null, 9 * 2, "Map Switch");
                            inv.addItem(PrisonGame.createGuiItem(Material.COBBLESTONE, ChatColor.GRAY + "Fortress Of Gaeae"));
                            inv.addItem(PrisonGame.createGuiItem(Material.QUARTZ_BLOCK, ChatColor.WHITE + "Hypertech"));
                            //inv.addItem(PrisonGame.createGuiItem(Material.END_CRYSTAL, ChatColor.DARK_PURPLE + "The End?"));
                            inv.addItem(PrisonGame.createGuiItem(Material.CRIMSON_PLANKS, ChatColor.YELLOW + "Train"));
                            //inv.addItem(PrisonGame.createGuiItem(Material.RED_STAINED_GLASS, ChatColor.RED + "MAP DISABLED"));
                            inv.addItem(PrisonGame.createGuiItem(Material.STONE_BRICK_SLAB, ChatColor.WHITE + "Gladiator"));
                            inv.addItem(PrisonGame.createGuiItem(Material.SAND, ChatColor.GOLD + "Island"));
                            inv.addItem(PrisonGame.createGuiItem(Material.SNOW_BLOCK, ChatColor.BOLD + "Santa's Workshop"));
                            inv.addItem(PrisonGame.createGuiItem(Material.LAVA_BUCKET, ChatColor.RED + "Volcano"));
                            inv.addItem(PrisonGame.createGuiItem(Material.GRAY_CONCRETE, ChatColor.GRAY + "Skeld"));
                            //inv.addItem(PrisonGame.createGuiItem(Material.QUARTZ, ChatColor.BLUE + "Boat"));
                            //inv.addItem(PrisonGame.createGuiItem(Material.NETHERRACK, ChatColor.RED + "Nether"));
                            event.getPlayer().openInventory(inv);
                        } else {
                            event.getPlayer().sendMessage(ChatColor.RED + "That's on cooldown!" + ChatColor.YELLOW + PrisonGame.swapcool / 20 + " seconds left.");
                        }

                    }
                }

                if (sign.getLine(2).equals("Scrap Metal")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 150.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 150);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.RAW_IRON));
                    }
                }
                if (sign.getLine(2).equals("Not Drugs")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 30.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 30.0);

                        ItemStack card = new ItemStack(Material.GOLDEN_APPLE, 1);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Gapple " + ChatColor.RED + "[CONTRABAND]");
                        card.setItemMeta(cardm);

                        event.getPlayer().getInventory().addItem(card);
                    }
                }
                if (sign.getLine(2).equals("Chainmail Helmet")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 300.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 300.0);

                        ItemStack card = new ItemStack(Material.CHAINMAIL_HELMET);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Helmet " + ChatColor.RED + "[CONTRABAND]");
                        card.setItemMeta(cardm);

                        event.getPlayer().getInventory().addItem(card);
                    }
                }
                if (sign.getLine(2).equals("Tax Skip")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 15.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 15.0);

                        ItemStack card = new ItemStack(Material.BOOK);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.LIGHT_PURPLE + "Tax Skip");
                        card.setItemMeta(cardm);

                        event.getPlayer().getInventory().addItem(card);
                    }
                }
                if (sign.getLine(2).equals("Dagger")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 1000.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 1000.0);

                        ItemStack card = new ItemStack(Material.IRON_SWORD);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Dagger " + ChatColor.RED + "[CONTRABAND]");
                        card.setItemMeta(cardm);

                        event.getPlayer().getInventory().addItem(card);
                    }
                }
            }
            if (event.getClickedBlock().getType().equals(Material.CRAFTING_TABLE)) {
                event.setCancelled(true);
                Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                    Inventory inv = Bukkit.createInventory(null, 9, "Crafting");
                    inv.addItem(PrisonGame.createGuiItem(Material.CRAFTING_TABLE, ChatColor.LIGHT_PURPLE + "Normal Crafting"));
                    inv.addItem(PrisonGame.createGuiItem(Material.COBBLESTONE, ChatColor.LIGHT_PURPLE + "Rock", "§aRecipe:", "§b9 Pebbles"));
                    inv.addItem(PrisonGame.createGuiItem(Material.PAPER, ChatColor.WHITE + "Paper", "§aRecipe:", "§b1 Coal", "§b1 Scrap Metal", "§a15$"));
                    inv.addItem(PrisonGame.createGuiItem(Material.TRIPWIRE_HOOK, ChatColor.LIGHT_PURPLE + "Fake Card", "§aRecipe:", "§b3 Paper", "§b2 Sticks"));
                    inv.addItem(PrisonGame.createGuiItem(Material.SHEARS, ChatColor.GRAY + "WireCutters", "§aRecipe:", "§b4 Scrap Metal", "§b2 Sticks", "§b1 Rock"));
                    inv.addItem(PrisonGame.createGuiItem(Material.LEATHER_CHESTPLATE, ChatColor.DARK_GRAY + "Cloak", "§aRecipe:", "§b1 Coal", "§ba15$"));
                    event.getPlayer().openInventory(inv);
                }, 1L);
            }
            if (event.getClickedBlock().getType().equals(Material.FURNACE)) {
                event.setCancelled(true);
            }

            if (event.getClickedBlock().getType().equals(Material.SPRUCE_WALL_SIGN)) {
                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) event.getClickedBlock().getState();
                if (sign.getLine(1).equals("TP To Prison")) {
                    if (!MyTask.bossbar.getTitle().equals("LIGHTS OUT")) {
                        event.getPlayer().setCooldown(Material.IRON_DOOR, 20);
                        event.getPlayer().teleport(PrisonGame.active.spwn);
                    }
                }
                if (sign.getLine(1).equals("Shop")) {
                    Inventory inv = Bukkit.createInventory(null, 9, "Shop");
                    inv.addItem(PrisonGame.createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ""));
                    inv.addItem(PrisonGame.createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ""));
                    inv.addItem(PrisonGame.createGuiItem(Material.BEETROOT_SOUP, ChatColor.YELLOW + "Soup", ChatColor.GREEN + "$2"));
                    inv.addItem(PrisonGame.createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ""));
                    inv.addItem(PrisonGame.createGuiItem(Material.STICK, ChatColor.YELLOW + "Supreme Stick", ChatColor.GREEN + "$50"));
                    inv.addItem(PrisonGame.createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ""));
                    inv.addItem(PrisonGame.createGuiItem(Material.COAL, ChatColor.YELLOW + "Coal", ChatColor.GRAY + "$30"));
                    inv.addItem(PrisonGame.createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ""));
                    inv.addItem(PrisonGame.createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ""));
                    event.getPlayer().openInventory(inv);
                }
                if (sign.getLine(1).equals("Guard Dogs")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 500.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 500.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.WOLF_SPAWN_EGG, 4));
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.BONE, 32));
                    }
                }
                if (sign.getLine(1).equals("PROT 1")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 500.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 500.0);
                        if (event.getPlayer().getInventory().getHelmet() != null)
                            event.getPlayer().getInventory().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        if (event.getPlayer().getInventory().getChestplate() != null)
                            event.getPlayer().getInventory().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        if (event.getPlayer().getInventory().getLeggings() != null)
                            event.getPlayer().getInventory().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        if (event.getPlayer().getInventory().getBoots() != null)
                            event.getPlayer().getInventory().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                    }
                }
                if (sign.getLine(1).equals("Restore Kit")) {
                    if (PrisonGame.roles.get(event.getPlayer()) == -1) {
                        Player nw = event.getPlayer();
                        nw.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                        nw.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                        nw.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        nw.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));

                        ItemStack wardenSword = new ItemStack(Material.DIAMOND_SWORD);
                        wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                        wardenSword.addEnchantment(Enchantment.DURABILITY, 2);


                        ItemStack card2 = new ItemStack(Material.IRON_SHOVEL);
                        ItemMeta cardm2 = card2.getItemMeta();
                        cardm2.setDisplayName(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]");
                        cardm2.addEnchant(Enchantment.KNOCKBACK, 1, true);
                        card2.setItemMeta(cardm2);
                        nw.getInventory().addItem(card2);

                        if (!event.getPlayer().getInventory().contains(wardenSword))
                            nw.getInventory().addItem(wardenSword);
                        if (!event.getPlayer().getInventory().contains(Material.BOW))
                            nw.getInventory().addItem(new ItemStack(Material.BOW));

                        if (!event.getPlayer().getInventory().contains(Material.TRIPWIRE_HOOK)) {
                            ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
                            ItemMeta cardm = card.getItemMeta();
                            cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
                            card.setItemMeta(cardm);
                            nw.getInventory().addItem(card);
                        }
                    }
                    if (PrisonGame.roles.get(event.getPlayer()) == 3) {
                        Player g = event.getPlayer();
                        ItemStack orangechest = new ItemStack(Material.NETHERITE_CHESTPLATE);

                        ItemStack orangeleg = new ItemStack(Material.NETHERITE_LEGGINGS);
                        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                        ItemStack orangeboot = new ItemStack(Material.LEATHER_BOOTS);
                        orangeboot.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeboot.getItemMeta();
                        orangelegItemMeta.setColor(Color.GRAY);
                        orangeboot.setItemMeta(orangelegItemMeta);

                        g.getInventory().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
                        g.getInventory().setChestplate(orangechest);
                        g.getInventory().setLeggings(orangeleg);
                        g.getInventory().setBoots(orangeboot);

                        ItemStack card2 = new ItemStack(Material.IRON_SHOVEL);
                        ItemMeta cardm2 = card2.getItemMeta();
                        cardm2.setDisplayName(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]");
                        cardm2.addEnchant(Enchantment.KNOCKBACK, 1, true);
                        card2.setItemMeta(cardm2);
                        g.getInventory().addItem(card2);

                        ItemStack wardenSword = new ItemStack(Material.DIAMOND_SWORD);

                        g.getInventory().addItem(wardenSword);

                        g.getInventory().addItem(new ItemStack(Material.BOW));
                        g.getInventory().addItem(new ItemStack(Material.ARROW, 16));
                        g.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));

                        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
                        card.setItemMeta(cardm);
                        g.getInventory().addItem(card);
                    }
                    if (PrisonGame.roles.get(event.getPlayer()) == 2) {
                        Player g = event.getPlayer();

                        ItemStack card2 = new ItemStack(Material.IRON_SHOVEL);
                        ItemMeta cardm2 = card2.getItemMeta();
                        cardm2.setDisplayName(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]");
                        cardm2.addEnchant(Enchantment.KNOCKBACK, 1, true);
                        card2.setItemMeta(cardm2);
                        g.getInventory().addItem(card2);

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

                        ItemStack wardenSword = new ItemStack(Material.STONE_SWORD);
                        wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                        wardenSword.addEnchantment(Enchantment.DURABILITY, 1);

                        g.getInventory().addItem(wardenSword);

                        g.getInventory().addItem(new ItemStack(Material.CROSSBOW));
                        g.getInventory().addItem(new ItemStack(Material.ARROW, 16));
                        g.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));

                        ItemStack pot = new ItemStack(Material.SPLASH_POTION);
                        PotionMeta potionMeta = (PotionMeta) pot.getItemMeta();
                        potionMeta.addCustomEffect(PotionEffectType.HEAL.createEffect(10, 0), true);
                        pot.setItemMeta(potionMeta);

                        g.getInventory().addItem(pot);

                        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
                        card.setItemMeta(cardm);
                        g.getInventory().addItem(card);

                    }
                    if (PrisonGame.roles.get(event.getPlayer()) == 1) {
                        Player g = event.getPlayer();

                        ItemStack card2 = new ItemStack(Material.IRON_SHOVEL);
                        ItemMeta cardm2 = card2.getItemMeta();
                        cardm2.setDisplayName(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]");
                        cardm2.addEnchant(Enchantment.KNOCKBACK, 1, true);
                        card2.setItemMeta(cardm2);
                        g.getInventory().addItem(card2);

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

                        ItemStack wardenSword = new ItemStack(Material.IRON_SWORD);
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


                    }
                }
                if (sign.getLine(1).equals("SWAT Guards")) {
                    if (!PrisonGame.swat) {
                        if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 2500.0) {
                            event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 2500.0);
                            PrisonGame.swat = true;
                            Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + " has enabled SWAT guards!");
                        }
                    }
                }
                if (sign.getLine(2).equals("Non-Illegal Drugs")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 70.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 70.0);

                        ItemStack card = new ItemStack(Material.GOLDEN_APPLE, 1);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Gapple ");
                        card.setItemMeta(cardm);

                        event.getPlayer().getInventory().addItem(card);
                    }
                }
                if (sign.getLine(2).equals("Soup")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 2.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 2.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.BEETROOT_SOUP));
                    }
                }
                if (sign.getLine(2).equals("Steak")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 5.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 5.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.COOKED_BEEF));
                    }
                }
                if (sign.getLine(2).equals("Milk")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 5.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 5.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.MILK_BUCKET));
                    }
                }
                if (sign.getLine(2).equals("Vomit Pot")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 30.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 30.0);

                        ItemStack pot = new ItemStack(new ItemStack(Material.SPLASH_POTION));
                        PotionMeta potMeta = (PotionMeta) pot.getItemMeta();
                        potMeta.addCustomEffect(PotionEffectType.POISON.createEffect(20 * 10, 1), true);
                        potMeta.setColor(Color.LIME);
                        pot.setItemMeta(potMeta);

                        event.getPlayer().getInventory().addItem(pot);
                    }
                }
                if (sign.getLine(1).equals("Cafeteria")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 1000.0) {
                        if (PrisonGame.active.cafedoor2.getBlock().getType().equals(Material.MUD_BRICKS)) {
                            event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 1000.0);
                            Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + " Bought the cafeteria!");
                            if (!PrisonGame.active.equals(PrisonGame.hyper)) {
                                for (Integer x = PrisonGame.active.cafedoor1.getBlockX(); x <= PrisonGame.active.cafedoor2.getBlockX(); x++) {
                                    for (Integer y = PrisonGame.active.cafedoor1.getBlockY(); y <= PrisonGame.active.cafedoor2.getBlockY(); y++) {
                                        for (Integer z = PrisonGame.active.cafedoor1.getBlockZ(); z <= PrisonGame.active.cafedoor2.getBlockZ(); z++) {
                                            event.getPlayer().getWorld().getBlockAt(x, y, z).setType(Material.AIR);
                                        }
                                    }
                                }
                                for (Integer x = PrisonGame.active.cafedoor2.getBlockX(); x <= PrisonGame.active.cafedoor1.getBlockX(); x++) {
                                    for (Integer y = PrisonGame.active.cafedoor2.getBlockY(); y <= PrisonGame.active.cafedoor1.getBlockY(); y++) {
                                        for (Integer z = PrisonGame.active.cafedoor2.getBlockZ(); z <= PrisonGame.active.cafedoor1.getBlockZ(); z++) {
                                            event.getPlayer().getWorld().getBlockAt(x, y, z).setType(Material.AIR);
                                        }
                                    }
                                }
                            }
                        }
                        if (new Location(Bukkit.getWorld("world"), 3, -58, -1008).getBlock().getType().equals(Material.MUD_BRICKS)) {
                            if (PrisonGame.active.equals(PrisonGame.hyper)) {
                                Bukkit.getWorld("world").getBlockAt(1, -58, -1008).setType(Material.AIR);
                                Bukkit.getWorld("world").getBlockAt(2, -58, -1008).setType(Material.AIR);
                                Bukkit.getWorld("world").getBlockAt(3, -58, -1008).setType(Material.AIR);

                            }
                        }
                    }
                }
                if (sign.getLine(2).equals("Piss Pot")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 30.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 30.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.HONEY_BOTTLE));
                    }
                }
                if (sign.getLine(2).equals("Strong Chest")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 1000.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 1000.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_CHESTPLATE));
                    }
                }
                if (sign.getLine(2).equals("Arrows")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 16.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 16.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.ARROW, 16));
                        event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_WOOD_PLACE, 1, 1);
                    }
                }
                if (sign.getLine(2).equals("Supreme Stick")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 50.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 50.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.STICK));
                        event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_WOOD_PLACE, 1, 1);
                    }
                }
                if (sign.getLine(2).equals("Coal")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 30.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 30.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.COAL));
                        event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_WOOD_PLACE, 1, 1);
                    }
                }
                if (sign.getLine(2).equals("Mining")) {
                    if (!event.getPlayer().getInventory().contains(Material.IRON_PICKAXE)) {
                        event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + event.getPlayer().getName() + " iron_pickaxe{Damage:36,display:{Name:'[{\"text\":\"Prisoner\\'s Pickaxe\",\"italic\":false}]'},CanDestroy:[deepslate_copper_ore,deepslate_emerald_ore,deepslate_gold_ore,deepslate_lapis_ore,deepslate_redstone_ore]} 1");
                        event.getPlayer().sendMessage(ChatColor.GRAY + "Mine Ores with the pickaxe.");
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "You already have a pickaxe!");
                    }
                }
                if (PrisonGame.prisonerlevel.getOrDefault(event.getPlayer(), 0) != 1) {
                    if (sign.getLine(2).equals("Lumberjack")) {
                        if (!event.getPlayer().getInventory().contains(Material.WOODEN_AXE)) {
                            event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            ItemStack card = new ItemStack(Material.WOODEN_AXE);
                            ItemMeta cardm = card.getItemMeta();
                            cardm.setDisplayName(ChatColor.GOLD + "Lumber's Axe");
                            card.setItemMeta(cardm);
                            event.getPlayer().getInventory().addItem(card);
                            event.getPlayer().sendMessage(ChatColor.GOLD + "Go right click the spruce logs in the Lumberjack Station.");
                        } else {
                            event.getPlayer().sendMessage(ChatColor.RED + "You already have an axe!");
                        }
                    }
                    if (sign.getLine(2).equals("Shovelling")) {
                        if (!event.getPlayer().getInventory().contains(Material.WOODEN_SHOVEL)) {
                            event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_COW_BELL, 1, 1);
                            ItemStack card = new ItemStack(Material.WOODEN_SHOVEL);
                            ItemMeta cardm = card.getItemMeta();
                            cardm.setDisplayName(ChatColor.LIGHT_PURPLE + "Shovel");
                            card.setItemMeta(cardm);
                            event.getPlayer().getInventory().addItem(card);
                            event.getPlayer().sendMessage(ChatColor.GRAY + "Right click on coarse dirt with the shovel.");
                        } else {
                            event.getPlayer().sendMessage(ChatColor.RED + "You already have a shovel!");
                        }
                    }
                    if (sign.getLine(2).equals("Plumber")) {
                        if (!event.getPlayer().getInventory().contains(Material.CARROT_ON_A_STICK)) {
                            event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                            ItemStack card = new ItemStack(Material.CARROT_ON_A_STICK);
                            ItemMeta cardm = card.getItemMeta();
                            sign.setLine(1, "$0.5/click");
                            cardm.setDisplayName(ChatColor.LIGHT_PURPLE + "Plumber");
                            card.setItemMeta(cardm);
                            event.getPlayer().getInventory().addItem(card);
                            event.getPlayer().sendMessage(ChatColor.BLUE + "Click on iron trapdoors with the plumber.");
                        } else {
                            event.getPlayer().sendMessage(ChatColor.RED + "You already have a plumber!");
                        }
                    }
                    if (sign.getLine(2).equals("Bounty Hunter")) {
                        if (!event.getPlayer().getInventory().contains(Material.WOODEN_SWORD)) {
                            event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 1);
                            ItemStack card = new ItemStack(Material.WOODEN_SWORD);
                            ItemMeta cardm = card.getItemMeta();
                            cardm.setDisplayName(ChatColor.RED + "Bounty Hunter's Knife");
                            card.setItemMeta(cardm);
                            event.getPlayer().getInventory().addItem(card);
                            event.getPlayer().sendMessage(ChatColor.RED + "Kill criminals (Glowing pepole).");
                        } else {
                            event.getPlayer().sendMessage(ChatColor.RED + "You already have a sword!");
                        }
                    }
                    if (sign.getLine(2).equals("Cafe Worker")) {
                        event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
                        sign.setLine(1, "$2/fish");
                        event.getPlayer().sendMessage(ChatColor.WHITE + "Click on the trapped chest to get food, cook it in the blast furnace.");
                    }
                } else {
                    event.getPlayer().sendMessage(ChatColor.GOLD + "As an F-CLASS, You can only mine!");
                }
            }
            if (event.getClickedBlock().getType().equals(Material.JUNGLE_WALL_SIGN)) {
                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) event.getClickedBlock().getState();
                if (sign.getLine(1).equals("Leave Market")) {
                    event.getPlayer().teleport(PrisonGame.active.getBmout());
                    event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_ENDER_PEARL_THROW, 1, 1);
                    event.getPlayer().addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 3, 0));
                    event.getPlayer().removePotionEffect(PotionEffectType.UNLUCK);
                }
                if (sign.getLine(1).equals("Get Gear")) {
                    if (PrisonGame.roles.get(event.getPlayer()) == 0 && !PrisonGame.escaped.get(event.getPlayer())) {
                        Player g = event.getPlayer();
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getPlayer().getName() + " only prison:escape");
                        g.playSound(g, Sound.ITEM_GOAT_HORN_SOUND_1, 1, 1);
                        PrisonGame.escaped.put(event.getPlayer(), true);
                        Bukkit.broadcastMessage(ChatColor.RED + g.getName() + " escaped...");
                        event.getPlayer().addPotionEffect(PotionEffectType.GLOWING.createEffect(999999999, 0));

                        g.setCustomName(ChatColor.GRAY + "[" + ChatColor.RED + "CRIMINAL" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                        g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "CRIMINAL" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                        g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.RED + "CRIMINAL" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());


                        ItemStack orangechest = new ItemStack(Material.LEATHER_CHESTPLATE);
                        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        LeatherArmorMeta chestmeta = (LeatherArmorMeta) orangechest.getItemMeta();
                        chestmeta.setColor(Color.RED);
                        chestmeta.setDisplayName("Armor " + ChatColor.RED + "[CONTRABAND]");
                        orangechest.setItemMeta(chestmeta);

                        ItemStack orangeleg = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        ItemMeta orangelegItemMeta = orangeleg.getItemMeta();
                        orangelegItemMeta.setDisplayName("Armor " + ChatColor.RED + "[CONTRABAND]");
                        orangeleg.setItemMeta(orangelegItemMeta);


                        g.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                        g.getInventory().setChestplate(orangechest);
                        g.getInventory().setLeggings(orangeleg);

                        ItemStack wardenSword = new ItemStack(Material.STONE_SWORD);
                        wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
                        wardenSword.addEnchantment(Enchantment.DURABILITY, 1);

                        g.getInventory().addItem(wardenSword);

                        g.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 4));


                        if (PrisonGame.hardmode.get(g)) {
                            g.setCustomName(ChatColor.GRAY + "[" + ChatColor.DARK_RED + "CRIMINAL" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY  + "Criminal " + PrisonGame.prisonnumber.get(g));
                            g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.DARK_RED + "CRIMINAL" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY  + "Criminal " + PrisonGame.prisonnumber.get(g));
                            g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "HARD MODE" + ChatColor.DARK_GRAY + "] " + g.getName());
                        }

                    }
                }
            }
            if (event.getClickedBlock().getType().equals(Material.IRON_TRAPDOOR)) {
                if (event.getItem() != null) {
                    if (!event.getPlayer().hasCooldown(Material.CARROT_ON_A_STICK)) {
                        event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 0.75f, 1.75f);
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)+ 0.5 * MyTask.jobm);
                    }
                }
            }
            if (event.getClickedBlock().getType().equals(Material.SPRUCE_LOG)) {
                if (event.getItem() != null) {
                    if (event.getItem().getType().equals(Material.WOODEN_AXE)) {
                        if (!event.getPlayer().hasCooldown(Material.WOODEN_AXE)) {
                            event.getPlayer().setCooldown(Material.WOODEN_AXE, 10);
                            event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_WOOD_BREAK, 1, 1);
                            event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)+ 2.0 * MyTask.jobm);
                        }
                    }
                }
            }
            if (event.getClickedBlock().getType().equals(Material.CAULDRON)) {
                if (!event.getPlayer().hasCooldown(Material.IRON_DOOR)) {
                    if (PrisonGame.roles.get(event.getPlayer()) != 1 && PrisonGame.roles.get(event.getPlayer()) != 2 && PrisonGame.roles.get(event.getPlayer()) != 3) {
                        event.getPlayer().teleport(PrisonGame.active.getBm());
                        event.getPlayer().sendTitle("", ChatColor.GRAY + "-= Black Market =-");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getPlayer().getName() + " only prison:market");
                        event.getPlayer().playSound(event.getPlayer(), Sound.AMBIENT_UNDERWATER_ENTER, 1, 0.75f);
                        event.getPlayer().addPotionEffect(PotionEffectType.UNLUCK.createEffect(999999, 2));
                    } else {
                        event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                        event.getPlayer().sendMessage(ChatColor.RED + "You wouldn't want to get yourself dirty in there!");
                    }
                } else {
                    event.getPlayer().sendMessage(ChatColor.RED + "You're in combat!");
                }
            }
            if (event.getClickedBlock().getType().equals(Material.JUNGLE_DOOR)) {
                if (PrisonGame.roles.get(event.getPlayer()) != -1) {
                    event.setCancelled(true);
                    event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    event.getPlayer().sendMessage(ChatColor.RED + "This door can only be opened by the warden!");
                }
            }
            if (event.getClickedBlock().getType().equals(Material.ACACIA_DOOR)) {
                BlockState state = event.getClickedBlock().getState();
                Door openable = (Door) state.getBlockData();

                if (Bukkit.getWorld("world").getTime() > 15000 && Bukkit.getWorld("world").getTime() < 24000) {
                    if (event.getItem() != null) {
                        if (event.getItem().getItemMeta() != null) {
                            if (event.getItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]")) {
                                event.setCancelled(true);
                                if (!openable.isOpen()) {
                                    openable.setOpen(true);
                                    state.setBlockData(openable);
                                    state.update();
                                } else {
                                    openable.setOpen(false);
                                    state.setBlockData(openable);
                                    state.update();

                                }
                            } else {
                                event.setCancelled(true);
                                event.getPlayer().sendMessage(ChatColor.RED + "You can't open this during lockdown/cell time!");
                                event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            }
                        } else {
                            event.setCancelled(true);
                            event.getPlayer().sendMessage(ChatColor.RED + "You can't open this during lockdown/cell time!");
                            event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                        }
                    } else {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(ChatColor.RED + "You can't open this during lockdown/cell time!");
                        event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    }
                }
            }
            if (event.getClickedBlock().getType().equals(Material.IRON_DOOR)) {
                BlockState state = event.getClickedBlock().getState();
                Door openable = (Door) state.getBlockData();

                if (event.getItem() != null) {
                    if (event.getItem().getItemMeta() != null) {
                        if (event.getItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]")) {
                            event.setCancelled(true);
                            if (!openable.isOpen()) {
                                event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_IRON_DOOR_OPEN, 1, 1);
                                Boolean yesdothat = true;
                                if (event.getPlayer().getInventory().getChestplate() != null) {
                                    if (event.getPlayer().getInventory().getChestplate().getItemMeta() != null) {
                                        if (event.getPlayer().getInventory().getChestplate().getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "Cloak Chestplate")) {
                                            yesdothat = false;
                                        }
                                    }
                                }
                                if (yesdothat && !event.getPlayer().hasPotionEffect(PotionEffectType.GLOWING) && !event.getPlayer().getPersistentDataContainer().has(PrisonGame.semicloak, PersistentDataType.INTEGER)) {
                                    if (PrisonGame.roles.get(event.getPlayer()) == 0 && !PrisonGame.escaped.get(event.getPlayer())) {
                                        event.getPlayer().sendMessage(ChatColor.RED + "You were caught opening a door! Get a cloak next time!");
                                        event.getPlayer().addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 30, 0));
                                        for (Player g : Bukkit.getOnlinePlayers()) {
                                            if (PrisonGame.roles.get(g) != 0) {
                                                g.playSound(g, Sound.ENTITY_SILVERFISH_DEATH, 1, 0.5f);
                                                g.sendMessage(ChatColor.RED + event.getPlayer().getName() + ChatColor.DARK_RED + " was caught opening a door!");
                                            }
                                        }
                                    }
                                }
                                openable.setOpen(true);
                                state.setBlockData(openable);
                                state.update();
                            } else {
                                openable.setOpen(false);
                                event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_IRON_DOOR_CLOSE, 1, 1);
                                state.setBlockData(openable);
                                state.update();

                            }
                        }
                    }
                }
            }
        }
    }
}
