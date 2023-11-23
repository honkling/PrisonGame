package prisongame.prisongame.commands;

import me.coralise.spigot.API.CBPAPI;
import me.coralise.spigot.CustomBansPlus;
import me.coralise.spigot.players.CBPlayer;
import me.coralise.spigot.players.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import prisongame.prisongame.FilteredWords;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.ProfileKt;
import prisongame.prisongame.lib.Role;

public class TeamChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        CBPAPI api = CBPAPI.getApi();

        if (api != null) {
            CustomBansPlus cbp = CustomBansPlus.getInstance();
            PlayerManager playerManager = cbp.plm;

            CBPlayer cbpPlayer = playerManager.getCBPlayer(player.getUniqueId());

            if (api.isPlayerMuted(cbpPlayer)) {
                player.sendMessage(ChatColor.RED + "You cannot use team chat while you are muted.");
                return true;
            }
        }

        String message = String.join(" ", args);
        Role genericRole = getGenericRole(player);
        String prefix = genericRole == Role.PRISONER ? "PRISONER" : "GUARD";
        ChatColor color = genericRole == Role.PRISONER ? ChatColor.GOLD : ChatColor.BLUE;

        for (Player recipient : Bukkit.getOnlinePlayers()) {
            if (getGenericRole(recipient) != genericRole)
                continue;

            recipient.sendMessage(String.format(
                    "%s[%s%s CHAT%s] %s: %s",
                    ChatColor.GRAY,
                    color,
                    prefix,
                    ChatColor.GRAY,
                    player.getName(),
                    FilteredWords.filtermsg(message)
            ));
        }

        return true;
    }

    private Role getGenericRole(Player player) {
        var profile = ProfileKt.getProfile(player);
        var role = profile.getRole();
        return role == Role.PRISONER ? Role.PRISONER : Role.GUARD;
    }
}