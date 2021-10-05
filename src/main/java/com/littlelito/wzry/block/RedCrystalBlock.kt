package com.littlelito.wzry.block

import net.minecraft.block.entity.BlockEntity
import net.minecraft.world.BlockView

class RedCrystalBlock: CrystalBlock(false) {
    override fun createBlockEntity(world: BlockView?): BlockEntity {
        return RedCrystalBlockEntity()
    }
}