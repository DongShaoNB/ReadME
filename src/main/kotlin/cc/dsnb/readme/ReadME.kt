package cc.dsnb.readme

import cc.dsnb.readme.command.MainCommand
import cc.dsnb.readme.config.DataConfig
import cc.dsnb.readme.config.GUIConfig
import cc.dsnb.readme.config.MainConfig
import cc.dsnb.readme.gui.ReadMEGUI
import cc.dsnb.readme.hook.AuthMeHook
import cc.dsnb.readme.listener.InventoryListener
import cc.dsnb.readme.listener.MainTabCompleteListener
import cc.dsnb.readme.listener.PlayerListener
import cc.dsnb.readme.manager.ConfigManager
import cc.dsnb.readme.util.UpdateUtil
import com.github.Anon8281.universalScheduler.UniversalScheduler
import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin
import java.util.Locale

/**
 * @author DongShaoNB
 */
class ReadME : JavaPlugin() {

    companion object {
        lateinit var instance: ReadME
        lateinit var scheduler: TaskScheduler
        lateinit var mainConfigManager: ConfigManager<MainConfig>
        lateinit var guiConfig: GUIConfig
        lateinit var dataConfig: DataConfig
        lateinit var audiences: BukkitAudiences
        lateinit var readmeGUI: ReadMEGUI
        val miniMessage = MiniMessage.miniMessage()
        val serializerLegacy = BukkitComponentSerializer.legacy()
        val systemLanguage = Locale.getDefault().language
        var hookPapi = false
    }

    override fun onEnable() {
        instance = this
        scheduler = UniversalScheduler.getScheduler(this)
        audiences = BukkitAudiences.create(this)
        loadConfig()
        loadFunction()
        hookPlugin()
        registerCommand()
        registerListener()
        loadMetrics()
        // WIP
        // checkUpdate()
    }

    override fun onDisable() {
        audiences.close()
    }

    private fun loadConfig() {
        mainConfigManager = ConfigManager.create(dataFolder.toPath(), "config.yml", MainConfig::class.java).also {
            it.reloadConfig()
        }
        guiConfig = GUIConfig().also {
            it.reloadConfig()
        }
        dataConfig = DataConfig().also {
            it.reloadConfig()
        }

    }

    private fun loadFunction() {
        readmeGUI = ReadMEGUI().also {
            it.reload()
        }
    }

    private fun hookPlugin() {
        if (server.pluginManager.isPluginEnabled("PlaceholderAPI")) {
            hookPapi = true
        }
    }

    private fun registerCommand() {
        getCommand("readme")?.also {
            it.executor = MainCommand()
            it.tabCompleter = MainTabCompleteListener()
        }
    }

    private fun registerListener() {
        // If enable hook AuthMe and server installed AuthMe, register special listener for AuthMe
        if (mainConfigManager.getConfigData().hookAuthMe() && server.pluginManager.isPluginEnabled("AuthMe")) {
            server.pluginManager.registerEvents(AuthMeHook(), this)
        } else {
            server.pluginManager.registerEvents(PlayerListener(), this)
        }
        server.pluginManager.registerEvents(InventoryListener(), this)
    }

    private fun loadMetrics() {
        Metrics(this, 22218)
    }

    private fun checkUpdate() {
        if (mainConfigManager.getConfigData().enableCheckUpdate()) {
            scheduler.runTaskAsynchronously {
                val currentVersion = description.version
                UpdateUtil.getLatestVersion { latestVersion ->
                    if (currentVersion == latestVersion) {
                        if ("zh" == systemLanguage) {
                            logger.info("插件是最新版本, 继续保持 ~")
                        } else {
                            logger.info("The plugin is the latest version, keep up ~")
                        }
                    } else {
                        if ("zh" == systemLanguage) {
                            logger.info("有新版本可以更新!")
                            logger.info("当前版本: $currentVersion | 最新版本: $latestVersion")
                        } else {
                            logger.info("There is a new version that can be updated!")
                            logger.info("Current version: $currentVersion | Latest version: $latestVersion")
                        }
                    }
                }
            }
        }
    }

}