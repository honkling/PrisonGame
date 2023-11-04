package prisongame.prisongame.commands.completers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import prisongame.prisongame.PrisonGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SubcommandCompleter implements TabCompleter {
    private static String[] COMMANDS;

    public SubcommandCompleter(String[] commands) {
        COMMANDS = commands;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1)
            return null;

        var completions = new ArrayList<String>();
        StringUtil.copyPartialMatches(args[0], List.of(COMMANDS), completions);
        Collections.sort(completions);
        return completions;
    }
}
