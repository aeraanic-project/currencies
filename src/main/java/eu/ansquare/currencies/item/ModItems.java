package eu.ansquare.currencies.item;

import eu.ansquare.currencies.Currencies;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModItems {
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
	public static final Item DIAMOND_COIN = createItem("diamond_coin", new CurrencyItem(new QuiltItemSettings()), ModItemGroups.CURRENCIES);
	public static final Item COPPER_COIN = createItem("copper_coin", new CurrencyItem(new QuiltItemSettings()), ModItemGroups.CURRENCIES);
	public static final Item EMERALD_COIN = createItem("emerald_coin", new CurrencyItem(new QuiltItemSettings()), ModItemGroups.CURRENCIES);
	public static final Item GOLD_COIN = createItem("gold_coin", new CurrencyItem(new QuiltItemSettings()), ModItemGroups.CURRENCIES);
	public static final Item GREEN_CASH = createItem("green_cash", new CurrencyItem(new QuiltItemSettings()), ModItemGroups.CURRENCIES);
	public static final Item BLUE_CASH = createItem("blue_cash", new CurrencyItem(new QuiltItemSettings()), ModItemGroups.CURRENCIES);
	public static final Item MAGENTA_CASH = createItem("magenta_cash", new CurrencyItem(new QuiltItemSettings()), ModItemGroups.CURRENCIES);
	public static final Item CHOCOLATE_COIN = createItem("chocolate_coin", new CurrencyItem(new QuiltItemSettings().food(new FoodComponent.Builder().hunger(4).build())), ModItemGroups.CURRENCIES);

	public static final Item LEATHER_WALLET = createItem("leather_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item BLUE_WALLET = createItem("blue_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item RED_WALLET = createItem("red_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item ORANGE_WALLET = createItem("orange_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item YELLOW_WALLET = createItem("yellow_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item MAGENTA_WALLET = createItem("magenta_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item PURPLE_WALLET = createItem("purple_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item PINK_WALLET = createItem("pink_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item LIME_WALLET = createItem("lime_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item GREEN_WALLET = createItem("green_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item BROWN_WALLET = createItem("brown_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item CYAN_WALLET = createItem("cyan_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item LIGHT_BLUE_WALLET = createItem("light_blue_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item BLACK_WALLET = createItem("black_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item GRAY_WALLET = createItem("gray_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item LIGHT_GRAY_WALLET = createItem("light_gray_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	public static final Item WHITE_WALLET = createItem("white_wool_wallet", new WalletItem(new QuiltItemSettings().maxCount(1)), ModItemGroups.WALLETS);
	private static <T extends Item> T createItem(String name, T item, RegistryKey<ItemGroup> itemGroup){
		ITEMS.put(item, Currencies.id(name));
		ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> {
			content.addItem(item);
		});
		return item;
	}
	private static <T extends Item> T createGrouplessItem(String name, T item){
		ITEMS.put(item, Currencies.id(name));
		return item;
	}
	public static void init() {
		ITEMS.keySet().forEach(item -> Registry.register(Registries.ITEM, ITEMS.get(item), item));
	}
}
