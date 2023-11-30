package prisongame.prisongame.commands.gangs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.gangs.Gangs;

import java.sql.SQLException;

public class CreateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>You aren't a player."));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>Please provide a gang name."));
            return true;
        }

        try {

        } catch (SQLException exception) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>An error occurred."));
            PrisonGame.instance.getLogger().severe(exception.getMessage());
            return true;
        }

        return true;
    }
}
