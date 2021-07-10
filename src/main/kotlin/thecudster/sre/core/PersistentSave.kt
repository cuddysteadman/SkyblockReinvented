package thecudster.sre.core

import java.io.FileReader
import java.io.FileWriter
import kotlin.reflect.KClass
import com.google.gson.Gson
import net.minecraft.client.Minecraft
import thecudster.sre.SkyblockReinvented
import java.io.File
import kotlin.concurrent.fixedRateTimer

/**
 * Taken from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author Sychic
 * @author My-Name-Is-Jeff
 */
abstract class PersistentSave(protected val saveFile: File, interval: Long = 30_000) {

    var dirty = false

    val gson: Gson = SkyblockReinvented.gson
    val mc: Minecraft = SkyblockReinvented.mc

    abstract fun read(reader: FileReader)

    abstract fun write(writer: FileWriter)

    abstract fun setDefault(writer: FileWriter)

    private fun readSave() {
        try {
            if (!this.saveFile.exists()) {
                this.saveFile.parentFile.mkdirs()
                this.saveFile.createNewFile()
            }
            FileReader(this.saveFile).use {
                read(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            try {
                FileWriter(this.saveFile).use {
                    setDefault(it)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun writeSave() {
        try {
            if (!this.saveFile.exists()) {
                this.saveFile.parentFile.mkdirs()
                this.saveFile.createNewFile()
            }
            FileWriter(this.saveFile).use { writer ->
                write(writer)
            }
            dirty = false
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    companion object {
        val SAVES = HashSet<PersistentSave>()

        fun markDirty(clazz: KClass<out PersistentSave>) {
            val save =
                SAVES.find { it::class == clazz } ?: throw IllegalAccessException("PersistentSave not found")
            save.dirty = true
        }

        inline fun <reified T : PersistentSave> markDirty() {
            markDirty(T::class)
        }
    }

    init {
        SAVES.add(this)
        readSave()
        fixedRateTimer("${this::class.simpleName}-Save", period = interval) {
            if (dirty) {
                writeSave()
            }
        }
        Runtime.getRuntime().addShutdownHook(Thread({
            if (dirty) {
                writeSave()
            }
        }, "${this::class.simpleName}-Save"))
    }

}