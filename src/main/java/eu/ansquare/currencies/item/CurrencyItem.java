package eu.ansquare.currencies.item;

import eu.ansquare.currencies.recipe.CurrencyRecipe;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class CurrencyItem extends Item {
	public CurrencyItem(Settings settings) {
		super(settings);
	}
	public TypedActionResult<ItemStack> use(World world,PlayerEntity user, Hand hand){
		if(!world.isClient){
			// For the sake of simplicity we draw the items off of the player's hands and create an inventory from that.
			// Usually you use an inventory of yours instead.
			SimpleInventory inventory = new SimpleInventory(user.getInventory().getStack(0), user.getInventory().getStack(1), user.getInventory().getStack(2) ,user.getInventory().getStack(3));

			// Or use .getAllMatches if you want all of the matches
			Optional<CurrencyRecipe> match = world.getRecipeManager()
					.getFirstMatch(CurrencyRecipe.Type.INSTANCE, inventory, world);

			if (match.isPresent()) {
				// Give the player the item and remove from what he has. Make sure to copy the ItemStack to not ruin it!
				user.getInventory().offerOrDrop(match.get().getResult(world.getRegistryManager()).copy());

			} else {
				// If it doesn't match we tell the player
				user.sendMessage(Text.literal("No match!"), true);
			}
		}
		return TypedActionResult.success(user.getStackInHand(hand));
	}
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		String player = stack.getOrCreateNbt().getString("player");
		tooltip.add(Text.translatable("item.currency.tooltip.player", player).formatted( Formatting.GRAY));
	}
}
