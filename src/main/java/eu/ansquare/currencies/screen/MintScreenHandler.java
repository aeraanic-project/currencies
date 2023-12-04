package eu.ansquare.currencies.screen;

import eu.ansquare.currencies.Currencies;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;

public class MintScreenHandler extends ScreenHandler {
	public MintScreenHandler(int id, PlayerInventory inv) {
		super(Currencies.MINT_SCREEN_HANDLER, id);

		//this.internal = new ItemStackHandler(0);
	}

	@Override
	public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
		return null;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}
}
