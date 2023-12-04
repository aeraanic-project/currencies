package eu.ansquare.currencies.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import oshi.util.Util;

import java.util.List;

public class CurrencyItem extends Item {
	public CurrencyItem(Settings settings) {
		super(settings);
	}
	public void onCraft(ItemStack stack, World world, PlayerEntity player) {
		stack.getOrCreateNbt().putString("player", player.getName().getString());
	}
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		String player = stack.getOrCreateNbt().getString("player");
		if(!Util.isBlank(player)){
			tooltip.add(Text.translatable("item.currency.tooltip.player", player).formatted( Formatting.GRAY));
		} else {
			tooltip.add(Text.translatable("item.currency.tooltip.empty").formatted( Formatting.GRAY));
		}
	}
}
