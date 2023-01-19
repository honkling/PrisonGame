package prisongame.prisongame.commands.completers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import prisongame.prisongame.PrisonGame;

public class WardenComplete implements TabCompleter {
    private static final String[] COMMANDS = { "help", "fire", "guard", "nurse", "pass", "prefix", "resign", "solitary", "swat", "target", "intercom", "mutechat", "grammar" };
    //create a static array of values

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        //create new array
        final List<String> nothing = new ArrayList<>();
        nothing.add("");
        if (args.length == 1) {
            if (sender.equals(PrisonGame.warden)) {
                final List<String> completions = new ArrayList<>();
                //copy matches of first argument from list (ex: if first arg is 'm' will return just 'minecraft')
                StringUtil.copyPartialMatches(args[0], List.of(COMMANDS), completions);
                //sort the list
                Collections.sort(completions);
                return completions;
            } else {
                return nothing;
            }
        }
        return null;
    }
}