package dev.foraged.shop.message

import dev.foraged.shop.ShopShared
import dev.foraged.shop.purchase.PackageAction
import dev.foraged.shop.purchase.PackageActionType
import dev.foraged.shop.purchase.PurchaseDetails
import dev.foraged.shop.purchase.PurchaseInfo
import gg.scala.aware.annotation.Subscribe
import gg.scala.aware.message.AwareMessage
import gg.scala.cache.uuid.ScalaStoreUuidCache
import net.evilblock.cubed.serializers.Serializers
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.Constants
import org.bukkit.Bukkit

object ShopMessages
{
    @Subscribe("ShopMessageInfo")
    fun shopAnnouncement(message: AwareMessage) {
        val info = message.retrieve<PurchaseInfo>("info")

        if (Bukkit.getPlayer(info.client) == null) {
            AwareMessage.of("ShopMessageUpdate", ShopShared.instance.aware, "info" to Serializers.gson.toJson(info)).publish()
            return
        }

        val doneItems = mutableListOf<PurchaseDetails>()
        info.items.forEach {
            it.actions.forEach { action ->
                if (action.target.equals(Bukkit.getServerName(), true) || action.target.equals("any", true)) { // ensure that the command is getting deployed to the correct instance
                    if (action.type == PackageActionType.COMMAND)
                    { // dont re-execute grants they should have been deployed from store
                        for (i in 1..it.quantity)
                        { // used if people purchaes multple ammounts of things
                            Bukkit.getServer()
                                .dispatchCommand(Bukkit.getConsoleSender(), action.data.replace("{player}", Bukkit.getPlayer(info.client).name ?: ScalaStoreUuidCache.username(info.client)!!)) // send commands in game :D
                        }
                        doneItems.add(it)
                    }
                }
            }
        }


        // broadcast purchaes msg
        Bukkit.broadcastMessage("")
        Bukkit.broadcastMessage("${CC.B_PRI}${ScalaStoreUuidCache.username(info.client)} has purchased")
        info.items.filter { it.actions.first().target.equals(Bukkit.getServerName(), true) || it.actions.first().target.equals("any", true) }.forEach {
            Bukkit.broadcastMessage(" ${CC.GRAY}${Constants.DOUBLE_ARROW_RIGHT} ${CC.PRI}${it.quantity}x ${CC.SEC}${it.name}")
        }
        Bukkit.broadcastMessage("${CC.SEC}from ${CC.PRI}${Constants.STORE_LINK}${CC.SEC}.")
        Bukkit.broadcastMessage("")
        info.items.removeIf {
            it in doneItems || it.actions.first().type == PackageActionType.GRANT
        }
        AwareMessage.of("ShopMessageUpdate", ShopShared.instance.aware, "info" to Serializers.gson.toJson(info)).publish()
    }
}