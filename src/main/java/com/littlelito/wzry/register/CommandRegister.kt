package com.littlelito.wzry.register

import com.littlelito.wzry.access.PlayerEntityAccess
import com.littlelito.wzry.block.BlueCrystalBlockEntity
import com.littlelito.wzry.block.RedCrystalBlockEntity
import com.littlelito.wzry.block.WzryBlocks
import com.littlelito.wzry.entity.effect.WzryEffects
import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType.getInteger
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.minecraft.block.Blocks
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.server.command.CommandManager.argument
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.text.LiteralText
import net.minecraft.util.math.BlockPos
import java.util.*
import kotlin.math.floor


class CommandRegister {
    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _ ->
                dispatcher.register(literal("wzry")
                    .then(argument("length", IntegerArgumentType.integer())).executes { ctx ->
                        println("a")
                        val random = Random()
                        val radius: Int = try {
                            getInteger(ctx, "length")
                        } catch (e: IllegalArgumentException) {
                            println(e.message)
                            100
                        }

                        ctx.source.world.randomAlivePlayer?.sendMessage(LiteralText("Game started!"), false)

                        val x = random.nextInt(floor((2.9999999E7 - radius) * 2).toInt()) - 2.9999999E7
                        var y: Int
                        val z = random.nextInt(floor((2.9999999E7 - radius) * 2).toInt()) - 2.9999999E7

                        var a = false; var b = false

                        for (w in IntRange(0, 128).reversed()){
                            if (!ctx.source.world.getBlockState(BlockPos(x, w.toDouble(), z)).isAir && ctx.source.world.getBlockState(BlockPos(x, w.toDouble(), z)).block != Blocks.WATER && !a) {
                                //ctx.source.world.setBlockState(BlockPos(x, w.toDouble() + 1, z), WzryBlocks.CRYSTALBLOCK.defaultState.with(CrystalBlock.IS_BLUE, true))
                                ctx.source.world.setBlockEntity(BlockPos(x, w.toDouble() + 1, z), BlueCrystalBlockEntity())
                                WzryBlocks.BLUECRYSTALBLOCK.onPlaced(ctx.source.world, BlockPos(x, w.toDouble() + 1, z), WzryBlocks.BLUECRYSTALBLOCK.defaultState, null, null)
                                a = true
                            }
                            if (!ctx.source.world.getBlockState(BlockPos(x + radius, w.toDouble(), z + radius)).isAir && ctx.source.world.getBlockState(BlockPos(x + radius, w.toDouble(), z + radius)).block != Blocks.WATER && !b) {
                                ctx.source.world.setBlockEntity(BlockPos(x + radius, w.toDouble(), z + radius), RedCrystalBlockEntity())
                                //ctx.source.world.setBlockState(BlockPos(x + radius, w.toDouble() + 1, z + radius), WzryBlocks.CRYSTALBLOCK.defaultState.with(CrystalBlock.IS_BLUE, false))
                                WzryBlocks.REDCRYSTALBLOCK.onPlaced(ctx.source.world, BlockPos(x + radius, w.toDouble() + 1, z + radius), WzryBlocks.REDCRYSTALBLOCK.defaultState, null, null)
                                b = true
                            }
                        }

                        for ((i, player) in ctx.source.world.players.withIndex()) {

                            if (i % 2 == 0) {
                                player.teleport(x, 128.0, z)
                                (player as PlayerEntityAccess).isBlue = true
                                player.addStatusEffect(StatusEffectInstance(StatusEffects.RESISTANCE, 200, 99999, true, false))
                                player.addStatusEffect(StatusEffectInstance(WzryEffects.CRYSTAL_RESISTANCE, 200, 0))
                            }
                            if (i % 2 != 0) {
                                player.teleport(x + radius, 128.0, z + radius)
                                (player as PlayerEntityAccess).isBlue = false
                                player.addStatusEffect(StatusEffectInstance(StatusEffects.RESISTANCE, 200, 99999, true, false))
                                player.addStatusEffect(StatusEffectInstance(WzryEffects.CRYSTAL_RESISTANCE, 200, 0))
                            }
                        }
                        Command.SINGLE_SUCCESS
                    })

        }
    }
}