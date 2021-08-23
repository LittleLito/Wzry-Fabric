package com.littlelito.wzry.mixin;

import com.littlelito.wzry.access.PlayerEntityAccess;
import com.littlelito.wzry.item.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityAccess {
    public float healthSucking = 0F;
    public float penetration = 0F;
    boolean EQUIPED = false;
    UUID uuid;
    float lastIncrease;

    @Shadow public abstract void resetLastAttackedTicks();

    @Shadow public abstract void spawnSweepAttackParticles();

    @Shadow public abstract void addCritParticles(Entity target);

    @Shadow public abstract void addEnchantedHitParticles(Entity target);

    @Shadow public abstract void increaseStat(Identifier stat, int amount);

    @Shadow public abstract void addExhaustion(float exhaustion);

    @Shadow @Final public PlayerInventory inventory;

    @Shadow public abstract float getAttackCooldownProgress(float baseTime);

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public int experienceLevel;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo info) {
        float maxHealthIncrease = 0F;
        boolean HAVE = false;


        for (ItemStack itemStack: getHotBar()) {
            if (itemStack.getItem() instanceof RiMian) {
            maxHealthIncrease += 3;
            HAVE = true;
            } if (itemStack.getItem() instanceof HongMaNao) {
                maxHealthIncrease += 3;
                HAVE = true;
            }
        }

        EntityAttributeModifier maxHealthModifier = new EntityAttributeModifier(
                "Tool modifier",
                maxHealthIncrease, EntityAttributeModifier.Operation.ADDITION
        );
        if (HAVE) {
            if (!EQUIPED){
                System.out.println(maxHealthModifier.getId());
                Objects.requireNonNull(this.getAttributes().getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH)).addTemporaryModifier(maxHealthModifier);
                uuid = maxHealthModifier.getId();
                EQUIPED = true;
            } if (maxHealthIncrease != lastIncrease) {
                Objects.requireNonNull(this.getAttributes().getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH)).removeModifier(uuid);
                Objects.requireNonNull(this.getAttributes().getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH)).addTemporaryModifier(maxHealthModifier);
                uuid = maxHealthModifier.getId();
            }
        } else {
            EQUIPED = false;
            Objects.requireNonNull(this.getAttributes().getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH)).removeModifier(uuid);
        }
        lastIncrease = maxHealthIncrease;
    }

    /**
     * @author Bugjang
     * @reason no reason
     */
    @Overwrite
    public float getAttackCooldownProgressPerTick() {
        boolean hasWzry = false;
        double attackSpeed = this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED);
        float attackSpeedPercentage = 0F;

        for (ItemStack itemStack: getHotBar()) {
            Item item = itemStack.getItem();
            if ((item instanceof WzrySwordItem || item instanceof WzryAxeItem)) {
                hasWzry = true;
                if (!itemStack.equals(this.getMainHandStack())) {
                    if (item instanceof WzrySwordItem) {
                        attackSpeed += ((WzrySwordItem) item).getAttackSpeed() + 4;
                        attackSpeedPercentage += ((WzrySwordItem) item).getAttackSpeedPercentage();
                    } if (item instanceof WzryAxeItem) {
                        attackSpeed += ((WzryAxeItem) item).getAttackSpeed() + 4;
                        attackSpeedPercentage += ((WzryAxeItem) item).getAttackSpeedPercentage();
                    }
                }
            }
        }
        if ((!(this.getMainHandStack().getItem() instanceof ToolItem) || !hasWzry) || getHotBar().equals(DefaultedList.ofSize(9, ItemStack.EMPTY))) {
            attackSpeed -= this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED);
        }

        attackSpeed = attackSpeed * (attackSpeedPercentage + 1);
        return (float)(1.0D / attackSpeed * 20.0D);
    }

    /**
     * @author Bugjang
     * @reason no reason
     */
    @Overwrite
    public void attack(Entity target) {
        boolean JINGZHUN = false;
        String CANFEI = "";
        boolean POBAI = false;
        boolean WUJIN = false;
        boolean POJUN = false;

        if (target.isAttackable()) {
            if (!target.handleAttack(this)) {
                float f = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);

                float critRate = 0F;
                float critEffect = 2.0F;
                healthSucking = 0F;

                for (ItemStack itemStack: getHotBar()) {
                    Item item = itemStack.getItem();
                    if (item instanceof ToolItem && !itemStack.equals(this.getMainHandStack())) {
                        if (item instanceof SwordItem) {f += ((SwordItem) item).getAttackDamage();}
                        if (item instanceof MiningToolItem) {f += ((MiningToolItem) item).getAttackDamage();}
                    }

                    if (item instanceof WzrySwordItem) {
                        critRate += ((WzrySwordItem) item).getCritRate();
                        healthSucking += ((WzrySwordItem) item).getSuckingHealthPercentage();

                        // passive skills
                        if (item instanceof SuJiZhiQiang && !JINGZHUN) {
                            f = ((SuJiZhiQiang) item).passiveSkill(f, (PlayerEntity) (Object) this);
                            JINGZHUN = true;
                        }
                        if (item instanceof PoJun && !POJUN) {
                            f = ((PoJun) item).passiveSkill(target, f);
                            POJUN = true;
                        }
                        if (item instanceof WuJinZhanRen && !WUJIN) {
                            critEffect = ((WuJinZhanRen) item).passiveSkill(critEffect);
                            WUJIN = true;
                        }
                    }
                    if (item instanceof WzryAxeItem) {
                        critRate += ((WzryAxeItem) item).getCritRate();
                        healthSucking += ((WzryAxeItem) item).getSuckingHealthPercentage();

                        // passive skills
                        if (item instanceof RiMian && CANFEI.equals("")) { CANFEI = "RiMian"; }
                        if (item instanceof MoShi && !POBAI) {
                            POBAI = true;
                        }
                    }

                }
                // crit
                if (critRate > Math.random()) {
                    f *= critEffect;
                }
                // CANFEI
                if (target instanceof LivingEntity) {
                    switch (CANFEI) {
                        case "":
                        case "RiMian": {
                            if (0.3 >= Math.random()) {
                                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 0));
                            }
                        }
                        case "HeiQie": {
                            if (0.3 >= Math.random()) {
                                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1));
                            }
                        }
                    }
                }
                // POBAI
                if (POBAI) {
                    f = new MoShi().passiveSkill(f, target);
                }

                float h;
                if (target instanceof LivingEntity) {
                    h = EnchantmentHelper.getAttackDamage(this.getMainHandStack(), ((LivingEntity)target).getGroup());
                } else {
                    h = EnchantmentHelper.getAttackDamage(this.getMainHandStack(), EntityGroup.DEFAULT);
                }

                float i = this.getAttackCooldownProgress(0.3F);
                f *= 0.2F + i * i * 0.8F;
                h *= i;
                this.resetLastAttackedTicks();
                if (f > 0.0F || h > 0.0F) {
                    boolean bl = i > 0.9F;
                    boolean bl2 = false;
                    int j = 0;
                    j = j + EnchantmentHelper.getKnockback(this);
                    if (this.isSprinting() && bl) {
                        this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, this.getSoundCategory(), 1.0F, 1.0F);
                        ++j;
                        bl2 = true;
                    }

                    boolean bl3 = bl && this.fallDistance > 0.0F && !this.onGround && !this.isClimbing() && !this.isTouchingWater() && !this.hasStatusEffect(StatusEffects.BLINDNESS) && !this.hasVehicle() && target instanceof LivingEntity;
                    bl3 = bl3 && !this.isSprinting();
                    if (bl3) {
                        f *= 1.5F;
                    }

                    f += h;
                    boolean bl4 = false;
                    double d = (this.horizontalSpeed - this.prevHorizontalSpeed);
                    if (bl && !bl3 && !bl2 && this.onGround && d < (double)this.getMovementSpeed()) {
                        ItemStack itemStack = this.getStackInHand(Hand.MAIN_HAND);
                        if (itemStack.getItem() instanceof SwordItem) {
                            bl4 = true;
                        }
                    }

                    float k = 0.0F;
                    boolean bl5 = false;
                    int l = EnchantmentHelper.getFireAspect(this);
                    if (target instanceof LivingEntity) {
                        k = ((LivingEntity)target).getHealth();
                        if (l > 0 && !target.isOnFire()) {
                            bl5 = true;
                            target.setOnFireFor(1);
                        }
                    }

                    Vec3d vec3d = target.getVelocity();
                    boolean bl6 = target.damage(DamageSource.player((PlayerEntity) (Object) this), f);
                    if (bl6) {
                        if (j > 0) {
                            if (target instanceof LivingEntity) {
                                ((LivingEntity)target).takeKnockback((float)j * 0.5F, MathHelper.sin(this.yaw * 0.017453292F), (-MathHelper.cos(this.yaw * 0.017453292F)));
                            } else {
                                target.addVelocity((-MathHelper.sin(this.yaw * 0.017453292F) * (float)j * 0.5F), 0.1D, (MathHelper.cos(this.yaw * 0.017453292F) * (float)j * 0.5F));
                            }

                            this.setVelocity(this.getVelocity().multiply(0.6D, 1.0D, 0.6D));
                            this.setSprinting(false);
                        }

                        if (bl4) {
                            float m = 1.0F + EnchantmentHelper.getSweepingMultiplier(this) * f;
                            List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(1.0D, 0.25D, 1.0D));
                            Iterator<LivingEntity> var19 = list.iterator();

                            label166:
                            while(true) {
                                LivingEntity livingEntity;
                                do {
                                    do {
                                        do {
                                            do {
                                                if (!var19.hasNext()) {
                                                    this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, this.getSoundCategory(), 1.0F, 1.0F);
                                                    this.spawnSweepAttackParticles();
                                                    break label166;
                                                }

                                                livingEntity = var19.next();
                                            } while(livingEntity == this);
                                        } while(livingEntity == target);
                                    } while(this.isTeammate(livingEntity));
                                } while(livingEntity instanceof ArmorStandEntity && ((ArmorStandEntity)livingEntity).isMarker());

                                if (this.squaredDistanceTo(livingEntity) < 9.0D) {
                                    livingEntity.takeKnockback(0.4F, MathHelper.sin(this.yaw * 0.017453292F), (-MathHelper.cos(this.yaw * 0.017453292F)));
                                    livingEntity.damage(DamageSource.player((PlayerEntity) (Object) this), m);
                                }
                            }
                        }

                        if (target instanceof ServerPlayerEntity && target.velocityModified) {
                            ((ServerPlayerEntity)target).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(target));
                            target.velocityModified = false;
                            target.setVelocity(vec3d);
                        }

                        if (bl3) {
                            this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, this.getSoundCategory(), 1.0F, 1.0F);
                            this.addCritParticles(target);
                        }

                        if (!bl3 && !bl4) {
                            if (bl) {
                                this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, this.getSoundCategory(), 1.0F, 1.0F);
                            } else {
                                this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, this.getSoundCategory(), 1.0F, 1.0F);
                            }
                        }

                        if (h > 0.0F) {
                            this.addEnchantedHitParticles(target);
                        }

                        this.onAttacking(target);
                        if (target instanceof LivingEntity) {
                            EnchantmentHelper.onUserDamaged((LivingEntity)target, this);
                        }

                        EnchantmentHelper.onTargetDamaged(this, target);
                        ItemStack itemStack2 = this.getMainHandStack();
                        Entity entity = target;
                        if (target instanceof EnderDragonPart) {
                            entity = ((EnderDragonPart)target).owner;
                        }

                        if (!this.world.isClient && !itemStack2.isEmpty() && entity instanceof LivingEntity) {
                            itemStack2.postHit((LivingEntity)entity, (PlayerEntity) (Object) this);
                            if (itemStack2.isEmpty()) {
                                this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                            }
                        }

                        if (target instanceof LivingEntity) {
                            float n = k - ((LivingEntity)target).getHealth();
                            this.increaseStat(Stats.DAMAGE_DEALT, Math.round(n * 10.0F));
                            if (l > 0) {
                                target.setOnFireFor(l * 4);
                            }

                            if (this.world instanceof ServerWorld && n > 2.0F) {
                                int o = (int)((double)n * 0.5D);
                                ((ServerWorld)this.world).spawnParticles(ParticleTypes.DAMAGE_INDICATOR, target.getX(), target.getBodyY(0.5D), target.getZ(), o, 0.1D, 0.0D, 0.1D, 0.2D);
                            }
                        }

                        this.addExhaustion(0.1F);
                    } else {
                        this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, this.getSoundCategory(), 1.0F, 1.0F);
                        if (bl5) {
                            target.extinguish();
                        }
                    }
                }

            }
        }
    }

    /**
     * @author Bugjang
     * @reason no reason
     */
    @Overwrite
    public void applyDamage(DamageSource source, float amount) {
        boolean JINGJI = false;

        if (!this.isInvulnerableTo(source)) {
            amount = this.applyArmorToDamage(source, amount);
            amount = this.applyEnchantmentsToDamage(source, amount);
            float f = amount;
            amount = Math.max(amount - this.getAbsorptionAmount(), 0.0F);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (f - amount));
            float g = f - amount;
            if (g > 0.0F && g < 3.4028235E37F) {
                this.increaseStat(Stats.DAMAGE_ABSORBED, Math.round(g * 10.0F));
            }

            if (this.getEquippedStack(EquipmentSlot.CHEST).getItem() == WzryItems.FANJIA) {
                JINGJI = true;
            }

            if (amount != 0.0F) {
                this.addExhaustion(source.getExhaustion());
                float h = this.getHealth();
                this.setHealth(this.getHealth() - amount);

                Entity attacker = source.getAttacker();
                if (attacker instanceof LivingEntity) {
                    if (JINGJI) {
                        attacker.damage(DamageSource.thorns(this), amount * 0.25F);
                        if (amount > this.getMaxHealth() * 0.2) {
                            attacker.damage(DamageSource.mob((LivingEntity) this), this.getMaxHealth() * 0.1F);
                        }
                    }
                    // health sucking
                    if (attacker instanceof PlayerEntity) {
                        ((PlayerEntity) attacker).setHealth(((PlayerEntity) attacker).getHealth() + ((PlayerEntityAccess) attacker).getHealthSucking() * amount);
                    }
                }

                this.getDamageTracker().onDamage(source, h, amount);
                if (amount < 3.4028235E37F) {
                    this.increaseStat(Stats.DAMAGE_TAKEN, Math.round(amount * 10.0F));
                }

            }
        }
    }
    @Override
    public DefaultedList<ItemStack> getHotBar() {
        DefaultedList<ItemStack> playerInventory = this.inventory.main;
        DefaultedList<ItemStack> playerHotBar = DefaultedList.of();
        for (int i=0;i<9;i++) {
            ItemStack item = playerInventory.get(i);
            playerHotBar.add(item);
        }
        return playerHotBar;
    }

    @Override
    public float getHealthSucking() { return this.healthSucking; }

    @Override
    public void setHealthSucking(float value) {this.healthSucking = value; }

}

