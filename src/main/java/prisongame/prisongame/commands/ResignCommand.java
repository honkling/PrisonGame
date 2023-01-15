package prisongame.prisongame.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import prisongame.prisongame.MyListener;
import prisongame.prisongame.PrisonGame;

import java.util.HashMap;
import java.util.UUID;

public class ResignCommand implements CommandExecutor {

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
                if (PrisonGame.warden != null) {
                    if (PrisonGame.savedPlayerGuards.containsKey(PrisonGame.warden.getUniqueId())) {
                        HashMap<UUID, Integer> roleHashMap = PrisonGame.savedPlayerGuards.get(PrisonGame.warden.getUniqueId());
                        if (PrisonGame.savedPlayerGuards.get(PrisonGame.warden.getUniqueId()).containsKey(((Player) sender).getUniqueId())) {
                            roleHashMap.remove(((Player) sender).getUniqueId());
                        }
                        PrisonGame.savedPlayerGuards.put(PrisonGame.warden.getUniqueId(), roleHashMap);
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