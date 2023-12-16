package prisongame.prisongame.discord.commands

import me.coralise.spigot.AbstractAnnouncer
import me.coralise.spigot.CustomBansPlus
import me.coralise.spigot.commands.AbstractCommand
import me.coralise.spigot.enums.AnnouncementType
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.bukkit.Bukkit

fun unmute(event: SlashCommandInteractionEvent) {
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

    cbp.mm.removeMute(player.uniqueId, "Unmuted", null)
    AbstractAnnouncer.getAnnouncer(cbpPlayer, null, null, null, AnnouncementType.UNMUTE, false)

    event.hook.sendMessage("Unmuted **${player.name}**.").queue()
}