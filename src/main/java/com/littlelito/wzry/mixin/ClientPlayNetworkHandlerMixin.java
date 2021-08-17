package com.littlelito.wzry.mixin;

import com.littlelito.wzry.item.WzryItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.sound.GuardianAttackSoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow private MinecraftClient client;

    @Shadow private ClientWorld world;

    /**
     * @author Bugjang
     * @reason no reason
     */
    @Overwrite
    private static ItemStack getActiveTotemOfUndying(PlayerEntity player) {
        DefaultedList<ItemStack> playerHotBar = DefaultedList.of();

        for (int i=0;i<9;i++) {
            ItemStack item = player.inventory.main.get(i);
            playerHotBar.add(item);
        }
        for (ItemStack itemStack: playerHotBar) {
            if (itemStack.getItem() == Items.TOTEM_OF_UNDYING || itemStack.getItem() == WzryItems.MINGDAOSIMING) {
                return itemStack.getItem() == Items.TOTEM_OF_UNDYING ? itemStack : new ItemStack(WzryItems.POSUIMINGDAO);
            }
        }

        return new ItemStack(Items.TOTEM_OF_UNDYING);
    }

    /**
     * @author Bugjang
     * @reason no reason
     */
    @Overwrite
    public void onEntityStatus(EntityStatusS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, (ClientPlayNetworkHandler) (Object) this, this.client);
        Entity entity = packet.getEntity(this.world);
        if (entity != null) {
            if (packet.getStatus() == 21) {
                this.client.getSoundManager().play(new GuardianAttackSoundInstance((GuardianEntity)entity));
            } else if (packet.getStatus() == 35) {
                this.client.particleManager.addEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
                this.world.playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLOCK_GLASS_BREAK, entity.getSoundCategory(), 3.0F, 1.0F, false);
                if (entity == this.client.player) {
                    this.client.gameRenderer.showFloatingItem(getActiveTotemOfUndying(this.client.player));
                }
            } else {
                entity.handleStatus(packet.getStatus());
            }
        }

    }
}
