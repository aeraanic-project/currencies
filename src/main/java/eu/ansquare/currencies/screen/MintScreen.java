package eu.ansquare.currencies.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import eu.ansquare.currencies.Currencies;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.packet.c2s.play.ItemRenameC2SPacket;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MintScreen extends HandledScreen<MintScreenHandler> {
	private static final Identifier TEXTURE = Currencies.id("textures/screen/mint.png");
	private TextFieldWidget nameField;

	public MintScreen(MintScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		this.backgroundWidth = 176;
		this.backgroundHeight = 166;
	}
	public void handledScreenTick() {
		super.handledScreenTick();
		this.nameField.tick();
	}
	protected void init() {
		super.init();
		int i = (this.width - this.backgroundWidth) / 2;
		int j = (this.height - this.backgroundHeight) / 2;
		this.nameField = new TextFieldWidget(this.textRenderer, i + 28, j + 19, 103, 12, Text.translatable("container.repair"));
		this.nameField.setFocusUnlocked(false);
		this.nameField.setEditableColor(-1);
		this.nameField.setUneditableColor(-1);
		this.nameField.setDrawsBackground(false);
		this.nameField.setMaxLength(50);
		this.nameField.setChangedListener(this::onRenamed);
		this.nameField.setText("");
		this.addSelectableChild(this.nameField);
		this.setInitialFocus(this.nameField);
		this.nameField.setEditable(true);
	}
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == 256) {
			this.client.player.closeHandledScreen();
		}

		return !this.nameField.keyPressed(keyCode, scanCode, modifiers) && !this.nameField.isActive() ? super.keyPressed(keyCode, scanCode, modifiers) : true;
	}

	private void onRenamed(String name) {
		this.client.player.networkHandler.sendPacket(new ItemRenameC2SPacket(name));
	}
	@Override
	protected void drawBackground(GuiGraphics matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShaderTexture(0, TEXTURE);
		matrices.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight, this.backgroundWidth, this.backgroundHeight);
		RenderSystem.disableBlend();
	}
	@Override
	public void render(GuiGraphics matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		this.nameField.render(matrices, mouseX, mouseY, delta);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

}
