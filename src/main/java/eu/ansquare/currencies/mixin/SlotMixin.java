package eu.ansquare.currencies.mixin;

import eu.ansquare.currencies.Currencies;
import eu.ansquare.currencies.item.CurrencyItem;
import eu.ansquare.currencies.item.WalletInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public class SlotMixin {
	@Shadow
	@Final
	public Inventory inventory;

	@Inject(method = "canInsert", at = @At("TAIL"), cancellable = true)
	public void onCanInsert(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
		if(inventory instanceof WalletInventory){
			if(!(stack.getItem() instanceof CurrencyItem)){
				cir.setReturnValue(false);
			}
		}
	}
}
