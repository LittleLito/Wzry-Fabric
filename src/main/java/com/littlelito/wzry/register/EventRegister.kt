package com.littlelito.wzry.register

import com.littlelito.wzry.access.LivingEntityAccess
import com.littlelito.wzry.access.PlayerEntityAccess
import com.littlelito.wzry.client.ClientWzry
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents.CopyFrom
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.loot.ConstantLootTableRange
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.MathHelper

class EventRegister {
    private val PLAYER_LOOT_TABLE_ID = EntityType.PLAYER.lootTableId
    private val HUSK_LOOT_TABLE_ID = EntityType.HUSK.lootTableId
    private val ZOMBIE_LOOT_TABLE_ID = EntityType.ZOMBIE.lootTableId
    private val CREEPER_LOOT_TABLE_ID = EntityType.CREEPER.lootTableId
    private val SKELETON_LOOT_TABLE_ID = EntityType.SKELETON.lootTableId


    fun register() {
        ServerPlayerEvents.COPY_FROM.register(CopyFrom { oldPlayer: ServerPlayerEntity, newPlayer: ServerPlayerEntity, alive: Boolean ->
            (newPlayer as LivingEntityAccess).lastUseMingDao = (oldPlayer as LivingEntityAccess).lastUseMingDao
            (newPlayer as LivingEntityAccess).canUseMingDao = (oldPlayer as LivingEntityAccess).canUseMingDao
            (newPlayer as PlayerEntityAccess).isBlue = (oldPlayer as PlayerEntityAccess).isBlue
            newPlayer.data = oldPlayer.data
            ClientWzry.canUseMingDao = (newPlayer as LivingEntityAccess).canUseMingDao
        })

        // drop loot callbacks
        LootTableLoadingCallback.EVENT.register {
                resourceManager, lootManager, id, table, setter ->
            when (id) {
                PLAYER_LOOT_TABLE_ID -> {
                    val poolBuider: FabricLootPoolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(MathHelper.floor(Math.random() * 3) + 4))
                        .with(ItemEntry.builder(Items.IRON_INGOT).weight(20))
                        .with(ItemEntry.builder(Items.EMERALD).weight(15))
                        .with(ItemEntry.builder(Items.REDSTONE).weight(10))
                        .with(ItemEntry.builder(Items.LAPIS_LAZULI).weight(10))
                        .with(ItemEntry.builder(Items.GUNPOWDER).weight(10))
                        .with(ItemEntry.builder(Items.LEATHER).weight(10))
                        .with(ItemEntry.builder(Items.DIAMOND).weight(8))
                    table.pool(poolBuider)
                }
                CREEPER_LOOT_TABLE_ID -> {
                    val poolBuider: FabricLootPoolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(2))
                        .with(ItemEntry.builder(Items.DIRT).weight(20))
                        .with(ItemEntry.builder(Items.IRON_INGOT).weight(15))
                        .with(ItemEntry.builder(Items.GUNPOWDER).weight(8))
                        .with(ItemEntry.builder(Items.DIAMOND).weight(1))
                    table.pool(poolBuider)
                }
                SKELETON_LOOT_TABLE_ID -> {
                    val poolBuider: FabricLootPoolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(2))
                        .with(ItemEntry.builder(Items.DIRT).weight(20))
                        .with(ItemEntry.builder(Items.IRON_INGOT).weight(15))
                        .with(ItemEntry.builder(Items.COAL).weight(8))
                        .with(ItemEntry.builder(Items.EMERALD).weight(20))
                        .with(ItemEntry.builder(Items.DIAMOND).weight(1))
                    table.pool(poolBuider)
                }
                HUSK_LOOT_TABLE_ID -> {
                    val poolBuider: FabricLootPoolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(2))
                        .with(ItemEntry.builder(Items.DIRT).weight(20))
                        .with(ItemEntry.builder(Items.IRON_INGOT).weight(15))
                        .with(ItemEntry.builder(Items.LAPIS_LAZULI).weight(5))
                        .with(ItemEntry.builder(Items.REDSTONE).weight(5))
                        .with(ItemEntry.builder(Items.LEATHER).weight(15))
                        .with(ItemEntry.builder(Items.DIAMOND).weight(1))
                        .with(ItemEntry.builder(Items.COOKED_BEEF).weight(8))
                    table.pool(poolBuider)
                }
                ZOMBIE_LOOT_TABLE_ID -> {
                    val poolBuider: FabricLootPoolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(2))
                        .with(ItemEntry.builder(Items.DIRT).weight(20))
                        .with(ItemEntry.builder(Items.IRON_INGOT).weight(15))
                        .with(ItemEntry.builder(Items.LAPIS_LAZULI).weight(5))
                        .with(ItemEntry.builder(Items.REDSTONE).weight(5))
                        .with(ItemEntry.builder(Items.LEATHER).weight(15))
                        .with(ItemEntry.builder(Items.DIAMOND).weight(1))
                        .with(ItemEntry.builder(Items.COOKED_BEEF).weight(8))
                    table.pool(poolBuider)
                }
            }
        }
    }
}