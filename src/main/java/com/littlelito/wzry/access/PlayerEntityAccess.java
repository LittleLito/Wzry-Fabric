package com.littlelito.wzry.access;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface PlayerEntityAccess {
    DefaultedList<ItemStack> getHotBar();

    float getHealthSucking();
    void setHealthSucking(float value);

    boolean getIsBlue();
    void setIsBlue(boolean value);
}
