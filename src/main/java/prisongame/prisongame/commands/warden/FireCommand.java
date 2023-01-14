package prisongame.prisongame.commands.warden;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.MyListener;
import prisongame.prisongame.PrisonGame;

public class FireCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (Bukkit.getPlayer(args[0]) != null) {
            Player g = Bukkit.getPlayer(args[0]);
            if (g.isOnline() && g != sender && PrisonGame.type.get(g) != 0) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + sender.getName() + " only prison:strike");
                Bukkit.broadcastMessage(ChatColor.GOLD + g.getName() + " was fired.");
                MyListener.playerJoin(g, false);
            }
        }

        return true;
    }
}
