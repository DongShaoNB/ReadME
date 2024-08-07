package cc.dsnb.readme.listener

import cc.dsnb.readme.ReadME
import cc.dsnb.readme.entity.RUser
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class InventoryListener : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {

        if (event.clickedInventory == null) {
            return
        }

        if (ReadME.readmeGUI.equalsInventory(event.clickedInventory)) {
            event.isCancelled = true
            val player = event.whoClicked as Player
            val itemStack = event.currentItem
            if (ReadME.readmeGUI.itemMap.containsKey(itemStack)) {
                if (ReadME.readmeGUI.itemMap[itemStack].isNullOrEmpty().not()) {
                    val miniMessage = ReadME.miniMessage
                    val legacySection = ReadME.serializerLegacy
                    for (action in ReadME.readmeGUI.itemMap[itemStack]!!) {
                        val splitAction = action.split(" ", limit = 2)
                        val actionContext = if (ReadME.hookPapi && splitAction.size == 2) {
                            PlaceholderAPI.setPlaceholders(
                                player,
                                splitAction[1]
                            )
                        } else if (splitAction.size == 2) {
                            splitAction[1]
                        } else {
                            ""
                        }
                        when (splitAction[0].uppercase()) {
                            "[PLAYER]" -> {
                                player.performCommand(actionContext)
                            }

                            "[CONSOLE]" -> {
                                ReadME.instance.server.dispatchCommand(
                                    ReadME.instance.server.consoleSender,
                                    actionContext
                                )
                            }

                            "[MESSAGE]" -> {
                                player.sendMessage(
                                    legacySection.serialize(
                                        miniMessage.deserialize(
                                            actionContext
                                        )
                                    )
                                )
                            }

                            "[KICK]" -> {
                                player.kickPlayer(
                                    legacySection.serialize(
                                        miniMessage.deserialize(
                                            actionContext
                                        )
                                    )
                                )
                            }

                            "[AGREE]" -> {
                                RUser(player.uniqueId).setAgree(true)
                                player.closeInventory()
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        if (ReadME.readmeGUI.equalsInventory(event.inventory)) {
            if (RUser(event.player.uniqueId).getAgree().not()) {
                ReadME.readmeGUI.open(event.player as Player)
            }
        }
    }

}