package prisongame.prisongame.commands.economy.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.persistence.PersistentDataType;
import prisongame.prisongame.PrisonGame;

public class ResetMoneyCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.getPlayer(args[0]).getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,0.0);
        return true;
    }
}