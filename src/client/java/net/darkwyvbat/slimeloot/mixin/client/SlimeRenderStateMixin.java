package net.darkwyvbat.slimeloot.mixin.client;

import net.darkwyvbat.slimeloot.SlimeRenderStateExtension;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Mixin(SlimeRenderState.class)
public class SlimeRenderStateMixin implements SlimeRenderStateExtension {

    @Unique
    private List<ItemStackRenderState> slimeloot_itemStates;
    @Unique
    private long slimeloot_itemSeed;

    @Override
    public List<ItemStackRenderState> slimeloot_getItemStates() {
        if (slimeloot_itemStates == null) slimeloot_itemStates = new ArrayList<>();
        return slimeloot_itemStates;
    }

    @Override
    public void slimeloot_setItemSeed(long seed) {
        slimeloot_itemSeed = seed;
    }

    @Override
    public long slimeloot_getItemSeed() {
        return slimeloot_itemSeed;
    }
}