package prisongame.prisongame.commands.economy.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import prisongame.prisongame.PrisonGame;

public class NerdCheatCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.broadcastMessage("lmao!!! " + sender.getName() + " used the cheat command to give them 1000$!! probbably was just testing but what a pussy L!!!!");
        Player p = (Player) sender;
        p.getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE ,p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)+ 1000.0);
        return true;
    }
}
