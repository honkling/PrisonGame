package prisongame.prisongame.commands.warden;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.FilteredWords;
import prisongame.prisongame.MyListener;
import prisongame.prisongame.PrisonGame;

public class IntercomCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        StringBuilder b = new StringBuilder(ChatColor.BLUE + "INTERCOM >> " + Color.fromRGB(255, 59, 98));
        for (String a : args) {
            b.append(a + " ");
        }
        if (!FilteredWords.isClean(b.toString())) {
            return true;
        }
        String be = ChatColor.translateAlternateColorCodes('&', b.toString());
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p, Sound.ENTITY_BEE_LOOP_AGGRESSIVE, 1, 0.75f);
            p.sendTitle(ChatColor.BLUE + "" ,be, 20, 40, 20);
        }
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(be);
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("");
        return true;
    }
}