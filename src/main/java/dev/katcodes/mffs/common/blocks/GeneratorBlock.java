package dev.katcodes.mffs.common.blocks;

import dev.katcodes.mffs.common.blocks.entities.GeneratorEntity;
import dev.katcodes.mffs.common.blocks.entities.ModBlockEntities;
import dev.katcodes.mffs.common.inventory.GeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class GeneratorBlock extends AbstractMachineBlock {


    public GeneratorBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return ModBlockEntities.GENERATOR_ENTITY.create(pos,state);
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if(!p_60504_.isClientSide) {
            BlockEntity blockEntity = p_60504_.getBlockEntity(p_60505_);
            if(blockEntity instanceof GeneratorEntity) {
                p_60506_.openMenu((MenuProvider) blockEntity);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return createTickerHelper(p_153214_, ModBlockEntities.GENERATOR_ENTITY.get(), GeneratorEntity::tick);
    }
}
