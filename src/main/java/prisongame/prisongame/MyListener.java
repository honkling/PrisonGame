package prisongame.prisongame;

import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Door;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MyListener implements Listener {

    public static void playerJoin(Player p, Boolean dontresetshit) {
        if (!dontresetshit)
            p.getInventory().clear();
        if (!dontresetshit)
            PrisonGame.escaped.put(p, false);
        p.playSound(p, Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1, 0.75f);

        if (!dontresetshit) {
            p.setCustomName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + p.getName());
            p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + p.getName());
            p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + p.getName());


            if (p.getName().equals("agmass")) {
                p.setCustomName(ChatColor.GRAY + "[" + ChatColor.RED + "OWNER" + ChatColor.GRAY + "] " + p.getDisplayName());
                p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "OWNER" + ChatColor.GRAY + "] " + p.getDisplayName());
                p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.RED + "OWNER" + ChatColor.GRAY + "] " + p.getDisplayName());
            }

            if (p.getName().equals("ClownCaked") || p.getName().equals("4950")) {
                p.setCustomName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + p.getDisplayName());
                p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + p.getDisplayName());
                p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + p.getDisplayName());
            }
        }

        if (!dontresetshit) {
            ItemStack orangechest = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta chestmeta = (LeatherArmorMeta) orangechest.getItemMeta();
            chestmeta.setColor(Color.ORANGE);
            orangechest.setItemMeta(chestmeta);

            ItemStack orangeleg = new ItemStack(Material.LEATHER_LEGGINGS);
            LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeleg.getItemMeta();
            orangelegItemMeta.setColor(Color.ORANGE);
            orangeleg.setItemMeta(orangelegItemMeta);

            ItemStack orangeboot = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta orangebootItemMeta = (LeatherArmorMeta) orangeboot.getItemMeta();
            orangebootItemMeta.setColor(Color.ORANGE);
            orangeboot.setItemMeta(orangebootItemMeta);

            p.getInventory().setChestplate(orangechest);
            p.getInventory().setLeggings(orangeleg);
            p.getInventory().setBoots(orangeboot);
        }
        if (!dontresetshit)
            PrisonGame.type.put(p, 0);
        Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {p.teleport(PrisonGame.active.getSpwn());}, 5L);
        Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {p.teleport(PrisonGame.active.getSpwn());}, 8L);
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners").addPlayer(p);
    }


    @EventHandler
    public void bertrudeiosepic(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            if (event.getCurrentItem().getItemMeta() != null) {
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

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "-1 dollar")) {
                    event.setCancelled(true);
                    event.getWhoClicked().damage(999999);
                    Bukkit.broadcastMessage(event.getWhoClicked().getName() + " was robbed by bertrude (L)");
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

    @EventHandler
    public void onPlayerJoin(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().equals(PrisonGame.bertrude)) {
            event.getPlayer().sendMessage("hello i am bertrude");
            event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
            Inventory inv = Bukkit.createInventory(null, 9, "bertrude");
            inv.addItem(PrisonGame.createGuiItem(Material.POTION, ChatColor.LIGHT_PURPLE + "epic bertude night vision", ChatColor.GRAY + "gives you night vision i think"));
            inv.addItem(PrisonGame.createGuiItem(Material.GRAY_STAINED_GLASS, ChatColor.LIGHT_PURPLE + "no warden spaces", ChatColor.GRAY + "disables/enables the spaces on the warden's messages"));
            inv.addItem(PrisonGame.createGuiItem(Material.NETHERITE_SWORD, ChatColor.LIGHT_PURPLE + "-1 dollar", ChatColor.GRAY + "this is a robbery"));
            event.getPlayer().openInventory(inv);
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(ChatColor.GOLD + event.getPlayer().getName() + " was caught and sent to prison! (JOIN)");
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
        PrisonGame.st.put(event.getPlayer(), 0.0);
        PrisonGame.sp.put(event.getPlayer(), 0.0);
        playerJoin(event.getPlayer(), false);
    }

    @EventHandler
    public void deathmsg(PlayerDeathEvent event) {
        event.getDrops().removeIf(i -> i.getType() == Material.TRIPWIRE_HOOK);
        if (PrisonGame.warden != null) {
            if (PrisonGame.warden.equals(event.getEntity())) {
                event.getDrops().clear();
                PrisonGame.warden = null;
                PrisonGame.type.put(event.getEntity(), 0);
                MyListener.playerJoin(event.getEntity(), false);
            }
        }
        if (PrisonGame.type.get(event.getEntity()) == 0) {
            event.setDeathMessage(ChatColor.GRAY + event.getDeathMessage());
        }
        if (PrisonGame.type.get(event.getEntity()) != 0) {
            event.setDeathMessage(ChatColor.GOLD + event.getDeathMessage());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event) {
        event.setQuitMessage(ChatColor.GOLD + event.getPlayer().getName() + " ran off somewhere else... (QUIT)");
    }


    @EventHandler
    public void chatCleanup(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        if (!event.getPlayer().getPersistentDataContainer().has(PrisonGame.muted, PersistentDataType.INTEGER)) {
            if (PrisonGame.warden == event.getPlayer()) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!p.getPersistentDataContainer().has(PrisonGame.whiff, PersistentDataType.INTEGER)) {
                        p.sendMessage("");
                    }
                    if (!p.getPersistentDataContainer().has(PrisonGame.whiff, PersistentDataType.INTEGER)) {
                        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
                    }

                    p.sendMessage(event.getPlayer().getPlayerListName() + ChatColor.RED + ": " + ChatColor.RED + FilteredWords.filtermsg(event.getMessage()));
                    if (!p.getPersistentDataContainer().has(PrisonGame.whiff, PersistentDataType.INTEGER)) {
                        p.sendMessage("");
                    }
                }
            }
            if (PrisonGame.warden != event.getPlayer())
                Bukkit.broadcastMessage(event.getPlayer().getPlayerListName() + ChatColor.GRAY + ": " + ChatColor.GRAY + ChatColor.GRAY + FilteredWords.filtermsg(event.getMessage()));
        } else {
            event.getPlayer().sendMessage(ChatColor.RED + "you're muted lol");
        }
    }

    @EventHandler
    public void ee(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            if (event.getCurrentItem().getItemMeta() != null) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "The End?")) {
                    PrisonGame.active = PrisonGame.endmap;
                    PrisonGame.swapcool = (20 * 60) * 5;
                    PrisonGame.bertrude.teleport(PrisonGame.active.getBert());
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (PrisonGame.type.get(p) != -1) {
                            MyListener.playerJoin(p, true);
                            p.sendTitle("New prison!", "THE END?");
                        } else {
                            p.teleport(PrisonGame.active.getWardenspawn());
                            p.sendTitle("New prison!", "THE END?");
                        }
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Fortress Of Gaeae")) {
                    PrisonGame.active = PrisonGame.gaeae;
                    for (Integer x = PrisonGame.gaeae.getCafedoor1().getBlockX(); x <= PrisonGame.gaeae.getCafedoor2().getBlockX(); x++) {
                        for (Integer y = PrisonGame.gaeae.getCafedoor1().getBlockY(); y <= PrisonGame.gaeae.getCafedoor2().getBlockY(); y++) {
                            for (Integer z = PrisonGame.gaeae.getCafedoor1().getBlockZ(); z <= PrisonGame.gaeae.getCafedoor2().getBlockZ(); z++) {
                                Bukkit.getWorld("world").getBlockAt(x, y, z).setType(Material.MUD_BRICKS);
                            }
                        }
                    }
                    PrisonGame.swapcool = (20 * 60) * 5;
                    PrisonGame.bertrude.teleport(PrisonGame.active.getBert());
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (PrisonGame.type.get(p) != -1) {
                            MyListener.playerJoin(p, true);
                            p.sendTitle("New prison!", "FORTRESS OF GAEAE");
                        } else {
                            p.teleport(PrisonGame.active.getWardenspawn());
                            p.sendTitle("New prison!", "FORTRESS OF GAEAE");
                        }
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Hypertech")) {
                    PrisonGame.active = PrisonGame.hyper;
                    for (Integer x = 1; x <= 3; x++) {
                        Bukkit.getWorld("world").getBlockAt(x, -58, -1008).setType(Material.MUD_BRICKS);
                    }
                    PrisonGame.swapcool = (20 * 60) * 5;
                    PrisonGame.bertrude.teleport(PrisonGame.active.getBert());
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (PrisonGame.type.get(p) != -1) {
                            MyListener.playerJoin(p, true);
                            p.sendTitle("New prison!", "HYPERTECH");
                        } else {
                            p.teleport(PrisonGame.active.getWardenspawn());
                            p.sendTitle("New prison!", "HYPERTECH");
                        }
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Rock")) {
                    event.setCancelled(true);
                    if (event.getWhoClicked().getInventory().containsAtLeast(new ItemStack(Material.STONE_BUTTON), 9)) {
                        event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.STONE_BUTTON, 9));
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.COBBLESTONE));
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
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Normal Crafting")) {
                    event.getWhoClicked().openWorkbench(null, true);
                }
            }
        }
    }

    @EventHandler
    public void ee(PlayerMoveEvent event) {
        if (PrisonGame.isInside(event.getPlayer(), PrisonGame.active.getRunpoint1(), PrisonGame.active.getRunpoint2()) && PrisonGame.active.getRunpoint1().getY() > event.getPlayer().getLocation().getY()) {
            PrisonGame.sp.put(event.getPlayer(), PrisonGame.sp.getOrDefault(event.getPlayer(), 0.0) + 0.5);
            event.getPlayer().sendTitle("", ChatColor.GREEN + PrisonGame.sp.get(event.getPlayer()).toString() + "/120", 0, 10, 10);
            if (PrisonGame.sp.get(event.getPlayer()) >= 120) {
                PrisonGame.sp.put(event.getPlayer(), 0.0);
                if (event.getPlayer().hasPotionEffect(PotionEffectType.SPEED)) {
                    event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(event.getPlayer().getPotionEffect(PotionEffectType.SPEED).getDuration() + 20 * 25, 0));
                } else {
                    event.getPlayer().addPotionEffect(PotionEffectType.SPEED.createEffect(20 * 30, 0));
                }
            }
        }
    }
    /*@EventHandler
    public void ee(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().getType().equals(Material.CHAIN) && !PrisonGame.isInside(event.getPlayer(), new Location(Bukkit.getWorld("world"), 67, -54,  -194), new Location(Bukkit.getWorld("world"), 73, -59,  -161))) {
                PrisonGame.st.put(event.getPlayer(), PrisonGame.st.getOrDefault(event.getPlayer(), 0.0) + 0.5);
                event.getPlayer().sendTitle("", ChatColor.GREEN + PrisonGame.st.get(event.getPlayer()).toString() + "/120", 0, 10, 10);
                if (PrisonGame.st.get(event.getPlayer()) >= 120) {
                    if (event.getPlayer().hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                        PrisonGame.st.put(event.getPlayer(), 0.0);
                        event.getPlayer().addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(event.getPlayer().getPotionEffect(PotionEffectType.INCREASE_DAMAGE).getDuration() + 20 * 15, 0));
                    } else {
                        event.getPlayer().addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(20 * 30, 0));
                    }
                }
            }
        }
    }*/

    @EventHandler
    public void chatCleanup(EntityDamageByEntityEvent event) {
        if (Bukkit.getWorld("world").getTime() > 0 && Bukkit.getWorld("world").getTime() < 2500) {
            if (event.getEntity() instanceof Player) {
                Player p = (Player) event.getEntity();
                if (new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ()).getBlock().getType().equals(Material.RED_SAND)) {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void chatCleanup(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().getType().equals(Material.ENDER_CHEST) || event.getClickedBlock().getType().equals(Material.BLAST_FURNACE) || event.getClickedBlock().getType().equals(Material.SMOKER) || event.getClickedBlock().getType().equals(Material.FURNACE) || event.getClickedBlock().getType().equals(Material.BARREL) || event.getClickedBlock().getType().equals(Material.CHEST) || event.getClickedBlock().getType().equals(Material.HOPPER) || event.getClickedBlock().getType().equals(Material.DROPPER) || event.getClickedBlock().getType().equals(Material.DISPENSER)) {
                if (PrisonGame.type.get(event.getPlayer()) != 0) {
                    event.getPlayer().sendMessage(ChatColor.RED + "You can't access this!");
                    event.setCancelled(true);
                }
            }
            if (event.getClickedBlock().getType().equals(Material.COBBLESTONE) || event.getClickedBlock().getType().equals(Material.STONE) || event.getClickedBlock().getType().equals(Material.ANDESITE)) {
                event.getPlayer().getInventory().addItem(new ItemStack(Material.STONE_BUTTON));
            }
            if (event.getClickedBlock().getType().equals(Material.OAK_WALL_SIGN)) {
                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) event.getClickedBlock().getState();
                if (sign.getLine(1).equals("+1 time tick")) {
                        Bukkit.getWorld("world").setTime(Bukkit.getWorld("world").getTime() + 1);
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
                if (sign.getLine(1).equals("Switch Maps")) {
                    if (event.getPlayer() == PrisonGame.warden) {
                        if (PrisonGame.swapcool <= 0) {
                            Inventory inv = Bukkit.createInventory(null, 9, "Map Switch");
                            inv.addItem(PrisonGame.createGuiItem(Material.COBBLESTONE, ChatColor.GRAY + "Fortress Of Gaeae"));
                            inv.addItem(PrisonGame.createGuiItem(Material.QUARTZ_BLOCK, ChatColor.WHITE + "Hypertech"));
                            inv.addItem(PrisonGame.createGuiItem(Material.END_CRYSTAL, ChatColor.DARK_PURPLE + "The End?"));
                            event.getPlayer().openInventory(inv);
                        } else {
                            event.getPlayer().sendMessage("That's on cooldown!");
                        }

                    }
                }

                if (sign.getLine(2).equals("Scrap Metal")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 150.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)  - 150);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.RAW_IRON));
                    }
                }
                if (sign.getLine(2).equals("Not Drugs")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 30.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 30.0);

                        ItemStack card = new ItemStack(Material.GOLDEN_APPLE, 1);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Gapple " + ChatColor.RED + "[CONTRABAND]");
                        card.setItemMeta(cardm);

                        event.getPlayer().getInventory().addItem(card);
                    }
                }
                if (sign.getLine(2).equals("Chainmail Helmet")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 300.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 300.0);

                        ItemStack card = new ItemStack(Material.CHAINMAIL_HELMET);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Helmet " + ChatColor.RED + "[CONTRABAND]");
                        card.setItemMeta(cardm);

                        event.getPlayer().getInventory().addItem(card);
                    }
                }
                if (sign.getLine(2).equals("Tax Skip")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 15.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 15.0);

                        ItemStack card = new ItemStack(Material.BOOK);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.LIGHT_PURPLE + "Tax Skip");
                        card.setItemMeta(cardm);

                        event.getPlayer().getInventory().addItem(card);
                    }
                }
                if (sign.getLine(2).equals("Dagger")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 1000.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 1000.0);

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
                    inv.addItem(PrisonGame.createGuiItem(Material.TRIPWIRE_HOOK, ChatColor.LIGHT_PURPLE + "Fake Card", "§aRecipe:", "§b3 Paper", "§b2 Sticks"));
                    event.getPlayer().openInventory(inv);
                }, 1L);
            }
            if (event.getClickedBlock().getType().equals(Material.FURNACE)) {
                event.setCancelled(true);
                if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 15.0) {
                    if (event.getPlayer().getInventory().contains(Material.COAL) && event.getPlayer().getInventory().contains(Material.RAW_IRON)) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)- 15.0);
                        event.getPlayer().getInventory().removeItem(new ItemStack(Material.COAL, 1));
                        event.getPlayer().getInventory().removeItem(new ItemStack(Material.RAW_IRON, 1));
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.PAPER));
                    }
                } else {
                    event.getPlayer().sendMessage(ChatColor.RED + "Not enough money!");
                }
            }

            if (event.getClickedBlock().getType().equals(Material.SPRUCE_WALL_SIGN)) {
                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) event.getClickedBlock().getState();
                if (sign.getLine(1).equals("Guard Dogs")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 500.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)- 500.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.WOLF_SPAWN_EGG, 4));
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.BONE, 32));
                    }
                }
                if (sign.getLine(1).equals("PROT 1")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 500.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)- 500.0);
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
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 70.0);

                        ItemStack card = new ItemStack(Material.GOLDEN_APPLE, 1);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Gapple ");
                        card.setItemMeta(cardm);

                        event.getPlayer().getInventory().addItem(card);
                    }
                }
                if (sign.getLine(2).equals("Soup")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 2.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)- 2.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.BEETROOT_SOUP));
                    }
                }
                if (sign.getLine(2).equals("Steak")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 5.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)- 5.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.COOKED_BEEF));
                    }
                }
                if (sign.getLine(2).equals("Milk")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 5.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)- 5.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.MILK_BUCKET));
                    }
                }
                if (sign.getLine(2).equals("Vomit Pot")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 30.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)- 30.0);

                        ItemStack pot = new ItemStack(new ItemStack(Material.SPLASH_POTION));
                        PotionMeta potMeta = (PotionMeta) pot.getItemMeta();
                        potMeta.addCustomEffect(PotionEffectType.POISON.createEffect(20 * 10, 1), true);
                        pot.setItemMeta(potMeta);

                        event.getPlayer().getInventory().addItem(pot);
                    }
                }
                if (sign.getLine(1).equals("Cafeteria")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 1000.0) {
                        if (PrisonGame.active.cafedoor2.getBlock().getType().equals(Material.MUD_BRICKS)) {
                            event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - 1000.0);
                            Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + " Bought the cafeteria!");
                            if (PrisonGame.active.equals(PrisonGame.gaeae)) {
                                for (Integer x = PrisonGame.active.cafedoor1.getBlockX(); x <= PrisonGame.active.cafedoor2.getBlockX(); x++) {
                                    for (Integer y = PrisonGame.active.cafedoor1.getBlockY(); y <= PrisonGame.active.cafedoor2.getBlockY(); y++) {
                                        for (Integer z = PrisonGame.active.cafedoor1.getBlockZ(); z <= PrisonGame.active.cafedoor2.getBlockZ(); z++) {
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
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)- 30.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.HONEY_BOTTLE));
                    }
                }
                if (sign.getLine(2).equals("Strong Chest")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 1000.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)- 1000.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_CHESTPLATE));
                    }
                }
                if (sign.getLine(2).equals("Arrows")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 16.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)- 16.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.ARROW, 16));
                    }
                }
                if (sign.getLine(2).equals("Supreme Stick")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 50.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)- 50.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.STICK));
                    }
                }
                if (sign.getLine(2).equals("Coal")) {
                    if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) >= 30.0) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)- 30.0);
                        event.getPlayer().getInventory().addItem(new ItemStack(Material.COAL));
                    }
                }
                if (sign.getLine(2).equals("Lumberjack")) {
                    ItemStack card = new ItemStack(Material.WOODEN_AXE);
                    ItemMeta cardm = card.getItemMeta();
                    cardm.setDisplayName(ChatColor.GOLD + "Lumber's Axe");
                    card.setItemMeta(cardm);
                    event.getPlayer().getInventory().addItem(card);
                    event.getPlayer().sendMessage(ChatColor.GOLD + "Go cut down the spruce logs in the Lumberjack Station.");
                }
                if (sign.getLine(2).equals("Plumber")) {
                    ItemStack card = new ItemStack(Material.CARROT_ON_A_STICK);
                    ItemMeta cardm = card.getItemMeta();
                    cardm.setDisplayName(ChatColor.LIGHT_PURPLE + "Plumber");
                    card.setItemMeta(cardm);
                    event.getPlayer().getInventory().addItem(card);
                    event.getPlayer().sendMessage(ChatColor.BLUE + "Click on iron trapdoors with the plumber.");
                }
                if (sign.getLine(2).equals("Bounty Hunter")) {
                    ItemStack card = new ItemStack(Material.WOODEN_SWORD);
                    ItemMeta cardm = card.getItemMeta();
                    cardm.setDisplayName(ChatColor.RED + "Bounty Hunter's Knife");
                    card.setItemMeta(cardm);
                    event.getPlayer().getInventory().addItem(card);
                    event.getPlayer().sendMessage(ChatColor.RED + "Kill criminals (Glowing pepole).");
                }
            }
            if (event.getClickedBlock().getType().equals(Material.JUNGLE_WALL_SIGN)) {
                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) event.getClickedBlock().getState();
                if (sign.getLine(1).equals("Leave Market")) {
                    event.getPlayer().teleport(PrisonGame.active.getBmout());
                    event.getPlayer().addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 3, 0));
                }
                if (sign.getLine(1).equals("Get Gear")) {
                    if (PrisonGame.type.get(event.getPlayer()) == 0 && !PrisonGame.escaped.get(event.getPlayer())) {
                        Player g = event.getPlayer();
                        PrisonGame.escaped.put(event.getPlayer(), true);
                        Bukkit.broadcastMessage(ChatColor.RED + g.getName() + " escaped...");
                        event.getPlayer().addPotionEffect(PotionEffectType.GLOWING.createEffect(999999999, 0));

                        g.setCustomName(ChatColor.GRAY + "[" + ChatColor.RED + "CRIMINAL" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                        g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "CRIMINAL" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                        g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.RED + "CRIMINAL" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());

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
                        chestmeta.setColor(Color.RED);
                        chestmeta.setDisplayName("Armor " + ChatColor.RED + "[CONTRABAND]");
                        orangechest.setItemMeta(chestmeta);

                        ItemStack orangeleg = new ItemStack(Material.LEATHER_LEGGINGS);
                        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeleg.getItemMeta();
                        orangelegItemMeta.setDisplayName("Armor " + ChatColor.RED + "[CONTRABAND]");
                        orangelegItemMeta.setColor(Color.RED);
                        orangeleg.setItemMeta(orangelegItemMeta);


                        g.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                        g.getInventory().setChestplate(orangechest);
                        g.getInventory().setLeggings(orangeleg);

                        ItemStack wardenSword = new ItemStack(Material.WOODEN_SWORD);
                        wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
                        wardenSword.addEnchantment(Enchantment.DURABILITY, 1);

                        g.getInventory().addItem(wardenSword);

                        g.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));

                    }
                }
            }
            if (event.getItem() != null) {
                if (!event.getPlayer().hasCooldown(Material.STRUCTURE_VOID)) {
                    if (event.getItem().getType().equals(Material.STRUCTURE_VOID)) {
                        event.getPlayer().setCooldown(Material.STRUCTURE_VOID, 20 * 600);
                        Bukkit.getWorld("world").setTime(13000);
                    }
                }
            }
            if (event.getClickedBlock().getType().equals(Material.IRON_TRAPDOOR)) {
                if (event.getItem() != null) {
                    if (!event.getPlayer().hasCooldown(Material.CARROT_ON_A_STICK)) {
                        event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)+ 0.5 * MyTask.jobm);
                    }
                }
            }
            if (event.getClickedBlock().getType().equals(Material.SPRUCE_LOG)) {
                if (event.getItem() != null) {
                    if (event.getItem().getType().equals(Material.WOODEN_AXE)) {
                        if (!event.getPlayer().hasCooldown(Material.WOODEN_AXE)) {
                            event.getPlayer().setCooldown(Material.WOODEN_AXE, 10);
                            event.getPlayer().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)+ 2.0 * MyTask.jobm);
                        }
                    }
                }
            }
            if (event.getClickedBlock().getType().equals(Material.CAULDRON)) {
                if (PrisonGame.type.get(event.getPlayer()) != 1) {
                    event.getPlayer().teleport(PrisonGame.active.getBm());
                    event.getPlayer().sendTitle("", ChatColor.GRAY + "-= Black Market =-");
                }
            }
            if (event.getClickedBlock().getType().equals(Material.JUNGLE_DOOR)) {
                if (PrisonGame.type.get(event.getPlayer()) != -1) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(ChatColor.RED + "This door can only be opened by the warden!");
                }
            }
            if (event.getClickedBlock().getType().equals(Material.ACACIA_DOOR)) {
                BlockState state = event.getClickedBlock().getState();
                Door openable = (Door) state.getBlockData();

                if (Bukkit.getWorld("world").getTime() > 13000 && Bukkit.getWorld("world").getTime() < 24000) {
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
                                event.getPlayer().sendMessage(ChatColor.RED + "You can't open this during lockdown!");
                            }
                        } else {
                            event.setCancelled(true);
                            event.getPlayer().sendMessage(ChatColor.RED + "You can't open this during lockdown!");
                        }
                    } else {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(ChatColor.RED + "You can't open this during lockdown!");
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
                                openable.setOpen(true);
                                state.setBlockData(openable);
                                state.update();
                            } else {
                                openable.setOpen(false);
                                state.setBlockData(openable);
                                state.update();

                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void anyName(PlayerDropItemEvent event) {
        if (PrisonGame.type.get(event.getPlayer()) != 0) {
            event.getItemDrop().setItemStack(new ItemStack(Material.AIR));
        }
    }
    @EventHandler
    public void anyName(PlayerDeathEvent event) {
        Player p = event.getEntity();
        if(p.isDead()) {
            if (p.getKiller() != null) {
                if (p.hasPotionEffect(PotionEffectType.GLOWING)) {
                    p.getKiller().sendMessage(ChatColor.GREEN + "You killed a criminal and got 100$!");
                    p.getKiller().getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,p.getKiller().getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) + 100.0);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerRespawnEvent event) {
        Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
            if (PrisonGame.type.get(event.getPlayer()) == -1) {
                Player nw = event.getPlayer();
                nw.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                nw.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                nw.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                nw.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));

                ItemStack wardenSword = new ItemStack(Material.DIAMOND_SWORD);
                wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                wardenSword.addEnchantment(Enchantment.DURABILITY, 2);

                nw.getInventory().addItem(wardenSword);
                nw.getInventory().addItem(new ItemStack(Material.BOW));
                nw.getInventory().addItem(new ItemStack(Material.ARROW, 64));

                ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
                ItemMeta cardm = card.getItemMeta();
                cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
                card.setItemMeta(cardm);
                nw.getInventory().addItem(card);
            }
            if (PrisonGame.type.get(event.getPlayer()) == 3) {
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

                ItemStack wardenSword = new ItemStack(Material.DIAMOND_SWORD);
                wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                wardenSword.addEnchantment(Enchantment.DURABILITY, 1);

                g.getInventory().addItem(wardenSword);

                g.getInventory().addItem(new ItemStack(Material.BOW));
                g.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
                g.getInventory().addItem(new ItemStack(Material.ARROW, 16));
                g.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));

                ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
                ItemMeta cardm = card.getItemMeta();
                cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
                card.setItemMeta(cardm);
                g.getInventory().addItem(card);
            }
            if (PrisonGame.type.get(event.getPlayer()) == 2) {
                Player g = event.getPlayer();

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
                potionMeta.addCustomEffect(PotionEffectType.HEAL.createEffect(10, 2), true);
                pot.setItemMeta(potionMeta);

                g.getInventory().addItem(pot);

                ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
                ItemMeta cardm = card.getItemMeta();
                cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
                card.setItemMeta(cardm);
                g.getInventory().addItem(card);

            }
            if (PrisonGame.type.get(event.getPlayer()) == 1) {
                Player g = event.getPlayer();

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
            if (PrisonGame.type.get(event.getPlayer()) == 0) {
                playerJoin(event.getPlayer(), false);
                Player p = event.getPlayer();
                event.getPlayer().sendTitle("", ChatColor.GOLD + "you died.");
                p.setCustomName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + p.getName());
                p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + p.getName());
                p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + p.getName());


                if (p.getName().equals("agmass")) {
                    p.setCustomName(ChatColor.GRAY + "[" + ChatColor.RED + "OWNER" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "OWNER" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.RED + "OWNER" + ChatColor.GRAY + "] " + p.getDisplayName());
                }

                if (p.getName().equals("ClownCaked") || p.getName().equals("4950")) {
                    p.setCustomName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + p.getDisplayName());
                }
            }
            event.getPlayer().teleport(PrisonGame.active.getNursebed());
            event.getPlayer().sendTitle("RESPAWNING", "Wait 15 seconds.");
            event.getPlayer().addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(15 * 20, 0));
            event.getPlayer().addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(15 * 20, 255));
            event.getPlayer().addPotionEffect(PotionEffectType.LUCK.createEffect(15 * 20, 255));
            event.getPlayer().addPotionEffect(PotionEffectType.WEAKNESS.createEffect(15 * 20, 255));
            event.getPlayer().addPotionEffect(PotionEffectType.DARKNESS.createEffect(15 * 20, 0));
            LivingEntity bat = (LivingEntity) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.BAT);
            bat.setInvulnerable(true);
            bat.setAI(false);
            bat.setInvisible(true);
            bat.setSilent(true);
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
            event.getPlayer().setSpectatorTarget(bat);
            Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                bat.remove();
                if (event.getPlayer().getGameMode() != GameMode.ADVENTURE) {
                    event.getPlayer().setGameMode(GameMode.ADVENTURE);
                    Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                        event.getPlayer().teleport(PrisonGame.active.getNursebedOutTP());
                    }, 5);
                    Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                        event.getPlayer().teleport(PrisonGame.active.getNursebedOutTP());
                    }, 8);
                }
            }, 20 * 15);
        }, 1);
    }
}