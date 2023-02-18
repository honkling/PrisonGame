package prisongame.prisongame.commands.danger;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import prisongame.prisongame.PrisonGame;

public class ResetAscensionCommand implements CommandExecutor {

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
            p.sendMessage(Color.fromRGB(255, 59, 98) + "Your ascension has been reset!");
        }
        return true;
    }
}