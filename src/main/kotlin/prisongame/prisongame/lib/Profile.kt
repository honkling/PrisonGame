package prisongame.prisongame.lib

import org.bukkit.entity.Player
import prisongame.prisongame.PrisonGame
import java.util.UUID
import kotlin.random.Random

private val profiles = mutableMapOf<Player, Profile>()

data class Profile(val player: Player) {
    var track = 0.0
    var wardenTime = -1
    var solitaryTimer = -1
    var builderMode = false
    var lastMessageSent = ""
    var killer: Player? = null
    var timeSinceGuardKill = -1
    var invitation: Role? = null
    var attendedRollCall = false
    var attendedLightsOut = false
    val hardModeIdentifier = Random.nextInt(100, 999)

    var hardMode = false
        set(value) {
            field = value

            when (value) {
                true -> PrisonGame.enableHardMode(player)
                false -> PrisonGame.enableNormalMode(player)
            }
        }

    var escaped = false
        set(value) {
            field = value

            if (!value)
                player.performCommand("resign")
            else
                PrisonGame.setCriminal(player)
        }

    var role = Role.PRISONER
        set(value) {
            field = value

            when (value) {
                Role.WARDEN -> PrisonGame.setWarden(player)
                Role.SWAT -> PrisonGame.setSwat(player)
                Role.GUARD -> PrisonGame.setGuard(player)
                Role.NURSE -> PrisonGame.setNurse(player)
                Role.PRISONER -> player.performCommand("resign")
            }
        }
}

fun Player.getProfile(): Profile {
    return profiles[this] ?: Profile(this)
}

fun Player.resetProfile() {
    profiles.remove(this)
}