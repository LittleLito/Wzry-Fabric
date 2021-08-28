package com.littlelito.wzry.mixin;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(MobEntity.class)
public class MobEntityMixin {
    @Shadow protected int experiencePoints;

    @Shadow @Final private DefaultedList<ItemStack> armorItems;

    @Shadow @Final protected float[] armorDropChances;

    @Shadow @Final protected float[] handDropChances;

    @Shadow @Final private DefaultedList<ItemStack> handItems;

    /**
     * @author Bugjang
     * @reason no reason
     */
    @Overwrite
    public int getXpToDrop(PlayerEntity player) {
        if (this.experiencePoints > 0) {
            int i = this.experiencePoints;

            int k;
            for(k = 0; k < this.armorItems.size(); ++k) {
                if (!this.armorItems.get(k).isEmpty() && this.armorDropChances[k] <= 1.0F) {
                    i += 1 + new Random().nextInt(3);
                }
            }

            for(k = 0; k < this.handItems.size(); ++k) {
                if (!this.handItems.get(k).isEmpty() && this.handDropChances[k] <= 1.0F) {
                    i += 1 + new Random().nextInt(3);
                }
            }

            return i * 2;
        } else {
            return this.experiencePoints * 2;
        }
    }
}
