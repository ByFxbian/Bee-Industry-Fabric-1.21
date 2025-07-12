package at.byfxbian.beeindustry.screen;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.networking.BeeIndustryNetworking;
import at.byfxbian.beeindustry.networking.payload.ToggleBeeSlotPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class BeepostScreen extends HandledScreen<BeepostScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(BeeIndustry.MOD_ID, "textures/gui/beepost_gui.png");
    private static final Identifier LIGHTS_TEXTURE = Identifier.of(BeeIndustry.MOD_ID, "textures/gui/status_lights.png");

    private ButtonWidget bee1Button;
    private ButtonWidget bee2Button;
    private ButtonWidget bee3Button;

    public BeepostScreen(BeepostScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 222;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void init() {
        super.init();

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        bee1Button = this.addDrawableChild(ButtonWidget.builder(
                getButtonText(0), button -> {
            ClientPlayNetworking.send(new ToggleBeeSlotPayload(this.handler.getPos(), 0));
        }).dimensions(x + 8, y + 17, 16, 16).build());

        bee2Button = this.addDrawableChild(ButtonWidget.builder(getButtonText(1), button -> {
            ClientPlayNetworking.send(new ToggleBeeSlotPayload(this.handler.getPos(), 1));
        }).dimensions(x + 8, y + 35, 16, 16).build());

        bee3Button = this.addDrawableChild(ButtonWidget.builder(getButtonText(2), button -> {
            ClientPlayNetworking.send(new ToggleBeeSlotPayload(this.handler.getPos(), 2));
        }).dimensions(x + 8, y + 53, 16, 16).build());
    }

    private Text getButtonText(int slotIndex) {
        boolean isActive = this.handler.getPropertyDelegate().get(3 + slotIndex) == 1;
        return isActive ? Text.literal("Y").formatted(Formatting.GREEN) : Text.literal("N").formatted(Formatting.RED);
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        updateButtonStates();
    }

    private void updateButtonStates() {
        bee1Button.setMessage(getButtonText(0));
        bee2Button.setMessage(getButtonText(1));
        bee3Button.setMessage(getButtonText(2));
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        /*for (int i = 0; i < 3; i++) {
            boolean isActive = this.handler.getPropertyDelegate().get(3 + i) == 1;
            int lightV = isActive ? 8 : 0;

            context.drawTexture(LIGHTS_TEXTURE,  x + 8, y + 17 + (i * 20), 0, lightV, 8, 8);
        }*/
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
