package prisongame.prisongame.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Config;

public class RulesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        var message = new StringBuilder("\n<red>Tip: Rules are also stored in our discord server. Join with /discord.</red>");

        for (int i = 0; i < Config.General.rules.size(); i++) {
            String rule = Config.General.rules.get(i);
            message.append(String.format("\n<gray>%s)</gray> %s", i + 1, rule));
        }

        message.append("\n");
        sender.sendMessage(PrisonGame.mm.deserialize(message.toString()));
        return true;
    }
}
