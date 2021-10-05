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

    public static final ItemGroup DEFEND_GROUP = FabricItemGroupBuilder.create(
            new Identifier("wzry", "defend_item_group"))
            .icon(() -> new ItemStack(WzryItems.WUJINZHANREN))
            .appendItems(itemStacks -> {

            })
            .build();

    // COMMON
    public static final Item TIEJIAN = new TieJian();
    public static final Item LEIMINGREN = new LeiMingRen();
    public static final Item BISHOU = new BiShou();
    public static final Item XIXUEZHILIAN = new XiXueZhiLian();
    public static final Item BOJIQUANTAO = new BoJiQuanTao();

    // UNCOMMON
    public static final Item FENGBAOJUJIAN = new FengBaoJuJian();
    public static final Item SUJIZHIQIANG = new SuJiZhiQiang();
    public static final Item YUNXING = new YunXing();
    public static final Item RIMIAN = new RiMian();

    // RARE
    public static final Item MOSHI = new MoShi();
    public static final Item QIXUEZHIREN = new QiXueZhiRen();
    public static final Item WUJINZHANREN = new WuJinZhanRen();
    public static final Item ANYINGZHANFU = new AnYingZhanFu();
    public static final Item POJUN = new PoJun();
    public static final Item MINGDAOSIMING = new MingDaoSiMing();
    public static final Item POSUIMINGDAO = new Item(new Item.Settings());


    // COMMON
    public static final Item HONGMANAO = new HongMaNao();
    public static final Item BUJIA = new BuJia();
    public static final Item BUHUTUI = new BuHuTui();
    public static final Item BUTOUKUI = new BuTouKui();
    public static final Item BUXUE = new BuXue();
    // UNCOMMON
    public static final Item DIKANGZHIXUE = new DiKangZhiXue();
    public static final Item BAOLIZHIXUE = new BaoLiZhiXue();
    public static final Item LILIANGYAODAI = new LiLiangYaoDai();
    public static final Item SHOUHUZHEZHIKAI = new ShouHuZheZhiKai();
    // RARE
    public static final Item BUXIANGZHENGZHAO = new BuXiangZhengZhao();
    public static final Item FANJIA = new FanShangCiJia();
}
