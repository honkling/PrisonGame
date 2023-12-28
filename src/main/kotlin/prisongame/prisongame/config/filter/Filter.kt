package prisongame.prisongame.config.filter

data class Filter(
    val action: FilterAction,
    val type: FilterType,
    val value: String
)
