package prisongame.prisongame.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.PrisonGame;

import static prisongame.prisongame.config.ConfigKt.getConfig;

public class RulesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        var general = getConfig().getGeneral();
        var message = new StringBuilder(String.format(
                "\n<red>Tip: Rules are also stored in our Discord server. Join with <b><hover:show_text:'%s'><click:open_url:%s>/discord</click></hover></b>.</red>",
                "<red>Click to join the Discord server.",
                "https://discord.gg/" + general.getDiscordInvite()
        ));

        // todo: rules
//        for (int i = 0; i < general.getRules().size(); i++) {
//            String rule = general.getRules().get(i);
//            message.append(String.format("\n<gray>%s)</gray> %s", i + 1, rule));
//        }

        message.append("\n");
        sender.sendMessage(PrisonGame.mm.deserialize(message.toString()));
        return true;
    }
}
