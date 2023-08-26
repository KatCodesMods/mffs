package dev.katcodes.mffs.client.gui.screens;

import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.client.gui.widgets.ModeButton;
import dev.katcodes.mffs.common.inventory.ProjectorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ProjectorScreen extends AbstractContainerScreen<ProjectorMenu> {
    private final ResourceLocation texture = new ResourceLocation(MFFSMod.MODID, "textures/gui/projector.png");
    private final ResourceLocation modeButtonTexture = new ResourceLocation(MFFSMod.MODID, "textures/gui/widgets/mode_button.png");


    private ModeButton modeButton;
    boolean widthTooNarrow = this.width < 379;

    public ProjectorScreen(ProjectorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

    }

    @Override
    protected void init() {
        super.init();
        this.imageHeight=186;
        this.imageWidth=176;
        modeButton=new ModeButton(this,modeButtonTexture,this.getGuiLeft()+154,this.getGuiTop()+5,Component.empty(),0,96,96);
        //modeButton.setMode(this.menu.getMode());
        this.addRenderableWidget(modeButton);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
//        fontRenderer.drawString(projector.getDeviceName(), 12, 9, 0x404040);
//        fontRenderer.drawString("MFFS Projector", 12, 24, 0x404040);
        pGuiGraphics.drawString(this.font,"MFFS Projector",12,24,4210752,false);
//        fontRenderer.drawString("Typ-Mode", 34, 44, 0x404040);
        pGuiGraphics.drawString(this.font,"Module",34,44,4210752,false);
//        fontRenderer.drawString("PowerLink", 34, 66, 0x404040);
        pGuiGraphics.drawString(this.font,"Power Link",34,66,4210752,false);
//        if (projector.hasPowerSource()) {
//            fontRenderer.drawString(String.valueOf(projector.getLinkPower()),
//                    30, 80, 0x404040);
//        } else {
//            fontRenderer.drawString("No Link/OOR", 30, 80, 0x404040);
//        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        pGuiGraphics.blit(this.texture, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}
