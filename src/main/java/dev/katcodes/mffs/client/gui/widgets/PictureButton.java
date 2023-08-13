package dev.katcodes.mffs.client.gui.widgets;


import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class PictureButton extends AbstractButton {

    public ResourceLocation getTexture() {
        return texture;
    }

    public void setTexture(ResourceLocation texture) {
        this.texture = texture;
    }

    public AbstractContainerScreen getScreen() {
        return screen;
    }

    public void setScreen(AbstractContainerScreen screen) {
        this.screen = screen;
    }

    public int getButtonId() {
        return buttonId;
    }

    public void setButtonId(int buttonId) {
        this.buttonId = buttonId;
    }

    private ResourceLocation texture;
    private AbstractContainerScreen screen;
    private int buttonId;
    public PictureButton(int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);
    }


}
