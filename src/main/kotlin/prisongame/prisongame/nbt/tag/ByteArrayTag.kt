package prisongame.prisongame.nbt.tag

import prisongame.prisongame.nbt.toByteArray
import java.util.zip.GZIPOutputStream

data class ByteArrayTag(
    override val name: String,
    override val data: List<ByteTag>
) : Tag<List<ByteTag>>(TagType.BYTE_ARRAY, name, data) {
    override fun writePayload(stream: GZIPOutputStream) {
        stream.write(data.size.toByteArray())
        data.forEach { it.writePayload(stream) }
    }
}
