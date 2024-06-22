package cc.dsnb.readme.util

import cc.dsnb.readme.ReadME
import org.bukkit.util.Consumer
import java.io.IOException
import java.net.URL
import java.util.*

/**
 * @author DongShaoNB
 */
object UpdateUtil {

    private const val SPIGOT_RESOURCE_ID = 111 // WIP

    fun getLatestVersion(consumer: Consumer<String>) {
        try {
            URL("https://api.spigotmc.org/legacy/update.php?resource=$SPIGOT_RESOURCE_ID/~")
                .openStream().use { `is` ->
                    Scanner(`is`).use { scanner ->
                        if (scanner.hasNext()) {
                            consumer.accept(scanner.next())
                        }
                    }
                }
        } catch (e: IOException) {
            if ("zh" == ReadME.systemLanguage) {
                ReadME.instance.logger.warning("无法检查更新: " + e.message)
            } else {
                ReadME.instance.logger.warning("Unable to check for update: " + e.message)
            }
        }
    }

}