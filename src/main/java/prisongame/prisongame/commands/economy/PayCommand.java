package prisongame.prisongame.commands.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import prisongame.prisongame.PrisonGame;

public class PayCommand implements CommandExecutor {

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