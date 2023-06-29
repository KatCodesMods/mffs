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

package dev.katcodes.mffs.common.world.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.katcodes.mffs.api.IForceEnergyCapability;
import dev.katcodes.mffs.api.MachineType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import org.antlr.v4.runtime.misc.MultiMap;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class NetworkData  implements IForceEnergyCapability {

    private UUID uuid;
    private int energy;
    private int capacity;
    private String name;

    private Map<MachineType, List<GlobalPos>> networkMachines;

    public NetworkData(UUID uuid, int energy, int capacity, Map<MachineType, List<GlobalPos>> networkMachines, String name) {
        this.uuid = uuid;
        this.energy = energy;
        this.capacity = capacity;
        this.networkMachines = networkMachines;
        this.name = name;
    }

    public NetworkData(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public static final Codec<Map<MachineType, List<GlobalPos>>> NETWORK_MACHINES_CODEC = Codec.unboundedMap(MachineType.MACHINE_TYPE_CODEC, GlobalPos.CODEC.listOf());
    public static final Codec<NetworkData> RECORD_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    UUIDUtil.CODEC.fieldOf("uuid").forGetter(NetworkData::getUuid),
                    Codec.INT.fieldOf("energy").forGetter(NetworkData::getEnergy),
                    Codec.INT.fieldOf("capacity").forGetter(NetworkData::getCapacity),
                    NETWORK_MACHINES_CODEC.fieldOf("machines").forGetter(NetworkData::getNetworkMachines),
                    Codec.STRING.fieldOf("name").forGetter(NetworkData::getName)

            ).apply(instance, NetworkData::new));

    public Map<MachineType, List<GlobalPos>> getNetworkMachines() {
        return networkMachines;
    }
    public List<GlobalPos> getNetworkList(MachineType type) {
        return networkMachines.getOrDefault(type,new ArrayList<>());
    }

    public static NetworkData of(NetworkData immutableData) {
        HashMap<MachineType, List<GlobalPos>> newMap = new HashMap<>();
        for(Map.Entry<MachineType,List<GlobalPos>> entry : immutableData.getNetworkMachines().entrySet()) {
            newMap.put(entry.getKey(),new ArrayList<>(entry.getValue()));
        }
        NetworkData data = new NetworkData(immutableData.getUuid(),immutableData.getEnergy(),immutableData.getCapacity(),newMap,immutableData.getName());
        return data;
    }

    @Override
    public UUID getNetworkID() {
        return uuid;
    }

    @Override
    public int receiveFE(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractFE(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getFEStored() {
        return 0;
    }

    @Override
    public int getMaxFEStored() {
        return capacity;
    }

    @Override
    public void setMaxFEStored(int maxFEStored) {
        this.capacity = maxFEStored;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void putMachine(MachineType type, GlobalPos pos) {
        if(networkMachines.containsKey(type)) {
            networkMachines.get(type).add(pos);
        } else {
            List<GlobalPos> posList = new ArrayList<>();
            posList.add(pos);
            networkMachines.put(type,posList);
        }
    }
}
