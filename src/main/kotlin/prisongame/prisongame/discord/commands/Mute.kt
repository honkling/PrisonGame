package prisongame.prisongame.discord.commands

import me.coralise.spigot.AbstractAnnouncer
import me.coralise.spigot.CustomBansPlus
import me.coralise.spigot.enums.AnnouncementType
import me.coralise.spigot.enums.MuteType
import me.coralise.spigot.enums.Punishment
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.bukkit.Bukkit
import java.util.*

fun mute(event: SlashCommandInteractionEvent) {
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
    val type = if (duration.startsWith("perm")) MuteType.PERM_MUTE else MuteType.TEMP_MUTE
    val announcementType = if (duration.startsWith("perm")) AnnouncementType.PERM_MUTE else AnnouncementType.TEMP_MUTE
    val punishment = Punishment.fromString(type.toString())

    cbp.database.updateHistoryStatus(player.uniqueId, "Mute", "Overwritten", null)
    cbp.mm.setMute(
        player.uniqueId,
        type,
        Date(),
        reason,
        duration,
        null,
        true)
    cbp.database.addHistory(cbpPlayer, punishment, null, reason)
    AbstractAnnouncer.getAnnouncer(cbpPlayer, null, duration, reason, announcementType, false)

    event.hook.sendMessage("Muted **${player.name}** for **$reason**.").queue()
}