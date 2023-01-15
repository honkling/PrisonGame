package prisongame.prisongame.commands.warden;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.MyListener;
import prisongame.prisongame.PrisonGame;

public class PassCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            if (Bukkit.getPlayer(args[0]) != null) {
                MyListener.playerJoin(PrisonGame.warden, false);
                PrisonGame.warden = null;
                Bukkit.getPlayer(args[0]).performCommand("warden");
            }
        }
        return true;
    }
}