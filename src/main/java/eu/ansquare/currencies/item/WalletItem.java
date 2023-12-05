package eu.ansquare.currencies.item;

import eu.ansquare.currencies.screen.WalletScreenHandler;
import eu.ansquare.currencies.util.ItemUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class WalletItem extends Item {

	public WalletItem(Settings settings) {
		super(settings);
	}
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand){
		ItemStack stack = user.getStackInHand(hand);
		if(!world.isClient){
			user.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, playerInventory, playerx) -> {
				Inventory inventory = new WalletInventory(stack);

				WalletScreenHandler handler = new WalletScreenHandler(syncId, playerInventory, inventory, 6);

				return handler;
			}, stack.getName()));


		}
		return TypedActionResult.pass(stack);
	}

}
