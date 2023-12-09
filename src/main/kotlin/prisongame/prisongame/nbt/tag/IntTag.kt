package prisongame.prisongame.nbt.tag

import prisongame.prisongame.nbt.toByteArray
import java.util.zip.GZIPOutputStream

data class IntTag(
    override val name: String,
    override val data: Int
) : Tag<Int>(TagType.INT, name, data) {
    override fun writePayload(stream: GZIPOutputStream) {
        stream.write(data.toByteArray())
    }
}