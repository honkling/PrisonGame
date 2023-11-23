package prisongame.prisongame.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.commands.warden.*;
import prisongame.prisongame.commands.warden.ResignCommand;
import prisongame.prisongame.lib.ProfileKt;

import java.util.Arrays;

public class WardenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        var profile = ProfileKt.getProfile(player);

        if (!PrisonGame.wardenenabled || profile.getHardMode()) {
            player.kickPlayer(ChatColor.RED + "Do not /warden during a reload.");
            player.sendMessage(ChatColor.RED + sender.getName() + " was kicked for doing /warden during a reload");
            return true;
        }

        if (args.length == 0) {
            new DefaultCommand().onCommand(sender, command, "", new String[0]);
            return true;
        }

        if (!PrisonGame.warden.equals(player))
            return true;

        CommandExecutor subcommand = switch (args[0]) {
            case "fire" -> new FireCommand();
            case "grammar" -> new GrammarCommand();
            case "guard" -> new GuardCommand();
            case "intercom" -> new IntercomCommand();
            case "mutechat" -> new MuteChatCommand();
            case "nurse" -> new NurseCommand();
            case "pass" -> new PassCommand();
            case "prefix" -> new PrefixCommand();
            case "release" -> new ReleaseCommand();
            case "resign" -> new ResignCommand();
            case "skip" -> new SkipCommand();
            case "solitary" -> new SolitaryCommand();
            case "swat" -> new SwatCommand();
            case "target" -> new TargetCommand();
            case "femboys" -> new FemboysCommand();
            default -> new HelpCommand();
        };

        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
        subcommand.onCommand(sender, command, args[0], subArgs);
        return true;
    }
}
