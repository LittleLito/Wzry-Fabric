package com.littlelito.wzry.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class WzryItems {

    public static final ItemGroup ATTACK_GROUP = FabricItemGroupBuilder.create(
            new Identifier("wzry", "attack_item_group"))
            .icon(() -> new ItemStack(WzryItems.WUJINZHANREN))
            .appendItems(itemStacks -> {
                itemStacks.add(new ItemStack(WzryItems.TIEJIAN));
                itemStacks.add(new ItemStack(WzryItems.WUJINZHANREN));
                itemStacks.add(new ItemStack(WzryItems.POJUN));
            })
            .build();

    // COMMON
    public static final Item TIEJIAN = new TieJian();

    // RARE
    public static final Item WUJINZHANREN = new WuJinZhanRen();
    public static final Item POJUN = new PoJun();
}
