package eu.ansquare.currencies.screen;

import eu.ansquare.currencies.Currencies;
import eu.ansquare.currencies.recipe.CurrencyRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.ItemCombinationSlotManager;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MintScreenHandler extends ScreenHandler {
	SimpleInventory inputInventory;
	SimpleInventory resultInventory;
	PlayerInventory playerInv;
	ScreenHandlerContext context;
	PlayerEntity player;
	public MintScreenHandler(int id, PlayerInventory inv){
		this(id, inv, ScreenHandlerContext.EMPTY);
	}
	public MintScreenHandler(int id, PlayerInventory inv, ScreenHandlerContext context) {
		super(Currencies.MINT_SCREEN_HANDLER, id);
		this.inputInventory = new SimpleInventory(4);
		this.resultInventory = new SimpleInventory(1);
		this.playerInv = inv;
		this.context = context;
		this.player = playerInv.player;
		int m;
		int l;
		this.addSlot(new Slot(resultInventory, 0, 134, 35){
			@Override
			public boolean canInsert(ItemStack stack) {
				return false;
			}
		});
		this.addSlot(new Slot(inputInventory, 0, 26, 26));
		this.addSlot(new Slot(inputInventory, 1, 44, 26));
		this.addSlot(new Slot(inputInventory, 2, 26, 44));
		this.addSlot(new Slot(inputInventory, 3, 44, 44));

		for (m = 0; m < 3; ++m) {
			for (l = 0; l < 9; ++l) {
				this.addSlot(new Slot(playerInv, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
			}
		}
		//The player Hotbar
		for (m = 0; m < 9; ++m) {
			this.addSlot(new Slot(playerInv, m, 8 + m * 18, 142));
		}
	}

	public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(fromIndex);
		if (slot != null && slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();
			if (fromIndex < this.inputInventory.size()) {
				if (!this.insertItem(originalStack, this.inputInventory.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(originalStack, 0, this.inputInventory.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return newStack;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}
}
