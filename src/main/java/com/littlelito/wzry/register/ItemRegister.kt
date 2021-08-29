package com.littlelito.wzry.register

import com.littlelito.wzry.item.WzryItems
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class ItemRegister {

    fun register() {

        // COMMON
        Registry.register(Registry.ITEM,
            Identifier("wzry", "tiejian"),
            WzryItems.TIEJIAN)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "leimingren"),
            WzryItems.LEIMINGREN)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "bishou"),
            WzryItems.BISHOU)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "xixuezhilian"),
            WzryItems.XIXUEZHILIAN)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "bojiquantao"),
            WzryItems.BOJIQUANTAO)


        // UNCOMMON
        Registry.register(Registry.ITEM,
            Identifier("wzry", "fengbaojujian"),
            WzryItems.FENGBAOJUJIAN)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "sujizhiqiang"),
            WzryItems.SUJIZHIQIANG)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "yunxing"),
            WzryItems.YUNXING)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "rimian"),
            WzryItems.RIMIAN)

        // RARE
        Registry.register(Registry.ITEM,
            Identifier("wzry", "moshi"),
            WzryItems.MOSHI)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "qixuezhiren"),
            WzryItems.QIXUEZHIREN)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "wujinzhanren"),
            WzryItems.WUJINZHANREN)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "anyingzhanfu"),
            WzryItems.ANYINGZHANFU)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "pojun"),
            WzryItems.POJUN)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "mingdaosiming"),
            WzryItems.MINGDAOSIMING)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "posuimingdao"),
            WzryItems.POSUIMINGDAO)


        // COMMON
        Registry.register(Registry.ITEM,
            Identifier("wzry", "hongmanao"),
            WzryItems.HONGMANAO)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "bujia"),
            WzryItems.BUJIA)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "buhutui"),
            WzryItems.BUHUTUI)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "butoukui"),
            WzryItems.BUTOUKUI)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "buxue"),
            WzryItems.BUXUE)
        // UNCOMMON
        Registry.register(Registry.ITEM,
            Identifier("wzry", "liliangyaodai"),
            WzryItems.LILIANGYAODAI)
        // RARE
        Registry.register(Registry.ITEM,
            Identifier("wzry", "fanjia"),
            WzryItems.FANJIA)
    }
}