package prisongame.prisongame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PrisonGame extends JavaPlugin {

    static Player warden = null;
    static HashMap<Player, Integer> type = new HashMap<>();
    static HashMap<Player, Double> money = new HashMap<>();

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

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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

