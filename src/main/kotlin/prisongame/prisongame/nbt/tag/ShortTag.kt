package prisongame.prisongame.nbt.tag

import prisongame.prisongame.nbt.toByteArray
import java.util.zip.GZIPOutputStream

data class ShortTag(
    override val name: String,
    override val data: Short
) : Tag<Short>(TagType.SHORT, name, data) {
    override fun writePayload(stream: GZIPOutputStream) {
        stream.write(data.toByteArray())
    }
}