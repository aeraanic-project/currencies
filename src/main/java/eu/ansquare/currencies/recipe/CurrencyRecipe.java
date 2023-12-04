package eu.ansquare.currencies.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import eu.ansquare.currencies.Currencies;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CurrencyRecipe implements CraftingRecipe {
	protected final Ingredient inputA;
	protected final Ingredient inputB;
	protected final Ingredient inputC;
	protected final Ingredient inputD;

	protected final ItemStack result;
	protected final Identifier id;

	public CurrencyRecipe(Identifier id, ItemStack result, Ingredient inputA, Ingredient inputB, Ingredient inputC, Ingredient inputD) {
		this.id = id;
		this.inputA = inputA;
		this.inputB = inputB;
		this.inputC = inputC;
		this.inputD = inputD;
		this.result = result;
	}
	@Override
	public boolean matches(RecipeInputInventory inventory, World world) {
		if(inventory.size() < 4) return false;
		return inputA.test(inventory.getStack(0)) && inputB.test(inventory.getStack(1)) && inputC.test(inventory.getStack(2)) && inputD.test(inventory.getStack(3));
	}

	@Override
	public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
		return this.getResult(registryManager).copy();
	}


	@Override
	public boolean fits(int width, int height) {
		return false;
	}

	@Override
	public ItemStack getResult(DynamicRegistryManager registryManager) {
		return result;
	}

	@Override
	public Identifier getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return CurrencyRecipeSerializer.INSTANCE;
	}

	public static class Type implements RecipeType<CurrencyRecipe> {
		private Type() {}
		public static final Type INSTANCE = new Type();
		public static final String ID = "currency_recipe";
	}
	@Override
	public RecipeType<?> getType() {
		return Type.CRAFTING;
	}

	@Override
	public CraftingCategory getCategory() {
		return CraftingCategory.MISC;
	}

	class CurrencyRecipeJsonFormat {
		JsonObject inputA;
		JsonObject inputB;
		JsonObject inputC;
		JsonObject inputD;
		String outputItem;
		int outputAmount;
	}

}
