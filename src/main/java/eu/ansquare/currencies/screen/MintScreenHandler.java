package eu.ansquare.currencies.screen;

import eu.ansquare.currencies.Currencies;
import eu.ansquare.currencies.recipe.CurrencyRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.ItemCombinationSlotManager;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MintScreenHandler extends AbstractRecipeScreenHandler<RecipeInputInventory> {
	CraftingInventory inputInventory;
	CraftingResultInventory resultInventory;
	PlayerInventory playerInv;
	ScreenHandlerContext context;
	PlayerEntity player;
	public MintScreenHandler(int id, PlayerInventory inv){
		this(id, inv, ScreenHandlerContext.EMPTY);
	}
	public MintScreenHandler(int id, PlayerInventory inv, ScreenHandlerContext context) {
		super(Currencies.MINT_SCREEN_HANDLER, id);
		this.inputInventory = new CraftingInventory(this, 2, 2);
		this.resultInventory = new CraftingResultInventory();
		this.playerInv = inv;
		this.context = context;
		this.player = playerInv.player;
		this.addSlot(new CraftingResultSlot(player, this.inputInventory, this.resultInventory, 0, 134, 35));

		int i;
		int j;
		for(i = 0; i < 2; ++i) {
			for(j = 0; j < 2; ++j) {
				this.addSlot(new Slot(this.inputInventory, j + i * 2, 26 + j * 18, 26 + i * 18));
			}
		}

		for(i = 0; i < 3; ++i) {
			for(j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInv, i, 8 + i * 18, 142));
		}
	}
	protected static void updateResult(ScreenHandler handler, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory) {
		if (!world.isClient) {
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
			ItemStack itemStack = ItemStack.EMPTY;
			Optional<CraftingRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
			if (optional.isPresent()) {
				ItemStack itemStack2 = optional.get().craft(craftingInventory, world.getRegistryManager());
					if (itemStack2.isEnabled(world.getEnabledFlags())) {
						itemStack = itemStack2;
					}
							}

			resultInventory.setStack(0, itemStack);
			handler.setPreviousTrackedSlot(0, itemStack);
			serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
		}
	}
	public void close(PlayerEntity player) {
		super.close(player);
		this.context.run((world, pos) -> {
			this.dropInventory(player, this.inputInventory);
		});
	}
	public void onContentChanged(Inventory inventory) {
		this.context.run((world, pos) -> {
			updateResult(this, world, this.player, this.inputInventory, this.resultInventory);
		});
	}
	public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(fromIndex);
		if (slot != null && slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();
			if (fromIndex == 0) {
				this.context.run((world, pos) -> {
					itemStack2.getItem().onCraft(itemStack2, world, player);
				});
				if (!this.insertItem(itemStack2, 5, 41, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickTransfer(itemStack2, itemStack);
			} else if (fromIndex >= 5 && fromIndex < 41) {
				if (!this.insertItem(itemStack2, 1, 5, false)) {
					if (fromIndex < 37) {
						if (!this.insertItem(itemStack2, 32, 41, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!this.insertItem(itemStack2, 5, 32, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.insertItem(itemStack2, 5, 41, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slot.setStackByPlayer(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}

			if (itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTakeItem(player, itemStack2);
			if (fromIndex == 0) {
				player.dropItem(itemStack2, false);
			}
		}

		return itemStack;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}
	public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
		return slot.inventory != this.resultInventory && super.canInsertIntoSlot(stack, slot);
	}
	@Override
	public void populateRecipeFinder(RecipeMatcher finder) {
		inputInventory.provideRecipeInputs(finder);
	}

	@Override
	public void clearCraftingSlots() {
		this.inputInventory.clear();
		this.resultInventory.clear();
	}

	@Override
	public boolean matches(Recipe<? super RecipeInputInventory> recipe) {
		return recipe.matches(inputInventory, this.player.getWorld());
	}

	@Override
	public int getCraftingResultSlotIndex() {
		return 0;
	}

	@Override
	public int getCraftingWidth() {
		return inputInventory.getWidth();
	}

	@Override
	public int getCraftingHeight() {
		return inputInventory.getHeight();
	}

	@Override
	public int getCraftingSlotCount() {
		return 5;
	}

	@Override
	public RecipeBookCategory getCategory() {
		return RecipeBookCategory.CRAFTING;
	}

	@Override
	public boolean canInsertIntoSlot(int index) {
		return index != this.getCraftingResultSlotIndex();
	}
}
