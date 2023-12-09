package prisongame.prisongame.nbt.tag

import java.util.zip.GZIPOutputStream

class EndTag : Tag<Unit>(TagType.END, "", Unit) {
    override fun writePayload(stream: GZIPOutputStream) {}
}