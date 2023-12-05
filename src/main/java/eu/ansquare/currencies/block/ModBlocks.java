package eu.ansquare.currencies.block;

import eu.ansquare.currencies.Currencies;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModBlocks {
	private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	private static final Map<Item, Identifier> BLOCKITEMS = new LinkedHashMap<>();
	private static final Map<BlockEntityType, Identifier> BLOCKENTITIES = new LinkedHashMap<>();

	public static final Block MINT = createBlockAndItem("mint", new MintBlock(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK).requiresTool()), ItemGroups.FUNCTIONAL_BLOCKS);
	public static final Block COIN_SCANNER = createBlockAndItem("coin_scanner", new CoinScannerBlock(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK).requiresTool().nonOpaque()), ItemGroups.FUNCTIONAL_BLOCKS);
	private static <T extends Block> T createBlockAndItem(String name, T block, RegistryKey<ItemGroup> itemGroup){
		BLOCKS.put(block, Currencies.id(name));
		BLOCKITEMS.put(new BlockItem(block, new QuiltItemSettings()), Currencies.id(name));
		ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> {
			content.addItem(block);
		});
		return block;
	}
	private static BlockEntityType<? extends BlockEntity> createBlockEntity(String name, BlockEntityType type){
		BLOCKENTITIES.put(type, Currencies.id(name));
		return type;
	}
	public static void init() {
		BLOCKS.keySet().forEach(item -> Registry.register(Registries.BLOCK, BLOCKS.get(item), item));
		BLOCKITEMS.keySet().forEach(item -> Registry.register(Registries.ITEM, BLOCKITEMS.get(item), item));
		BLOCKENTITIES.keySet().forEach(item -> Registry.register(Registries.BLOCK_ENTITY_TYPE, BLOCKENTITIES.get(item), item));

	}
}
