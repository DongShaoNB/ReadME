package cc.dsnb.readme.config

import cc.dsnb.readme.ReadME
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * @author DongShaoNB
 */
class DataConfig {

    private lateinit var config: YamlConfiguration
    private val file = File(ReadME.instance.dataFolder, "data.yml")

    fun reloadConfig() {
        if (file.exists().not()) {
            ReadME.instance.saveResource("data.yml", false)
        }
        config = YamlConfiguration.loadConfiguration(file)
    }

    fun getConfig(): YamlConfiguration {
        return config
    }

    fun saveConfig() {
        config.save(file)
    }

}