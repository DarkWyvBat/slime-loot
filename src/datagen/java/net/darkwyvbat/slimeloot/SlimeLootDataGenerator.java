package net.darkwyvbat.slimeloot;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.SlimePredicate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class SlimeLootDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider((output, registriesFuture) -> new SimpleFabricLootTableSubProvider(output, registriesFuture, LootContextParamSets.ENTITY) {
                    @Override
                    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
                        LootItemConditionalFunction.Builder<?> damage = SetItemDamageFunction.setDamage(UniformGenerator.between(0.05F, 0.1F));
                        //region generic
                        output.accept(
                                SlimeLoot.GENERIC_LOOT,
                                LootTable.lootTable()
                                        .withPool(
                                                LootPool.lootPool()
                                                        .add(LootItem.lootTableItem(Items.STICK).setWeight(20))
                                                        .add(LootItem.lootTableItem(Items.BONE).setWeight(20))
                                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(20))
                                                        .add(LootItem.lootTableItem(Items.RABBIT_FOOT).setWeight(20))
                                                        .add(LootItem.lootTableItem(Items.ARROW).setWeight(20))
                                                        .add(LootItem.lootTableItem(Items.LEATHER).setWeight(15))
                                                        .add(LootItem.lootTableItem(Items.FEATHER).setWeight(15))
                                                        .add(LootItem.lootTableItem(Items.COBBLESTONE).setWeight(15))
                                                        .add(LootItem.lootTableItem(Items.DIRT).setWeight(15))
                                                        .add(LootItem.lootTableItem(Items.SAND).setWeight(15))
                                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(15))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.WHEAT_SEEDS).setWeight(15))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.CARROT_CROPS).setWeight(15))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.POTATO_CROPS).setWeight(15))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.BEETROOT_CROPS).setWeight(15))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.COPPER_NUGGETS).setWeight(15))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.IRON_NUGGETS).setWeight(15))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.GOLD_NUGGETS).setWeight(15))
                                                        .add(LootItem.lootTableItem(Items.SWEET_BERRIES).setWeight(10))
                                                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(10))
                                                        .add(LootItem.lootTableItem(Items.DANDELION).setWeight(10))
                                                        .add(LootItem.lootTableItem(Items.OXEYE_DAISY).setWeight(10))
                                                        .add(LootItem.lootTableItem(Items.EGG).setWeight(10))
                                                        .add(LootItem.lootTableItem(Items.BROWN_EGG).setWeight(10))
                                                        .add(LootItem.lootTableItem(Items.HONEYCOMB).setWeight(10))
                                                        .add(LootItem.lootTableItem(Items.FLINT).setWeight(10))
                                                        .add(LootItem.lootTableItem(Items.GLOW_BERRIES).setWeight(5))
                                                        .add(LootItem.lootTableItem(Items.LILY_PAD).setWeight(5))
                                                        .add(LootItem.lootTableItem(Items.BROWN_MUSHROOM).setWeight(5))
                                                        .add(LootItem.lootTableItem(Items.LEATHER_BOOTS).setWeight(5).apply(damage))
                                                        .add(LootItem.lootTableItem(Items.STONE_SWORD).setWeight(5).apply(damage))
                                                        .add(LootItem.lootTableItem(Items.IRON_ORE).setWeight(5))
                                                        .add(LootItem.lootTableItem(Items.RAW_IRON).setWeight(5))
                                                        .add(LootItem.lootTableItem(Items.RAW_GOLD).setWeight(5))
                                                        .add(LootItem.lootTableItem(Items.NAME_TAG))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.EMERALD_GEMS))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.DIAMOND_GEMS))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.COPPER_INGOTS))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.IRON_INGOTS))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.GOLD_INGOTS))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.RESIN_CLUMPS))
                                                        .add(LootItem.lootTableItem(Items.SADDLE))
                                                        .add(LootItem.lootTableItem(Items.CAKE))
                                                        .add(LootItem.lootTableItem(Items.PUMPKIN))
                                                        .add(LootItem.lootTableItem(Items.IRON_HOE).apply(damage))

                                        )
                        );
                        //endregion
                        output.accept(
                                SlimeLoot.BIG_SLIME_LOOT,
                                LootTable.lootTable()
                                        .withPool(
                                                LootPool.lootPool()
                                                        .when(LootItemRandomChanceCondition.randomChance(0.2F))
                                                        .add(NestedLootTable.lootTableReference(SlimeLoot.GENERIC_LOOT)).setRolls(UniformGenerator.between(1.0F, 2.0F))
                                        )
                                        .withPool(
                                                LootPool.lootPool()
                                                        .add(NestedLootTable.inlineLootTable(
                                                                LootTable.lootTable()
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.FISHING_ROD).apply(damage)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.LEATHER_BOOTS).apply(damage)))
                                                                        .withPool(LootPool.lootPool().add(TagEntry.expandTag(ItemTags.FISHES)).setRolls(UniformGenerator.between(0.0F, 3.0F)))
                                                                        .withPool(LootPool.lootPool().add(TagEntry.expandTag(ItemTags.BOATS)).when(LootItemRandomChanceCondition.randomChance(0.3F)))
                                                                        .build()
                                                        ))
                                                        .add(NestedLootTable.inlineLootTable(
                                                                LootTable.lootTable()
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.IRON_HELMET).apply(damage)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).apply(damage)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.IRON_LEGGINGS).apply(damage)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.IRON_BOOTS).apply(damage)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.IRON_SWORD).apply(damage)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.ARROW)).setRolls(UniformGenerator.between(0.0F, 4.0F)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.BONE)))
                                                                        .build()
                                                        ))
                                                        .add(NestedLootTable.inlineLootTable(
                                                                LootTable.lootTable()
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.BOW).apply(damage)))
                                                                        .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 6.0F)).add(LootItem.lootTableItem(Items.ARROW)))
                                                                        .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F)).add(LootItem.lootTableItem(Items.BONE)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.LEATHER_HELMET).apply(damage)).when(LootItemRandomChanceCondition.randomChance(0.4F)))
                                                                        .build()
                                                        ))
                                                        .add(NestedLootTable.inlineLootTable(
                                                                LootTable.lootTable()
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.IRON_PICKAXE).apply(damage)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.TORCH)).setRolls(UniformGenerator.between(0.0F, 3.0F)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.COAL)).setRolls(UniformGenerator.between(0.0F, 3.0F)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.IRON_INGOT)).setRolls(UniformGenerator.between(0.0F, 2.0F)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.RAW_IRON)).setRolls(UniformGenerator.between(1.0F, 3.0F)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.BREAD)).when(LootItemRandomChanceCondition.randomChance(0.5F)))
                                                                        .build()
                                                        ))
                                                        .add(NestedLootTable.inlineLootTable(
                                                                LootTable.lootTable()
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.LEAD)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.LEATHER)).setRolls(UniformGenerator.between(0.0F, 1.0F)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.MELON_SLICE)).setRolls(UniformGenerator.between(1.0F, 3.0F)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.MELON_SEEDS)).setRolls(UniformGenerator.between(1.0F, 2.0F)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.MELON)).when(LootItemRandomChanceCondition.randomChance(0.3F)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.GLASS_BOTTLE)))
                                                                        .withPool(LootPool.lootPool().add(TagEntry.expandTag(ItemTags.WOOL_CARPETS)).setRolls(UniformGenerator.between(0.0F, 1.0F)))
                                                                        .withPool(LootPool.lootPool().add(TagEntry.expandTag(ConventionalItemTags.EMERALD_GEMS)).setRolls(UniformGenerator.between(0.0F, 1.0F)))
                                                                        .build()
                                                        ))
                                                        .add(NestedLootTable.inlineLootTable(
                                                                LootTable.lootTable()
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.BREAD)).setRolls(UniformGenerator.between(1.0F, 3.0F)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.LEATHER_BOOTS).apply(damage)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.LEATHER_LEGGINGS).apply(damage)))
                                                                        .withPool(LootPool.lootPool().add(TagEntry.expandTag(ConventionalItemTags.EMERALD_GEMS)).setRolls(UniformGenerator.between(0.0F, 3.0F)))
                                                                        .build()
                                                        ))
                                                        .add(NestedLootTable.inlineLootTable(
                                                                LootTable.lootTable()
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.COMPASS)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.LEATHER_BOOTS).apply(damage)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.PAPER)))
                                                                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.MAP).when(LootItemRandomChanceCondition.randomChance(0.2F))))
                                                                        .build()
                                                        ))
                                        )
                        );
                        output.accept(
                                SlimeLoot.MEDIUM_SLIME_LOOT,
                                LootTable.lootTable().withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(SlimeLoot.GENERIC_LOOT)))
                        );
                        output.accept(
                                SlimeLoot.SMALL_SLIME_LOOT,
                                LootTable.lootTable()
                                        .withPool(
                                                LootPool.lootPool()
                                                        .add(LootItem.lootTableItem(Items.STICK).setWeight(8))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.WHEAT_SEEDS).setWeight(5))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.CARROT_CROPS).setWeight(5))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.POTATO_CROPS).setWeight(5))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.BEETROOT_CROPS).setWeight(5))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.COPPER_NUGGETS).setWeight(5))
                                                        .add(LootItem.lootTableItem(Items.SWEET_BERRIES).setWeight(4))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.IRON_NUGGETS).setWeight(4))
                                                        .add(TagEntry.expandTag(ConventionalItemTags.GOLD_NUGGETS).setWeight(3))
                                                        .add(LootItem.lootTableItem(Items.FLINT).setWeight(3))
                                        )
                        );
                        output.accept(
                                SlimeLoot.SLIME_LOOT,
                                LootTable.lootTable()
                                        .withPool(
                                                LootPool.lootPool()
                                                        .when(LootItemRandomChanceCondition.randomChance(0.1F))
                                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(SlimePredicate.sized(MinMaxBounds.Ints.atMost(1)))))
                                                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                                                        .add(NestedLootTable.lootTableReference(SlimeLoot.SMALL_SLIME_LOOT))
                                        )
                                        .withPool(
                                                LootPool.lootPool()
                                                        .when(LootItemRandomChanceCondition.randomChance(0.2F))
                                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(SlimePredicate.sized(MinMaxBounds.Ints.between(2, 3)))))
                                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                                        .add(NestedLootTable.lootTableReference(SlimeLoot.GENERIC_LOOT))
                                        )
                                        .withPool(
                                                LootPool.lootPool()
                                                        .when(LootItemRandomChanceCondition.randomChance(0.3F))
                                                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(SlimePredicate.sized(MinMaxBounds.Ints.atLeast(4)))))
                                                        .add(NestedLootTable.inlineLootTable(LootTable.lootTable()
                                                                        .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 5.0F)).add(NestedLootTable.lootTableReference(SlimeLoot.GENERIC_LOOT))).build())
                                                                .setWeight(4))
                                                        .add(NestedLootTable.lootTableReference(SlimeLoot.BIG_SLIME_LOOT).setWeight(1))
                                        )
                        );
                    }
                }
        );
    }
}