package prisongame.prisongame.nbt.tag

import prisongame.prisongame.nbt.toByteArray
import java.util.zip.GZIPOutputStream

data class FloatTag(
    override val name: String,
    override val data: Float
) : Tag<Float>(TagType.FLOAT, name, data) {
    override fun writePayload(stream: GZIPOutputStream) {
        stream.write(data.toByteArray())
    }
}
