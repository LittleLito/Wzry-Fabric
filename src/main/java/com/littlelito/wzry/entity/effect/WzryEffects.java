package com.littlelito.wzry.entity.effect;

import com.littlelito.wzry.entity.attribute.WzryAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;

public class WzryEffects {
    public static final StatusEffect DIZZINESS = new Dizziness().addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, null, -10000, EntityAttributeModifier.Operation.ADDITION).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, null, -10000, EntityAttributeModifier.Operation.ADDITION).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, null, -10000, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect CRITRATE_INCREMENT = new CritRateIncremente().addAttributeModifier(WzryAttributes.GENERIC_CRIT_RATE, null, 0.05, EntityAttributeModifier.Operation.ADDITION);

    public static final StatusEffect CRYSTAL_RESISTANCE = new CrystalResistance();
    public static final StatusEffect CRYSTAL_TARGET_BLUE = new CrystalTarget(true);
    public static final StatusEffect CRYSTAL_TARGET_RED = new CrystalTarget(false);


}
