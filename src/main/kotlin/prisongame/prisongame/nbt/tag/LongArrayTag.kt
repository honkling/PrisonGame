package prisongame.prisongame.nbt.tag

import prisongame.prisongame.nbt.toByteArray
import java.util.zip.GZIPOutputStream

data class LongArrayTag(
    override val name: String,
    override val data: List<LongTag>
) : Tag<List<LongTag>>(TagType.LONG_ARRAY, name, data) {
    override fun writePayload(stream: GZIPOutputStream) {
        stream.write(data.size.toByteArray())
        data.forEach { it.writePayload(stream) }
    }
}
