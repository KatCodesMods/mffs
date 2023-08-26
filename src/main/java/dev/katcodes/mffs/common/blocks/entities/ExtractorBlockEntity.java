package dev.katcodes.mffs.common.blocks.entities;

import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.api.*;
import dev.katcodes.mffs.common.blocks.AbstractActivatableBlock;
import dev.katcodes.mffs.common.configs.MFFSConfigs;
import dev.katcodes.mffs.common.inventory.ExtractorMenu;
import dev.katcodes.mffs.common.items.ModItems;
import dev.katcodes.mffs.common.items.UpgradeItem;
import dev.katcodes.mffs.common.storage.MFFSEnergyStorage;
import dev.katcodes.mffs.common.world.NetworkWorldData;
import dev.katcodes.mffs.common.world.data.NetworkData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class ExtractorBlockEntity extends PoweredBlockEntity implements ILinkable, IDebugStickOutput, MenuProvider {




    // variables

    /// energy
    protected IEnergyStorage energyStorage = new MFFSEnergyStorage(MFFSConfigs.EXTRACTOR_BUFFER.get(),1000);

    public int forceEnergyBuffer = 0;
    public int maxForceEnergyBuffer = 1000000;

    public int workCycle = 0;
    public int workTicker = 20;
    public int workDone = 0;
    public int maxWorkCycle = 125;
    public int capacity = 0;



    protected LazyOptional<IEnergyStorage> energyCapability = LazyOptional.of(() -> energyStorage);

    /// generic capabilities


    /// inventory


    protected IItemHandlerModifiable upgradeSlots = createUpgradeSlots(this);

    protected IItemHandlerModifiable forciumSlot = createForciumSlot(this);

    /// Item capabilities


    protected LazyOptional<IItemHandler> forciumCapability = LazyOptional.of(() -> forciumSlot);

    public final ContainerData containerData=new ContainerData() {
        @Override
        public int get(int pIndex) {
            if(pIndex==0)
                return ExtractorBlockEntity.this.getCurrentMode();
            else if(pIndex==1)
                return 4;
            else if(pIndex==2)
                return ExtractorBlockEntity.this.workDone;
            else if(pIndex==3)
                return ExtractorBlockEntity.this.workCycle;
            else if(pIndex==4)
                return ExtractorBlockEntity.this.forceEnergyBuffer;
            else if(pIndex==5)
                return ExtractorBlockEntity.this.maxForceEnergyBuffer;
            else return 0;
        }


        @Override
        public void set(int pIndex, int pValue) {
            if(pIndex==0)
                ExtractorBlockEntity.this.setCurrentMode(pValue);
        }

        @Override
        public int getCount() {
            return 6;
        }
    };


    // Getters and Setters

    public int forceEnergyBuffer() {
        return forceEnergyBuffer;
    }

    public ExtractorBlockEntity setForceEnergyBuffer(int forceEnergyBuffer) {
        this.forceEnergyBuffer = forceEnergyBuffer;
        return this;
    }

    public int maxForceEnergyBuffer() {
        return maxForceEnergyBuffer;
    }

    public ExtractorBlockEntity setMaxForceEnergyBuffer(int maxForceEnergyBuffer) {
        this.maxForceEnergyBuffer = maxForceEnergyBuffer;
        return this;
    }

    public int workCycle() {
        return workCycle;
    }

    public ExtractorBlockEntity setWorkCycle(int workCycle) {
        this.workCycle = workCycle;
        return this;
    }

    public int workTicker() {
        return workTicker;
    }

    public ExtractorBlockEntity setWorkTicker(int workTicker) {
        this.workTicker = workTicker;
        return this;
    }

    public int workDone() {
        return workDone;
    }

    public ExtractorBlockEntity setWorkDone(int workDone) {
        this.workDone = workDone;
        return this;
    }

    public int maxWorkCycle() {
        return maxWorkCycle;
    }

    public ExtractorBlockEntity setMaxWorkCycle(int maxWorkCycle) {
        this.maxWorkCycle = maxWorkCycle;
        return this;
    }

    public int capacity() {
        return capacity;
    }

    public ExtractorBlockEntity setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public IItemHandlerModifiable getLinkCardSlot() {
        return linkCardSlot;
    }

    public void setLinkCardSlot(IItemHandlerModifiable linkCardSlot) {
        this.linkCardSlot = linkCardSlot;
    }

    public IItemHandlerModifiable getUpgradeSlots() {
        return upgradeSlots;
    }

    public void setUpgradeSlots(IItemHandlerModifiable upgradeSlots) {
        this.upgradeSlots = upgradeSlots;
    }


    public IItemHandlerModifiable getForciumSlot() {
        return forciumSlot;
    }

    public void setForciumSlot(IItemHandlerModifiable forciumSlot) {
        this.forciumSlot = forciumSlot;
    }


    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
            ((ItemStackHandler) linkCardSlot).deserializeNBT(pTag.getCompound("linkCard"));

            ((ItemStackHandler) upgradeSlots).deserializeNBT(pTag.getCompound("upgrades"));

            ((ItemStackHandler)forciumSlot).deserializeNBT(pTag.getCompound("forcium"));

            ((EnergyStorage)energyStorage).deserializeNBT(pTag.get("energy"));
        if(pTag.contains("forceEnergy"))
            forceEnergyBuffer=pTag.getInt("forceEnergy");
        if(pTag.contains("maxForceEnergy"))
            maxForceEnergyBuffer=pTag.getInt("maxForceEnergy");
        if(pTag.contains("workCycle"))
            workCycle=pTag.getInt("workCycle");
        if(pTag.contains("workTicker"))
            workTicker = pTag.getInt("workTicker");
        if(pTag.contains("workDone"))
            workDone=pTag.getInt("workDone");
        if(pTag.contains("maxWorkCycle"))
            maxWorkCycle=pTag.getInt("maxWorkCycle");
        if(pTag.contains("capacity"))
            capacity=pTag.getInt("capacity");

    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    protected boolean firstInit() {
        super.firstInit();
        AtomicBoolean present= new AtomicBoolean(false);
        LazyOptional<IForceEnergyCapability> data = this.getNetworkData();
        data.ifPresent(network -> {
            this.setChanged();
            if(network instanceof NetworkData networkData) {
                networkData.putMachine(this.getMachineType(), GlobalPos.of(level.dimension(), this.worldPosition));
            }
            present.set(true);
        });

        return present.get();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("linkCard", ((ItemStackHandler) linkCardSlot).serializeNBT());
        pTag.put("upgrades", ((ItemStackHandler) upgradeSlots).serializeNBT());
        pTag.put("forcium", ((ItemStackHandler)forciumSlot).serializeNBT());
        pTag.put("energy", ((EnergyStorage)energyStorage).serializeNBT());
        pTag.putInt("forceEnergy",forceEnergyBuffer);
        pTag.putInt("maxForceEnergy",maxForceEnergyBuffer);
        pTag.putInt("workCycle",workCycle);
        pTag.putInt("workTicker", workTicker);
        pTag.putInt("workDone",workDone);
        pTag.putInt("maxWorkCycle",maxWorkCycle);
        pTag.putInt("capacity",capacity);

    }

    public ExtractorBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Override
    protected void checkSlots() {
        int capCount = upgradeSlots.getStackInSlot(0).getCount();
        maxForceEnergyBuffer = 1000000 + (capCount * 100000);

        int boostCount = upgradeSlots.getStackInSlot(1).getCount();
        workTicker = 20 - boostCount;
        // todo: Forcium cell

    }

    // Warning, Side effect of setting energy to 0
    private boolean hasPowerToConvert() {
        if(energyStorage.getEnergyStored() >= energyStorage.getMaxEnergyStored()-1) {
            ((MFFSEnergyStorage)energyStorage).setEnergy(0);
            return true;
        }
        return false;
    }

    private boolean hasFreeForceEnergyStorage() {
        return this.maxForceEnergyBuffer > this.forceEnergyBuffer;
    }

    private boolean hasStuffToConvert() {
        if(workCycle > 0)
            return true;
        if(MFFSConfigs.ADVENTURE_MAP.get()) {
            maxWorkCycle =MFFSConfigs.MONAZIT_CELL_WORK_CYCLE.get();
            workCycle = maxWorkCycle;
            return true;
        }
        if(forciumSlot.getStackInSlot(0).getItem() == ModItems.MONAZIT.get()) {
            // Monazit
            maxWorkCycle = MFFSConfigs.MONAZIT_WORK_CYCLE.get();
            workCycle = maxWorkCycle;
            forciumSlot.getStackInSlot(0).setCount(forciumSlot.getStackInSlot(0).getCount()-1);
            return true;
        }

        // Make forcium cells

        return false;
    }

    public void transferForceEnergy() {
        if(forceEnergyBuffer > 0) {
            MFFSMod.LOGGER.info("Transfering force energy");
                getNetworkData().ifPresent(data -> {
                    MFFSMod.LOGGER.info("Transfering force energy present");
                   int powerTransferRate = data.getMaxFEStored() / 120;
                   int freeAmount = data.getMaxFEStored() - data.getFEStored();
                   if(forceEnergyBuffer > freeAmount) {
                       if(freeAmount > powerTransferRate) {
                           data.receiveFE(powerTransferRate,false);
                           forceEnergyBuffer -=powerTransferRate;
                       } else {
                           data.receiveFE(freeAmount,false);
                           forceEnergyBuffer -=freeAmount;
                       }
                   } else {
                       if(freeAmount > forceEnergyBuffer) {
                           data.receiveFE(forceEnergyBuffer,false);
                           forceEnergyBuffer -= forceEnergyBuffer;
                       } else {
                           data.receiveFE(freeAmount,false);
                           forceEnergyBuffer -=freeAmount;
                       }
                   }
                NetworkWorldData.get().setDirty();
                });
        }
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);

        if(tickCount >= workTicker) {
            checkSlots();
            if(state.getValue(AbstractActivatableBlock.ACTIVATED) == true) {
                if(workDone != energyStorage.getEnergyStored() * 100  / energyStorage.getMaxEnergyStored()) {
                    workDone = energyStorage.getEnergyStored() * 100 / energyStorage.getMaxEnergyStored();
                }
                if(workDone>100)
                    workDone=100;

                if(capacity!= forceEnergyBuffer * 100  / maxForceEnergyBuffer) {
                    capacity = forceEnergyBuffer * 100  / maxForceEnergyBuffer;
                }

                if(capacity > 100)
                    capacity = 100;
                if(hasFreeForceEnergyStorage() && this.hasStuffToConvert()) {
                    if(this.hasPowerToConvert()) {
                        workCycle -=1;
                        // ExtractorPassForceEnergyGenerate
                        forceEnergyBuffer +=MFFSConfigs.EXTRACTOR_FE_GENERATED.get();
                    }
                }
                transferForceEnergy();
                tickCount=0;
            }
            // TODO: Add forcium cell stuff
        }
    }



    @Override
    public String getDebugStickOutput() {
        return "ExtractorBlockEntity{" +
                "network UUID: " + getNetworkId() +
                "Energy: "+energyStorage.getEnergyStored() + "/" + energyStorage.getMaxEnergyStored() +
                ", ForceEnergybuffer=" + forceEnergyBuffer +
                ", MaxForceEnergyBuffer=" + maxForceEnergyBuffer +
                ", WorkCylce=" + workCycle +
                ", workTicker=" + workTicker +
                ", workDone=" + workDone +
                ", maxworkcylce=" + maxWorkCycle +
                ", capacity=" + capacity +
                ", energyCapability=" + energyCapability +
                ", upgradeSlots=" + upgradeSlots +
                ", forciumSlot=" + forciumSlot +
                ", forciumCapability=" + forciumCapability +
                ", containerData=" + containerData +
                ", tickCount=" + tickCount +
                ", initialized=" + initialized +
                '}';
    }

    @Override
    public boolean linkCard(ItemStack card) {
        return false;
    }


    @Override
    public MachineType getMachineType() {
        return MachineType.EXTRACTOR;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("mffs.extractor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ExtractorMenu(pContainerId,pPlayerInventory,containerData, this);
    }


    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        forciumCapability.invalidate();
        energyCapability.invalidate();
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap== ForgeCapabilities.ITEM_HANDLER ) {
            return forciumCapability.cast();
        }
        if(cap == ForgeCapabilities.ENERGY) {
            return energyCapability.cast();
        }

        return super.getCapability(cap, side);
    }


    // static functions
    public static IItemHandlerModifiable createForciumSlot(ExtractorBlockEntity extractorBlockEntity) {
        return new ItemStackHandler(1) {
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if(extractorBlockEntity!=null)
                    extractorBlockEntity.setChanged();
            }

            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() == ModItems.MONAZIT.get();
            }

        };
    }
    public static IItemHandlerModifiable createUpgradeSlots(ExtractorBlockEntity extractorBlockEntity) {


        return new ItemStackHandler(2) {

            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if(extractorBlockEntity!=null)
                    extractorBlockEntity.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                if (!(stack.getItem() instanceof UpgradeItem))
                    return false;
                if (slot == 0)
                    return ((UpgradeItem) stack.getItem()).getType() == UpgradeTypes.Capacity;
                else if (slot == 1)
                    return ((UpgradeItem) stack.getItem()).getType() == UpgradeTypes.Booster;
                return false;
            }

            @Override
            public int getSlotLimit(int slot) {
                if(slot==0)
                    return 9;
                else
                    return 19;
            }
        };
    }
}
