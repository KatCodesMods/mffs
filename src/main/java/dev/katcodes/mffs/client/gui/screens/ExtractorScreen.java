package dev.katcodes.mffs.client.gui.screens;

import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.client.gui.widgets.ModeButton;
import dev.katcodes.mffs.common.configs.MFFSConfigs;
import dev.katcodes.mffs.common.inventory.ExtractorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ExtractorScreen extends AbstractContainerScreen<ExtractorMenu> {

    private final ResourceLocation texture = new ResourceLocation(MFFSMod.MODID, "textures/gui/extractor.png");
    private final ResourceLocation modeButtonTexture = new ResourceLocation(MFFSMod.MODID, "textures/gui/widgets/mode_button.png");


    private ModeButton modeButton;


    @Override
    protected void init() {
        super.init();
        boolean widthTooNarrow = this.width < 379;
        this.imageHeight=186;
        this.imageWidth=176;
        modeButton=new ModeButton(this,modeButtonTexture,this.getGuiLeft()+154,this.getGuiTop()+5,Component.empty(),0,96,96);
        modeButton.setMode(this.menu.getMode());
        this.addRenderableWidget(modeButton);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        // todo: enable once container data is set
        modeButton.setMode(this.menu.getMode());
    }

    public ExtractorScreen(ExtractorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {


        pGuiGraphics.drawString(this.font,"Force Energy", 5, 25, 4210752, false);

        pGuiGraphics.drawString(this.font,"Upgrades", 10,50, 4210752, false);
//        fontRenderer.drawString(Extractor.getDeviceName(), 8, 9, 0x404040);
        pGuiGraphics.drawString(this.font,"Extractor", 8,9, 4210752, false);
        pGuiGraphics.drawString(this.font,String.valueOf(this.menu.getForceEnergy()/1000).concat("k"), 140, 89, 4210752, false);
        pGuiGraphics.drawString(this.font,String.valueOf(this.menu.getWorkDone()).concat("%"), 23, 89, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        pGuiGraphics.blit(this.texture, i, j, 0, 0, this.imageWidth, this.imageHeight);

        int workPowerSlider = (79* this.menu.getWorkDone() /100);
        pGuiGraphics.blit(this.texture,i+49,j+89,176,0,workPowerSlider,6);
        int workCycle = (32 * this.menu.getWorkCycle()) / MFFSConfigs.MONAZIT_WORK_CYCLE.get();
        pGuiGraphics.blit(this.texture,i+73,j+50,179,81,workCycle,32);
        int forceEnergy = (24 * this.menu.getForceEnergy() / this.menu.getMaxForceEnergy());
        pGuiGraphics.blit(this.texture,i+137,j+60,219,80,32,forceEnergy);
    }
}
