package prisongame.prisongame;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import prisongame.prisongame.commands.*;
import prisongame.prisongame.commands.completers.WardenComplete;
import prisongame.prisongame.commands.danger.HardCommand;
import prisongame.prisongame.commands.danger.NormalCommand;
import prisongame.prisongame.commands.danger.ResetAscensionCommand;
import prisongame.prisongame.commands.economy.PayCommand;
import prisongame.prisongame.commands.economy.staff.NerdCheatCommand;
import prisongame.prisongame.commands.economy.staff.ResetMoneyCommand;
import prisongame.prisongame.commands.economy.staff.SetMoneyCommand;
import prisongame.prisongame.commands.staff.BuilderCommand;
import prisongame.prisongame.commands.staff.PBBReloadCommand;
import prisongame.prisongame.lib.Config;
import prisongame.prisongame.lib.Role;
import prisongame.prisongame.listeners.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public final class PrisonGame extends JavaPlugin {
    public static MiniMessage mm = MiniMessage.miniMessage();
    public static HashMap<Player, Double> st = new HashMap<>();
    public static HashMap<Player, Double> sp = new HashMap<>();
    public static Player warden = null;
    public static HashMap<Player, Boolean> escaped = new HashMap<>();
    public static HashMap<Player, Role> roles = new HashMap<>();
    public static HashMap<Player, Integer> askType = new HashMap<>();
    static HashMap<Player, Integer> lastward = new HashMap<>();
    static HashMap<Player, Integer> lastward2 = new HashMap<>();
    static HashMap<Player, Integer> wardenban = new HashMap<>();
    public static HashMap<Player, String> word = new HashMap<>();
    static HashMap<Player, Integer> saidcycle = new HashMap<>();
    public static HashMap<Player, String> prisonnumber = new HashMap<>();
    static HashMap<Player, Double> wealthcycle = new HashMap<>();
    public static HashMap<Player, Integer> wardentime = new HashMap<>();
    static HashMap<Player, Integer> calls = new HashMap<>();
    public static HashMap<Player, Integer> worryachieve = new HashMap<>();
    public static HashMap<Player, Integer> axekills = new HashMap<>();
    static HashMap<Player, Integer> timebet = new HashMap<>();
    public static Boolean givepig = false;
//    public static Prison gaeae;
//    public static Prison rag;
//    public static Prison hyper;
//    public static Prison endmap;
//    public static Prison train;
//    public static Prison gladiator;
//    public static Prison island;
//    public static Prison santa;
//    public static Prison volcano;
//    public static Prison boat;
//    public static Prison nether;
//    public static Prison amongus;
//    public static Prison ms;
    public static Integer solitcooldown = 0;
    public static Prison active = null;
    public static NamespacedKey nightvis;
    static NamespacedKey rank;
    static NamespacedKey coarsemined;
    public static NamespacedKey hg;
    public static NamespacedKey ascendcoins;
    public static NamespacedKey guardelo;
    public static NamespacedKey doubincome;
    public static NamespacedKey taxevasion;
    static NamespacedKey rankprefix;
    public static NamespacedKey semicloak;
    public static NamespacedKey reinforcement;
    public static NamespacedKey bckupmny;
    public static NamespacedKey protspawn;
    public static NamespacedKey tab;
    public static NamespacedKey randomz;
    public static Integer swapcool = 0;
    public static Integer wardenCooldown = 20;
    public static Integer lockdowncool = 0;
    public static NamespacedKey whiff;
    public static Boolean wardenenabled = false;

    public static NamespacedKey mny;
    static HashMap<Player, Integer> respect = new HashMap<>();
    public static HashMap<Player, Integer> solittime = new HashMap<>();
    static HashMap<Material, Double> moneyore = new HashMap<>();
    static HashMap<Player, Player> handcuff = new HashMap<>();
    public static HashMap<Player, Integer> trustlevel = new HashMap<>();
    public static HashMap<Player, Integer> prisonerlevel = new HashMap<>();
    public static HashMap<Player, Boolean> gotcafefood = new HashMap<>();
    static NamespacedKey trust;
    public static HashMap<Player, Boolean> hardmode = new HashMap<>();
    public static HashMap<Player, Player> killior = new HashMap<>();
    public static NamespacedKey muted;

    public static HashMap<UUID, HashMap<UUID, Integer>> savedPlayerGuards = new HashMap<>();

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
    static LivingEntity guardsh;

    static Villager bmsh1;
    static Villager bmsh2;
    static Villager shop;
    public static Boolean chatmuted = false;
    public static Boolean grammar = false;
    public static Boolean swat = false;

    public static void tptoBed(Player p) {
        p.teleport(PrisonGame.active.getNursebedOutTP());
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
        Config.register();
        setupLuckPerms();
        loadGuardData();
        setupKeys();
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
            savedPlayerGuards = data.playerguards;
            Bukkit.broadcastMessage("LOADED PLAYER'S GUARDS");
        }
    }

    public void setupKeys() {
        nightvis = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "night");
        mny = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "money");
        whiff = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "whiff");
        muted = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "mutedd");
        coarsemined = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "coarsemined");
        ascendcoins = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "ascendcoins");
        bckupmny = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "bckupmny");
        hg = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "headguard");
        tab = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "oldtab");
        doubincome = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "doubleinc");
        taxevasion = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "taxev");
        semicloak = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "scloak");
        reinforcement = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "reinf");
        protspawn = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "prots");
        randomz = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "rand");
        rankprefix = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "rankprefix");
        trust = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "trust");
        guardelo = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "elo");
        Bukkit.broadcastMessage("RELOAD: Loaded NameSpacedKeys");
    }

    public void setupOres() {
        moneyore.put(Material.DEEPSLATE_COPPER_ORE, 7.5);
        moneyore.put(Material.DEEPSLATE_EMERALD_ORE, 45.0);
        moneyore.put(Material.DEEPSLATE_GOLD_ORE, 25.0);
        moneyore.put(Material.DEEPSLATE_LAPIS_ORE, 15.0);
        moneyore.put(Material.DEEPSLATE_REDSTONE_ORE, 10.0);
    }

    public void registerCommands() {
        this.getCommand("pbbreload").setExecutor(new PBBReloadCommand());
        this.getCommand("warden").setExecutor(new WardenCommand());
        this.getCommand("warden").setTabCompleter(new WardenComplete());
        this.getCommand("resign").setExecutor(new ResignCommand());
        this.getCommand("tc").setExecutor(new TeamChatCommand());
        this.getCommand("disc").setExecutor(new DiscordCommand());
        this.getCommand("accept").setExecutor(new AcceptCommand());
        this.getCommand("hello").setExecutor(new HelloCommand());
        this.getCommand("nerdcheatcommand").setExecutor(new NerdCheatCommand());
        this.getCommand("rstmoney").setExecutor(new ResetMoneyCommand());
        this.getCommand("pay").setExecutor(new PayCommand());
        this.getCommand("builder").setExecutor(new BuilderCommand());
        this.getCommand("rstascen").setExecutor(new ResetAscensionCommand());
        this.getCommand("hard").setExecutor(new HardCommand());
        this.getCommand("normal").setExecutor(new NormalCommand());
        this.getCommand("setmoney").setExecutor(new SetMoneyCommand());
        this.getCommand("pbsettings").setExecutor(new PBSettingsCommand());
        Bukkit.broadcastMessage("RELOAD: Loaded Commands");
    }

    public void restorePlayerRoles() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setGameMode(GameMode.ADVENTURE);
            if (p.getDisplayName().contains("GUARD")) {
                PrisonGame.roles.put(p, Role.GUARD);
                p.sendMessage("RELOAD: Restored Guards");
            }
            if (p.getDisplayName().contains("NURSE")) {
                PrisonGame.roles.put(p, Role.NURSE);
                p.sendMessage("RELOAD: Restored Nurses");
            }
            if (p.getDisplayName().contains("SWAT")) {
                PrisonGame.roles.put(p, Role.SWAT);
                p.sendMessage("RELOAD: Restored SWATs");
            }
            if (p.getDisplayName().contains("CRIMINAL")) {
                PrisonGame.escaped.put(p, true);
                p.addPotionEffect(PotionEffectType.GLOWING.createEffect(99999, 255));
                p.sendMessage("RELOAD: Restored Criminals");
            }
            if (p.getDisplayName().contains("PRISONER")) {
                PrisonGame.roles.put(p, Role.PRISONER);
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
            p.removePotionEffect(PotionEffectType.DARKNESS);
            p.removePotionEffect(PotionEffectType.WEAKNESS);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist off");
            p.sendTitle(ChatColor.GREEN + "Loaded!", "thanks for your patience!", 0, 40, 0);
            PrisonGame.st.put(p, 0.0);
            PrisonGame.sp.put(p, 0.0);
            if (!PrisonGame.roles.containsKey(p)) {
                PrisonGame.roles.put(p, Role.PRISONER);
                MyListener.playerJoin(p, true);
            }

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
    public static void setNurse(Player g) {
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Guards").addPlayer(g);
        PrisonGame.roles.put(g, Role.NURSE);
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + g.getName() + " was promoted to a nurse!");

        g.setCustomName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
        g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
        g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());

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
        if (g.getPersistentDataContainer().has(PrisonGame.hg, PersistentDataType.INTEGER)) {
            g.getInventory().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
        }

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

        ItemStack card2 = new ItemStack(Material.IRON_SHOVEL);
        ItemMeta cardm2 = card2.getItemMeta();
        cardm2.setDisplayName(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]");
        cardm2.addEnchant(Enchantment.KNOCKBACK, 1, true);
        card2.setItemMeta(cardm2);
        g.getInventory().addItem(card2);

        if (hardmode.get(g)) {
            String prisonerNumber = "" + new Random().nextInt(100, 999);
            PrisonGame.prisonnumber.put(g, prisonerNumber);
            PlayerDisguise playerDisguise = new PlayerDisguise("Hubertus1703" );
            playerDisguise.setName("NURSE " + prisonerNumber);
            playerDisguise.setKeepDisguiseOnPlayerDeath(true);
            DisguiseAPI.disguiseToAll(g, playerDisguise);
            g.setCustomName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE " + ChatColor.GRAY + "] " + ChatColor.GRAY + "NURSE" + prisonerNumber);
            g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "HARD MODE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
            g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE " + ChatColor.GRAY + "] " + ChatColor.GRAY + "NURSE" + prisonerNumber);
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + g.getName() + " only prison:guard");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + PrisonGame.warden.getName() + " only prison:support");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + g.getName() + " only prison:swat");
        Player nw = (Player) g;
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

    }
    public static void setSwat(Player g) {
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Guards").addPlayer(g);
        PrisonGame.roles.put(g, Role.SWAT);
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + g.getName() + " was promoted to a SWAT member!");

        g.setCustomName(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
        g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
        g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());

        ItemStack orangechest = new ItemStack(Material.NETHERITE_CHESTPLATE);

        ItemStack orangeleg = new ItemStack(Material.NETHERITE_LEGGINGS);
        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);


        ItemStack orangeboot = new ItemStack(Material.LEATHER_BOOTS);
        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeboot.getItemMeta();
        orangelegItemMeta.setColor(Color.GRAY);
        orangeboot.setItemMeta(orangelegItemMeta);

        g.getInventory().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
        g.getInventory().setChestplate(orangechest);
        g.getInventory().setLeggings(orangeleg);
        g.getInventory().setBoots(orangeboot);

        if (g.getPersistentDataContainer().has(PrisonGame.hg, PersistentDataType.INTEGER)) {
            g.getInventory().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
        }

        ItemStack wardenSword = new ItemStack(Material.DIAMOND_SWORD);

        g.getInventory().addItem(wardenSword);

        g.getInventory().addItem(new ItemStack(Material.BOW));
        g.getInventory().addItem(new ItemStack(Material.ARROW, 16));
        g.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));

        ItemStack card2 = new ItemStack(Material.IRON_SHOVEL);
        ItemMeta cardm2 = card2.getItemMeta();
        cardm2.setDisplayName(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]");
        cardm2.addEnchant(Enchantment.KNOCKBACK, 1, true);
        card2.setItemMeta(cardm2);
        g.getInventory().addItem(card2);

        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta cardm = card.getItemMeta();
        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
        card.setItemMeta(cardm);
        g.getInventory().addItem(card);

        if (hardmode.get(g)) {
            String prisonerNumber = "" + new Random().nextInt(100, 999);
            PrisonGame.prisonnumber.put(g, prisonerNumber);
            PlayerDisguise playerDisguise = new PlayerDisguise("Hubertus1703");
            playerDisguise.setName("SWAT " + prisonerNumber);
            playerDisguise.setKeepDisguiseOnPlayerDeath(true);
            DisguiseAPI.disguiseToAll(g, playerDisguise);
            g.setCustomName(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + "SWAT " + prisonerNumber);
            g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "HARD MODE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
            g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + "SWAT " + prisonerNumber);
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + g.getName() + " only prison:guard");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + PrisonGame.warden.getName() + " only prison:support");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + g.getName() + " only prison:swat");
        Player nw = (Player) g;
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

    }

    public static void setGuard(Player g) {
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Guards").addPlayer(g);
        PrisonGame.roles.put(g, Role.GUARD);
        Bukkit.broadcastMessage(ChatColor.BLUE + g.getName() + " was promoted to a guard!");

        g.setCustomName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
        g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
        g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());


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

        if (g.getPersistentDataContainer().has(PrisonGame.hg, PersistentDataType.INTEGER)) {
            g.getInventory().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
        }

        ItemStack wardenSword = new ItemStack(Material.IRON_SWORD);
        wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        wardenSword.addEnchantment(Enchantment.DURABILITY, 1);

        g.getInventory().addItem(wardenSword);

        g.getInventory().addItem(new ItemStack(Material.CROSSBOW));
        g.getInventory().addItem(new ItemStack(Material.ARROW, 16));
        g.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));

        ItemStack card2 = new ItemStack(Material.IRON_SHOVEL);
        ItemMeta cardm2 = card2.getItemMeta();
        cardm2.setDisplayName(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]");
        cardm2.addEnchant(Enchantment.KNOCKBACK, 1, true);
        card2.setItemMeta(cardm2);
        g.getInventory().addItem(card2);

        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta cardm = card.getItemMeta();
        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
        card.setItemMeta(cardm);
        g.getInventory().addItem(card);

        if (hardmode.get(g)) {
            String prisonerNumber = "" + new Random().nextInt(100, 999);
            PrisonGame.prisonnumber.put(g, prisonerNumber);
            PlayerDisguise playerDisguise = new PlayerDisguise("Hubertus1703");
            playerDisguise.setName("GUARD " + prisonerNumber);
            playerDisguise.setKeepDisguiseOnPlayerDeath(true);
            DisguiseAPI.disguiseToAll(g, playerDisguise);
            g.setCustomName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + "GUARD " + prisonerNumber);
            g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "HARD MODE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
            g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + "GUARD " + prisonerNumber);
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + g.getName() + " only prison:guard");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + PrisonGame.warden.getName() + " only prison:support");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + g.getName() + " only prison:swat");
        Player nw = (Player) g;
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