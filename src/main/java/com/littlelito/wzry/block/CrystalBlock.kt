package com.littlelito.wzry.block

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.*
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.text.LiteralText
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class CrystalBlock(val IS_BLUE: Boolean): BlockWithEntity(FabricBlockSettings.of(Material.WOOD).hardness(50.0F)) {


    override fun onPlaced(
        world: World,
        pos: BlockPos,
        state: BlockState,
        placer: LivingEntity?,
        itemStack: ItemStack?
    ) {
        super.onPlaced(world, pos, state, placer, itemStack)
        world.setBlockState(pos, state)
        world.updateNeighbors(pos, Blocks.OBSIDIAN)
        state.updateNeighbors(world, pos, 3)

        val color = if (IS_BLUE) "blue" else "red"
        for (playerEntity in world.players) {
            playerEntity.sendMessage(LiteralText("The $color crystal is in ${pos.x} ${pos.y} ${pos.z}"), false)
        }
    }

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        super.onBreak(world, pos, state, player)
        if (state.block is CrystalBlock) {
            val winner = if (IS_BLUE) "red" else "blue"

            for (playerEntity in world.players) {
                playerEntity.sendMessage(LiteralText("Game over!"), false)
                playerEntity.sendMessage(LiteralText("The winner is $winner"), false)

            }
        }
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

}