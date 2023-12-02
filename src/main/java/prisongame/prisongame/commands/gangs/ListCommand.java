package prisongame.prisongame.commands.gangs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.gangs.Gangs;

import java.sql.SQLException;

public class ListCommand implements CommandExecutor {
    public static final int PER_PAGE = 10;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        var page = 0;

        if (args.length > 0 && args[0].matches("^[0-9]+$"))
            page = Math.max(0, Integer.parseInt(args[0]) - 1);

        try {
            var count = Gangs.count();

            if (count <= 0) {
                sender.sendMessage(PrisonGame.mm.deserialize("<red>No gangs currently exist."));
                return true;
            }

            var pages = (int) Math.ceil(((double) count) / PER_PAGE);
            page = Math.min(page, Math.max(0, pages - 1));

            var allGangs = Gangs.list();
            var gangs = allGangs.subList(page * PER_PAGE, Math.min(allGangs.size(), page * PER_PAGE + PER_PAGE));
            var listing = Component.empty();

            for (var gang : gangs) {
                if (!listing.equals(Component.empty()))
                    listing = listing.append(Component.newline());

                listing = listing.append(PrisonGame.mm.deserialize(
                        """
                        <gray>Gang <gang> owned by <owner>
                        """,
                        Placeholder.component("gang", Component
                                .text(gang.name)
                                .color(NamedTextColor.WHITE)),
                        Placeholder.component("owner", Component
                                .text(gang.owner.getName())
                                .color(NamedTextColor.WHITE))
                ));
            }

            sender.sendMessage(PrisonGame.mm.deserialize(
                    """
                    <dark_blue>-=- <gray>Page <page> of <max></gray> -=-<blue>
                    <listing>
                    </blue>-=-=-=-=-=-=-=-</dark_blue>
                    """,
                    Placeholder.component("page", Component
                            .text(page + 1)
                            .color(NamedTextColor.WHITE)),
                    Placeholder.component("max", Component
                            .text(pages)
                            .color(NamedTextColor.WHITE)),
                    Placeholder.component("listing", listing)
            ));
        } catch (SQLException exception) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>An error occurred."));
            PrisonGame.instance.getLogger().severe(exception.getMessage());
        }

        return true;
    }
}
