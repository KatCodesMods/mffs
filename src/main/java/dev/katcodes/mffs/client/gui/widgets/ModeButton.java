/*
 * This file is part of MFFS.
 * Copyright (c) 2014-2023 KatCodes.
 * <p>
 *  MFFS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  <p>
 *  MFFS is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  <p>
 * You should have received a copy of the GNU General Public License
 * along with MFFS.  If not, see <http://www.gnu.org/licenses/>.
 * </p>
 * </p>
 * </p>
 */

package dev.katcodes.mffs.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.katcodes.mffs.MFFSMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Objects;

public class ModeButton extends PictureButton {
    private int mode;
    private int texWidth;
    private int texHeight;

    public ModeButton(AbstractContainerScreen screen,ResourceLocation texture, int x, int y, Component p_93369_, int buttonId, int texWidth, int texHeight) {
        super(x, y, 16,16, p_93369_);
        this.setTexture(texture);
        this.setScreen(screen);
        this.setButtonId(buttonId);
        this.texWidth=texWidth;
        this.texHeight=texHeight;
    }


    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {

        pGuiGraphics.blit(this.getTexture(),this.getX(),this.getY(),mode*this.getWidth(),0,this.getWidth(),this.getHeight(),this.texWidth,this.texHeight);
        //pGuiGraphics.blit(texture, this.getX(), this.getY(), 16,16,16f,16f,0,0,16,16);

    }

    @Override
    public void onPress() {
        Objects.requireNonNull(this.getScreen().getMinecraft().gameMode).handleInventoryButtonClick(this.getScreen().getMenu().containerId,this.getButtonId());
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
