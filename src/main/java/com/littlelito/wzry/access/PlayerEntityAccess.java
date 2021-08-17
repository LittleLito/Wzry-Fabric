package com.littlelito.wzry.access;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface PlayerEntityAccess {
    DefaultedList<ItemStack> getHotBar();
}
