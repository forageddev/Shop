package dev.foraged.shop.command

import dev.foraged.commons.acf.annotation.CommandAlias
import dev.foraged.commons.acf.annotation.CommandPermission
import dev.foraged.commons.acf.annotation.Default
import dev.foraged.commons.annotations.commands.AutoRegister
import dev.foraged.commons.command.GoodCommand
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.Constants
import org.bukkit.command.CommandSender

@CommandAlias("shop|store|buy|sitelink|storelink|purchase")
@AutoRegister
object ShopCommand : GoodCommand()
{
    @Default
    fun execute(sender: CommandSender) {
        sender.sendMessage("${CC.SEC}You can visit our store at ${CC.PRI}${Constants.STORE_LINK}${CC.SEC}.")
    }
}