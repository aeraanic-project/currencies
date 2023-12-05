package eu.ansquare.currencies.util;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class ItemUtils {
	public static Inventory populate(Inventory inventory, DefaultedList<ItemStack> items){
		for(int i = 0; i < items.size(); i++){
			inventory.setStack(i, items.get(i));
		}
		return inventory;
	}
}
