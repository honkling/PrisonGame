package prisongame.prisongame;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import prisongame.prisongame.commands.*;
import prisongame.prisongame.commands.completers.*;
import prisongame.prisongame.commands.danger.HardCommand;
import prisongame.prisongame.commands.danger.NormalCommand;
import prisongame.prisongame.commands.danger.ResetAscensionCommand;
import prisongame.prisongame.commands.danger.staff.SeasonCommand;
import prisongame.prisongame.commands.economy.BalanceCommand;
import prisongame.prisongame.commands.economy.PayCommand;
import prisongame.prisongame.commands.economy.staff.NerdCheatCommand;
import prisongame.prisongame.commands.economy.staff.ResetMoneyCommand;
import prisongame.prisongame.commands.economy.staff.SetMoneyCommand;
import prisongame.prisongame.commands.staff.*;
import prisongame.prisongame.lib.*;
import prisongame.prisongame.listeners.*;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public final class PrisonGame extends JavaPlugin {
    public static PrisonGame instance;
    public static MiniMessage mm = MiniMessage.miniMessage();
    public static Player warden = null;
    public static Integer BBpower = 100;
    public static Boolean givepig = false;
    public static Integer solitcooldown = 0;
    public static Prison active = null;
    public static Integer swapcool = 0;
    public static Integer wardenCooldown = 20;
    public static Integer lockdowncool = 0;
    public static Boolean wardenenabled = false;
    static HashMap<Material, Double> moneyore = new HashMap<>();

    public static HashMap<UUID, HashMap<UUID, Role>> savedPlayerGuards = new HashMap<>();

    public static Material[] oretypes = {
            Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.DEEPSLATE_EMERALD_ORE
    };
    public static LivingEntity bertrude;
    public static Boolean chatmuted = false;
    public static Boolean grammar = false;
    public static Boolean FEMBOYS = false;
    public static Boolean swat = false;

    public static void tptoBed(Player p) {
        p.teleport(PrisonGame.active.getNursebedOutTP());
    }

    public static Component getPingDisplay(Player player) {
        var ping = player.getPing();

        var color = NamedTextColor.GREEN;
        if (ping > 400) color = NamedTextColor.RED;
        else if (ping > 200) color = NamedTextColor.YELLOW;

        return PrisonGame.mm.deserialize(
                "<gray>[<ping>ms]</gray>",
                Placeholder.component("ping", Component.text(ping).color(color))
        );
    }

    public static Location move(Location loc, Vector offset) {
        // Convert rotation to radians
        float ryaw = -loc.getYaw() / 180f * (float) Math.PI;
        float rpitch = loc.getPitch() / 180f * (float) Math.PI;

        //Conversions found by (a lot of) testing
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        z -= offset.getX() * Math.sin(ryaw);
        z += offset.getY() * Math.cos(ryaw) * Math.sin(rpitch);
        z += offset.getZ() * Math.cos(ryaw) * Math.cos(rpitch);
        x += offset.getX() * Math.cos(ryaw);
        x += offset.getY() * Math.sin(rpitch) * Math.sin(ryaw);
        x += offset.getZ() * Math.sin(ryaw) * Math.cos(rpitch);
        y += offset.getY() * Math.cos(rpitch);
        y -= offset.getZ() * Math.sin(rpitch);
        return new Location(loc.getWorld(), x, y, z, loc.getYaw(), 32);
    }
    public static Location lookAt(Location loc, Location lookat) {
        //Clone the loc to prevent applied changes to the input loc
        loc = loc.clone();

        // Values of change in distance (make it relative)
        double dx = lookat.getX() - loc.getX();
        double dy = lookat.getY() - loc.getY();
        double dz = lookat.getZ() - loc.getZ();

        // Set yaw
        if (dx != 0) {
            // Set yaw start value based on dx
            if (dx < 0) {
                loc.setYaw((float) (1.5 * Math.PI));
            } else {
                loc.setYaw((float) (0.5 * Math.PI));
            }
            loc.setYaw((float) loc.getYaw() - (float) Math.atan(dz / dx));
        } else if (dz < 0) {
            loc.setYaw((float) Math.PI);
        }

        // Get the distance from dx/dz
        double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

        // Set pitch
        loc.setPitch((float) -Math.atan(dy / dxz));

        // Set values, convert to degrees (invert the yaw since Bukkit uses a different yaw dimension format)
        loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
        loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);

        return loc;
    }

    public static LuckPerms api;
    @Override
    public void onEnable() {
        instance = this;
        Config.register();
        setupLuckPerms();
        loadGuardData();
        setupOres();
        registerCommands();
        restorePlayerRoles();
        beginReloadSafety();
        registerRecipes();
        removeEntities();
        setupPrisons();
        setupBertrude();
        endReloadSafety();
        registerEvents();
    }

    public void setupLuckPerms() {
        api = LuckPermsProvider.get();
    }

    public void loadGuardData() {
        if (Data.loadData("saveguard.data") != null) {
            Data data = new Data(Data.loadData("saveguard.data"));
            data.playerguards.forEach((warden, save) -> {
                savedPlayerGuards.put(warden, new HashMap<>());

                save.forEach((uuid, value) -> {
                    var role = value == -1 ? Role.WARDEN : Role.values()[value];
                    savedPlayerGuards.get(warden).put(uuid, role);
                });
            });
            Bukkit.broadcastMessage("LOADED PLAYER'S GUARDS");
        }
    }

    public void setupOres() {
        moneyore.put(Material.DEEPSLATE_COPPER_ORE, 7.5);
        moneyore.put(Material.DEEPSLATE_EMERALD_ORE, 45.0);
        moneyore.put(Material.DEEPSLATE_GOLD_ORE, 25.0);
        moneyore.put(Material.DEEPSLATE_LAPIS_ORE, 15.0);
        moneyore.put(Material.DEEPSLATE_REDSTONE_ORE, 10.0);
    }

    public void registerCommands() {
        this.getCommand("pay").setExecutor(new PayCommand());
        this.getCommand("hard").setExecutor(new HardCommand());
        this.getCommand("debug").setExecutor(new DebugCommand());
        this.getCommand("rules").setExecutor(new RulesCommand());
        this.getCommand("tc").setExecutor(new TeamChatCommand());
        this.getCommand("hello").setExecutor(new HelloCommand());
        this.getCommand("disc").setExecutor(new DiscordCommand());
        this.getCommand("season").setExecutor(new SeasonCommand());
        this.getCommand("vanish").setExecutor(new VanishCommand());
        this.getCommand("warden").setExecutor(new WardenCommand());
        this.getCommand("resign").setExecutor(new ResignCommand());
        this.getCommand("accept").setExecutor(new AcceptCommand());
        this.getCommand("normal").setExecutor(new NormalCommand());
        this.getCommand("balance").setExecutor(new BalanceCommand());
        this.getCommand("builder").setExecutor(new BuilderCommand());
        this.getCommand("setmoney").setExecutor(new SetMoneyCommand());
        this.getCommand("pbbreload").setExecutor(new PBBReloadCommand());
        this.getCommand("rstmoney").setExecutor(new ResetMoneyCommand());
        this.getCommand("enderchest").setExecutor(new EnderChestCommand());
        this.getCommand("pbsettings").setExecutor(new PBSettingsCommand());
        this.getCommand("rstascen").setExecutor(new ResetAscensionCommand());
        this.getCommand("nerdcheatcommand").setExecutor(new NerdCheatCommand());

        this.getCommand("debug").setTabCompleter(new DebugCompleter());
        this.getCommand("warden").setTabCompleter(new WardenCompleter());
        this.getCommand("season").setTabCompleter(new SeasonCompleter());
        this.getCommand("balance").setTabCompleter(new BalanceCompleter());
        this.getCommand("builder").setTabCompleter(new BuilderCompleter());
        this.getCommand("setmoney").setTabCompleter(new SetMoneyCompleter());
        this.getCommand("enderchest").setTabCompleter(new EnderChestCompleter());
        Bukkit.broadcastMessage("RELOAD: Loaded Commands");
    }

    public void restorePlayerRoles() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            var profile = ProfileKt.getProfile(p);
            p.setGameMode(GameMode.ADVENTURE);
            if (p.getDisplayName().contains("GUARD")) {
                profile.setRole(Role.GUARD);
                p.sendMessage("RELOAD: Restored Guards");
            }
            if (p.getDisplayName().contains("NURSE")) {
                profile.setRole(Role.NURSE);
                p.sendMessage("RELOAD: Restored Nurses");
            }
            if (p.getDisplayName().contains("SWAT")) {
                profile.setRole(Role.SWAT);
                p.sendMessage("RELOAD: Restored SWATs");
            }
            if (p.getDisplayName().contains("CRIMINAL")) {
                profile.setEscaped(true);
                p.addPotionEffect(PotionEffectType.GLOWING.createEffect(99999, 255));
                p.sendMessage("RELOAD: Restored Criminals");
            }
            if (p.getDisplayName().contains("PRISONER")) {
                profile.setRole(Role.PRISONER);
                p.sendMessage("RELOAD: Restored Prisoner");
            }
        }
    }

    public void beginReloadSafety() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist on");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:kick @a Reload");
    }

    public void registerRecipes() {
        NamespacedKey key = new NamespacedKey(this, "cobble");
        ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.COBBLESTONE));
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);

        Bukkit.broadcastMessage("RELOAD: Loaded Recipes");
        Bukkit.broadcastMessage("RELOAD: Safewaiting For Worlds");

        Bukkit.addRecipe(recipe);
    }

    public void removeEntities() {
        for (Entity e : Bukkit.getWorld("world").getEntities()) {
            if (e.getType().equals(EntityType.VILLAGER) || e.getType().equals(EntityType.WOLF)) {
                e.remove();
            }
        }
        Bukkit.broadcastMessage("RELOAD: Removed Entities");
    }

    public void setupPrisons() {
        active = Config.defaultPrison;
        Bukkit.broadcastMessage("RELOAD: Loaded Maps");
    }

    public void setupBertrude() {
        MyListener.reloadBert();
        Bukkit.broadcastMessage("RELOAD: loaded bertrude lmao");
    }

    public void endReloadSafety() {
        wardenenabled = true;
        for (Player p : Bukkit.getOnlinePlayers()) {
            var profile = ProfileKt.getProfile(p);
            p.removePotionEffect(PotionEffectType.DARKNESS);
            p.removePotionEffect(PotionEffectType.WEAKNESS);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist off");
            p.sendTitle(ChatColor.GREEN + "Loaded!", "thanks for your patience!", 0, 40, 0);

            if (PrisonGame.warden != null) {
                PrisonGame.warden.teleport(active.wardenspawn);
            }
        }

        MyTask task = new MyTask();
        task.runTaskTimer(getPlugin(this.getClass()), 0, 1);
    }

    public void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new AsyncPlayerChatListener(), this);
        pm.registerEvents(new BlockBreakListener(), this);
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new EntityDamageByEntityListener(), this);
        pm.registerEvents(new EntityDamageListener(), this);
        pm.registerEvents(new EntityDismountListener(), this);
        pm.registerEvents(new EntityMoveListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new PlayerAdvancementDoneListener(), this);
        pm.registerEvents(new PlayerBedLeaveListener(), this);
        pm.registerEvents(new PlayerChatListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerDropItemListener(), this);
        pm.registerEvents(new PlayerInteractAtEntityListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new PlayerItemConsumeListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerRespawnListener(), this);
        pm.registerEvents(new PlayerTeleportListener(), this);
        pm.registerEvents(new PlayerToggleSneakListener(), this);
        pm.registerEvents(new PlayerBedEnterListener(), this);
        pm.registerEvents(new PlayerCommandPreprocessListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        new Data(PrisonGame.savedPlayerGuards).saveData("saveguard.data");
        bertrude.remove();
        MyTask.bossbar.removeAll();
    }

    public static Location nl(String world, Double X, Double Y, Double Z, Float yaw, Float pitch) {
        return new Location(Bukkit.getWorld(world), X, Y, Z, yaw, pitch);
    }

    public static void setCriminal(Player player) {
        var profile = ProfileKt.getProfile(player);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + player.getName() + " only prison:escape");
        player.playSound(player, Sound.ITEM_GOAT_HORN_SOUND_1, 1, 1);
        Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " escaped...");
        player.addPotionEffect(PotionEffectType.GLOWING.createEffect(999999999, 0));

        player.setCustomName(ChatColor.GRAY + "[" + ChatColor.RED + "CRIMINAL" + ChatColor.GRAY + "] " + ChatColor.GRAY + player.getName());
        player.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "CRIMINAL" + ChatColor.GRAY + "] " + ChatColor.GRAY + player.getName());
        player.setDisplayName(ChatColor.GRAY + "[" + ChatColor.RED + "CRIMINAL" + ChatColor.GRAY + "] " + ChatColor.GRAY + player.getName());


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


        player.sendMessage(ChatColor.LIGHT_PURPLE + "Reclick the sign to get armor; it will override any current armor!");

        ItemStack wardenSword = new ItemStack(Material.STONE_SWORD);
        wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        wardenSword.addEnchantment(Enchantment.DURABILITY, 1);

        player.getInventory().addItem(wardenSword);

        player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 4));


        if (profile.getHardMode()) {
            var id = profile.getHardModeIdentifier();
            player.setCustomName(ChatColor.GRAY + "[" + ChatColor.DARK_RED + "CRIMINAL" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY  + "Criminal " + id);
            player.setDisplayName(ChatColor.GRAY + "[" + ChatColor.DARK_RED + "CRIMINAL" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY  + "Criminal " + id);
            player.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "HARD MODE" + ChatColor.DARK_GRAY + "] " + player.getName());
        }
    }

    public static void enableHardMode(Player player) {
        var profile = ProfileKt.getProfile(player);
        if (!(player.getDisplayName().contains("SOLITARY") && !(player.hasCooldown(Material.IRON_DOOR) && !new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 1, player.getLocation().getZ()).getBlock().getType().equals(Material.RED_SAND)))) {
            player.setViewDistance(2);
            Keys.BACKUP_MONEY.set(player, Keys.MONEY.get(player, 0.0));
            Keys.MONEY.set(player, 0.0);
            if (PrisonGame.warden != null) {
                if (PrisonGame.warden.equals(player)) {
                    PrisonGame.warden = null;
                }
            }
            profile.setRole(Role.PRISONER);
            MyListener.playerJoin(player, false);
            PlayerDisguise playerDisguise = new PlayerDisguise("pdlCAMERA");
            var prisonerNumber = profile.getHardModeIdentifier();
            playerDisguise.setName("Prisoner " + prisonerNumber);
            playerDisguise.setKeepDisguiseOnPlayerDeath(true);
            DisguiseAPI.disguiseToAll(player, playerDisguise);
            player.setCustomName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY  + "Prisoner " + prisonerNumber);
            player.setDisplayName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY  + "Prisoner " + prisonerNumber);

        }
    }

    public static void enableNormalMode(Player player) {
        var profile = ProfileKt.getProfile(player);
        if (!(player.getDisplayName().contains("SOLITARY") && !(player.hasCooldown(Material.IRON_DOOR) && !new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 1, player.getLocation().getZ()).getBlock().getType().equals(Material.RED_SAND)))) {
            Keys.MONEY.set(player, Keys.BACKUP_MONEY.get(player, 0.0));
            Keys.BACKUP_MONEY.set(player, 0.0);
            if (PrisonGame.warden != null) {
                if (PrisonGame.warden.equals(player)) {
                    PrisonGame.warden = null;
                }
            }
            profile.setRole(Role.PRISONER);
            DisguiseAPI.undisguiseToAll(player);
            MyListener.playerJoin(player, false);
        }
    }

    public static void setWarden(Player warden) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + warden.getName() + " only prison:mprison");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + warden.getName() + " only prison:guard");
        for (Player p : Bukkit.getOnlinePlayers()) {
            var profile = ProfileKt.getProfile(p);
            if (profile.getRole() != Role.PRISONER && profile.getRole() != Role.WARDEN) {
                MyListener.playerJoin(p, false);
            }
            if (profile.getRole() != Role.WARDEN)
                profile.setRole(Role.PRISONER, false);
            profile.setInvitation(null);
            p.playSound(p, Sound.BLOCK_END_PORTAL_SPAWN, 1, 1);
            p.sendTitle("", ChatColor.RED + warden.getName() + ChatColor.GREEN + " is the new warden!");
            PrisonGame.wardenCooldown = 20 * 6;
        }
        PrisonGame.warden = warden;
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Warden").addPlayer(warden);
        if (PrisonGame.savedPlayerGuards.containsKey(PrisonGame.warden)) {
            for (Player pe : Bukkit.getOnlinePlayers()) {
                if (PrisonGame.savedPlayerGuards.get(PrisonGame.warden.getUniqueId()).containsKey(pe.getUniqueId())) {
                    switch (PrisonGame.savedPlayerGuards.get(PrisonGame.warden.getUniqueId()).get(pe.getUniqueId())) {
                        case NURSE -> PrisonGame.setNurse((Player) pe);
                        case GUARD -> PrisonGame.setGuard((Player) pe);
                        case SWAT -> PrisonGame.setSwat((Player) pe);
                        default -> ((Player) pe).sendMessage("An error has occured.");
                    }
                }
            }
        }

        PrisonGame.swat = false;
        PrisonGame.chatmuted = false;
        PrisonGame.grammar = false;
        warden.teleport(PrisonGame.active.getWardenspawn());
        warden.setCustomName(ChatColor.GRAY + "[" + ChatColor.RED + "WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + warden.getName());
        warden.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + warden.getName());
        warden.setDisplayName(ChatColor.GRAY + "[" + ChatColor.RED + "WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + warden.getName());

        warden.setNoDamageTicks(20 * 45);
        ItemStack card2 = new ItemStack(Material.IRON_SHOVEL);
        ItemMeta cardm2 = card2.getItemMeta();
        cardm2.setDisplayName(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]");
        cardm2.addEnchant(Enchantment.KNOCKBACK, 1, true);
        card2.setItemMeta(cardm2);
        warden.getInventory().addItem(card2);

        warden.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        warden.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        warden.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        warden.getInventory().setBoots(new ItemStack(Material.NETHERITE_BOOTS));

        ItemStack wardenSword = new ItemStack(Material.DIAMOND_SWORD);
        wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        wardenSword.addEnchantment(Enchantment.DURABILITY, 2);


        warden.getInventory().addItem(wardenSword);
        warden.getInventory().addItem(new ItemStack(Material.BOW));
        warden.getInventory().addItem(new ItemStack(Material.ARROW, 64));
        warden.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));

        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta cardm = card.getItemMeta();
        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
        card.setItemMeta(cardm);
        warden.getInventory().addItem(card);

        if (warden.getInventory().getHelmet() != null)
            warden.getInventory().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        if (warden.getInventory().getChestplate() != null)
            warden.getInventory().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        if (warden.getInventory().getLeggings() != null)
            warden.getInventory().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        if (warden.getInventory().getBoots() != null)
            warden.getInventory().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

        warden.setHealth(20);
    }

    public static void setNurse(Player player) {
        var profile = ProfileKt.getProfile(player);
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Guards").addPlayer(player);
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + player.getName() + " was promoted to a nurse!");

        player.setCustomName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE" + ChatColor.GRAY + "] " + ChatColor.GRAY + player.getName());
        player.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE" + ChatColor.GRAY + "] " + ChatColor.GRAY + player.getName());
        player.setDisplayName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE" + ChatColor.GRAY + "] " + ChatColor.GRAY + player.getName());

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

        player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        player.getInventory().setChestplate(orangechest);
        player.getInventory().setLeggings(orangeleg);
        player.getInventory().setBoots(orangeboot);
        if (Keys.HEAD_GUARD.has(player)) {
            player.getInventory().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
        }

        ItemStack wardenSword = new ItemStack(Material.STONE_SWORD);
        wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        wardenSword.addEnchantment(Enchantment.DURABILITY, 1);

        player.getInventory().addItem(wardenSword);

        player.getInventory().addItem(new ItemStack(Material.CROSSBOW));
        player.getInventory().addItem(new ItemStack(Material.ARROW, 16));
        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));

        ItemStack pot = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta = (PotionMeta) pot.getItemMeta();
        potionMeta.addCustomEffect(PotionEffectType.HEAL.createEffect(10, 2), true);
        pot.setItemMeta(potionMeta);

        player.getInventory().addItem(pot);

        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta cardm = card.getItemMeta();
        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
        card.setItemMeta(cardm);
        player.getInventory().addItem(card);

        ItemStack card2 = new ItemStack(Material.IRON_SHOVEL);
        ItemMeta cardm2 = card2.getItemMeta();
        cardm2.setDisplayName(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]");
        cardm2.addEnchant(Enchantment.KNOCKBACK, 1, true);
        card2.setItemMeta(cardm2);
        player.getInventory().addItem(card2);

        if (profile.getHardMode()) {
            var prisonerNumber = profile.getHardModeIdentifier();
            PlayerDisguise playerDisguise = new PlayerDisguise("Hubertus1703" );
            playerDisguise.setName("NURSE " + prisonerNumber);
            playerDisguise.setKeepDisguiseOnPlayerDeath(true);
            DisguiseAPI.disguiseToAll(player, playerDisguise);
            player.setCustomName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE " + ChatColor.GRAY + "] " + ChatColor.GRAY + "NURSE" + prisonerNumber);
            player.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "HARD MODE" + ChatColor.GRAY + "] " + ChatColor.GRAY + player.getName());
            player.setDisplayName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE " + ChatColor.GRAY + "] " + ChatColor.GRAY + "NURSE" + prisonerNumber);
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + player.getName() + " only prison:guard");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + PrisonGame.warden.getName() + " only prison:support");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + player.getName() + " only prison:swat");
        Player nw = (Player) player;
        if (Keys.SPAWN_PROTECTION.has(nw)) {
            if (nw.getInventory().getHelmet() != null)
                nw.getInventory().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getChestplate() != null)
                nw.getInventory().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getLeggings() != null)
                nw.getInventory().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getBoots() != null)
                nw.getInventory().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        }

    }
    public static void setSwat(Player player) {
        var profile = ProfileKt.getProfile(player);
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Guards").addPlayer(player);
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + player.getName() + " was promoted to a SWAT member!");

        player.setCustomName(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + player.getName());
        player.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + player.getName());
        player.setDisplayName(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + player.getName());

        ItemStack orangechest = new ItemStack(Material.NETHERITE_CHESTPLATE);

        ItemStack orangeleg = new ItemStack(Material.NETHERITE_LEGGINGS);
        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);


        ItemStack orangeboot = new ItemStack(Material.LEATHER_BOOTS);
        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeboot.getItemMeta();
        orangelegItemMeta.setColor(Color.GRAY);
        orangeboot.setItemMeta(orangelegItemMeta);

        player.getInventory().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
        player.getInventory().setChestplate(orangechest);
        player.getInventory().setLeggings(orangeleg);
        player.getInventory().setBoots(orangeboot);

        if (Keys.HEAD_GUARD.has(player)) {
            player.getInventory().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
        }

        ItemStack wardenSword = new ItemStack(Material.DIAMOND_SWORD);

        player.getInventory().addItem(wardenSword);

        player.getInventory().addItem(new ItemStack(Material.BOW));
        player.getInventory().addItem(new ItemStack(Material.ARROW, 16));
        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));

        ItemStack card2 = new ItemStack(Material.IRON_SHOVEL);
        ItemMeta cardm2 = card2.getItemMeta();
        cardm2.setDisplayName(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]");
        cardm2.addEnchant(Enchantment.KNOCKBACK, 1, true);
        card2.setItemMeta(cardm2);
        player.getInventory().addItem(card2);

        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta cardm = card.getItemMeta();
        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
        card.setItemMeta(cardm);
        player.getInventory().addItem(card);

        if (profile.getHardMode()) {
            var prisonerNumber = profile.getHardModeIdentifier();
            PlayerDisguise playerDisguise = new PlayerDisguise("Hubertus1703");
            playerDisguise.setName("SWAT " + prisonerNumber);
            playerDisguise.setKeepDisguiseOnPlayerDeath(true);
            DisguiseAPI.disguiseToAll(player, playerDisguise);
            player.setCustomName(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + "SWAT " + prisonerNumber);
            player.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "HARD MODE" + ChatColor.GRAY + "] " + ChatColor.GRAY + player.getName());
            player.setDisplayName(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + "SWAT " + prisonerNumber);
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + player.getName() + " only prison:guard");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + PrisonGame.warden.getName() + " only prison:support");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + player.getName() + " only prison:swat");
        Player nw = (Player) player;
        if (Keys.SPAWN_PROTECTION.has(nw)) {
            if (nw.getInventory().getHelmet() != null)
                nw.getInventory().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getChestplate() != null)
                nw.getInventory().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getLeggings() != null)
                nw.getInventory().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getBoots() != null)
                nw.getInventory().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        }

    }

    public static void setGuard(Player player) {
        var profile = ProfileKt.getProfile(player);
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Guards").addPlayer(player);
        Bukkit.broadcastMessage(ChatColor.BLUE + player.getName() + " was promoted to a guard!");

        player.setCustomName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + player.getName());
        player.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + player.getName());
        player.setDisplayName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + player.getName());


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

        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(orangechest);
        player.getInventory().setLeggings(orangeleg);
        player.getInventory().setBoots(orangeboot);

        if (Keys.HEAD_GUARD.has(player)) {
            player.getInventory().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
        }

        ItemStack wardenSword = new ItemStack(Material.IRON_SWORD);
        wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        wardenSword.addEnchantment(Enchantment.DURABILITY, 1);

        player.getInventory().addItem(wardenSword);

        player.getInventory().addItem(new ItemStack(Material.CROSSBOW));
        player.getInventory().addItem(new ItemStack(Material.ARROW, 16));
        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));

        ItemStack card2 = new ItemStack(Material.IRON_SHOVEL);
        ItemMeta cardm2 = card2.getItemMeta();
        cardm2.setDisplayName(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]");
        cardm2.addEnchant(Enchantment.KNOCKBACK, 1, true);
        card2.setItemMeta(cardm2);
        player.getInventory().addItem(card2);

        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta cardm = card.getItemMeta();
        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
        card.setItemMeta(cardm);
        player.getInventory().addItem(card);

        if (profile.getHardMode()) {
            var prisonerNumber = profile.getHardModeIdentifier();
            PlayerDisguise playerDisguise = new PlayerDisguise("Hubertus1703");
            playerDisguise.setName("GUARD " + prisonerNumber);
            playerDisguise.setKeepDisguiseOnPlayerDeath(true);
            DisguiseAPI.disguiseToAll(player, playerDisguise);
            player.setCustomName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + "GUARD " + prisonerNumber);
            player.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "HARD MODE" + ChatColor.GRAY + "] " + ChatColor.GRAY + player.getName());
            player.setDisplayName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + "GUARD " + prisonerNumber);
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + player.getName() + " only prison:guard");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + PrisonGame.warden.getName() + " only prison:support");
        Player nw = (Player) player;
        if (Keys.SPAWN_PROTECTION.has(nw)) {
            if (nw.getInventory().getHelmet() != null)
                nw.getInventory().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getChestplate() != null)
                nw.getInventory().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getLeggings() != null)
                nw.getInventory().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getBoots() != null)
                nw.getInventory().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        }
    }

    public static boolean isInside(Player player, Location loc1, Location loc2)
    {
        double[] dim = new double[2];

        dim[0] = loc1.getX();
        dim[1] = loc2.getX();
        Arrays.sort(dim);
        if(player.getLocation().getX() > dim[1] || player.getLocation().getX() < dim[0])
            return false;

        dim[0] = loc1.getZ();
        dim[1] = loc2.getZ();
        Arrays.sort(dim);
        if(player.getLocation().getZ() > dim[1] || player.getLocation().getZ() < dim[0])
            return false;


        return true;
    }

    public static String formatBalance(double balance) {
        var format = new DecimalFormat("#0.0");

        if (Double.isInfinite(balance)) {
            var isPositive = balance > 0;

            return (isPositive ? "" : "-") + "∞";
        }

        return format.format(balance);
    }

    public static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

}