package eu.ansquare.currencies.block;

import eu.ansquare.currencies.item.CurrencyItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.WallMountedBlock;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class CoinScannerBlock extends WallMountedBlock {
	public CoinScannerBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(FACE, WallMountLocation.WALL));

	}
	protected static final VoxelShape CEILING_X_SHAPE = Block.createCuboidShape(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);
	protected static final VoxelShape CEILING_Z_SHAPE = Block.createCuboidShape(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);
	protected static final VoxelShape FLOOR_X_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
	protected static final VoxelShape FLOOR_Z_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
	protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 12.0, 16.0, 16.0, 16.0);
	protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 4.0);
	protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(12.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 4.0, 16.0, 16.0);
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(new Property[]{FACING, FACE});
	}
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if(player.getStackInHand(hand).getItem() instanceof CurrencyItem && !world.isClient){
		ItemStack stack = player.getStackInHand(hand);
		NbtCompound nbt = stack.getOrCreateNbt();
		String player1 = nbt.getString("player");
		BlockPos blockPos = NbtHelper.toBlockPos(nbt.getCompound("pos"));
		if(!Util.isBlank(player1) && blockPos != null){
			player.sendMessage(Text.translatable("message.coin_scanner.name", stack.getName().getString()), false);
			player.sendMessage(Text.translatable("item.currency.tooltip.player", player1), false);
			player.sendMessage(Text.translatable("message.coin_scanner.pos", blockPos.getX(), blockPos.getY(), blockPos.getZ()), false);
		}
		return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Direction direction = (Direction)state.get(FACING);
		switch ((WallMountLocation)state.get(FACE)) {
			case FLOOR:
				if (direction.getAxis() == Direction.Axis.X) {
					return FLOOR_X_SHAPE;
				}

				return FLOOR_Z_SHAPE;
			case WALL:
				VoxelShape var10000;
				switch (direction) {
					case EAST:
						var10000 = EAST_SHAPE;
						break;
					case WEST:
						var10000 = WEST_SHAPE;
						break;
					case SOUTH:
						var10000 = SOUTH_SHAPE;
						break;
					case NORTH:
					case UP:
					case DOWN:
						var10000 = NORTH_SHAPE;
						break;
					default:
						throw new IncompatibleClassChangeError();
				}

				return var10000;
			case CEILING:
			default:
				if (direction.getAxis() == Direction.Axis.X) {
					return CEILING_X_SHAPE;
				} else {
					return CEILING_Z_SHAPE;
				}
		}
	}
}
