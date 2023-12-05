package eu.ansquare.currencies.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class WalletInventory implements Inventory {
	public WalletInventory(ItemStack stack){
		this.stack = stack;
		Inventories.readNbt(stack.getOrCreateNbt(), stacks);
	}
	ItemStack stack ;
	public DefaultedList<ItemStack> stacks = DefaultedList.ofSize(54, ItemStack.EMPTY);
	@Override
	public int size() {
		return 54;
	}

	@Override
	public boolean isEmpty() {
		return stacks.isEmpty();
	}

	@Override
	public ItemStack getStack(int slot) {
		return stacks.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack itemStack = Inventories.splitStack(this.stacks, slot, amount);
		if (!itemStack.isEmpty()) {
			this.markDirty();
		}

		return itemStack;	}

	public ItemStack removeStack(int slot) {
		ItemStack itemStack = (ItemStack)this.stacks.get(slot);
		if (itemStack.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			this.stacks.set(slot, ItemStack.EMPTY);
			return itemStack;
		}
	}

	public void setStack(int slot, ItemStack stack) {
		this.stacks.set(slot, stack);
		if (!stack.isEmpty() && stack.getCount() > this.getMaxCountPerStack()) {
			stack.setCount(this.getMaxCountPerStack());
		}

		this.markDirty();
	}

	@Override
	public void markDirty() {
		Inventories.writeNbt(stack.getOrCreateNbt(), stacks);
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return true;
	}

	@Override
	public void clear() {
		stacks.clear();
	}
}
