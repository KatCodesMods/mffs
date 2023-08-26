package dev.katcodes.mffs.common.blocks.entities;

import dev.katcodes.mffs.api.IForceEnergyCapability;
import dev.katcodes.mffs.api.MFFSCapabilities;
import dev.katcodes.mffs.common.items.PowerLinkCardItem;
import dev.katcodes.mffs.common.world.NetworkWorldData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public abstract class PoweredBlockEntity extends SwitchableBlockEntities{
    public PoweredBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    protected IItemHandlerModifiable linkCardSlot = createLinkCardSlot(this);

    protected LazyOptional<IForceEnergyCapability> networkCapability = LazyOptional.of(() -> NetworkWorldData.get().getNetwork(getNetworkId()));


    @Override
    public UUID getNetworkId() {
        if(linkCardSlot.getStackInSlot(0).getCount()==0)
            return null;
        Optional<UUID> id=PowerLinkCardItem.getNetworkID(linkCardSlot.getStackInSlot(0));
        return id.orElse(null);
    }

    public LazyOptional<IForceEnergyCapability> getNetworkData(UUID uuid) {
        if(uuid==null)
            return LazyOptional.empty();
        return networkCapability;
    }
    public LazyOptional<IForceEnergyCapability> getNetworkData() {
        return getNetworkData(getNetworkId());
    }
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        networkCapability.invalidate();
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap== MFFSCapabilities.FORCE_ENERGY_CAPABILITY) {
            return networkCapability.cast();
        }

        return super.getCapability(cap, side);
    }

    public static IItemHandlerModifiable createLinkCardSlot(PoweredBlockEntity extractorBlockEntity) {
        return new ItemStackHandler(1){
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if(extractorBlockEntity!=null) {
                    extractorBlockEntity.setChanged();
                    extractorBlockEntity.networkCapability.invalidate();
                }

            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() instanceof PowerLinkCardItem;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }
}
