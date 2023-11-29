package prisongame.prisongame.commands.gangs;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.PrisonGame;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(PrisonGame.mm.deserialize("""
                <dark_blue>-=-=-=-=-=-=-=-<blue>
                /gangs list <dark_gray>- <gray>List all gangs.</dark_gray>
                /gangs create (name) <dark_gray>- <gray>Create a gang.</dark_gray>
                /gangs bank <dark_gray>- <gray>View bank information.</dark_gray>
                /gangs bank deposit (amount) <dark_gray>- <gray>Deposit cash into the bank.</dark_gray>
                /gangs bank withdraw (amount) <dark_gray>- <gray>Withdraw cash from the bank.</gray>
                /gangs leaderboard <dark_gray>- <gray>View a leaderboard of gangs.</gray>
                </blue>-=-=-=-=-=-=-=-</dark_blue>
                """));
        return true;
    }
}
