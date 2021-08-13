package com.littlelito.wzry.mixin;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityAttributes.class)
public class EntityAttributesMixin {
    /**
     * @author Bugjang
     */
    @Overwrite
    private static EntityAttribute register(String id, EntityAttribute attribute) {
        if (id.equals("generic.armor")) {
            attribute = (new ClampedEntityAttribute("attribute.name.generic.armor", 0.0D, 0.0D, 100000.0D)).setTracked(true);
        }
        return Registry.register(Registry.ATTRIBUTE, id, attribute);
    }
}
