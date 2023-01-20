package dev.katcodes.mffs.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.client.gui.widgets.VerticalGuage;
import dev.katcodes.mffs.common.inventory.GeneratorContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;

public class GeneratorScreen extends AbstractContainerScreen<GeneratorContainer> {
    private boolean widthTooNarrow;
    private VerticalGuage guage;
    private final ResourceLocation texture = new ResourceLocation(MFFSMod.MODID, "textures/gui/generator.png");
    private ResourceLocation FORGE_GUAGE=new ResourceLocation(MFFSMod.MODID,"textures/gui/widgets/forge_guage.png");

    public GeneratorScreen(GeneratorContainer p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Override
    protected void init() {
        super.init();
        this.widthTooNarrow = this.width < 379;
        guage=new VerticalGuage(this.getGuiLeft()+110,this.getGuiTop()+20,14,42,1,0,17,0,14,42,FORGE_GUAGE);
        // TODO: Add guage
        guage.setMax(1000);
        this.addRenderableWidget(guage);

    }

    @Override
    public void render(PoseStack p_97795_, int p_97796_, int p_97797_, float p_97798_) {
        this.renderBackground(p_97795_);
        super.render(p_97795_, p_97796_, p_97797_, p_97798_);
        this.renderTooltip(p_97795_, p_97796_, p_97797_);
    }

    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.texture);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(p_97787_, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}
