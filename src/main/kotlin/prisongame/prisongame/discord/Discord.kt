package prisongame.prisongame.discord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands as Command
import net.dv8tion.jda.api.requests.GatewayIntent
import prisongame.prisongame.discord.listeners.Commands
import prisongame.prisongame.discord.listeners.Messages
import prisongame.prisongame.lib.Config

lateinit var jda: JDA
lateinit var guild: Guild
lateinit var chatChannel: TextChannel
lateinit var commandsChannel: TextChannel
lateinit var filterChannel: TextChannel
lateinit var linkedRole: Role
lateinit var canSpeakRole: Role

fun setup() {
    if (Config.dev)
        return

    jda = JDABuilder
        .createDefault(Config.Discord.token)
        .enableIntents(GatewayIntent.GUILD_MESSAGES)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .addEventListeners(Messages, Commands)
        .build();

    jda.awaitReady()
    guild = jda.getGuildById(Config.Discord.guild)!!
    chatChannel = jda.getTextChannelById(Config.Discord.chatChannel)!!
    commandsChannel = jda.getTextChannelById(Config.Discord.commandsChannel)!!
    filterChannel = jda.getTextChannelById(Config.Discord.filterChannel)!!
    linkedRole = jda.getRoleById(Config.Discord.linkedRole)!!
    canSpeakRole = jda.getRoleById(Config.Discord.canSpeakRole)!!

    chatChannel.guild.updateCommands().addCommands(
        Command.slash("players", "List online players."),
        Command.slash("link", "Link your Minecraft and Discord accounts.")
            .addOption(OptionType.INTEGER, "code", "The link code.", true),
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
    if (Config.dev)
        return

    jda.shutdown()
}

fun addMuted(member: Member) {
    guild.removeRoleFromMember(member, canSpeakRole).queue()
}

fun removeMuted(member: Member) {
    guild.addRoleToMember(member, canSpeakRole).queue()
}