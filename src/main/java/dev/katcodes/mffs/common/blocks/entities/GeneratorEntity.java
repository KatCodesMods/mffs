package dev.katcodes.mffs.common.blocks.entities;

import dev.katcodes.mffs.common.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GeneratorEntity extends BlockEntity {


    //<editor-fold desc="Capability">
    private IItemHandlerModifiable fuelHandler= createFuelHandler(this);


    private IItemHandlerModifiable crystalHandler= createCrystalHandler(this);


    public IItemHandlerModifiable getFuelHandler() {
        return fuelHandler;
    }

    public IItemHandlerModifiable getCrystalHandler() {
        return crystalHandler;
    }

    @NotNull
    public static IItemHandlerModifiable createFuelHandler(GeneratorEntity thisEntity) {
        return new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return AbstractFurnaceBlockEntity.isFuel(stack);
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if(!AbstractFurnaceBlockEntity.isFuel(stack)) {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if(thisEntity!=null)
                    thisEntity.setChanged();
            }
        };
    }
    public static boolean isCrystal(ItemStack stack) {
        return stack.getTags().anyMatch(s -> s.equals(ModTags.ItemTags.MONAZIT));
    }
    @NotNull
    public static ItemStackHandler createCrystalHandler(GeneratorEntity thisEntity) {
        return new ItemStackHandler(1){
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return isCrystal(stack);
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if(!isCrystal(stack)) {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if(thisEntity!=null)
                    thisEntity.setChanged();
            }
        };
    }
    private final LazyOptional<IItemHandler> fuelOptional = LazyOptional.of(() -> this.fuelHandler);
    private final LazyOptional<IItemHandler> crystalOptional = LazyOptional.of(() -> this.crystalHandler);
    private final LazyOptional<IItemHandler> combinedOptional = LazyOptional.of(() -> new CombinedInvWrapper(this.fuelHandler,this.crystalHandler));

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == Direction.UP) {
                return crystalOptional.cast();
            }
            if(side == Direction.NORTH || side == Direction.SOUTH || side == Direction.EAST || side == Direction.WEST) {
                return fuelOptional.cast();
            }
            if(side==null) {
                return combinedOptional.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        fuelOptional.invalidate();
        crystalOptional.invalidate();
        combinedOptional.invalidate();
    }

    //</editor-fold>


    @Override
    protected void saveAdditional(CompoundTag p_187471_) {
        super.saveAdditional(p_187471_);
    }


    @Override
    public void load(CompoundTag p_155245_) {
        super.load(p_155245_);
    }

    public GeneratorEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


}
