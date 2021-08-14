package com.littlelito.wzry;

import com.littlelito.wzry.register.Register;
import net.fabricmc.api.ModInitializer;

public class Wzry implements ModInitializer {
    @Override
    public void onInitialize() {
        System.out.println("Hello, Wzry World!");
        new Register().register();
    }
}
