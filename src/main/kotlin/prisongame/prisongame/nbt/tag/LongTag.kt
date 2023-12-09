package prisongame.prisongame.nbt.tag

import prisongame.prisongame.nbt.toByteArray
import java.util.zip.GZIPOutputStream

data class LongTag(
    override val name: String,
    override val data: Long
) : Tag<Long>(TagType.LONG, name, data) {
    override fun writePayload(stream: GZIPOutputStream) {
        stream.write(data.toByteArray())
    }
}
