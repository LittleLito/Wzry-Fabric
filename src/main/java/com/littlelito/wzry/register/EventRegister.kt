package com.littlelito.wzry.register

import com.littlelito.wzry.access.LivingEntityAccess
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
import net.minecraft.util.Identifier

class EventRegister {
    private val HUSK_LOOT_TABLE_ID: Identifier = EntityType.HUSK.lootTableId
    private val CREEPER_LOOT_TABLE_ID: Identifier = EntityType.CREEPER.lootTableId

    fun register() {
        ServerPlayerEvents.COPY_FROM.register(CopyFrom { oldPlayer: ServerPlayerEntity, newPlayer: ServerPlayerEntity, alive: Boolean ->
            (newPlayer as LivingEntityAccess).lastUseMingDao = (oldPlayer as LivingEntityAccess).lastUseMingDao
            (newPlayer as LivingEntityAccess).canUseMingDao = (oldPlayer as LivingEntityAccess).canUseMingDao
            ClientWzry.canUseMingDao = (newPlayer as LivingEntityAccess).canUseMingDao
        })

        // drop loot callbacks
        LootTableLoadingCallback.EVENT.register {
                resourceManager, lootManager, id, table, setter ->
            when (id) {
                CREEPER_LOOT_TABLE_ID -> {
                    val poolBuider: FabricLootPoolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(2))
                        .with(ItemEntry.builder(Items.IRON_INGOT).weight(15))
                        .with(ItemEntry.builder(Items.REDSTONE).weight(8))
                        .with(ItemEntry.builder(Items.DIAMOND).weight(1))
                    table.pool(poolBuider)
                }
                HUSK_LOOT_TABLE_ID -> {
                    val poolBuider: FabricLootPoolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(2))
                        .with(ItemEntry.builder(Items.IRON_INGOT).weight(15))
                        .with(ItemEntry.builder(Items.LAPIS_LAZULI).weight(5))
                        .with(ItemEntry.builder(Items.DIAMOND).weight(1))
                    table.pool(poolBuider)
                }
            }
        }
    }
}