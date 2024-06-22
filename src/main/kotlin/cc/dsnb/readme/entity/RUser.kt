package cc.dsnb.readme.entity

import cc.dsnb.readme.ReadME
import java.util.UUID

/**
 * @author DongShaoNB
 */
class RUser(private val uuid: UUID) {

    private var agree = false

    init {
        agree = ReadME.dataConfig.getConfig().getBoolean(uuid.toString())
    }

    fun getAgree(): Boolean {
        return agree
    }

    fun setAgree(agree: Boolean) {
        this.agree = agree
        ReadME.dataConfig.getConfig().set(uuid.toString(), agree)
        ReadME.dataConfig.saveConfig()
    }

}