package cc.dsnb.readme.gui

import cc.dsnb.readme.ReadME
import com.cryptomorin.xseries.XMaterial
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * @author DongShaoNB
 */
class ReadMEGUI {

    private lateinit var inventory: Inventory
    val itemMap = mutableMapOf<ItemStack, List<String>>()

    fun reload() {
        val guiConfig = ReadME.guiConfig.getConfig()
        inventory = ReadME.instance.server.createInventory(
            null,
            guiConfig.getInt("size"),
            ReadME.serializerLegacy.serialize(ReadME.miniMessage.deserialize(guiConfig.getString("title")))
        )
        itemMap.clear()
        for (key in guiConfig.getConfigurationSection("items").getKeys(false)) {
            XMaterial.matchXMaterial(guiConfig.getString("items.$key.material").uppercase()).apply {
                if (isPresent) {
                    val material = this.get().parseMaterial()
                    val loreList = if (guiConfig.getStringList("items.$key.lore").isNullOrEmpty()) {
                        null
                    } else {
                        guiConfig.getStringList("items.$key.lore").map {
                            ReadME.serializerLegacy.serialize(ReadME.miniMessage.deserialize(it))
                        }
                    }
                    val itemStack = ItemStack(material).also {
                        it.amount = guiConfig.getInt("items.$key.amount")
                        it.itemMeta = it.itemMeta.also { meta ->
                            meta.displayName =
                                ReadME.serializerLegacy.serialize(ReadME.miniMessage.deserialize(guiConfig.getString("items.$key.name")))
                            meta.lore = loreList
                        }
                    }
                    itemMap[itemStack] = guiConfig.getStringList("items.$key.click-action")
                    inventory.setItem(guiConfig.getInt("items.$key.slot"), itemStack)
                } else {
                    throw NullPointerException("Can not find item ${guiConfig.getString("items.$key.material")}, please check readme.yml")
                }
            }
        }
    }

    fun open(player: Player) {
        player.openInventory(inventory)
    }

    fun equalsInventory(eInventory: Inventory): Boolean {
        return inventory == eInventory
    }

}