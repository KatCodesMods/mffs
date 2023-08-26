package dev.katcodes.mffs.common.blocks;

import dev.katcodes.mffs.api.ForcefieldTypes;
import dev.katcodes.mffs.common.blocks.entities.ExtractorBlockEntity;
import dev.katcodes.mffs.common.blocks.entities.ModBlockEntities;
import dev.katcodes.mffs.common.blocks.entities.ProjectorBlockEntity;
import dev.katcodes.mffs.common.items.modules.ProjectorModule;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class ProjectorBlock extends AbstractActivatableBlock{

    public static final EnumProperty<ProjectorModule> MODULE = EnumProperty.create("module", ProjectorModule.class);
    /**
     * Instantiates a new Abstract machine block.
     *
     * @param properties the block properties
     */
    protected ProjectorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MODULE, ProjectorModule.EMPTY));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ModBlockEntities.PROJECTOR_ENTITY.create(pPos,pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.PROJECTOR_ENTITY.get(), ProjectorBlockEntity::tick);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if(blockEntity instanceof ProjectorBlockEntity) {
                //p_60506_.openMenu((MenuProvider) blockEntity);
                NetworkHooks.openScreen((ServerPlayer) pPlayer, (MenuProvider) blockEntity, pPos);
            }
        }
        return InteractionResult.SUCCESS;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, ACTIVATED, MODULE);
    }
}
