package com.littlelito.wzry.block

import net.minecraft.block.entity.BlockEntityType

class BlueCrystalBlockEntity: CrystalBlockEntity(WzryBlockEntityTypes.BLUE_CRYSTAL_BLOCK_ENTITY_TYPE, true) {
    override fun getType(): BlockEntityType<*> {
        return WzryBlockEntityTypes.BLUE_CRYSTAL_BLOCK_ENTITY_TYPE
    }
}