package prisongame.prisongame.nbt

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataHolder
import prisongame.prisongame.nbt.tag.CompoundTag
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.UUID
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class OfflinePlayerHolder(val uuid: UUID) : PersistentDataHolder {
    constructor(player: OfflinePlayer) : this(player.uniqueId)

    private val world = Bukkit.getWorlds()[0]
    private val file = world.worldFolder.resolve("playerdata/$uuid.dat")
    private var reader: NBTReader? = null
    private var compound: CompoundTag
    private var values: CompoundTag

    init {
        val empty = CompoundTag("BukkitValues", mutableMapOf())

        if (!file.exists()) {
            compound = CompoundTag("", mutableMapOf())
            values = empty
        } else {
            reader = NBTReader(GZIPInputStream(FileInputStream(file)))
            compound = reader!!.readTag() as CompoundTag
            values = (compound.data["BukkitValues"] ?: empty) as CompoundTag
            reader!!.stream.close()
        }
    }

    override fun getPersistentDataContainer(): PersistentDataContainer {
        return if (isOnline()) Bukkit.getPlayer(uuid)!!.persistentDataContainer
            else OfflinePlayerPDC(values.data)
    }

    private fun isOnline(): Boolean {
        return Bukkit.getOnlinePlayers().any { it.uniqueId == uuid }
    }

    fun close() {
        println(uuid)
        println(isOnline())
        if (isOnline())
            return

        val writer = NBTWriter(GZIPOutputStream(FileOutputStream(file)))
        writer.writeTag(compound)
        writer.stream.close()
    }
}