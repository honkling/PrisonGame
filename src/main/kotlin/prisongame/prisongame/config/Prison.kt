package prisongame.prisongame.config

import org.bukkit.Material
import prisongame.prisongame.PrisonGame

data class Prison(
    val priority: Int,
    private val rawMaterial: String,
    val showInSelector: Boolean,
    private val rawDisplayName: String,
    val name: String,
    val firstTrack: TOMLLocation,
    val secondTrack: TOMLLocation,
    val nurse: TOMLLocation,
    val warden: TOMLLocation,
    val prisoner: TOMLLocation,
    val blackMarketIn: TOMLLocation,
    val blackMarketOut: TOMLLocation,
    val solitary: TOMLLocation,
    val bertrude: TOMLLocation
) {
    val material = Material.matchMaterial(rawMaterial)
    val displayName = PrisonGame.mm.deserialize(rawDisplayName)
}

fun reloadPrisons(): Map<String, Prison> {
    val mapper = tomlMapper {}
}