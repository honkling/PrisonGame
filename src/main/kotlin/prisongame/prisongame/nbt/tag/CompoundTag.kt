package prisongame.prisongame.nbt.tag

import java.util.zip.GZIPOutputStream

data class CompoundTag(
    override val name: String,
    override val data: MutableMap<String, Tag<*>>
) : Tag<Map<String, Tag<*>>>(TagType.COMPOUND, name, data) {
    override fun writePayload(stream: GZIPOutputStream) {
        data.values.forEach { it.write(stream) }
        EndTag().write(stream)
    }
}
