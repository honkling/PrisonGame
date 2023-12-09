package prisongame.prisongame.nbt.tag

import java.util.zip.GZIPOutputStream

data class ByteTag(
    override val name: String,
    override val data: Byte
) : Tag<Byte>(TagType.BYTE, name, data) {
    override fun writePayload(stream: GZIPOutputStream) {
        stream.write(byteArrayOf(data))
    }
}