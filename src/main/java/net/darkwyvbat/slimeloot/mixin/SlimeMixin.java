package net.darkwyvbat.slimeloot.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.darkwyvbat.slimeloot.SlimeItemsHolder;
import net.darkwyvbat.slimeloot.SlimeLoot;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(Slime.class)
public abstract class SlimeMixin extends Mob implements SlimeItemsHolder {

    @Unique
    private static final EntityDataAccessor<List<ItemStack>> DATA_ITEMS = SynchedEntityData.defineId(Slime.class, SlimeLoot.SLIME_ITEMS_LIST);

    protected SlimeMixin(EntityType<? extends Mob> type, Level level) {
        super(type, level);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void slimeloot_defineSynchedData(SynchedEntityData.Builder entityData, CallbackInfo ci) {
        entityData.define(DATA_ITEMS, List.of());
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void slimeloot_addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
        List<ItemStack> items = slimeloot_getItems();
        if (items != null && !items.isEmpty())
            output.store("slime_items", ItemStack.CODEC.listOf(), items);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void slimeloot_readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
        input.read("slime_items", ItemStack.CODEC.listOf()).ifPresent(l -> slimeloot_setItems(new ArrayList<>(l)));
    }

    @Override
    public void slimeloot_setItems(List<ItemStack> items) {
        entityData.set(DATA_ITEMS, items);
    }

    @Override
    public void slimeloot_addItem(ItemStack itemStack) {
        List<ItemStack> items = new ArrayList<>(slimeloot_getItems());
        items.add(itemStack);
        slimeloot_setItems(items);
    }

    @Override
    public List<ItemStack> slimeloot_getItems() {
        return entityData.get(DATA_ITEMS);
    }

    @Inject(method = "finalizeSpawn", at = @At("TAIL"))
    private void slimeloot_finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, SpawnGroupData groupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        slimeloot_setItems(level.getServer().reloadableRegistries().getLootTable(SlimeLoot.SLIME_LOOT).getRandomItems(
                new LootParams.Builder((ServerLevel) level)
                        .withParameter(LootContextParams.THIS_ENTITY, this)
                        .withParameter(LootContextParams.ORIGIN, position())
                        .create(LootContextParamSets.CHEST))
        );
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean killedByPlayer) {
        super.dropCustomDeathLoot(level, source, killedByPlayer);
        List<ItemStack> savedItems = new ArrayList<>();
        for (ItemStack itemStack : slimeloot_getItems()) {
            if (!itemStack.isEmpty()) {
                int size = ((Slime) (Object) this).getSize();
                if (getRandom().nextFloat() < (size < 2 ? 1.0F : 0.4F / size))
                    spawnAtLocation(level, itemStack);
                else
                    savedItems.add(itemStack);
            }
        }
        slimeloot_setItems(savedItems);
    }

    @WrapOperation(method = "remove", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/monster/Slime;" +
                    "convertTo(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/ConversionParams;Lnet/minecraft/world/entity/EntitySpawnReason;Lnet/minecraft/world/entity/ConversionParams$AfterConversion;)" +
                    "Lnet/minecraft/world/entity/Mob;"
    ))
    private Mob slimeloot_remove(Slime slime, EntityType<?> type, ConversionParams params, EntitySpawnReason reason, ConversionParams.AfterConversion<?> afterConversion, Operation<Mob> mob) {
        Mob child = mob.call(slime, type, params, reason, afterConversion);
        if (child instanceof SlimeItemsHolder itemsHolder) {
            List<ItemStack> savedItems = new ArrayList<>();
            for (ItemStack itemStack : slimeloot_getItems())
                if (!itemStack.isEmpty()) {
                    if (slime.getRandom().nextFloat() < 0.5F)
                        itemsHolder.slimeloot_addItem(itemStack);
                    else
                        savedItems.add(itemStack);
                }
//            savedItems.forEach(System.out::println);
            slimeloot_setItems(savedItems);
        }
        return child;
    }

    @Inject(method = "remove", at = @At("TAIL"))
    private void slimeloot_remove(RemovalReason reason, CallbackInfo ci) {
        if (level() instanceof ServerLevel serverLevel && reason == RemovalReason.KILLED) {
            for (ItemStack itemStack : slimeloot_getItems())
                if (!itemStack.isEmpty()) spawnAtLocation(serverLevel, itemStack);
            slimeloot_setItems(List.of());
        }
    }
}