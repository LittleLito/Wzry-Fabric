package com.littlelito.wzry.data;

import net.minecraft.entity.LivingEntity;

public class LivingEntityData {
    public LivingEntity owner;
    public float health;
    public int age;
    public float damageAmount;

    public LivingEntityData(LivingEntity livingEntity) {
        this.owner = livingEntity;
        this.health = livingEntity.getHealth();
        this.age = 0;
        this.damageAmount = 4.0F;
    }
}
