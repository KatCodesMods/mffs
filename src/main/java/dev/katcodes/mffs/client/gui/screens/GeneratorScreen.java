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

package dev.katcodes.mffs.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.client.gui.widgets.VerticalGuage;
import dev.katcodes.mffs.common.inventory.GeneratorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;


public class GeneratorScreen extends AbstractContainerScreen<GeneratorMenu> {
    private final ResourceLocation texture = new ResourceLocation(MFFSMod.MODID, "textures/gui/generator.png");
    private final ResourceLocation FORGE_GUAGE = new ResourceLocation(MFFSMod.MODID, "textures/gui/widgets/forge_guage.png");
    private VerticalGuage guage;

    public GeneratorScreen(GeneratorMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Override
    protected void init() {
        super.init();
        boolean widthTooNarrow = this.width < 379;
        guage = new VerticalGuage(this.getGuiLeft() + 110, this.getGuiTop() + 20, 14, 42, 1, 0, 17, 0, 14, 42, FORGE_GUAGE);
        guage.setMax(this.menu.getMaxEnergy()).setCurrent(this.menu.getEnergy());
        this.addRenderableWidget(guage);

    }

    @Override
    protected void containerTick() {
        super.containerTick();
        guage.setMax(this.menu.getMaxEnergy()).setCurrent(this.menu.getEnergy());
    }

    @Override
    public void render(GuiGraphics p_97795_, int p_97796_, int p_97797_, float p_97798_) {
        this.renderBackground(p_97795_);
        super.render(p_97795_, p_97796_, p_97797_, p_97798_);
        this.renderTooltip(p_97795_, p_97796_, p_97797_);
    }

    @Override
    protected void renderBg(GuiGraphics p_97787_, float p_97788_, int p_97789_, int p_97790_) {
        int i = this.leftPos;
        int j = this.topPos;
        p_97787_.blit(this.texture, i, j, 0, 0, this.imageWidth, this.imageHeight);

        if (this.menu.is_burning()) {
            int k = this.menu.getBurnLeftScaled();
            p_97787_.blit(this.texture, i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }
        int l = this.menu.getCookProgressionScaled();
        p_97787_.blit(this.texture, i + 79, j + 34, 176, 14, l + 1, 16);
    }
}
