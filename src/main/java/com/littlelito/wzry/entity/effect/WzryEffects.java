package com.littlelito.wzry.entity.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WzryEffects {
    public static final StatusEffect DIZZINESS = Registry.register(Registry.STATUS_EFFECT, new Identifier("wzry", "dizziness"), new Dizziness().addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, null, -10000, EntityAttributeModifier.Operation.ADDITION)).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, null, -10000, EntityAttributeModifier.Operation.ADDITION).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, null, -10000, EntityAttributeModifier.Operation.ADDITION);
}
