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

    private var escaped = false
    private var role = Role.PRISONER

    fun isEscaped() = escaped
    fun setEscaped(value: Boolean) = setEscaped(value, true)
    fun setEscaped(value: Boolean, action: Boolean) {
        escaped = value

        if (action)
            if (!value)
                player.performCommand("resign")
            else
                PrisonGame.setCriminal(player)
    }

    fun getRole() = role
    fun setRole(value: Role) = setRole(value, true)
    fun setRole(value: Role, action: Boolean) {
        if (value == Role.PRISONER)
            Exception().printStackTrace()

        role = value

        if (action)
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
    val profile = profiles[this] ?: Profile(this)
    profiles[this] = profile
    return profile
}

fun Player.resetProfile() {
    profiles.remove(this)
}