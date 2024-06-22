package cc.dsnb.readme.listener

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

/**
 * @author DongShaoNB
 */
class MainTabCompleteListener : TabCompleter {

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): MutableList<String> {
        if (args.size == 1) {
            return mutableListOf("test", "reload")
        }
        return mutableListOf()
    }

}