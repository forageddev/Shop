package dev.foraged.shop

import dev.foraged.commons.ExtendedPaperPlugin
import dev.foraged.commons.annotations.container.ContainerDisable
import dev.foraged.commons.annotations.container.ContainerEnable
import dev.foraged.shop.message.ShopMessages
import gg.scala.aware.Aware
import gg.scala.aware.AwareBuilder
import gg.scala.aware.codec.codecs.interpretation.AwareMessageCodec
import gg.scala.aware.message.AwareMessage
import me.lucko.helper.plugin.ap.Plugin
import me.lucko.helper.plugin.ap.PluginDependency

@Plugin(
    name = "Shop",
    version = "\${git.commit.id.abbrev}",
    depends = [
        PluginDependency("Commons")
    ]
)
class ShopExtendedPlugin : ExtendedPaperPlugin()
{
    companion object {
        lateinit var instance: ShopExtendedPlugin
    }

    @ContainerEnable
    fun containerEnable()
    {
        instance = this
        ShopShared().configure(ShopMessages)
    }
}
