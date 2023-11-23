package prisongame.prisongame.commands.danger;

import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import prisongame.prisongame.MyListener;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Keys;
import prisongame.prisongame.lib.Role;

public class NormalCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player && PrisonGame.hardmode.get(player)) {
            PrisonGame.enableNormalMode(player);
        } else {
            sender.sendMessage(ChatColor.RED + "Really? Did you deadass try to run normal when already in normal mode? Come on. You're better than this. (I had to add this since people were losing money by doing normal... come on...)");
        }
        return true;
    }
}
