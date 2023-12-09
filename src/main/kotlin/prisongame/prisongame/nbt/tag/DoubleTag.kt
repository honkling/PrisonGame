package prisongame.prisongame.nbt.tag

import prisongame.prisongame.nbt.toByteArray
import java.util.zip.GZIPOutputStream

data class DoubleTag(
    override val name: String,
    override val data: Double
) : Tag<Double>(TagType.DOUBLE, name, data) {
    override fun writePayload(stream: GZIPOutputStream) {
        stream.write(data.toByteArray())
    }
}
