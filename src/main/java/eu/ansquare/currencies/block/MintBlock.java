package eu.ansquare.currencies.block;

import eu.ansquare.currencies.screen.MintScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MintBlock extends Block {
	public MintBlock(Settings settings) {
		super(settings);
	}
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		} else {
			player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
			player.incrementStat(Stats.INTERACT_WITH_ANVIL);
			return ActionResult.CONSUME;
		}
	}

	@Nullable
	public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
		return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new MintScreenHandler(syncId, inventory), Text.translatable("screen.currencies.mint.title"));
	}
}
