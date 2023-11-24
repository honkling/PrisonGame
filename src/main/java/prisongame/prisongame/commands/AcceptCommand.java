package prisongame.prisongame.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import oshi.jna.platform.mac.SystemB;
import prisongame.prisongame.MyListener;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.ProfileKt;
import prisongame.prisongame.lib.Role;

import java.util.HashMap;
import java.util.UUID;

public class AcceptCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>You aren't a player."));
            return true;
        }

        var profile = ProfileKt.getProfile(player);
        var invitation = profile.getInvitation();

        if (invitation == null || invitation == Role.PRISONER) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>You haven't been invited."));
            return true;
        }

        if (invitation == Role.WARDEN && PrisonGame.warden != null)
            MyListener.playerJoinignoreAsc(PrisonGame.warden, false);

        profile.setRole(invitation);
        profile.setInvitation(null);

        if (invitation != Role.WARDEN) {
            if (!PrisonGame.savedPlayerGuards.containsKey(PrisonGame.warden.getUniqueId())) {
                Bukkit.broadcastMessage(ChatColor.AQUA + "Creating warden save file...");
                HashMap<UUID, Role> roleHashMap = new HashMap<>();
                roleHashMap.put(((Player) sender).getUniqueId(), profile.getInvitation());
                PrisonGame.savedPlayerGuards.put(PrisonGame.warden.getUniqueId(), roleHashMap);
            } else {
                Bukkit.broadcastMessage(ChatColor.AQUA + "Saving warden save file...");
                HashMap<UUID, Role> roleHashMap = PrisonGame.savedPlayerGuards.get(PrisonGame.warden.getUniqueId());
                if (PrisonGame.savedPlayerGuards.get(PrisonGame.warden.getUniqueId()).containsKey(((Player) sender).getUniqueId())) {
                    roleHashMap.remove(((Player) sender).getUniqueId());
                }
                roleHashMap.put(((Player) sender).getUniqueId(), profile.getInvitation());
                PrisonGame.savedPlayerGuards.put(PrisonGame.warden.getUniqueId(), roleHashMap);
            }
        }

        return true;
    }
}