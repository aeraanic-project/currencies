package eu.ansquare.currencies;

import eu.ansquare.currencies.block.ModBlocks;
import eu.ansquare.currencies.item.ModItemGroups;
import eu.ansquare.currencies.item.ModItems;
import eu.ansquare.currencies.recipe.CurrencyRecipe;
import eu.ansquare.currencies.recipe.CurrencyRecipeSerializer;
import eu.ansquare.currencies.screen.MintScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Currencies implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Currencies");
	public static final String MODID = "currencies";
	public static final ScreenHandlerType<MintScreenHandler> MINT_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(id("mint_screen_handler"), MintScreenHandler::new);

	public static Identifier id(String path){
		return new Identifier(MODID, path);
	}

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		Registry.register(Registries.RECIPE_SERIALIZER, CurrencyRecipeSerializer.ID,
				CurrencyRecipeSerializer.INSTANCE);
		Registry.register(Registries.RECIPE_TYPE, id(CurrencyRecipe.Type.ID), CurrencyRecipe.Type.INSTANCE);
		ModItemGroups.init();
		ModBlocks.init();
		ModItems.init();
	}
}
