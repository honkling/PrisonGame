package prisongame.prisongame.discord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands as Command
import net.dv8tion.jda.api.requests.GatewayIntent
import prisongame.prisongame.discord.listeners.Commands
import prisongame.prisongame.discord.listeners.Messages
import prisongame.prisongame.lib.Config

lateinit var jda: JDA
lateinit var channel: TextChannel
lateinit var filter: TextChannel

fun setup() {
    jda = JDABuilder
        .createDefault(Config.Discord.token)
        .enableIntents(GatewayIntent.GUILD_MESSAGES)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .addEventListeners(Messages, Commands)
        .build();

    jda.awaitReady()
    channel = jda.getTextChannelById(Config.Discord.channel)!!
    filter = jda.getTextChannelById(Config.Discord.filter)!!

    channel.guild.updateCommands().addCommands(
        Command.slash("players", "List online players."),
        Command.slash("ban", "Ban players from PrisonButBad.")
            .addOption(OptionType.STRING, "player", "The player to ban.", true, true)
            .addOption(OptionType.STRING, "duration", "The ban duration.", true)
            .addOption(OptionType.STRING, "reason", "The ban reason.", false)
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.BAN_MEMBERS)),
        Command.slash("mute", "Mute players on PrisonButBad.")
            .addOption(OptionType.STRING, "player", "The player to mute.", true, true)
            .addOption(OptionType.STRING, "duration", "The mute duration.", true)
            .addOption(OptionType.STRING, "reason", "The mute reason.", false)
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.BAN_MEMBERS)),
        Command.slash("unban", "Unban players from PrisonButBad.")
            .addOption(OptionType.STRING, "player", "The player to unban.", true, true)
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.BAN_MEMBERS)),
        Command.slash("unmute", "Unmute players from PrisonButBad.")
            .addOption(OptionType.STRING, "player", "The player to unmute.", true, true)
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.BAN_MEMBERS))
    ).queue()
}

fun close() {
    jda.shutdown()
}