package prisongame.prisongame.discord.commands

import me.coralise.spigot.CustomBansPlus
import me.coralise.spigot.commands.AbstractCommand
import me.coralise.spigot.enums.BanType
import me.coralise.spigot.enums.Punishment
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.bukkit.Bukkit

fun ban(event: SlashCommandInteractionEvent) {
    val playerName = event.getOption("player")!!.asString
    val duration = event.getOption("duration")!!.asString
    val reason = event.getOption("reason")?.asString

    if (!Bukkit.getPluginManager().isPluginEnabled("CustomBansPlus")) {
        event.reply("CustomBansPlus is disabled.")
            .setEphemeral(true)
            .queue()
        return
    }

    event.deferReply().queue()

    val player = Bukkit.getOfflinePlayer(playerName)

    val cbp = CustomBansPlus.getInstance()
    val playerManager = cbp.plm
    val cbpPlayer = playerManager.getCBPlayer(player.uniqueId)
    val type = if (duration.startsWith("perm")) BanType.PERM_BAN else BanType.TEMP_BAN
    val punishment = Punishment.fromString(type.toString())

    if (cbp.u.getValueType(duration) == null) {
        event.reply("Please provide a valid duration.")
            .setEphemeral(true)
            .queue()
    }

    cbp.database.updateHistoryStatus(player.uniqueId, "Ban", "Overwritten", null)
    cbp.bm.ban(
        cbpPlayer,
        type,
        reason,
        duration,
        null,
        false,
        null,
        cbp.u.determineBanAnnType(type, reason),
        false
    )
    cbp.database.addHistory(cbpPlayer, punishment, null, reason)

    event.hook.sendMessage("Banned **${player.name}** for **$reason**.").queue()
}