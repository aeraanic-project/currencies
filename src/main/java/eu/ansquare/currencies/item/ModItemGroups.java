package eu.ansquare.currencies.item;

import eu.ansquare.currencies.Currencies;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public class ModItemGroups {
	private static Map<ItemGroup, RegistryKey<ItemGroup>> map = new HashMap<>();
	public static final RegistryKey<ItemGroup> CURRENCIES = createRegistryKey("currencies");


	private static RegistryKey<ItemGroup> createRegistryKey(String name) {
		return RegistryKey.of(RegistryKeys.ITEM_GROUP, Currencies.id(name));
	}
	private static ItemGroup createGroup(RegistryKey<ItemGroup> key, String name, ItemConvertible item){
		ItemGroup group = FabricItemGroup.builder().name(Text.translatable("itemgroup.currencies." + name)).icon(() -> new ItemStack(item)).build();
		map.put(group, key);
		return group;
	}
	public static void init(){
		createGroup(CURRENCIES, "currencies", ModItems.TEST_COIN);

		map.keySet().forEach(itemGroup -> Registry.register(Registries.ITEM_GROUP, map.get(itemGroup), itemGroup));
	}
}
