package prisongame.prisongame;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
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
    static NamespacedKey nightvis;
    static NamespacedKey whiff;

    static NamespacedKey mny;

    static LivingEntity bertrude;

    @Override
    public void onEnable() {
        // Plugin startup logic
        nightvis = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "night");
        mny = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "money");
        whiff = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "whiff");
        bertrude = (LivingEntity) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), 70, -59, -137), EntityType.VILLAGER);
        bertrude.setAI(false);
        bertrude.setCustomName("bertrude (real settings)");
        bertrude.setInvulnerable(true);
        this.getCommand("warden").setExecutor(new CommandKit());
        this.getCommand("resign").setExecutor(new TestCommand());
        this.getCommand("tc").setExecutor(new TeamChat());
        this.getCommand("disc").setExecutor(new Discordcmd());
        getServer().getPluginManager().registerEvents(new MyListener(), this);
        MyTask task = new MyTask();
        task.runTaskTimer(getPlugin(this.getClass()), 0, 1);

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
            PrisonGame.st.put(p, 0.0);
            PrisonGame.sp.put(p, 0.0);
            if (!PrisonGame.type.containsKey(p)) {
                PrisonGame.type.put(p, 0);
                MyListener.playerJoin(p);
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        bertrude.remove();
        MyTask.bossbar.removeAll();
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
        MyListener.playerJoin((Player) sender);
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

