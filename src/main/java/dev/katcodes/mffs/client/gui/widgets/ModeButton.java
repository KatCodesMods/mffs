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

public class ModeButton extends AbstractButton {
    private ResourceLocation texture;
    private int mode;
    private AbstractContainerScreen screen;
    private int buttonId;

    public ModeButton(AbstractContainerScreen screen,ResourceLocation texture, int x, int y, Component p_93369_, int buttonId) {
        super(x, y, 16,16, p_93369_);
        this.texture=texture;
        this.screen=screen;
        this.buttonId=buttonId;
    }


    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {

        pGuiGraphics.blit(this.texture,this.getX(),this.getY(),mode*16,0,16,16,96,96);
        //pGuiGraphics.blit(texture, this.getX(), this.getY(), 16,16,16f,16f,0,0,16,16);

    }

    @Override
    public void onPress() {
        this.screen.getMinecraft().gameMode.handleInventoryButtonClick(this.screen.getMenu().containerId,this.buttonId);
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
