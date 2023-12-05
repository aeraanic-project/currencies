package eu.ansquare.currencies;

import eu.ansquare.currencies.screen.MintScreen;
import eu.ansquare.currencies.screen.WalletScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class CurrenciesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		HandledScreens.register(Currencies.MINT_SCREEN_HANDLER, MintScreen::new);
		HandledScreens.register(Currencies.WALLET_SCREEN_HANDLER, WalletScreen::new);
	}
}
