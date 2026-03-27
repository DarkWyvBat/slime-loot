package net.darkwyvbat.slimeloot;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface SlimeItemsHolder {
    void slimeloot_setItems(List<ItemStack> items);

    void slimeloot_addItem(ItemStack itemStack);

    List<ItemStack> slimeloot_getItems();
}