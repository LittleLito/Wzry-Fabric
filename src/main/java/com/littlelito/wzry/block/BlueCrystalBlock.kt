package com.littlelito.wzry.block

import net.minecraft.block.entity.BlockEntity
import net.minecraft.world.BlockView

class BlueCrystalBlock: CrystalBlock(true) {
    override fun createBlockEntity(world: BlockView?): BlockEntity? {
        return BlueCrystalBlockEntity()
    }
}