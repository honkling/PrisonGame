package prisongame.prisongame.commands.gangs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
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

        var name = args[0];

        if (!name.matches("^[0-9a-zA-Z-_ ]{1,16}$")) {
            sender.sendMessage(PrisonGame.mm.deserialize(
                    "<red>Please provide a valid gang name. (<charset>)",
                    Placeholder.component("charset", Component.text("[0-9a-zA-Z-_ ]"))
            ));
            return true;
        }

        try {
            if (Gangs.exists(name)) {
                sender.sendMessage(PrisonGame.mm.deserialize("<red>That name is taken."));
                return true;
            }

            Gangs.create(player, name);
            sender.sendMessage(PrisonGame.mm.deserialize(
                    "<gray>Created gang with name <name>.",
                    Placeholder.component("name", Component
                            .text(name)
                            .color(NamedTextColor.WHITE))
            ));
        } catch (SQLException exception) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>An error occurred."));
            PrisonGame.instance.getLogger().severe(exception.getMessage());
        }

        return true;
    }
}
