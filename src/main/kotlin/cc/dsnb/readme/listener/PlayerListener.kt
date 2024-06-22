package cc.dsnb.readme.listener

import cc.dsnb.readme.ReadME
import cc.dsnb.readme.entity.RUser
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

/**
 * @author DongShaoNB
 */
class PlayerListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (RUser(player.uniqueId).getAgree().not()) {
            ReadME.readmeGUI.open(player)
        }
    }

}