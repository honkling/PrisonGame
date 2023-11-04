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
import prisongame.prisongame.lib.Config;

public class WardenComplete extends SubcommandCompleter {
    public WardenComplete() {
        super(Config.Warden.help.keySet().toArray(new String[0]));
    }
}