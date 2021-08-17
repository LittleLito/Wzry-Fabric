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
                itemStacks.add(new ItemStack(WzryItems.FENGBAOJUJIAN));
                itemStacks.add(new ItemStack(WzryItems.WUJINZHANREN));
                itemStacks.add(new ItemStack(WzryItems.POJUN));
            })
            .build();

    // COMMON
    public static final Item TIEJIAN = new TieJian();
    public static final Item LEIMINGREN = new LeiMingRen();
    public static final Item BISHOU = new BiShou();

    // UNCOMMON
    public static final Item FENGBAOJUJIAN = new FengBaoJuJian();
    public static final Item SUJIZHIQIANG = new SuJiZhiQiang();

    // RARE
    public static final Item WUJINZHANREN = new WuJinZhanRen();
    public static final Item POJUN = new PoJun();
    public static final Item MINGDAOSIMING = new MingDaoSiMing();
    public static final Item POSUIMINGDAO = new Item(new Item.Settings());
}
