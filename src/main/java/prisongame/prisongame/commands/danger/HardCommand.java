package prisongame.prisongame.commands.danger;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
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
import prisongame.prisongame.lib.ProfileKt;
import prisongame.prisongame.lib.Role;

import java.util.Random;

public class HardCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player))
            return true;

        var profile = ProfileKt.getProfile(player);

        if (!profile.getHardMode()) {
            profile.setHardMode(true);
        } else {
            sender.sendMessage(ChatColor.RED + "Really? Did you deadass try to run hard when already in hard mode? Come on. You're better than this. (I had to add this since people were losing money by doing hard... come on...)");
        }
        return true;
    }
}