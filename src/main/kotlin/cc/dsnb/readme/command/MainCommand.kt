package cc.dsnb.readme.command

import cc.dsnb.readme.ReadME
import cc.dsnb.readme.util.TimeUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @author DongShaoNB
 */
class MainCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (args.isNotEmpty()) {
            when (args[0]) {
                "test" -> {
                    if (sender is Player) {
                        ReadME.readmeGUI.open(sender)
                    } else {
                        if ("zh" == ReadME.systemLanguage) {
                            sender.sendMessage(ReadME.serializerLegacy.serialize(ReadME.miniMessage.deserialize("<gold>[ReadME] <red>该子命令仅玩家可用!")))
                        } else {
                            sender.sendMessage(ReadME.serializerLegacy.serialize(ReadME.miniMessage.deserialize("<gold>[ReadME] <red>The subcommand only player can use")))
                        }
                    }
                    return true
                }

                "reload" -> {
                    val time = TimeUtil.measureTimeMillis {
                        ReadME.mainConfigManager.reloadConfig()
                        ReadME.guiConfig.reloadConfig()
                        ReadME.readmeGUI.reload()
                    }
                    if ("zh" == ReadME.systemLanguage) {
                        sender.sendMessage(ReadME.serializerLegacy.serialize(ReadME.miniMessage.deserialize("<gold>[ReadME] <green>重载成功, 耗时: $time 毫秒")))
                    } else {
                        sender.sendMessage(ReadME.serializerLegacy.serialize(ReadME.miniMessage.deserialize("<gold>[ReadME] <green>Reloaded successfully, 耗时: $time ms")))
                    }
                    return true
                }
            }
        }
        return false
    }
}