package eu.ansquare.currencies.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import eu.ansquare.currencies.Currencies;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class CurrencyRecipeSerializer implements RecipeSerializer<CurrencyRecipe> {
	private CurrencyRecipeSerializer() {
	}

	public static final CurrencyRecipeSerializer INSTANCE = new CurrencyRecipeSerializer();
	public static final Identifier ID = Currencies.id("currency_recipe");


	// This will be the "type" field in the json
	@Override
	public CurrencyRecipe read(Identifier id, JsonObject json) {
		CurrencyRecipe.CurrencyRecipeJsonFormat recipeJson = new Gson().fromJson(json, CurrencyRecipe.CurrencyRecipeJsonFormat.class);
		Ingredient inputA = Ingredient.fromJson(recipeJson.inputA);
		Ingredient inputB = Ingredient.fromJson(recipeJson.inputB);
		Ingredient inputC = Ingredient.fromJson(recipeJson.inputC);
		Ingredient inputD = Ingredient.fromJson(recipeJson.inputD);
		if (recipeJson.inputA == null || recipeJson.inputB == null || recipeJson.inputC == null || recipeJson.inputD == null || recipeJson.outputItem == null) {
			throw new JsonSyntaxException("A required attribute is missing!");
		}
		if (recipeJson.outputAmount == 0) recipeJson.outputAmount = 1;
		Item outputItem = Registries.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem)).orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.outputItem));
		ItemStack output = new ItemStack(outputItem, recipeJson.outputAmount);
		return new CurrencyRecipe(id, output, inputA, inputB, inputC, inputD);
	}
	@Override
	// Turns Recipe into PacketByteBuf
	public void write(PacketByteBuf packetData, CurrencyRecipe recipe) {
		recipe.inputA.write(packetData);
		recipe.inputB.write(packetData);
		recipe.inputC.write(packetData);
		recipe.inputD.write(packetData);
		packetData.writeItemStack(recipe.result);
	}

	@Override
	// Turns PacketByteBuf into Recipe
	public CurrencyRecipe read(Identifier id, PacketByteBuf packetData) {
		Ingredient inputA = Ingredient.fromPacket(packetData);
		Ingredient inputB = Ingredient.fromPacket(packetData);
		Ingredient inputC = Ingredient.fromPacket(packetData);
		Ingredient inputD = Ingredient.fromPacket(packetData);
		ItemStack output = packetData.readItemStack();
		return new CurrencyRecipe(id, output, inputA, inputB, inputC, inputD);
	}
}
