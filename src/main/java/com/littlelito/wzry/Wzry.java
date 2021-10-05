package com.littlelito.wzry;

import com.littlelito.wzry.block.WzryBlockEntityTypes;
import com.littlelito.wzry.register.*;
import net.fabricmc.api.ModInitializer;

public class Wzry implements ModInitializer {
    @Override
    public void onInitialize() {
        System.out.println("Hello, Wzry World!");
        new ItemRegister().register();
        new EventRegister().register();
        new EffectRegister().register();
        new CommandRegister().register();
        new BlockRegister().register();

        new WzryBlockEntityTypes().register();
    }
}
