package net.darkwyvbat.slimeloot;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityDataRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;

public class SlimeLoot implements ModInitializer {
    public static final String MOD_ID = "slime-loot";
    public static final EntityDataSerializer<List<ItemStack>> SLIME_ITEMS_LIST = EntityDataSerializer.forValueType(ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()));
    public static final ResourceKey<LootTable> GENERIC_LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(MOD_ID, "generic_loot"));
    public static final ResourceKey<LootTable> BIG_SLIME_LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(MOD_ID, "big_slime_loot"));
    public static final ResourceKey<LootTable> MEDIUM_SLIME_LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(MOD_ID, "medium_slime_loot"));
    public static final ResourceKey<LootTable> SMALL_SLIME_LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(MOD_ID, "small_slime_loot"));
    public static final ResourceKey<LootTable> SLIME_LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(MOD_ID, "slime_loot"));

    @Override
    public void onInitialize() {
        FabricEntityDataRegistry.register(Identifier.fromNamespaceAndPath(MOD_ID, "slime_items_list"), SLIME_ITEMS_LIST);
    }
}