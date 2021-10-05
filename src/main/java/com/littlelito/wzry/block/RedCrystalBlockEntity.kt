package com.littlelito.wzry.block

import net.minecraft.block.entity.BlockEntityType

class RedCrystalBlockEntity: CrystalBlockEntity(WzryBlockEntityTypes.RED_CRYSTAL_BLOCK_ENTITY_TYPE, false) {
    override fun getType(): BlockEntityType<*> {
        return WzryBlockEntityTypes.RED_CRYSTAL_BLOCK_ENTITY_TYPE
    }
}