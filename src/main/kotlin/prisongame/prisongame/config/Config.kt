package prisongame.prisongame.config

import prisongame.prisongame.config.filter.Filter

data class Config(
    val dev: Boolean,
    private val rawDefaultPrison: String
) {
    val
    val defaultPrison = prisons[rawDefaultPrison]
}
