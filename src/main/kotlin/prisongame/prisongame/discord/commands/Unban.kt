package prisongame.prisongame.discord.commands

import me.coralise.spigot.AbstractAnnouncer
import me.coralise.spigot.CustomBansPlus
import me.coralise.spigot.enums.AnnouncementType
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.bukkit.Bukkit

fun unban(event: SlashCommandInteractionEvent) {
    val playerName = event.getOption("player")!!.asString

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

    cbp.database.updateHistoryStatus(player.uniqueId, "Ban", "Unbanned", null)
    cbp.bm.removeBan(cbp.bm.getBan(player.uniqueId), "Unbanned", null)
    cbp.u.removeOci(player.uniqueId)
    AbstractAnnouncer.getAnnouncer(cbpPlayer, null, null, null, AnnouncementType.UNBAN, false)

    event.hook.sendMessage("Unbanned **${player.name}**.").queue()
}