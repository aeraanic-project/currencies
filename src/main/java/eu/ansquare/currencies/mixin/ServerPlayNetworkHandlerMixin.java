package eu.ansquare.currencies.mixin;

import eu.ansquare.currencies.Currencies;
import eu.ansquare.currencies.screen.MintScreenHandler;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.listener.TickingPacketListener;
import net.minecraft.network.packet.c2s.play.ItemRenameC2SPacket;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.EntityTrackingListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin implements EntityTrackingListener, TickingPacketListener, ServerPlayPacketListener {
	@Shadow
	public ServerPlayerEntity player;

	@Inject(method = "onItemRename", at = @At("TAIL"))
	public void onOnItemRename(ItemRenameC2SPacket packet, CallbackInfo ci){
		ScreenHandler hadler = this.player.currentScreenHandler;
		if (hadler instanceof MintScreenHandler mintScreenHandler) {
			if (!mintScreenHandler.canUse(this.player)) {
				Currencies.LOGGER.debug("Player {} interacted with invalid menu {}", this.player, mintScreenHandler);
				return;
			}

			mintScreenHandler.setNewItemName(packet.getName());
		}
	}
}
