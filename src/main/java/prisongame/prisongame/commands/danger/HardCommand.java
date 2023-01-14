package prisongame.prisongame.commands.danger;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import prisongame.prisongame.MyListener;
import prisongame.prisongame.PrisonGame;

import java.util.Random;

public class HardCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !PrisonGame.hardmode.get((Player) sender)) {
            if (!((Player) sender).getDisplayName().contains("SOLITARY") && !((Player) sender).hasCooldown(Material.IRON_DOOR)) {
                Player p = (Player) sender;
                p.setViewDistance(2);
                p.getPersistentDataContainer().set(PrisonGame.bckupmny, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0));
                p.getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0);
                if (PrisonGame.warden != null) {
                    if (PrisonGame.warden.equals(sender)) {
                        PrisonGame.warden = null;
                    }
                }
                PrisonGame.type.put((Player) sender, 0);
                PrisonGame.hardmode.put(p, true);
                MyListener.playerJoin(p, false);
                String prisonerNumber = "" + new Random().nextInt(100, 999);
                PrisonGame.prisonnumber.put(p, prisonerNumber);
                PlayerDisguise playerDisguise = new PlayerDisguise("pdlCAMERA");
                playerDisguise.setName("Prisoner " + prisonerNumber);
                playerDisguise.setKeepDisguiseOnPlayerDeath(true);
                DisguiseAPI.disguiseToAll(p, playerDisguise);
                p.setCustomName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY  + "Prisoner " + prisonerNumber);
                p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY  + "Prisoner " + prisonerNumber);

            }
        }  else {
            sender.sendMessage(ChatColor.RED + "Really? Did you deadass try to run hard when already in hard mode? Come on. You're better than this. (I had to add this since people were losing money by doing hard... come on...)");
        }
        return true;
    }
}