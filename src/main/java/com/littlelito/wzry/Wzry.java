package com.littlelito.wzry;

import com.littlelito.wzry.register.EventRegister;
import com.littlelito.wzry.register.ItemRegister;
import net.fabricmc.api.ModInitializer;

public class Wzry implements ModInitializer {
    @Override
    public void onInitialize() {
        System.out.println("Hello, Wzry World!");
        new ItemRegister().register();
        new EventRegister().register();
    }
}
