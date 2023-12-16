package prisongame.prisongame.discord.listeners

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.Command
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import prisongame.prisongame.discord.commands.*

object Commands : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when (event.name) {
            "players" -> players(event)
            "ban" -> ban(event)
            "mute" -> mute(event)
            "unban" -> unban(event)
            "unmute" -> unmute(event)
            else -> {}
        }
    }

    override fun onCommandAutoCompleteInteraction(event: CommandAutoCompleteInteractionEvent) {
        val players = Bukkit.getOnlinePlayers().map { Command.Choice(it.name, it.name) }

        when (event.name) {
            "ban", "mute", "kick", "warn", "unmute" -> {
                if (event.focusedOption.name != "player")
                    return

                event.replyChoices(players).queue()
            }
        }
    }
}