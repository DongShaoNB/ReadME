package cc.dsnb.readme.config

import cc.dsnb.readme.ReadME
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * @author DongShaoNB
 */
class GUIConfig {

    private lateinit var config: YamlConfiguration
    private val file = File(ReadME.instance.dataFolder, "readme.yml")

    fun reloadConfig() {
        if (file.exists().not()) {
            ReadME.instance.saveResource("readme.yml", false)
        }
        config = YamlConfiguration.loadConfiguration(file)
    }

    fun getConfig(): YamlConfiguration {
        return config
    }

}