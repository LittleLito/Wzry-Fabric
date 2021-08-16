package com.littlelito.wzry;

import com.littlelito.wzry.access.LivingEntityAccess;
import com.littlelito.wzry.client.ClientWzry;
import com.littlelito.wzry.register.Register;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class Wzry implements ModInitializer {
    @Override
    public void onInitialize() {
        System.out.println("Hello, Wzry World!");
        new Register().register();
        ServerPlayerEvents.COPY_FROM.register((ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) -> {
            ((LivingEntityAccess) newPlayer).setLastUseMingDao(((LivingEntityAccess) oldPlayer).getLastUseMingDao());
            ((LivingEntityAccess) newPlayer).setCanUseMingDao(((LivingEntityAccess) oldPlayer).getCanUseMingDao());
            ClientWzry.canUseMingDao = ((LivingEntityAccess) newPlayer).getCanUseMingDao();
        });
    }
}
