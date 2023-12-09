package prisongame.prisongame.nbt.tag

import prisongame.prisongame.nbt.toByteArray
import java.util.zip.GZIPOutputStream

data class IntArrayTag(
    override val name: String,
    override val data: List<IntTag>
) : Tag<List<IntTag>>(TagType.INT_ARRAY, name, data) {
    override fun writePayload(stream: GZIPOutputStream) {
        stream.write(data.size.toByteArray())
        data.forEach { it.writePayload(stream) }
    }
}
