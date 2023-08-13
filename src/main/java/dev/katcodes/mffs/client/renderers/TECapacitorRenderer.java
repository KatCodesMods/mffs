package dev.katcodes.mffs.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.katcodes.mffs.common.blocks.entities.CapacitorBlockEntity;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//@OnlyIn(Dist.CLIENT)
//public class SignRenderer implements BlockEntityRenderer<SignBlockEntity>
@OnlyIn(Dist.CLIENT)
public class TECapacitorRenderer implements BlockEntityRenderer<CapacitorBlockEntity> {

    private final Font font;

    public TECapacitorRenderer(BlockEntityRendererProvider.Context pContext) {

    this.font = pContext.getFont();
    }
    @Override
    public void render(CapacitorBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        BlockState blockstate = pBlockEntity.getBlockState();
        pPoseStack.pushPose();

        pPoseStack.popPose();
    }
}
