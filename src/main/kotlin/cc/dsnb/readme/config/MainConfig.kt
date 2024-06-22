package cc.dsnb.readme.config

import space.arim.dazzleconf.annote.ConfComments
import space.arim.dazzleconf.annote.ConfDefault.DefaultBoolean
import space.arim.dazzleconf.annote.ConfHeader
import space.arim.dazzleconf.annote.ConfKey
import space.arim.dazzleconf.sorter.AnnotationBasedSorter.Order

/**
 * @author DongShaoNB
 */
@ConfHeader(
    " ---------------------------------------------------",
    " ReadME Config File | Made by DongShaoNB",
    " Docs: https://docs.readme.dsnb.cc",
    " GitHub: https://github.com/DongShaoNB/ReadME",
    " ---------------------------------------------------"
)
interface MainConfig {

    @ConfKey("plugin.check-update")
    @ConfComments(
        "启动服务器时检测更新", "Check update when starting the server"
    )
    @DefaultBoolean(true)
    @Order(0)
    fun enableCheckUpdate(): Boolean

    @ConfKey("plugin.hook.authme")
    @ConfComments(
        "是否兼容AuthMe (如有使用AuthMe请设置为true)", "Hook AuthMe (If installed AuthMe please set to true)"
    )
    @DefaultBoolean(false)
    @Order(10)
    fun hookAuthMe(): Boolean

}