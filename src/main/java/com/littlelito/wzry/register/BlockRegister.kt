package com.littlelito.wzry.register

import com.littlelito.wzry.block.WzryBlocks
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class BlockRegister {
    fun register() {
        Registry.register(Registry.BLOCK, Identifier("wzry", "blue_crystal"), WzryBlocks.BLUECRYSTALBLOCK)
        Registry.register(Registry.BLOCK, Identifier("wzry", "red_crystal"), WzryBlocks.REDCRYSTALBLOCK)

    }
}