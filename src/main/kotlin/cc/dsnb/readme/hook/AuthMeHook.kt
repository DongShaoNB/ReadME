package cc.dsnb.readme.hook

import cc.dsnb.readme.ReadME
import fr.xephi.authme.events.LoginEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * @author DongShaoNB
 */
class AuthMeHook : Listener {

    @EventHandler
    fun onLogin(event: LoginEvent) {
        ReadME.readmeGUI.open(event.player)
    }

}