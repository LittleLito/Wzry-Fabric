package com.littlelito.wzry.mixin;

import com.littlelito.wzry.access.LivingEntityAccess;
import com.littlelito.wzry.client.ClientWzry;
import com.littlelito.wzry.item.WzryItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityAccess {
    public int lastUseMingDao;
    public boolean canUseMingDao;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }



    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract boolean clearStatusEffects();

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo info) {
        if ((LivingEntity) (Object) this instanceof ServerPlayerEntity){
            lastUseMingDao -= 1;
            canUseMingDao = lastUseMingDao <= 0;
            ClientWzry.canUseMingDao = lastUseMingDao <= 0;
        }
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
