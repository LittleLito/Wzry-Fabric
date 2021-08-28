package com.littlelito.wzry.mixin;

import com.littlelito.wzry.access.LivingEntityAccess;
import com.littlelito.wzry.access.PlayerEntityAccess;
import com.littlelito.wzry.client.ClientWzry;
import com.littlelito.wzry.data.LivingEntityData;
import com.littlelito.wzry.item.AnYingZhanFu;
import com.littlelito.wzry.item.WzryAxeItem;
import com.littlelito.wzry.item.WzryItems;
import com.littlelito.wzry.item.WzrySwordItem;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityAccess {
    private LivingEntityData data;
    public int lastUseMingDao;
    public boolean canUseMingDao;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract boolean clearStatusEffects();

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow protected abstract float applyEnchantmentsToDamage(DamageSource source, float amount);

    @Shadow public abstract float getAbsorptionAmount();

    @Shadow public abstract void setAbsorptionAmount(float amount);

    @Shadow public abstract float getHealth();

    @Shadow public abstract DamageTracker getDamageTracker();

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract float getMaxHealth();

    @Shadow @Final private DefaultedList<ItemStack> equippedArmor;

    @Shadow protected abstract void damageArmor(DamageSource source, float amount);

    @Shadow public abstract double getAttributeValue(EntityAttribute attribute);

    @Shadow public abstract boolean isAlive();

    @Shadow public abstract boolean canBeRiddenInWater();

    @Shadow public abstract boolean isDead();

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow public abstract boolean isSleeping();

    @Shadow public abstract void wakeUp();

    @Shadow protected int despawnCounter;

    @Shadow protected abstract boolean blockedByShield(DamageSource source);

    @Shadow protected abstract void damageShield(float amount);

    @Shadow protected abstract void takeShieldHit(LivingEntity attacker);

    @Shadow public float limbDistance;

    @Shadow protected float lastDamageTaken;

    @Shadow public int maxHurtTime;

    @Shadow public int hurtTime;

    @Shadow public float knockbackVelocity;

    @Shadow public abstract void setAttacker(@Nullable LivingEntity attacker);

    @Shadow protected int playerHitTimer;

    @Shadow @Nullable protected PlayerEntity attackingPlayer;

    @Shadow public abstract void takeKnockback(float f, double d, double e);

    @Shadow @Nullable protected abstract SoundEvent getDeathSound();

    @Shadow protected abstract float getSoundVolume();

    @Shadow protected abstract float getSoundPitch();

    @Shadow public abstract void onDeath(DamageSource source);

    @Shadow protected abstract void playHurtSound(DamageSource source);

    @Shadow private DamageSource lastDamageSource;

    @Shadow private long lastDamageTime;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo info) {
        if ((LivingEntity) (Object) this instanceof ServerPlayerEntity){
            lastUseMingDao -= 1;
            canUseMingDao = lastUseMingDao <= 0;
            ClientWzry.canUseMingDao = lastUseMingDao <= 0;
        }
        float a = this.getData().health;
        if (this.data == null) {
            this.data = new LivingEntityData((LivingEntity) (Object) this);
            this.data.health = a;
        }
        this.data.age += 1;
    }

    /**
     * @author Bugjang
     *//*
    @Overwrite
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (this.world.isClient) {
            return false;
        } else if (this.isDead()) {
            return false;
        } else if (source.isFire() && this.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
            return false;
        } else {
            if (this.isSleeping() && !this.world.isClient) {
                this.wakeUp();
            }

            this.despawnCounter = 0;
            float f = amount;
            if ((source == DamageSource.ANVIL || source == DamageSource.FALLING_BLOCK) && !this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                this.getEquippedStack(EquipmentSlot.HEAD).damage((int)(amount * 4.0F + this.random.nextFloat() * amount * 2.0F), (LivingEntity) (Object) this, (livingEntityx) -> {
                    livingEntityx.sendEquipmentBreakStatus(EquipmentSlot.HEAD);
                });
                amount *= 0.75F;
            }

            boolean bl = false;
            float g = 0.0F;
            if (amount > 0.0F && this.blockedByShield(source)) {
                this.damageShield(amount);
                g = amount;
                amount = 0.0F;
                if (!source.isProjectile()) {
                    Entity entity = source.getSource();
                    if (entity instanceof LivingEntity) {
                        this.takeShieldHit((LivingEntity)entity);
                    }
                }

                bl = true;
            }

            this.limbDistance = 1.5F;
            boolean bl2 = true;
            if ((float)this.timeUntilRegen > 10.0F) {
                if (amount <= this.lastDamageTaken) {
                    return false;
                }

                this.applyDamage(source, amount - this.lastDamageTaken);
                this.lastDamageTaken = amount;
                bl2 = false;
            } else {
                this.lastDamageTaken = amount;
                this.timeUntilRegen = 20;
                this.applyDamage(source, amount);
                this.maxHurtTime = 20;
                this.hurtTime = this.maxHurtTime;
            }

            this.knockbackVelocity = 0.0F;
            Entity entity2 = source.getAttacker();
            if (entity2 != null) {
                if (entity2 instanceof LivingEntity) {
                    this.setAttacker((LivingEntity)entity2);
                }

                if (entity2 instanceof PlayerEntity) {
                    this.playerHitTimer = 100;
                    this.attackingPlayer = (PlayerEntity)entity2;
                } else if (entity2 instanceof WolfEntity) {
                    WolfEntity wolfEntity = (WolfEntity)entity2;
                    if (wolfEntity.isTamed()) {
                        this.playerHitTimer = 100;
                        LivingEntity livingEntity = wolfEntity.getOwner();
                        if (livingEntity != null && livingEntity.getType() == EntityType.PLAYER) {
                            this.attackingPlayer = (PlayerEntity)livingEntity;
                        } else {
                            this.attackingPlayer = null;
                        }
                    }
                }
            }

            if (bl2) {
                if (bl) {
                    this.world.sendEntityStatus(this, (byte)29);
                } else if (source instanceof EntityDamageSource && ((EntityDamageSource)source).isThorns()) {
                    this.world.sendEntityStatus(this, (byte)33);
                } else {
                    byte e;
                    if (source == DamageSource.DROWN) {
                        e = 36;
                    } else if (source.isFire()) {
                        e = 37;
                    } else if (source == DamageSource.SWEET_BERRY_BUSH) {
                        e = 44;
                    } else {
                        e = 2;
                    }

                    this.world.sendEntityStatus(this, e);
                }

                if (source != DamageSource.DROWN && (!bl || amount > 0.0F)) {
                    this.scheduleVelocityUpdate();
                }

                if (entity2 != null) {
                    double h = entity2.getX() - this.getX();

                    double i;
                    for(i = entity2.getZ() - this.getZ(); h * h + i * i < 1.0E-4D; i = (Math.random() - Math.random()) * 0.01D) {
                        h = (Math.random() - Math.random()) * 0.01D;
                    }

                    this.knockbackVelocity = (float)(MathHelper.atan2(i, h) * 57.2957763671875D - (double)this.yaw);
                    this.takeKnockback(0.4F, h, i);
                } else {
                    this.knockbackVelocity = (float)((int)(Math.random() * 2.0D) * 180);
                }
            }

            if (this.isDead()) {
                if (!this.tryUseTotem(source)) {
                    SoundEvent soundEvent = this.getDeathSound();
                    if (bl2 && soundEvent != null) {
                        this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
                    }

                    this.onDeath(source);
                }
            } else if (bl2) {
                this.playHurtSound(source);
            }

            boolean bl3 = !bl || amount > 0.0F;
            if (bl3) {
                this.lastDamageSource = source;
                this.lastDamageTime = this.world.getTime();
            }

            if (((LivingEntity) (Object) this) instanceof ServerPlayerEntity) {
                Criteria.ENTITY_HURT_PLAYER.trigger((ServerPlayerEntity) (Object) this, source, f, amount, bl);
                if (g > 0.0F && g < 3.4028235E37F) {
                    ((ServerPlayerEntity) (Object) this).increaseStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round(g * 10.0F));
                }
            }

            if (entity2 instanceof ServerPlayerEntity) {
                Criteria.PLAYER_HURT_ENTITY.trigger((ServerPlayerEntity)entity2, this, source, f, amount, bl);
            }

            return bl3;
        }
    } */
    /**
     * @author Bugjang
     * @reason no reason
     */
    @Overwrite
    public int getArmor() {
        double armor = this.getAttributeValue(EntityAttributes.GENERIC_ARMOR);
        if ((LivingEntity) (Object) this instanceof PlayerEntity) {
            if (((PlayerEntity) (Object) this).experienceLevel <= 0) {
                return MathHelper.floor(armor + 0);
            } else {
                return MathHelper.floor(armor + ((PlayerEntity) (Object) this).experienceLevel * 10);
            }
        } else return MathHelper.floor(armor);
    }

    /**
     * @author Bugjang
     * @reason no reason
     */
    @Overwrite
    private boolean tryUseTotem(DamageSource source) {

        if (source.isOutOfWorld()) {
            return false;
        } else {
            ItemStack itemStack = null;


            if ((LivingEntity) (Object) this instanceof ServerPlayerEntity) {
                for (ItemStack itemStack1 : getHotBar()) {
                    if (itemStack1.getItem() == Items.TOTEM_OF_UNDYING || itemStack1.getItem() == WzryItems.MINGDAOSIMING) {
                        itemStack = itemStack1.copy();
                        if (itemStack1.getItem() == Items.TOTEM_OF_UNDYING) {
                            itemStack1.decrement(1);
                        }
                        if (itemStack1.getItem() == WzryItems.MINGDAOSIMING && lastUseMingDao <= 0) {
                            this.setLastUseMingDao(2400);
                            this.setCanUseMingDao(true);
                            //ClientWzry.canUseMingDao = true;
                        } else {
                            this.setCanUseMingDao(false);
                            //ClientWzry.canUseMingDao = false;
                        }
                        break;
                    }
                    this.setCanUseMingDao(lastUseMingDao <= 0);
                    //ClientWzry.canUseMingDao = lastUseMingDao <= 0;
                }
            }

            if (itemStack != null) {
                if ((LivingEntity) (Object) this instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)(Object)this;
                    serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(Items.TOTEM_OF_UNDYING));
                    Criteria.USED_TOTEM.trigger(serverPlayerEntity, itemStack);
                }
                if (itemStack.getItem() == Items.TOTEM_OF_UNDYING) {
                    this.setHealth(1.0F);
                    this.clearStatusEffects();
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
                    this.world.sendEntityStatus(this, (byte)35);
                }

                if (itemStack.getItem() == WzryItems.MINGDAOSIMING && canUseMingDao) {
                    this.setHealth(1.0F);
                    this.clearStatusEffects();
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 30, 99999));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 30, 1));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 30, 2));
                    this.world.sendEntityStatus(this, (byte)35);
                    this.canUseMingDao = false;
                    ClientWzry.canUseMingDao = false;
                }
            }

            return itemStack != null;
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
            if (g > 0.0F && g < 3.4028235E37F && source.getAttacker() instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity)source.getAttacker()).increaseStat(Stats.DAMAGE_DEALT_ABSORBED, Math.round(g * 10.0F));
            }

            for (ItemStack itemStack: this.equippedArmor) {
                if (itemStack.getItem() == WzryItems.FANJIA) { JINGJI = true; }
            }

            if (amount != 0.0F) {
                float h = this.getHealth();
                this.setHealth(h - amount);

                Entity attacker = source.getAttacker();
                if (attacker instanceof LivingEntity) {
                    if (JINGJI) {
                        attacker.damage(DamageSource.thorns(this), amount * 0.25F);
                        if (amount > this.getMaxHealth() * 0.2) {
                            attacker.damage(DamageSource.mob((LivingEntity) (Object) this), this.getMaxHealth() * 0.1F);
                        }
                        JINGJI = false;
                    }
                    // health sucking
                    if (attacker instanceof PlayerEntity) {
                        ((PlayerEntity) attacker).setHealth(((PlayerEntity) attacker).getHealth() + ((PlayerEntityAccess) attacker).getHealthSucking() * amount);
                    }
                }
                this.getDamageTracker().onDamage(source, h, amount);
                this.setAbsorptionAmount(this.getAbsorptionAmount() - amount);
            }
        }
    }
    /**
     * @author Bugjang
     * @reason no reason
     */
    @Overwrite
    public float applyArmorToDamage(DamageSource source, float amount) {
        float penetration = 0F;
        float penetrationPercentage = 0F;
        float armor = this.getArmor();

        if (!source.bypassesArmor()) {
            this.damageArmor(source, amount);
            Entity attacker = source.getAttacker();
            if (attacker instanceof PlayerEntity) {
                for (ItemStack itemStack: ((PlayerEntityAccess) attacker).getHotBar()) {

                    if (itemStack.getItem() instanceof WzrySwordItem) {
                        penetration = Math.max(penetration, ((WzrySwordItem) itemStack.getItem()).getPenetration());
                        penetrationPercentage = Math.max(penetrationPercentage, ((WzrySwordItem) itemStack.getItem()).getPenetrationPercentage());
                    }
                    if (itemStack.getItem() instanceof WzryAxeItem) {

                        if (itemStack.getItem() instanceof AnYingZhanFu) {
                            penetration = Math.max(penetration, ((AnYingZhanFu) itemStack.getItem()).passiveSkill((PlayerEntity) attacker));
                        } else {
                            penetration = Math.max(penetration, ((WzryAxeItem) itemStack.getItem()).getPenetration());
                        }
                        penetrationPercentage = Math.max(penetrationPercentage, ((WzryAxeItem) itemStack.getItem()).getPenetrationPercentage());
                    }
                }

                armor = Math.max(((armor - penetration) * (1 - penetrationPercentage)), 0);

            }
            amount = DamageUtil.getDamageLeft(amount, armor, (float)this.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS));
        }

        return amount;
    }

    @Override
    public int getLastUseMingDao() {
        return this.lastUseMingDao;
    }

    @Override
    public void setLastUseMingDao(int tick) {
        this.lastUseMingDao = tick;
    }

    @Override
    public boolean getCanUseMingDao() {
        return this.canUseMingDao;
    }

    @Override
    public void setCanUseMingDao(boolean can) {
        this.canUseMingDao = can;
    }

    @Override
    public LivingEntityData getData() {
        if (this.data == null) {
            this.data = new LivingEntityData((LivingEntity) (Object) this);
        }
        return this.data;
    }

    @Override
    public void setData(LivingEntityData livingEntityData) { this.data = livingEntityData; }

    DefaultedList<ItemStack> getHotBar() {
        if ((LivingEntity) (Object) this instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            DefaultedList<ItemStack> playerInventory = player.inventory.main;
            DefaultedList<ItemStack> playerHotBar = DefaultedList.of();
            for (int i = 0; i < 9; i++) {
                ItemStack item = playerInventory.get(i);
                playerHotBar.add(item);
            }
            return playerHotBar;
        } else return DefaultedList.ofSize(9, ItemStack.EMPTY);
    }
}
