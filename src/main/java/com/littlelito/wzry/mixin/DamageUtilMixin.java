package com.littlelito.wzry.mixin;

import net.minecraft.entity.DamageUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DamageUtil.class)
public class DamageUtilMixin {
    /**
     * @author Bugjang
     */
    @Overwrite
    public static float getDamageLeft(float damage, float armor, float armorToughness) {
        float f = 2.0F + armorToughness / 4.0F;
        float g = Math.max(armor - damage / f, armor * 0.2F);
        return damage * (1.0F - g / (g + 30.0F));
    }
}
