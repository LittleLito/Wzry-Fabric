package com.littlelito.wzry.client;

import com.littlelito.wzry.item.WzryItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;

public class ClientWzry implements ClientModInitializer {
    public static boolean canUseMingDao = true;

    @Override
    public void onInitializeClient() {
        FabricModelPredicateProviderRegistry.register(
                WzryItems.MINGDAOSIMING,
                new Identifier("active"),
                (itemStack, clientWorld, livingEntity) -> {
                    if (livingEntity == null) {
                        return 1.0F;
                    }
                    if (livingEntity instanceof ClientPlayerEntity){
                        return ClientWzry.canUseMingDao ? 1.0F : 0.0F;
                    } else return 0.0F;
                });
    }
}
