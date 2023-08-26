package dev.katcodes.mffs.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.katcodes.mffs.api.ForcefieldTypes;
import dev.katcodes.mffs.common.blocks.ForceFieldBlock;
import dev.katcodes.mffs.common.blocks.entities.ForceFieldBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.phys.Vec3;

public class ForcefieldRenderer implements BlockEntityRenderer<ForceFieldBlockEntity> {
    BlockEntityRenderDispatcher berd;
    BlockRenderDispatcher brd;

    public ForcefieldRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, BlockRenderDispatcher blockRenderDispatcher) {
        this.berd=blockEntityRenderDispatcher;
        this.brd=blockRenderDispatcher;
    }

    @Override
    public void render(ForceFieldBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if(pBlockEntity.getBlockState().getValue(ForceFieldBlock.BLOCK_TYPE) == ForcefieldTypes.Camo) {

            BakedModel model = brd.getBlockModel(pBlockEntity.getCamoState());
            brd.getModelRenderer().renderModel(pPoseStack.last(), pBuffer.getBuffer(RenderType.solid()), pBlockEntity.getCamoState(), model, 1f, 1f, 1f, pPackedLight, pPackedOverlay);
        }
        else {
            BakedModel model = brd.getBlockModel(pBlockEntity.getBlockState());
            brd.getModelRenderer().renderModel(pPoseStack.last(), pBuffer.getBuffer(RenderType.cutout()), pBlockEntity.getCamoState(), model, 1f, 1f, 1f, pPackedLight, pPackedOverlay);
        }
    }

    @Override
    public boolean shouldRenderOffScreen(ForceFieldBlockEntity pBlockEntity) {
        return BlockEntityRenderer.super.shouldRenderOffScreen(pBlockEntity);
    }

    @Override
    public int getViewDistance() {
        return BlockEntityRenderer.super.getViewDistance();
    }

    @Override
    public boolean shouldRender(ForceFieldBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return BlockEntityRenderer.super.shouldRender(pBlockEntity, pCameraPos);
    }
}
