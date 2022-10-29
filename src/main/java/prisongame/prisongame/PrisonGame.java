package prisongame.prisongame;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PrisonGame extends JavaPlugin {

    static Player warden = null;
    static HashMap<Player, Integer> type = new HashMap<>();
    static HashMap<Player, Double> money = new HashMap<>();

    static Team crims = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals");
    static Team guards = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Guards");
    static Team ward = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Warden");
    static Team prisoner = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners");

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("warden").setExecutor(new CommandKit());
        this.getCommand("resign").setExecutor(new TestCommand());
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
            if (!PrisonGame.type.containsKey(p)) {
                PrisonGame.type.put(p, 0);
                MyListener.playerJoin(p);
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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

