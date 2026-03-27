package net.darkwyvbat.slimeloot.mixin.client;

import net.darkwyvbat.slimeloot.SlimeItemsHolder;
import net.darkwyvbat.slimeloot.SlimeItemsLayer;
import net.darkwyvbat.slimeloot.SlimeRenderStateExtension;
import net.minecraft.client.model.monster.slime.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SlimeRenderer.class)
public abstract class SlimeRendererMixin extends MobRenderer<Slime, SlimeRenderState, SlimeModel> {

    protected SlimeRendererMixin(EntityRendererProvider.Context context, SlimeModel model, float shadow) {
        super(context, model, shadow);
    }

    @Unique
    private ItemModelResolver slimeloot_itemModelResolver;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(EntityRendererProvider.Context context, CallbackInfo ci) {
        slimeloot_itemModelResolver = context.getItemModelResolver();
        addLayer(new SlimeItemsLayer(this));
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/monster/Slime;Lnet/minecraft/client/renderer/entity/state/SlimeRenderState;F)V", at = @At("TAIL"))
    private void slimeloot_extractRenderState(Slime entity, SlimeRenderState state, float partialTicks, CallbackInfo ci) {
        if (entity instanceof SlimeItemsHolder itemsHolder && state instanceof SlimeRenderStateExtension extension) {
            extension.slimeloot_setItemSeed(entity.getUUID().getLeastSignificantBits());
            List<ItemStack> items = itemsHolder.slimeloot_getItems();
            List<ItemStackRenderState> itemStates = extension.slimeloot_getItemStates();
            while (itemStates.size() < items.size())
                itemStates.add(new ItemStackRenderState());
            for (int i = 0; i < items.size(); i++) {
                ItemStack itemStack = items.get(i);
                ItemStackRenderState itemState = itemStates.get(i);
                if (!itemStack.isEmpty())
                    slimeloot_itemModelResolver.updateForNonLiving(itemState, itemStack, ItemDisplayContext.GROUND, entity);
                else
                    itemState.clear();
            }
            for (int i = items.size(); i < itemStates.size(); i++)
                itemStates.get(i).clear();
        }
    }
}