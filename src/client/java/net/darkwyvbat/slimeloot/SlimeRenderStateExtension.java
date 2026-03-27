package net.darkwyvbat.slimeloot;

import net.minecraft.client.renderer.item.ItemStackRenderState;

import java.util.List;

public interface SlimeRenderStateExtension {
    List<ItemStackRenderState> slimeloot_getItemStates();

    void slimeloot_setItemSeed(long seed);

    long slimeloot_getItemSeed();
}