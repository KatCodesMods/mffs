package dev.katcodes.mffs.common.blocks;

import dev.katcodes.mffs.common.blocks.entities.GeneratorEntity;
import dev.katcodes.mffs.common.blocks.entities.ModBlockEntities;
import dev.katcodes.mffs.common.inventory.GeneratorContainer;
import dev.katcodes.mffs.common.inventory.ModMenus;
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
           p_60506_.openMenu(new MenuProvider() {
               @Override
               public Component getDisplayName() {
                   return Component.translatable("container.mffs.generator");
               }

               @Nullable
               @Override
               public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
                   return new GeneratorContainer(p_39954_, p_39955_, (GeneratorEntity) p_60504_.getBlockEntity(p_60505_));
               }
           });
        }
        return InteractionResult.SUCCESS;
    }
}
