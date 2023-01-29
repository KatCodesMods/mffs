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

import dev.katcodes.mffs.api.ILinkable;
import dev.katcodes.mffs.api.MachineType;
import dev.katcodes.mffs.common.items.CardItem;
import dev.katcodes.mffs.common.storage.MFFSEnergyStorage;
import dev.katcodes.mffs.common.world.NetworkWorldData;
import dev.katcodes.mffs.common.world.data.NetworkData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class CapacitorBlockEntity extends NetworkedBlockEntities implements ILinkable {

    private UUID networkUUID;
    private int capacity;
    private int energy;
    private String networkName;

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.putInt("energy", energy);
        compoundTag.putInt("capcity",capacity);
        if(networkUUID!=null)
            compoundTag.putUUID("networkID", networkUUID);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        energy=compoundTag.getInt("energy");
        capacity = compoundTag.getInt("capacity");
        if(compoundTag.hasUUID("networkID")){
            UUID newUUID=compoundTag.getUUID("networkID");
            if(newUUID!=networkUUID) {
                onNetworkChange(newUUID);
            }
        }

    }

    private void onNetworkChange(UUID newUUID) {
        this.networkUUID=newUUID;
        if(level == null)
            return;
        if(level.isClientSide) {
            if(NetworkWorldData.CLIENT_INSTANCE.getNetwork(newUUID).isPresent()) {
                networkName = NetworkWorldData.CLIENT_INSTANCE.getNetwork(newUUID).get().getName();
            }
        } else {
            if(getNetworkData(newUUID).isPresent())
            networkName = getNetworkData(newUUID).get().getName();
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
            Optional<NetworkData> data=getNetworkData();
            if(data.isPresent()) {
                NetworkData network=data.get();
                this.capacity=network.getCapacity();
                this.energy=network.getEnergy();
                this.setChanged();
                network.putMachine(MachineType.CAPACITOR,GlobalPos.of(level.dimension(),this.worldPosition));
            }
        }
    }

    public Optional<NetworkData> getNetworkData(UUID uuid) {
        if(uuid==null)
            return Optional.empty();
        return NetworkWorldData.get().getNetwork(uuid);
    }
    public Optional<NetworkData> getNetworkData() {
        return getNetworkData(networkUUID);
    }

    @Override
    public String toString() {
        return "CapacitorBlockEntity{" +
                "networkUUID=" + networkUUID +
                ", capacity=" + capacity +
                ", energy=" + energy +
                ", networkName='" + networkName + '\'' +
                '}';
    }

    @Override
    public boolean linkCard(ItemStack card) {
        if(!CardItem.hasLink(card)) {
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



}
