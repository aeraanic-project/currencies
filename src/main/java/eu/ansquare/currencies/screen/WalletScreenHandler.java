package eu.ansquare.currencies.screen;

import eu.ansquare.currencies.Currencies;
import eu.ansquare.currencies.item.CurrencyItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class WalletScreenHandler extends ScreenHandler {
	private static final int SLOTS_PER_ROW = 9;
	private final Inventory inventory;
	private final int rows;

	public WalletScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(54), 6);
	}
	public WalletScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, int rows) {
		super(Currencies.WALLET_SCREEN_HANDLER, syncId);
		checkSize(inventory, rows * 9);
		this.inventory = inventory;
		this.rows = rows;
		inventory.onOpen(playerInventory.player);
		int i = (this.rows - 4) * 18;

		int j;
		int k;
		for(j = 0; j < this.rows; ++j) {
			for(k = 0; k < 9; ++k) {
				this.addSlot(new Slot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18){
					@Override
					public boolean canInsert(ItemStack stack) {
						return stack.getItem() instanceof CurrencyItem;
					}
				});
			}
		}

		for(j = 0; j < 3; ++j) {
			for(k = 0; k < 9; ++k) {
				this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
			}
		}

		for(j = 0; j < 9; ++j) {
			this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 161 + i));
		}

	}
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = (Slot)this.slots.get(fromIndex);
		if (slot != null && slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();
			if (fromIndex < this.rows * 9) {
				if (!this.insertItem(itemStack2, this.rows * 9, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(itemStack2, 0, this.rows * 9, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slot.setStackByPlayer(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return itemStack;
	}

	public void close(PlayerEntity player) {
		super.close(player);
		this.inventory.onClose(player);
	}

	public Inventory getInventory() {
		return this.inventory;
	}

	public int getRows() {
		return this.rows;
	}
}
