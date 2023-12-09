package prisongame.prisongame.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.gangs.GangRole;
import prisongame.prisongame.gangs.Gangs;
import prisongame.prisongame.keys.Keys;

import java.sql.SQLException;

public class GangChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>You aren't a player."));
            return true;
        }

        if (Keys.GANG_ROLE.get(player, 0) <= 0) {
            player.sendMessage(PrisonGame.mm.deserialize("<red>You aren't in a gang."));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(PrisonGame.mm.deserialize("<red>Please provide a message."));
            return true;
        }

        var message = String.join(" ", args);

        try {
            var gang = Gangs.get(player);
            gang.broadcast(GangRole.MEMBER, (member) -> {
                member.sendMessage(PrisonGame.mm.deserialize(
                        "<gray>[<red>GANG CHAT</red>] <player>: <message>",
                        Placeholder.component("player", Component.text(player.getName())),
                        Placeholder.component("message", Component.text(message))
                ));
            });
        } catch (SQLException exception) {
            player.sendMessage(PrisonGame.mm.deserialize("<red>An error occurred."));
            PrisonGame.instance.getLogger().severe(exception.getMessage());
        }

        return true;
    }
}
