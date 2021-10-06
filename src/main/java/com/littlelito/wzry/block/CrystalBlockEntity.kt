package com.littlelito.wzry.block

import com.littlelito.wzry.access.LivingEntityAccess
import com.littlelito.wzry.access.PlayerEntityAccess
import com.littlelito.wzry.entity.effect.WzryEffects
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Tickable
import net.minecraft.util.math.BlockPos


abstract class CrystalBlockEntity(type: BlockEntityType<*>, val isBlue: Boolean): BlockEntity(type), Tickable {
    var lastPlayerList: MutableList<PlayerEntity> = mutableListOf()
    var currentPlayerList: MutableList<PlayerEntity> = mutableListOf()


    override fun tick() {
        if (this.hasWorld()) {

            for (blockPos in BlockPos.iterateOutwards(this.pos, 10, 10, 10)) {
                val player = world!!.getClosestPlayer(blockPos.x.toDouble(), blockPos.y.toDouble(), blockPos.z.toDouble(), 1.0, true)

                if (player != null && player is ServerPlayerEntity && ((player as PlayerEntityAccess).isBlue != this.isBlue)) {
                    val effect = if (this.isBlue) WzryEffects.CRYSTAL_TARGET_BLUE else WzryEffects.CRYSTAL_TARGET_RED
                    player.addStatusEffect(StatusEffectInstance(effect, 2, 0, true, true))

                    currentPlayerList.add(player)

                    if (lastPlayerList.contains(player)) {
                        if (!player.hasStatusEffect(WzryEffects.CRYSTAL_RESISTANCE)){
                            (player as LivingEntityAccess).data.damageAmount *= 1.2F
                            player.damage(DamageSource.LAVA, (player as LivingEntityAccess).data.damageAmount)
                            player.addStatusEffect(StatusEffectInstance(WzryEffects.CRYSTAL_RESISTANCE, 40, 0, false, false))
                        }
                        if (!currentPlayerList.contains(player)) {
                            (player as LivingEntityAccess).data.damageAmount = 3.0F
                        }
                    } else {
                        (player as LivingEntityAccess).data.damageAmount = 3.0F
                        player.damage(DamageSource.LAVA, (player as LivingEntityAccess).data.damageAmount)
                        player.addStatusEffect(StatusEffectInstance(WzryEffects.CRYSTAL_RESISTANCE, 40, 0, false, true))

                    }
                    lastPlayerList = currentPlayerList

                }
            }
        }
    }

}