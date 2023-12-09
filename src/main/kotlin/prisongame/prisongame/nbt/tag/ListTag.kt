package prisongame.prisongame.nbt.tag

import prisongame.prisongame.nbt.toByteArray
import java.util.zip.GZIPOutputStream

data class ListTag(
    override val name: String,
    val listType: TagType,
    override val data: List<Tag<*>>
) : Tag<List<Tag<*>>>(TagType.LIST, name, data) {
    override fun writePayload(stream: GZIPOutputStream) {
        stream.write(listType.ordinal)
        stream.write(data.size.toByteArray())

        for (tag in data)
            tag.writePayload(stream)
    }
}
