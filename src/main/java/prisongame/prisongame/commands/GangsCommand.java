package prisongame.prisongame.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.commands.gangs.CreateCommand;
import prisongame.prisongame.commands.gangs.HelpCommand;
import prisongame.prisongame.commands.gangs.LeaderboardCommand;
import prisongame.prisongame.commands.gangs.ListCommand;
import prisongame.prisongame.commands.gangs.bank.BankCommand;

import java.util.Arrays;

public class GangsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>You aren't a player."));
            return true;
        }

        if (args.length == 0)
            return showHelp(player, command);

        var subcommand = args[0];
        var rest = Arrays.stream(args).toList().subList(1, args.length).toArray(String[]::new);

        return (switch (subcommand) {
            case "list" -> new ListCommand();
            case "leaderboard" -> new LeaderboardCommand();
            case "create" -> new CreateCommand();
            case "bank" -> new BankCommand();
            default -> new HelpCommand();
        }).onCommand(player, command, subcommand, rest);
    }

    private boolean showHelp(Player player, Command command) {
        new HelpCommand().onCommand(player, command, "help", new String[] {});
        return true;
    }
}
