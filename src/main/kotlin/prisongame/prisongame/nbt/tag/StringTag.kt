package prisongame.prisongame.nbt.tag

import prisongame.prisongame.nbt.toByteArray
import java.util.zip.GZIPOutputStream

data class StringTag(
    override val name: String,
    override val data: String
) : Tag<String>(TagType.STRING, name, data) {
    override fun writePayload(stream: GZIPOutputStream) {
        stream.write(data.length.toShort().toByteArray())
        stream.write(data.map { it.code.toByte() }.toByteArray())
    }
}
