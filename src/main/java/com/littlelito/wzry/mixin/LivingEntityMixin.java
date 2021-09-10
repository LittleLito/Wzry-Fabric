package com.littlelito.wzry.mixin;

import com.littlelito.wzry.access.LivingEntityAccess;
import com.littlelito.wzry.access.PlayerEntityAccess;
import com.littlelito.wzry.client.ClientWzry;
import com.littlelito.wzry.data.LivingEntityData;
import com.littlelito.wzry.item.*;
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
import net.minecraft.stat.Stats;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

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

    @Shadow public abstract boolean canHaveStatusEffect(StatusEffectInstance effect);

    @Shadow @Final private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

    @Shadow protected abstract void onStatusEffectApplied(StatusEffectInstance effect);

    @Shadow protected abstract void onStatusEffectUpgraded(StatusEffectInstance effect, boolean reapplyEffect);

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
     * @reason no reason
     */
    @Overwrite
    public boolean addStatusEffect(StatusEffectInstance effect) {
        StatusEffectInstance sEI = effect;
        if (!this.canHaveStatusEffect(effect)) {
            return false;
        } else {
            if (this.getEquippedStack(EquipmentSlot.FEET).getItem() instanceof DiKangZhiXue && !effect.getEffectType().isBeneficial()) {
                sEI = new StatusEffectInstance(effect.getEffectType(), (int) Math.floor(effect.getDuration() * 0.65), effect.getAmplifier(), effect.isAmbient(), effect.shouldShowParticles(), effect.shouldShowIcon());
            }
            StatusEffectInstance statusEffectInstance = (StatusEffectInstance)this.activeStatusEffects.get(effect.getEffectType());
            if (statusEffectInstance == null) {
                this.activeStatusEffects.put(sEI.getEffectType(), sEI);
                this.onStatusEffectApplied(sEI);
                return true;
            } else if (statusEffectInstance.upgrade(sEI)) {
                this.onStatusEffectUpgraded(statusEffectInstance, true);
                return true;
            } else {
                return false;
            }
        }
    }
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
