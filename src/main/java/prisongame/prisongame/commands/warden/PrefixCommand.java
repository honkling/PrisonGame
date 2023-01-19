package prisongame.prisongame.commands.warden;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Role;

public class PrefixCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String prefix = args[0].toUpperCase();
        Integer prefixlength = 16;
                /*if (((Player) sender).getPersistentDataContainer().getOrDefault(PrisonGame.rank, PersistentDataType.INTEGER, 0) == 1) {
                    prefixlength = 32;
                }*/
        if (prefix.length() <= prefixlength) {
            if (args.length > 1) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    Player p = Bukkit.getPlayer(args[1]);
                    if (PrisonGame.roles.get(p) == Role.PRISONER) {
                        Player g = (Player) Bukkit.getPlayer(args[1]);
                        g.setCustomName(ChatColor.GRAY + "[" + PrisonGame.roles.get(p).color + ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.GOLD + " PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + g.getName());
                        g.setPlayerListName(ChatColor.GRAY + "[" + PrisonGame.roles.get(p).color + ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.GOLD + " PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + g.getName());
                        g.setDisplayName(ChatColor.GRAY + "[" + PrisonGame.roles.get(p).color + ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.GOLD + " PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + g.getName());
                    }
                    if (PrisonGame.roles.get(p) == Role.NURSE) {
                        Player g = (Player) Bukkit.getPlayer(args[1]);
                        g.setCustomName(ChatColor.GRAY + "[" + PrisonGame.roles.get(p).color + ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.LIGHT_PURPLE + " NURSE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                        g.setPlayerListName(ChatColor.GRAY + "[" + PrisonGame.roles.get(p).color + ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.LIGHT_PURPLE + " NURSE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                        g.setDisplayName(ChatColor.GRAY + "[" + PrisonGame.roles.get(p).color + ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.LIGHT_PURPLE + " NURSE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                    }
                    if (PrisonGame.roles.get(p) == Role.GUARD) {
                        Player g = (Player) Bukkit.getPlayer(args[1]);
                        g.setCustomName(ChatColor.GRAY + "[" + PrisonGame.roles.get(p).color + ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.BLUE + " GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                        g.setPlayerListName(ChatColor.GRAY + "[" + PrisonGame.roles.get(p).color + ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.BLUE + " GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                        g.setDisplayName(ChatColor.GRAY + "[" + PrisonGame.roles.get(p).color + ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.BLUE + " GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                    }
                    if (PrisonGame.roles.get(p) == Role.SWAT) {
                        Player g = (Player) Bukkit.getPlayer(args[1]);
                        g.setCustomName(ChatColor.GRAY + "[" + PrisonGame.roles.get(p).color + ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.DARK_GRAY + " SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                        g.setPlayerListName(ChatColor.GRAY + "[" + PrisonGame.roles.get(p).color + ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.DARK_GRAY + " SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                        g.setDisplayName(ChatColor.GRAY + "[" + PrisonGame.roles.get(p).color + ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.DARK_GRAY + " SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
                    }
                }
            } else {
                Player g = (Player) sender;
                g.setCustomName(ChatColor.GRAY + "[" + ChatColor.RED + ChatColor.translateAlternateColorCodes('&', prefix) + " WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + g.getName());
                g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + ChatColor.translateAlternateColorCodes('&', prefix) + " WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + g.getName());
                g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.RED + ChatColor.translateAlternateColorCodes('&', prefix) + " WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + g.getName());
            }

        } else {
            sender.sendMessage("That's too long!");
        }

        return true;
    }
}