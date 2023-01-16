package prisongame.prisongame.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import prisongame.prisongame.FilteredWords;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Role;

public class TeamChatCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!Bukkit.getPlayer(sender.getName()).getPersistentDataContainer().has(PrisonGame.muted, PersistentDataType.INTEGER)) {
            String msg = String.join(" ", args);
            if (PrisonGame.roles.get((Player) sender) == Role.PRISONER) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (PrisonGame.roles.get(p) == Role.PRISONER) {
                        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER CHAT" + ChatColor.GRAY + "] " + ChatColor.WHITE + sender.getName() + ": " + FilteredWords.filtermsg(msg));
                    }
                }
            }
            if (PrisonGame.roles.get((Player) sender) != Role.PRISONER) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (PrisonGame.roles.get(p) != Role.PRISONER) {
                        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD CHAT" + ChatColor.GRAY + "] " + ChatColor.WHITE + sender.getName() + ": " + FilteredWords.filtermsg(msg));
                    }
                }
            }
        }
        return true;
    }
}