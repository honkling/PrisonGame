package prisongame.prisongame.commands.gangs.bank;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.commands.gangs.HelpCommand;

import java.util.Arrays;

public class BankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0)
            return new DefaultCommand().onCommand(sender, command, label, args);

        var subcommand = args[0];
        var rest = Arrays.stream(args).toList().subList(1, args.length).toArray(String[]::new);

        return (switch (subcommand) {
            case "deposit" -> new DepositCommand();
            case "withdraw" -> new WithdrawCommand();
            default -> new HelpCommand();
        }).onCommand(sender, command, subcommand, args);
    }
}
