package prisongame.prisongame;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.LibsDisguises;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;
import oshi.jna.platform.mac.SystemB;

import java.io.Console;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PrisonGame extends JavaPlugin {

    static HashMap<Player, Double> st = new HashMap<>();
    static HashMap<Player, Double> sp = new HashMap<>();
    static Player warden = null;
    static HashMap<Player, Boolean> escaped = new HashMap<>();
    static HashMap<Player, Integer> type = new HashMap<>();
    static HashMap<Player, Integer> askType = new HashMap<>();
    static HashMap<Player, Integer> lastward = new HashMap<>();
    static HashMap<Player, Integer> lastward2 = new HashMap<>();
    static HashMap<Player, Integer> wardenban = new HashMap<>();
    static HashMap<Player, String> word = new HashMap<>();
    static HashMap<Player, Integer> saidcycle = new HashMap<>();
    static HashMap<Player, String> prisonnumber = new HashMap<>();
    static HashMap<Player, Double> wealthcycle = new HashMap<>();
    static HashMap<Player, Integer> wardentime = new HashMap<>();
    static HashMap<Player, Integer> worryachieve = new HashMap<>();
    static HashMap<Player, Integer> axekills = new HashMap<>();
    static HashMap<Player, Integer> timebet = new HashMap<>();
    static Boolean givepig = false;
    static Prison gaeae;
    static Prison hyper;
    static Prison endmap;
    static Prison train;
    static Prison gladiator;
    static Prison island;
    static Prison santa;
    static Prison volcano;
    static Prison boat;
    static Prison nether;
    static Prison amongus;
    static Integer solitcooldown = 0;
    static Prison active = null;
    static NamespacedKey nightvis;
    static NamespacedKey rank;
    static NamespacedKey coarsemined;
    static NamespacedKey hg;
    static NamespacedKey ascendcoins;
    static NamespacedKey doubincome;
    static NamespacedKey taxevasion;
    static NamespacedKey rankprefix;
    static NamespacedKey semicloak;
    static NamespacedKey reinforcement;
    static NamespacedKey bckupmny;
    static NamespacedKey protspawn;
    static NamespacedKey tab;
    static NamespacedKey randomz;
    static Integer swapcool = 0;
    static Integer wardenCooldown = 20;
    static Integer lockdowncool = 0;
    static NamespacedKey whiff;
    static Boolean wardenenabled = false;

    static NamespacedKey mny;
    static HashMap<Player, Integer> respect = new HashMap<>();
    static HashMap<Player, Integer> solittime = new HashMap<>();
    static HashMap<Material, Double> moneyore = new HashMap<>();
    static HashMap<Player, Player> handcuff = new HashMap<>();
    static HashMap<Player, Integer> trustlevel = new HashMap<>();
    static HashMap<Player, Integer> prisonerlevel = new HashMap<>();
    static NamespacedKey trust;
    static HashMap<Player, Boolean> hardmode = new HashMap<>();
    static HashMap<Player, Player> killior = new HashMap<>();
    static NamespacedKey muted;

    static Material[] oretypes = {
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
    static LivingEntity bertrude;
    static LivingEntity guardsh;
    static Villager bmsh1;
    static Villager bmsh2;
    static Villager shop;
    static Boolean swat = false;

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
    @Override
    public void onEnable() {
        // Plugin startup logic

        active = gaeae;
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

        moneyore.put(Material.DEEPSLATE_COPPER_ORE, 7.5);
        moneyore.put(Material.DEEPSLATE_EMERALD_ORE, 45.0);
        moneyore.put(Material.DEEPSLATE_GOLD_ORE, 25.0);
        moneyore.put(Material.DEEPSLATE_LAPIS_ORE, 15.0);
        moneyore.put(Material.DEEPSLATE_REDSTONE_ORE, 10.0);

        Bukkit.broadcastMessage("RELOAD: Loaded NameSpacedKeys");
        this.getCommand("warden").setExecutor(new CommandKit());
        this.getCommand("resign").setExecutor(new TestCommand());
        this.getCommand("tc").setExecutor(new TeamChat());
        this.getCommand("disc").setExecutor(new Discordcmd());
        this.getCommand("accept").setExecutor(new accpt());
        this.getCommand("hello").setExecutor(new hello());
        this.getCommand("nerdcheatcommand").setExecutor(new shittonmoney());
        //this.getCommand("bragrightpackage").setExecutor(new bragright());
        this.getCommand("rstmoney").setExecutor(new nomone());
        this.getCommand("amute").setExecutor(new accmute());
        this.getCommand("aunmute").setExecutor(new accunmute());
        this.getCommand("pay").setExecutor(new gib());
        this.getCommand("safereload").setExecutor(new sfreload());
        this.getCommand("builder").setExecutor(new BuilderCMD());
        this.getCommand("rstascen").setExecutor(new resetasc());
        this.getCommand("hard").setExecutor(new hard());
        this.getCommand("normal").setExecutor(new normal());
        this.getCommand("setmoney").setExecutor(new stmoney());
        this.getCommand("pbsettings").setExecutor(new settings());
        this.getCommand("giverank").setExecutor(new giverank());

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("RELOAD: Loaded Commands");
            p.setGameMode(GameMode.ADVENTURE);
            if (p.getDisplayName().contains("GUARD")) {
                PrisonGame.type.put(p, 1);
                p.sendMessage("RELOAD: Restored Guards");
            }
            if (p.getDisplayName().contains("NURSE")) {
                PrisonGame.type.put(p, 2);
                p.sendMessage("RELOAD: Restored Nurses");
            }
            if (p.getDisplayName().contains("SWAT")) {
                PrisonGame.type.put(p, 3);
                p.sendMessage("RELOAD: Restored SWATs");
            }
            if (p.getDisplayName().contains("CRIMINAL")) {
                PrisonGame.escaped.put(p, true);
                p.addPotionEffect(PotionEffectType.GLOWING.createEffect(99999, 255));
                p.sendMessage("RELOAD: Restored Criminals");
            }
            if (p.getDisplayName().contains("PRISONER")) {
                PrisonGame.type.put(p, 0);
                p.sendMessage("RELOAD: Restored Prisoner");
            }
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist on");
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
        while (Bukkit.getWorld("world") == null) {
            // hi chat
        }
            // code
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:kick @a Reload");

            for (Entity e : Bukkit.getWorld("world").getEntities()) {
                if (e.getType().equals(EntityType.VILLAGER) || e.getType().equals(EntityType.WOLF)) {
                    e.remove();
                }
            }
            Bukkit.broadcastMessage("RELOAD: Removed Entities");
            gaeae = new Prison("Gaeae Fort", new Location(Bukkit.getWorld("world"), 61, -54, -159), new Location(Bukkit.getWorld("world"), 76, -59, -169), new Location(Bukkit.getWorld("world"), 44, -58, -141), new Location(Bukkit.getWorld("world"), 44, -58, -137), new Location(Bukkit.getWorld("world"), 41.5, -52, -120.5), new Location(Bukkit.getWorld("world"), 12, -60, -119), new Location(Bukkit.getWorld("world"), -26.5, -56.5, -115.5), new Location(Bukkit.getWorld("world"), -8.5, -57, -108.5), new Location(Bukkit.getWorld("world"), 33, -59, -132), new Location(Bukkit.getWorld("world"), 70.5, -59, -137.5), new Location(Bukkit.getWorld("world"), 87, -59, -129), new Location(Bukkit.getWorld("world"), 87, -56, -125));
            hyper = new Prison("HyperTech", new Location(Bukkit.getWorld("world"), 18, -56, -988), new Location(Bukkit.getWorld("world"), 8, -59, -981), new Location(Bukkit.getWorld("world"), -29, -58, -988, 0, 0), new Location(Bukkit.getWorld("world"), -29, -58, -991), new Location(Bukkit.getWorld("world"), 12, -53, -970), new Location(Bukkit.getWorld("world"), -18, -59, -995), new Location(Bukkit.getWorld("world"), -26.5, -56.5, -115.5), new Location(Bukkit.getWorld("world"), 3.5, -59, -1006.5), new Location(Bukkit.getWorld("world"), 13, -59, -1009), new Location(Bukkit.getWorld("world"), 0.5, -59, -996.5), new Location(Bukkit.getWorld("world"), 1, -58, -1008), new Location(Bukkit.getWorld("world"), 3, -58, -1008));
            endmap = new Prison("The End?", new Location(Bukkit.getWorld("endprison"), 7, 133, 8), new Location(Bukkit.getWorld("endprison"), 19, 127, 20), new Location(Bukkit.getWorld("endprison"), -30, 170, -48), new Location(Bukkit.getWorld("endprison"), -32, 170, -47), new Location(Bukkit.getWorld("endprison"), -1, 150, 13), new Location(Bukkit.getWorld("endprison"), 0, 135, -41), new Location(Bukkit.getWorld("world"), -26.5, -56.5, -115.5), new Location(Bukkit.getWorld("endprison"), 0, 131, -6), new Location(Bukkit.getWorld("endprison"), 0, 125 ,0), new Location(Bukkit.getWorld("endprison"), 16.5, 129, 2.5), new Location(Bukkit.getWorld("endprison"), -100000, 256, -100000), new Location(Bukkit.getWorld("endprison"), -100000, 256, -100000));
            train = new Prison("Train", nl("world",0D,0D,0D, 0f, 0f), nl("world",0D,0D,0D, 0f, 0f), nl("world",82.8D,-58D,953D, 0f, 0f), nl("world",82.8D,-58D,953D, 0f, 0f), nl("world",83.0D,-54D,980D, -180f, 0f), nl("world",91.9D,-58D,982D, 0f, 0f), nl("world",64D,-56D,981D, -180f, 0f), nl("world",75.5D,-58D,964.5D, 0f, 0f), nl("world",91D,-58D,946D, 0f, 0f), nl("world",80.5D,-54D,961.5D, 0f, 0f), nl("world",79D,-58D,976D, 0f, 0f), nl("world",79D,-56D,975D, 0f, 0f));
            gladiator = new Prison("Gladiator", new Location(Bukkit.getWorld("world"), -2024, -55, 1919), new Location(Bukkit.getWorld("world"), -1999, -60, 1940), new Location(Bukkit.getWorld("world"), -2039, -59, 1933, 180, 0), new Location(Bukkit.getWorld("world"), -2040, -59, 1933, 180, 0), new Location(Bukkit.getWorld("world"), -2041, -47, 1957), new Location(Bukkit.getWorld("world"), -2037, -60, 1947), new Location(Bukkit.getWorld("world"), -2030, -60, 2015), new Location(Bukkit.getWorld("world"), -2022, -60, 1967), new Location(Bukkit.getWorld("world"), -1973, -60, 1984), new Location(Bukkit.getWorld("world"), -2022.5, -60, 1957.5), new Location(Bukkit.getWorld("world"), -2084, -60, 1973), new Location(Bukkit.getWorld("world"), -2080, -56, 1973));
            island = new Prison("Island", new Location(Bukkit.getWorld("world"), 1976, -55, -2001), new Location(Bukkit.getWorld("world"), 1968, -60, -2009), new Location(Bukkit.getWorld("world"), 2003, -59, -1988, 90, 0), new Location(Bukkit.getWorld("world"), 2003, -59, -1988, 90, 0), new Location(Bukkit.getWorld("world"), 1988, -60, -1978), new Location(Bukkit.getWorld("world"), 1964, -60, -1981), new Location(Bukkit.getWorld("world"), 1990, -59, -1861), new Location(Bukkit.getWorld("world"), 1989, -60, -1990), new Location(Bukkit.getWorld("world"), 1979, -60, -1982), new Location(Bukkit.getWorld("world"), 1981.5, -60, -1989.5), new Location(Bukkit.getWorld("world"), 1958, -60, -1999), new Location(Bukkit.getWorld("world"), 1962, -57, -1999));
            santa = new Prison("Santa's Workshop", nl("world", 1960D, -56D, 1990D, 0f, 0f), nl("world", 1973D, -60D, 1981D, 0f, 0f), nl("world", 1981D, -59D,  1993D, 0f, 0f), nl("world", 1981D, -59D, 1993D, 0f, 0f), nl("world", 1966D, -53D, 2003D, 0f, 0f), nl("world", 1961D, -60D, 1921D, 0f, 0f), nl("world", 1970D, -59D, 2041D, 0f, 0f), nl("world", 1957D, -60D, 1992D, 0f, 0f), nl("world", 1967D, -53D, 1999D, 0f, 0f), nl("world", 1957.5D, -60D, 2007.5D, 0f, 0f), nl("world", 1989D, -60D, 2008D, 0f, 0f), nl("world", 1989D, -57D, 2013D, 0f, 0f));
            volcano = new Prison("Volcano", nl("world", -2016D, -56D, -1933D, 0f, 0f), nl("world", -2025D, -60D, -1925D, 0f, 0f), nl("world", -2029D, -59D,  -2001D, 0f, 0f), nl("world", -2029D, -59D,  -2001D, 0f, 0f), nl("world", -2026D, -55D, -1956D, -90f, 0f), nl("world", -2004D, -60D, -1981D, 0f, 0f), nl("world", -1931D, -57D, -1976D, 0f, 0f), nl("world", -2019D, -60D, -1990D, 0f, 0f), nl("world", -2032D, -60D, -1966D, 0f, 0f), nl("world", -2011.5D, -60D, -1965.5D, 0f, 0f), nl("world", -2041D, -60D, -1974D, 0f, 0f), nl("world", -2041D, -57D, -1979D, 0f, 0f));
            boat = new Prison("Boat", nl("world", -1000D, -47D, 24D, 0f, 0f), nl("world", -996D, -50D, 22D, 0f, 0f), nl("world", -998D, -48D, 17D, 0f, 0f), nl("world", -998D, -48D, 17D, 0f, 0f), nl("world", -994D, -44D, 27D, -90f, 0f), nl("world", -993D, -54D, 7D, 0f, 0f), nl("world", -961D, -59D, 64D, 0f, 0f), nl("world",  -999.5D, -49D, 7D, 0f, 0f), nl("world", -988D, -57D, 18D, 0f, 0f), nl("world", -987D, -49D, 19D, 0f, 0f), nl("world", -992D, -48D, 27D, 0f, 0f), nl("world", -991D, -49D, 27D, 0f, 0f));
            nether = new Prison("Nether", nl("world", -1000D, -47D, 24D, 0f, 0f), nl("world", -996D, -50D, 22D, 0f, 0f), nl("world",  1009D, -38D, 990D, 0f, 0f), nl("world", 1009D, -38D, 990D, 0f, 0f), nl("world", 1007D, -34D, 994D, -90f, 0f), nl("world", 948D, -39D, 946D, 0f, 0f), nl("world",  937D, -59D, 1030D, 0f, 0f), nl("world",  981D, -35D, 976D, 0f, 0f), nl("world", 1003D, -38D, 1003D, 0f, 0f), nl("world", 956.3, -38D, -168.8, 0f, 0f), nl("world", -992D, -48D, 27D, 0f, 0f), nl("world", -991D, -49D, 27D, 0f, 0f));
            amongus = new Prison(
                    "Skeld",
                    nl("world", 0D, 0D, 0D, 0F, 0f),
                    nl("world", 0D, 0D, 0D, 0F, 0f),
                    nl("world", 1484D, -33D, 1475D, 0F, 0f),
                    nl("world", 1484D, -33D, 1475D, 0F, 0f),
                    nl("world", 1477D, -34D, 1433D, 0F, 0f),
                    nl("world", 1500D, -40D, 1494D, 0F, 0f),
                    nl("world", 1445D, -34D, 1497D, 0F, 0f),
                    nl("world", 1488D, -34D, 1507D, 0F, 0f),
                    nl("world", 1472D, -33D, 1470D, 0F, 0f),
                    nl("world", 1483D, -34D, 1502D, 0F, 0f),
                    nl("world", 0D, 0D, 0D, 0F, 0f),
                    nl("world", 0D, 0D, 0D, 0F, 0f)
            );
            active = gaeae;
            Bukkit.broadcastMessage("RELOAD: Loaded Maps");
            MyListener.reloadBert();
            Bukkit.broadcastMessage("RELOAD: loaded bertrude lmao");

            if (Data.loadData("save.data") != null) {
                Data data = new Data(Data.loadData("save.data"));

                if (data.isreload) {
                    if (data.ward != null) {
                        if (data.ward.isOnline()) {
                            Bukkit.broadcastMessage("RELOAD: Restoring warden");
                            PrisonGame.warden = (Player) data.ward;
                            PrisonGame.type.put(PrisonGame.warden, -1);
                        } else {
                            PrisonGame.warden = null;
                            Bukkit.broadcastMessage("RELOAD: Warden not online - Removing warden");
                        }
                    }
                    if (data.hasSwat) {
                        Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), -17, -60, -17)).setType(Material.RED_CONCRETE);
                        PrisonGame.swat = true;
                        Bukkit.broadcastMessage("RELOAD: Restored SWAT");
                    }
                    switch (data.map) {
                        case "Gaeae Fort":
                            active = gaeae;
                            break;
                        case "HyperTech":
                            active = hyper;
                            break;
                        case "The End?":
                            active = endmap;
                            break;
                        case "Train":
                            active = train;
                            break;
                        case "Gladiator":
                            active = gladiator;
                            break;
                        case "Island":
                            active = island;
                            break;
                        case "Santa's Workshop":
                            active = santa;
                            break;
                        case "Volcano":
                            active = volcano;
                            break;
                    }
                    Bukkit.broadcastMessage("RELOAD: Restored Map");
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (data.playerLocationHashMap != null) {
                            if (data.playerLocationHashMap.containsKey(p)) {
                                p.teleport(data.playerLocationHashMap.get(p));
                            } else {
                                Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                                    p.teleport(PrisonGame.active.getSpwn());
                                }, 5L);
                                Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                                    p.teleport(PrisonGame.active.getSpwn());
                                }, 8L);
                            }
                        }
                    }
                    Bukkit.broadcastMessage("RELOAD: Loaded SafeReload Save");
                    new Data(PrisonGame.warden, PrisonGame.active.getName(), false, PrisonGame.swat, new HashMap<Player, Location>()).saveData("save.data");
                    Bukkit.broadcastMessage("RELOAD: Reset SafeReload Save");
                }
            }

            wardenenabled = true;
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.removePotionEffect(PotionEffectType.DARKNESS);
                p.removePotionEffect(PotionEffectType.WEAKNESS);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist off");
                p.sendTitle(ChatColor.GREEN + "Loaded!", "thanks for your patience!", 0, 40, 0);
                PrisonGame.st.put(p, 0.0);
                PrisonGame.sp.put(p, 0.0);
                if (!PrisonGame.type.containsKey(p)) {
                    PrisonGame.type.put(p, 0);
                    MyListener.playerJoin(p, true);
                }

                if (PrisonGame.warden != null) {
                    PrisonGame.warden.teleport(active.wardenspawn);
                }
            }
            MyTask task = new MyTask();
            task.runTaskTimer(getPlugin(this.getClass()), 0, 1);
            getServer().getPluginManager().registerEvents(new MyListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        bertrude.remove();
        MyTask.bossbar.removeAll();
    }

    static Location nl(String world, Double X, Double Y, Double Z, Float yaw, Float pitch) {
        return new Location(Bukkit.getWorld(world), X, Y, Z, yaw, pitch);
    }
    static void setNurse(Player g) {
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Guards").addPlayer(g);
        PrisonGame.type.put(g, 2);
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

    }
    static void setSwat(Player g) {
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Guards").addPlayer(g);
        PrisonGame.type.put(g, 3);
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

    }

    static void setGuard(Player g) {
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Guards").addPlayer(g);
        PrisonGame.type.put(g, 1);
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

class TestCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!((Player) sender).hasCooldown(Material.IRON_DOOR)) {
            if (!((Player) sender).getDisplayName().contains("SOLITARY")) {
                if (PrisonGame.warden != null) {
                    if (PrisonGame.warden.equals(sender)) {
                        PrisonGame.wardenCooldown = 20 * 3;
                        PrisonGame.warden = null;
                    }
                }
                PrisonGame.type.put((Player) sender, 0);
                MyListener.playerJoin((Player) sender, false);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You're in combat!");
        }
        return true;
    }
}

class accmute implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0] == null) {
            sender.sendMessage("you didnt add a player idiot fucking stupid!!!");
            return true;
        }
        if (args[1] == null) {
            sender.sendMessage("add a time (in seconds) too!!! smh dumbasss!! idiot!!!!!!!");
            return true;
        }
        Bukkit.getPlayer(args[0]).getPersistentDataContainer().set(PrisonGame.muted, PersistentDataType.INTEGER, Integer.valueOf(args[1]) * 20);
        Bukkit.broadcastMessage(ChatColor.GREEN + args[0] + " was muted for" + Integer.valueOf(args[1]) * 20 + " seconds!!!! lmao laugh at this user!!");
        return true;
    }
}

class accunmute implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.getPlayer(args[0]).getPersistentDataContainer().remove(PrisonGame.muted);
        return true;
    }
}

class bragright implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getPlayer(args[0]).getPersistentDataContainer().set(PrisonGame.rank, PersistentDataType.INTEGER, 1);
        }
        return true;
    }
}


class Discordcmd implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.BLUE + "https://discord.gg/GrcHKkFQsv");
        return true;
    }
}


    class hello implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.GRAY + "Hello! " + ChatColor.GOLD + "You're currently playing on " + ChatColor.BLUE + "PrisonButBad.minehut.gg" + ChatColor.RED + ", You're on the " + ChatColor.WHITE + PrisonGame.active.name + " map, " + ChatColor.DARK_GREEN + " with " + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + " players online. " + ChatColor.GRAY + "(PrisonButBad made by agmass)");
        return true;
    }
}

class shittonmoney implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.broadcastMessage("lmao!!! " + sender.getName() + " used the cheat command to give them 1000$!! probbably was just testing but what a pussy L!!!!");
        Player p = (Player) sender;
        p.getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)+ 1000.0);
        return true;
    }
}

class stmoney implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = Bukkit.getPlayer(args[0]);
        p.getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,Double.valueOf(args[1]));
        return true;
    }
}

class sttrust implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = Bukkit.getPlayer(args[0]);
        p.getPersistentDataContainer().set(PrisonGame.trust, PersistentDataType.DOUBLE ,Double.valueOf(args[1]));
        return true;
    }
}


class nomone implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.getPlayer(args[0]).getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,0.0);
        return true;
    }
}

class giverank implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            Bukkit.getPlayer(args[0]).getPersistentDataContainer().remove(PrisonGame.rankprefix);
        } else {
            Bukkit.getPlayer(args[0]).getPersistentDataContainer().set(PrisonGame.rankprefix, PersistentDataType.STRING, args[1]);
        }
        return true;
    }
}

class resetasc implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.getPersistentDataContainer().remove(PrisonGame.randomz);
            p.getPersistentDataContainer().remove(PrisonGame.taxevasion);
            p.getPersistentDataContainer().remove(PrisonGame.protspawn);
            p.getPersistentDataContainer().remove(PrisonGame.reinforcement);
            p.getPersistentDataContainer().remove(PrisonGame.semicloak);
            p.getPersistentDataContainer().remove(PrisonGame.ascendcoins);
            p.sendMessage(ChatColor.RED + "Your ascension has been reset!");
        }
        return true;
    }
}


class gib implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (Double.valueOf(args[1]) > 0) {
            if (p.getPersistentDataContainer().get(PrisonGame.mny, PersistentDataType.DOUBLE) >= Double.valueOf(args[1])) {
                Bukkit.getPlayer(args[0]).getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, Bukkit.getPlayer(args[0]).getPersistentDataContainer().get(PrisonGame.mny, PersistentDataType.DOUBLE) + Double.valueOf(args[1]));
                p.getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, p.getPersistentDataContainer().get(PrisonGame.mny, PersistentDataType.DOUBLE) - Double.valueOf(args[1]));
            }
        }
        return true;
    }
}

class hard implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player || PrisonGame.hardmode.get((Player) sender)) {
            if (!((Player) sender).getDisplayName().contains("SOLITARY") || !((Player) sender).hasCooldown(Material.IRON_DOOR)) {
                Player p = (Player) sender;
                p.setViewDistance(2);
                p.getPersistentDataContainer().set(PrisonGame.bckupmny, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0));
                p.getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0);
                if (PrisonGame.warden != null) {
                    if (PrisonGame.warden.equals(sender)) {
                        PrisonGame.warden = null;
                    }
                }
                PrisonGame.type.put((Player) sender, 0);
                PrisonGame.hardmode.put(p, true);
                MyListener.playerJoin(p, false);
                String prisonerNumber = "" + new Random().nextInt(100, 999);
                PrisonGame.prisonnumber.put(p, prisonerNumber);
                PlayerDisguise playerDisguise = new PlayerDisguise("pdlCAMERA");
                playerDisguise.setName("Prisoner " + prisonerNumber);
                playerDisguise.setKeepDisguiseOnPlayerDeath(true);
                DisguiseAPI.disguiseToAll(p, playerDisguise);
                p.setCustomName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY  + "Prisoner " + prisonerNumber);
                p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY  + "Prisoner " + prisonerNumber);

            }
        }  else {
            sender.sendMessage(ChatColor.RED + "Really? Did you deadass try to run hard when already in hard mode? Come on. You're better than this. (I had to add this since people were losing money by doing hard... come on...)");
        }
        return true;
    }
}

class normal implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player || !PrisonGame.hardmode.get((Player) sender)) {
            if (!((Player) sender).getDisplayName().contains("SOLITARY") || !((Player) sender).hasCooldown(Material.IRON_DOOR)) {
                Player p = (Player) sender;
                p.getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(PrisonGame.bckupmny, PersistentDataType.DOUBLE, 0.0));
                p.getPersistentDataContainer().set(PrisonGame.bckupmny, PersistentDataType.DOUBLE, 0.0);
                if (PrisonGame.warden != null) {
                    if (PrisonGame.warden.equals(sender)) {
                        PrisonGame.warden = null;
                    }
                }
                PrisonGame.type.put((Player) sender, 0);
                DisguiseAPI.undisguiseToAll(p);
                PrisonGame.hardmode.put(p, false);
                MyListener.playerJoin(p, false);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Really? Did you deadass try to run normal when already in normal mode? Come on. You're better than this. (I had to add this since people were losing money by doing normal... come on...)");
        }
        return true;
    }
}

class accpt implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (PrisonGame.askType.getOrDefault((Player) sender, 0) == 2) {
                PrisonGame.setNurse((Player) sender);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + sender.getName() + " only prison:guard");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + PrisonGame.warden.getName() + " only prison:support");
                Player nw = (Player) sender;
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
            if (PrisonGame.askType.getOrDefault((Player) sender, 0) == 1) {
                PrisonGame.setGuard((Player) sender);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + sender.getName() + " only prison:guard");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + PrisonGame.warden.getName() + " only prison:support");
                Player nw = (Player) sender;
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
            if (PrisonGame.askType.getOrDefault((Player) sender, 0) == 3) {
                PrisonGame.setSwat((Player) sender);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + sender.getName() + " only prison:guard");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + PrisonGame.warden.getName() + " only prison:support");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + sender.getName() + " only prison:swat");
                Player nw = (Player) sender;
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
            PrisonGame.askType.put((Player) sender, 0);

            return true;
        }
}

class tgciv implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }
}
class sfreload implements CommandExecutor {


    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        HashMap<Player, Location> plhash = new HashMap<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            plhash.put(p, p.getLocation());
        }
        new Data(PrisonGame.warden, PrisonGame.active.getName(), true, PrisonGame.swat, plhash).saveData("save.data");
        for (Player p : Bukkit.getOnlinePlayers()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:reload");
            p.sendTitle(ChatColor.RED + "LOADING...", "this may take a bit.", 0, Integer.MAX_VALUE, 0);
            p.sendMessage(ChatColor.RED +  "THE SERVER IS RELOADING!");
            p.sendMessage(ChatColor.GREEN +  "DO NOT SPAM /WARDEN -" + ChatColor.ITALIC + "YOU WILL BE WARNED!");
            p.sendMessage(ChatColor.GRAY + "the server will make an attempt to save your items and roles, however this may not work.");
            p.setGameMode(GameMode.ADVENTURE);
            p.addPotionEffect(PotionEffectType.DARKNESS.createEffect(Integer.MAX_VALUE, 255));
            p.addPotionEffect(PotionEffectType.WEAKNESS.createEffect(Integer.MAX_VALUE, 255));
            p.teleport(new Location(Bukkit.getWorld("world"), -2062, -50, 1945));
        }
        Bukkit.getServer().getLogger().log(Level.INFO, "Data Saved");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rl confirm");
        return true;
    }
}

class TeamChat implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!Bukkit.getPlayer(sender.getName()).getPersistentDataContainer().has(PrisonGame.muted, PersistentDataType.INTEGER)) {
            String msg = String.join(" ", args);
            if (PrisonGame.type.get((Player) sender) == 0) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (PrisonGame.type.get(p) == 0) {
                        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER CHAT" + ChatColor.GRAY + "] " + ChatColor.WHITE + sender.getName() + ": " + FilteredWords.filtermsg(msg));
                    }
                }
            }
            if (PrisonGame.type.get((Player) sender) != 0) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (PrisonGame.type.get(p) != 0) {
                        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD CHAT" + ChatColor.GRAY + "] " + ChatColor.WHITE + sender.getName() + ": " + FilteredWords.filtermsg(msg));
                    }
                }
            }
        }
        return true;
    }
}

class settings implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("hello i am bertrude");
        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
            Inventory inv = Bukkit.createInventory(null, 9, "bertrude");
            inv.addItem(PrisonGame.createGuiItem(Material.PLAYER_HEAD, ChatColor.BLUE + "old tab", ChatColor.GRAY + "sets tab to the default minecraft one, if you're boring."));
            inv.addItem(PrisonGame.createGuiItem(Material.POTION, ChatColor.LIGHT_PURPLE + "epic bertude night vision", ChatColor.GRAY + "gives you night vision i think"));
            inv.addItem(PrisonGame.createGuiItem(Material.GRAY_STAINED_GLASS, ChatColor.LIGHT_PURPLE + "no warden spaces", ChatColor.GRAY + "disables/enables the spaces on the warden's messages"));
            p.openInventory(inv);
        }
        return true;
    }
}
class BuilderCMD implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof  Player) {
            Player p = (Player) sender;
            if (p.hasPermission("minecraft.command.gamemode")) {
                Inventory inv = Bukkit.createInventory(null, 9 * 6, "BUILDER ZONE");
                inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Fortress [CMD]", "tp 0 0 0"));
                inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "HyperTech [CMD]", "tp 0 0 -1000"));
                inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "The End [CMD]", "mvtp endprison", ChatColor.RED + "DO /mvtp world TO GET OUT!"));
                inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Train [CMD]", "tp 0 0 1000"));
                inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Gladiator [CMD]", "tp -2000 0 2000"));
                inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Island [CMD]", "tp 2000 0 -2000"));
                inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Santa's Workshop [CMD]", "tp 2000 0 2000"));
                inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Volcano [CMD]", "tp -2000 0 -2000"));
                inv.addItem(PrisonGame.createGuiItem(Material.PLAYER_HEAD, ChatColor.YELLOW + "Teleport All Creative [CMD]", "tp @a[gamemode=creative] @s"));
                inv.addItem(PrisonGame.createGuiItem(Material.PLAYER_HEAD, ChatColor.YELLOW + "Get Coords [CUSTOM]", "gets you the coords required for me to put in the plugin"));
                p.openInventory(inv);
            } else {
                p.sendMessage("You aren't a builder!");
            }
        }
        return true;
    }
}

