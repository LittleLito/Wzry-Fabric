package com.littlelito.wzry.mixin;

import com.google.common.collect.Lists;
import com.littlelito.wzry.access.PlayerEntityAccess;
import com.littlelito.wzry.item.SuJiZhiQiang;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;
import java.util.List;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends Entity {

    @Shadow public PersistentProjectileEntity.PickupPermission pickupType;

    @Shadow private double damage;

    @Shadow private SoundEvent sound;

    public PersistentProjectileEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow protected abstract SoundEvent getHitSound();

    /*protected PersistentProjectileEntityMixin(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world) {
        this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
        this.setOwner(owner);
        if (owner instanceof PlayerEntity) {
            this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        }
    }

    public PersistentProjectileEntityMixin(EntityType<? extends PersistentProjectileEntity> type, double x, double y, double z, World world) {
        this(type, world);
        this.setPosition(x, y, z);
    }

    public PersistentProjectileEntityMixin(EntityType<? extends PersistentProjectileEntity> type, World world) {
        super(type, world);
        this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
        this.damage = 2.0D;
        this.sound = this.getHitSound();
    }*/

    @Shadow public abstract byte getPierceLevel();

    @Shadow private IntOpenHashSet piercedEntities;

    @Shadow private List<Entity> piercingKilledEntities;

    @Shadow public abstract boolean isCritical();

    @Shadow private int punch;

    @Shadow protected abstract void onHit(LivingEntity target);

    @Shadow public abstract boolean isShotFromCrossbow();

    @Shadow protected abstract ItemStack asItemStack();

    @Shadow public abstract void setOwner(@Nullable Entity entity);

    /**
     * @author Bugjang
     */
    @Overwrite
    public void onEntityHit(EntityHitResult entityHitResult) {
        boolean JINGZHUN = false;

        Entity entity = entityHitResult.getEntity();
        Entity entity2 = ((PersistentProjectileEntity) (Object) this).getOwner();

        float f = (float)this.getVelocity().length();

        if (entity2 instanceof PlayerEntity) {
            DefaultedList<ItemStack> hotBar = ((PlayerEntityAccess) entity2).getHotBar();

            for (ItemStack itemStack: hotBar) {
                if (itemStack.getItem() instanceof SuJiZhiQiang && !JINGZHUN) {
                    this.damage = ((SuJiZhiQiang) itemStack.getItem()).passiveSkill((float) this.damage, (PlayerEntity) entity2);
                    JINGZHUN = true;
                }
            }


        }
        int i = MathHelper.ceil(MathHelper.clamp((double)f * this.damage, 0.0D, 2.147483647E9D));
        if (this.getPierceLevel() > 0) {
            if (this.piercedEntities == null) {
                this.piercedEntities = new IntOpenHashSet(5);
            }

            if (this.piercingKilledEntities == null) {
                this.piercingKilledEntities = Lists.newArrayListWithCapacity(5);
            }

            if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
                this.remove();
                return;
            }

            this.piercedEntities.add(entity.getEntityId());
        }

        if (this.isCritical()) {
            long l = (long)this.random.nextInt(i / 2 + 2);
            i = (int)Math.min(l + (long)i, 2147483647L);
        }

        entity2 = ((PersistentProjectileEntity) (Object) this).getOwner();
        DamageSource damageSource2;
        if (entity2 == null) {
            damageSource2 = DamageSource.arrow((PersistentProjectileEntity) (Object) this, this);
        } else {
            damageSource2 = DamageSource.arrow((PersistentProjectileEntity) (Object) this, entity2);
            if (entity2 instanceof LivingEntity) {
                ((LivingEntity)entity2).onAttacking(entity);
            }
        }

        boolean bl = entity.getType() == EntityType.ENDERMAN;
        int j = entity.getFireTicks();
        if (this.isOnFire() && !bl) {
            entity.setOnFireFor(5);
        }

        if (entity.damage(damageSource2, (float)i)) {
            if (bl) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                if (!this.world.isClient && this.getPierceLevel() <= 0) {
                    livingEntity.setStuckArrowCount(livingEntity.getStuckArrowCount() + 1);
                }

                if (this.punch > 0) {
                    Vec3d vec3d = this.getVelocity().multiply(1.0D, 0.0D, 1.0D).normalize().multiply((double)this.punch * 0.6D);
                    if (vec3d.lengthSquared() > 0.0D) {
                        livingEntity.addVelocity(vec3d.x, 0.1D, vec3d.z);
                    }
                }

                if (!this.world.isClient && entity2 instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity, entity2);
                    EnchantmentHelper.onTargetDamaged((LivingEntity)entity2, livingEntity);
                }

                this.onHit(livingEntity);
                if (entity2 != null && livingEntity != entity2 && livingEntity instanceof PlayerEntity && entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity2).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
                }

                if (!entity.isAlive() && this.piercingKilledEntities != null) {
                    this.piercingKilledEntities.add(livingEntity);
                }

                if (!this.world.isClient && entity2 instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity2;
                    if (this.piercingKilledEntities != null && this.isShotFromCrossbow()) {
                        Criteria.KILLED_BY_CROSSBOW.trigger(serverPlayerEntity, this.piercingKilledEntities);
                    } else if (!entity.isAlive() && this.isShotFromCrossbow()) {
                        Criteria.KILLED_BY_CROSSBOW.trigger(serverPlayerEntity, Arrays.asList(entity));
                    }
                }
            }

            this.playSound(this.sound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.remove();
            }
        } else {
            entity.setFireTicks(j);
            this.setVelocity(this.getVelocity().multiply(-0.1D));
            this.yaw += 180.0F;
            this.prevYaw += 180.0F;
            if (!this.world.isClient && this.getVelocity().lengthSquared() < 1.0E-7D) {
                if (this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1F);
                }

                this.remove();
            }
        }

    }
}
