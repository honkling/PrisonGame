package prisongame.prisongame;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PrisonGame extends JavaPlugin {

    static HashMap<Player, Double> st = new HashMap<>();
    static HashMap<Player, Double> sp = new HashMap<>();
    static Player warden = null;
    static HashMap<Player, Boolean> escaped = new HashMap<>();
    static HashMap<Player, Integer> type = new HashMap<>();
    static HashMap<Player, Integer> askType = new HashMap<>();
    static Prison gaeae;
    static Prison hyper;
    static Prison endmap;
    static Prison active = null;
    static NamespacedKey nightvis;
    static Integer swapcool = 0;
    static NamespacedKey whiff;

    static NamespacedKey mny;

    static LivingEntity bertrude;

    @Override
    public void onEnable() {
        // Plugin startup logic

        active = gaeae;
        nightvis = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "night");
        mny = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "money");
        whiff = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "whiff");
        this.getCommand("warden").setExecutor(new CommandKit());
        this.getCommand("resign").setExecutor(new TestCommand());
        this.getCommand("hello").setExecutor(new hello());
        this.getCommand("tc").setExecutor(new TeamChat());
        this.getCommand("disc").setExecutor(new Discordcmd());
        this.getCommand("accept").setExecutor(new accpt());

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

        Bukkit.addRecipe(recipe);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle(ChatColor.RED + "LOADING...", "this may take a bit.", 0, 80, 0);
            p.addPotionEffect(PotionEffectType.DARKNESS.createEffect(80, 255));
        }
        Bukkit.getScheduler().runTaskLater(getPlugin(this.getClass()), () -> {
            // code
            for (Entity e : Bukkit.getWorld("world").getEntities()) {
                if (e.getType().equals(EntityType.VILLAGER)) {
                    e.remove();
                }
            }
            gaeae = new Prison("Fortress Of Gaeae", new Location(Bukkit.getWorld("world"), 61, -54, -159), new Location(Bukkit.getWorld("world"), 76, -59, -169), new Location(Bukkit.getWorld("world"), 44, -58, -141), new Location(Bukkit.getWorld("world"), 44, -58, -137), new Location(Bukkit.getWorld("world"), 41.5, -52, -120.5), new Location(Bukkit.getWorld("world"), 12, -60, -119), new Location(Bukkit.getWorld("world"), -26.5, -56.5, -115.5), new Location(Bukkit.getWorld("world"), -8.5, -57, -108.5), new Location(Bukkit.getWorld("world"), 33, -59, -132), new Location(Bukkit.getWorld("world"), 70, -59, -137), new Location(Bukkit.getWorld("world"), 87, -59, -129), new Location(Bukkit.getWorld("world"), 87, -56, -125));
            hyper = new Prison("HyperTech", new Location(Bukkit.getWorld("world"), 18, -56, -988), new Location(Bukkit.getWorld("world"), 8, -59, -981), new Location(Bukkit.getWorld("world"), -29, -58, -988), new Location(Bukkit.getWorld("world"), -29, -58, -991), new Location(Bukkit.getWorld("world"), 12, -53, -970), new Location(Bukkit.getWorld("world"), -18, -59, -995), new Location(Bukkit.getWorld("world"), -26.5, -56.5, -115.5), new Location(Bukkit.getWorld("world"), 3.5, -59, -1006.5), new Location(Bukkit.getWorld("world"), 8, -59, -1004), new Location(Bukkit.getWorld("world"), -3, -59, -1008), new Location(Bukkit.getWorld("world"), 1, -58, -1008), new Location(Bukkit.getWorld("world"), 3, -58, -1008));
            endmap = new Prison("The End?", new Location(Bukkit.getWorld("endprison"), 7, 133, 8), new Location(Bukkit.getWorld("endprison"), 19, 127, 20), new Location(Bukkit.getWorld("endprison"), -30, 170, -48), new Location(Bukkit.getWorld("endprison"), -32, 170, -47), new Location(Bukkit.getWorld("endprison"), -1, 150, 13), new Location(Bukkit.getWorld("endprison"), 0, 135, -41), new Location(Bukkit.getWorld("world"), -26.5, -56.5, -115.5), new Location(Bukkit.getWorld("endprison"), 0, 131, -6), new Location(Bukkit.getWorld("endprison"), 0, 125 ,0), new Location(Bukkit.getWorld("endprison"), -4, 131, 3), new Location(Bukkit.getWorld("endprison"), -100000, 256, -100000), new Location(Bukkit.getWorld("endprison"), -100000, 256, -100000));
            active = gaeae;
            bertrude = (LivingEntity) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), 70, -59, -137), EntityType.VILLAGER);
            bertrude.setAI(false);
            bertrude.setCustomName("bertrude (real settings)");
            bertrude.setInvulnerable(true);
            for (Player p : Bukkit.getOnlinePlayers()) {
                PrisonGame.st.put(p, 0.0);
                PrisonGame.sp.put(p, 0.0);
                if (!PrisonGame.type.containsKey(p)) {
                    PrisonGame.type.put(p, 0);
                    MyListener.playerJoin(p, false);
                }
            }
            MyTask task = new MyTask();
            task.runTaskTimer(getPlugin(this.getClass()), 0, 1);
            getServer().getPluginManager().registerEvents(new MyListener(), this);
        }, 80);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        bertrude.remove();
        MyTask.bossbar.removeAll();
    }

    static void setNurse(Player g) {
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Guards").addPlayer(g);
        PrisonGame.type.put(g, 2);
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

    }
    static void setGuard(Player g) {
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Guards").addPlayer(g);
        PrisonGame.type.put(g, 1);
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
        if (PrisonGame.warden != null) {
            if (PrisonGame.warden.equals(sender)) {
                PrisonGame.warden = null;
            }
        }
        PrisonGame.type.put((Player) sender, 0);
        MyListener.playerJoin((Player) sender, false);
        return true;
    }
}

class Discordcmd implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.BLUE + "https://discord.gg/Y6TFEPUMB9");
        return true;
    }
}


    class hello implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.GRAY + "Hello! " + ChatColor.GOLD + "You're currently playing on " + ChatColor.BLUE + "PrisonButBad.minehut.gg" + ChatColor.RED + ", You're on the " + ChatColor.WHITE + PrisonGame.active.name + " map, " + ChatColor.DARK_GREEN + " with " + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + " players online. " + ChatColor.GRAY + "(made by agmass)");
        return true;
    }
}

class accpt implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (PrisonGame.askType.getOrDefault((Player) sender, 0) == 2) {
            PrisonGame.setNurse((Player) sender);
        }
        if (PrisonGame.askType.getOrDefault((Player) sender, 0) == 1) {
            PrisonGame.setGuard((Player) sender);
        }
        PrisonGame.askType.put((Player) sender, 0);
        return true;
    }
}

class TeamChat implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String msg = String.join(" ", args);
        if (PrisonGame.type.get((Player) sender) == 0) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (PrisonGame.type.get(p) == 0) {
                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER CHAT" + ChatColor.GRAY + "] " + ChatColor.WHITE + sender.getName() + ": " + msg);
                }
            }
        }
        if (PrisonGame.type.get((Player) sender) != 0) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (PrisonGame.type.get(p) != 0) {
                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD CHAT" + ChatColor.GRAY + "] " + ChatColor.WHITE + sender.getName() + ": " + msg);
                }
            }
        }
        return true;
    }
}
