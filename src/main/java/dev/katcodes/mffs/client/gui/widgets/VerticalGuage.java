package dev.katcodes.mffs.client.gui.widgets;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.katcodes.mffs.common.misc.ModTranslations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public class VerticalGuage extends AbstractWidget {
    private ResourceLocation guageImage;
    private int max;
    private int min;
    private int current;

    private int xTexStart;
    private int yTexStart;
    private int xGuageStart;
    private int yGuageStart;
    private int texWidth;
    private int texHeight;

    public int getMax() {
        return max;
    }

    public VerticalGuage setMax(int max) {
        this.max = max;
        return this;
    }

    public int getMin() {
        return min;
    }

    public VerticalGuage setMin(int min) {
        this.min = min;
        return this;
    }

    public int getCurrent() {
        return current;
    }

    public VerticalGuage setCurrent(int current) {
        this.current = current;
        this.setTooltip(Tooltip.create(Component.translatable(ModTranslations.GUAGE_TOOLTIP,current,max)));
        return this;
    }

    @Override
    protected boolean isValidClickButton(int p_93652_) {
        return false;
    }

    public float getPercent() {
        return ((current - min) * 100f) / (max - min);
    }


    public VerticalGuage(int x, int y, int width, int height, int xTexStart, int yTexStart, int xGuageStart, int yGuageStart, int texWidthIn, int texHeightIn, ResourceLocation guageImage) {
        super(x,y,width,height,Component.empty());
        this.guageImage = guageImage;
        this.xGuageStart = xGuageStart;
        this.yGuageStart = yGuageStart;
        this.xTexStart = xTexStart;
        this.yTexStart = yTexStart;
        this.texHeight = texHeightIn;
        this.texWidth = texWidthIn;
        this.min = 0;

    }

    @Override
    public void render(PoseStack matrixStack, int p_93658_, int p_93659_, float p_93660_) {
        if(this.visible) {
            float curPercent = getPercent();
            int bar2height = (int) (1f + ((curPercent / 100f) * (this.height - 2f)));
            int bar1height = this.height - bar2height;
            Minecraft minecraft = Minecraft.getInstance();
            Font font = minecraft.font;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, guageImage);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.disableDepthTest();
            blit(matrixStack,this.getX(), this.getY(), this.xTexStart, this.yTexStart, this.width, bar1height, 32, 64);
            blit(matrixStack,this.getX(), this.getY() + bar1height, this.xGuageStart, this.yGuageStart + bar1height, this.width, bar2height, 32, 64);
            RenderSystem.enableDepthTest();

        }
    }




    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
        p_259858_.add(NarratedElementType.USAGE, Float.toString(getPercent())+" percent");
    }
}
