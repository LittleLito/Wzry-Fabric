package com.littlelito.wzry.entity.attribute;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WzryAttributes {
    public static final EntityAttribute GENERIC_CRIT_RATE = Registry.register(Registry.ATTRIBUTE, new Identifier("wzry", "crit_rate_attribute"), new ClampedEntityAttribute("attribute.name.generic.crit_rate", 0.0, 0.0, 100.0));
    public static final EntityAttribute GENERIC_CRIT_EFFECT = Registry.register(Registry.ATTRIBUTE, new Identifier("wzry", "crit_effect_attribute"), new ClampedEntityAttribute("attribute.name.generic.crit_effect", 2.0, 0.0, 100.0));
    public static final EntityAttribute GENERIC_HEALTH_SUCKING = Registry.register(Registry.ATTRIBUTE, new Identifier("wzry", "health_sucking_attribute"), new ClampedEntityAttribute("attribute.name.generic.crit_rate", 0.0, 0.0, 100.0));

}
