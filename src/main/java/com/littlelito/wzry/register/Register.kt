package com.littlelito.wzry.register

import com.littlelito.wzry.item.WzryItems
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class Register {

    fun register() {
        Registry.register(Registry.ITEM,
            Identifier("wzry", "wujinzhanren"),
            WzryItems.WUJINZHANREN)
        Registry.register(Registry.ITEM,
            Identifier("wzry", "pojun"),
            WzryItems.POJUN)
    }
}