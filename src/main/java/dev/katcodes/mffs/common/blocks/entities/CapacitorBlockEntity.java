/*
 * This file is part of MFFS.
 * Copyright (c) 2014-2023 KatCodes.
 * <p>
 *  MFFS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  <p>
 *  MFFS is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  <p>
 * You should have received a copy of the GNU General Public License
 * along with MFFS.  If not, see <http://www.gnu.org/licenses/>.
 * </p>
 * </p>
 * </p>
 */

package dev.katcodes.mffs.common.blocks.entities;

import dan200.computercraft.api.peripheral.IPeripheral;
import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.api.*;
import dev.katcodes.mffs.common.compat.Compats;
import dev.katcodes.mffs.common.compat.ComputerCraft;
import dev.katcodes.mffs.common.compat.peripherals.CapacitorPeripheral;
import dev.katcodes.mffs.common.configs.MFFSConfigs;
import dev.katcodes.mffs.common.inventory.CapacitorMenu;
import dev.katcodes.mffs.common.items.CardItem;
import dev.katcodes.mffs.common.items.UpgradeItem;
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
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CapacitorBlockEntity extends SwitchableBlockEntities implements ILinkable, IDebugStickOutput, MenuProvider {

    private UUID networkUUID;
    private int capacity;
    private int energy;
    private String networkName;
    private boolean initialized = false;
    int tickCount = 0;

    private int powerMode = 0;





    private IItemHandlerModifiable upgradesHandler = createUpgradeHandler(this);
    private LazyOptional<IItemHandler> upgradesHandlerLazyOptional = LazyOptional.of(() -> upgradesHandler);


    public final ContainerData containerData=new ContainerData() {
        @Override
        public int get(int pIndex) {
            if(pIndex==0)
                return CapacitorBlockEntity.this.getCurrentMode();
            else if(pIndex==1)
                return 4;
            else if(pIndex==2)
                return CapacitorBlockEntity.this.getPowerMode();
            else if(pIndex==3)
                return CapacitorBlockEntity.this.getTransmitRange();
            else if(pIndex==4)
                return CapacitorBlockEntity.this.getCapacity();
            else if(pIndex==5)
                return CapacitorBlockEntity.this.getEnergy();
            return 0;
        }
        @Override
        public void set(int pIndex, int pValue) {
            if(pIndex==0)
                CapacitorBlockEntity.this.setCurrentMode(pValue);
            if(pIndex==2)
                CapacitorBlockEntity.this.setPowerMode(pValue);
        }

        @Override
        public int getCount() {
            return 6;
        }
    };

    public int getTransmitRange() {
        return 8 * (upgradesHandler.getStackInSlot(1).getCount()+1);

    }

    public int getCapacity() {
        AtomicInteger capacity= new AtomicInteger();
        getNetworkData().ifPresent(data -> capacity.set(data.getMaxFEStored()));
        return capacity.get();
    }

    public int getEnergy() {
        AtomicInteger energy= new AtomicInteger();
        getNetworkData().ifPresent(data -> energy.set(data.getFEStored()));
        return energy.get();
    }
    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.putInt("energy", energy);
        compoundTag.putInt("capcity",capacity);
        if(networkUUID!=null)
            compoundTag.putUUID("networkID", networkUUID);
        compoundTag.putInt("powerMode", powerMode);
        compoundTag.put("upgrades", ((ItemStackHandler) upgradesHandler).serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        energy=compoundTag.getInt("energy");
        capacity = compoundTag.getInt("capacity");
        if(compoundTag.hasUUID("networkID")){
            UUID newUUID=compoundTag.getUUID("networkID");
            if(newUUID!=networkUUID) {
                onNetworkChange(newUUID);
            }
        }
        if(compoundTag.contains("powerMode"))
            powerMode = compoundTag.getInt("powerMode");
        if(compoundTag.contains("upgrades")){
            ((ItemStackHandler) upgradesHandler).deserializeNBT(compoundTag.getCompound("upgrades"));
        }

    }

    private void onNetworkChange(UUID newUUID) {
        this.networkUUID=newUUID;
        if(level == null)
            return;
        if(level.isClientSide) {
            if(NetworkWorldData.CLIENT_INSTANCE.getNetwork(newUUID).isPresent()) {
                networkName = ((NetworkData)NetworkWorldData.CLIENT_INSTANCE.getNetwork(newUUID).resolve().get()).getName();
            }
        } else {
            if(getNetworkData(newUUID).isPresent())
            networkName = ((NetworkData)getNetworkData(newUUID).resolve().get()).getName();
            this.setChanged();;
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
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

    public CapacitorBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Override
    public UUID getNetworkId() {
        return networkUUID;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if(!level.isClientSide) {

        }
    }

    public LazyOptional<IForceEnergyCapability> getNetworkData(UUID uuid) {
        if(uuid==null)
            return LazyOptional.empty();
        return NetworkWorldData.get().getNetwork(uuid);
    }
    public LazyOptional<IForceEnergyCapability> getNetworkData() {
        return getNetworkData(networkUUID);
    }

    @Override
    public String toString() {
        return "CapacitorBlockEntity{" +
                "networkUUID=" + networkUUID +
                ", capacity=" + capacity +
                ", energy=" + energy +
                ", networkName='" + networkName + '\'' +
                ", initialized=" + initialized +
                ", tickCount=" + tickCount +
                ", mode=" + getCurrentMode() +
                '}';
    }

    @Override
    public boolean linkCard(ItemStack card)
    {
        if (!CardItem.hasLink(card)) {
            if (networkUUID == null) {
                NetworkData data = NetworkWorldData.get().createNetwork(level, 0, 1000);
                this.onNetworkChange(data.getUuid());
            }
            CardItem.setNetworkID(card, this.networkUUID);
            return true;
        } else {
            return false;
        }
    }

    public void clientTick() {

    }

    private void checkSlots() {

        int curCount=upgradesHandler.getStackInSlot(0).getCount();
        int newCap= MFFSConfigs.CAPACITOR_CAPACITY.get() + ( MFFSConfigs.CAPACITOR_UPGRADE_CAPACITY.get() * curCount);
        this.getNetworkData().ifPresent(data -> {
            if(data.getMaxFEStored() != newCap) {
                data.setMaxFEStored(newCap);
                if(data.getFEStored()>newCap)
                    data.setFEStored(newCap);
            }
        });
    }

    public void serverTick(Level level, BlockPos pos, BlockState state) {

       this.tickCount++;
       if(tickCount%20 ==0)
           checkSlots();

        if(!this.initialized && this.tickCount >= 20 && this.networkUUID!=null){
            MFFSMod.LOGGER.info("Initializing Capacitor");
            LazyOptional<IForceEnergyCapability> data=this.getNetworkData();
            if(data.isPresent()) {
                IForceEnergyCapability network=data.orElseThrow(()->new RuntimeException("capability was present, then disapeared"));
                this.capacity=network.getMaxFEStored();
                this.energy=network.getFEStored();
                this.setChanged();
                ((NetworkData)network).putMachine(this.getMachineType(),GlobalPos.of(level.dimension(),this.worldPosition));
                this.initialized=true;
            }
        }
    }

    @Override
    public MachineType getMachineType() {
        return MachineType.CAPACITOR;
    }

    @Override
    public String getDebugStickOutput() {
        LazyOptional<IForceEnergyCapability> data=this.getNetworkData();
        return data.map(networkData -> "Network Info?" + networkData.toString()).orElse("No Network");
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Capacitor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return new CapacitorMenu(p_39954_, p_39955_, this, containerData);
    }

    @Override
    public int getDefaultMode() {
        return 1;
    }

    @Override
    public boolean getDefaultActive() {
        return true;
    }

    public int getPowerMode() {
        return powerMode;
    }

    public void setPowerMode(int powerMode) {
        this.powerMode = powerMode;
        this.setChanged();
    }

    public void togglePowerMode() {
        this.setPowerMode((this.getPowerMode() + 1) % 5);
    }

    public static IItemHandlerModifiable createUpgradeHandler(CapacitorBlockEntity thisEntity) {
        return new ItemStackHandler(2) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if(thisEntity!=null)
                    thisEntity.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                if (!(stack.getItem() instanceof UpgradeItem))
                    return false;
                if (slot == 0)
                    return ((UpgradeItem) stack.getItem()).getType() == UpgradeTypes.Capacity;
                else if (slot == 1)
                    return ((UpgradeItem) stack.getItem()).getType() == UpgradeTypes.Range;
                return false;
            }
        };

        //return new UpgradeStackHandler(MachineType.CAPACITOR,thisEntity);

    }

//    @Override
//    public void invalidateCaps() {
//        super.invalidateCaps();
//        upgradesHandlerLazyOptional.invalidate();
//    }
//

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == MFFSCapabilities.FORCE_ENERGY_CAPABILITY &&  this.getNetworkData().isPresent())
            return this.getNetworkData().cast();
        if (Compats.isCCLoaded && cap == ComputerCraft.CAPABILITY_PERIPHERAL)
            return ComputerCraft.getCapacitorPeripheral(this).cast();
        return super.getCapability(cap, side);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return super.getCapability(cap);
    }

    public IItemHandlerModifiable getUpgradesHandler() {
        return upgradesHandler;
    }
}
