package com.littlelito.wzry.block;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class WzryBlockEntityTypes {
    public static BlockEntityType<BlueCrystalBlockEntity> BLUE_CRYSTAL_BLOCK_ENTITY_TYPE;
    public static BlockEntityType<RedCrystalBlockEntity> RED_CRYSTAL_BLOCK_ENTITY_TYPE;

    public void register() {
        BLUE_CRYSTAL_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, "wzry:blue_crystal_block_entity", BlockEntityType.Builder.create(BlueCrystalBlockEntity::new, WzryBlocks.BLUECRYSTALBLOCK).build(null));
        RED_CRYSTAL_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, "wzry:red_crystal_block_entity", BlockEntityType.Builder.create(RedCrystalBlockEntity::new, WzryBlocks.REDCRYSTALBLOCK).build(null));
    }
}
