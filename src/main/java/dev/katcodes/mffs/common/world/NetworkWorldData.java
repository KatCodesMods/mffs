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

package dev.katcodes.mffs.common.world;

import dev.katcodes.mffs.common.networking.MFFSPacketHandler;
import dev.katcodes.mffs.common.networking.packets.NetworkNamesPacket;
import dev.katcodes.mffs.common.world.data.NetworkData;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.core.jmx.Server;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class NetworkWorldData extends SavedData {

    public static NetworkWorldData CLIENT_INSTANCE=new NetworkWorldData();

    public static NetworkWorldData getClientInstance() {
        return CLIENT_INSTANCE;
    }

    public void clear() {
        networkData.clear();
    }

    public void add(NetworkData data) {
        this.networkData.put(data.getUuid(), data);
    }
    public void addAll(Map<UUID, NetworkData> data) {
        this.networkData.putAll(data);
    }

    public NetworkWorldData() {
    }

    public int getNetworkCount() {
        return networkData.size();
    }
    public static NetworkWorldData create() {
        return new NetworkWorldData();
    }
    public static NetworkWorldData get() {
        return ServerLifecycleHooks.getCurrentServer().overworld().getDataStorage().computeIfAbsent(NetworkWorldData::load, NetworkWorldData::create, "mffs_network_data");
    }
    public static NetworkWorldData load(CompoundTag compoundTag) {
        NetworkWorldData data =create();
        ListTag listTag = compoundTag.getList("networkData", 10);
        listTag.forEach(tag -> {
            NetworkData networkData = NetworkData.RECORD_CODEC.parse(NbtOps.INSTANCE, tag).getOrThrow(false, s -> {
            });
            data.networkData.put(networkData.getUuid(), networkData);
        });
        return data;
    }
    private Map<UUID, NetworkData> networkData = new HashMap<>();

    public NetworkData createNetwork(Level level,int energy, int capacity) {
        UUID uuid = UUID.randomUUID();
        NetworkData data = new NetworkData(uuid, energy, capacity, new HashMap<>(),uuid.toString());
        networkData.put(uuid, data);
        this.setDirty();
        if(!level.isClientSide) {
            Map<UUID,String> names = new HashMap<>();
            networkData.forEach((uuid1, networkData) -> {
                names.put(uuid1, networkData.getName());
            });
            NetworkNamesPacket packet = new NetworkNamesPacket(names);
            MFFSPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
        }
        return data;
    }

    public Optional<NetworkData> getNetwork(UUID uuid) {
        if(networkData.containsKey(uuid)) {
            return Optional.of(networkData.get(uuid));
        }
        return Optional.empty();
        //return networkData.get(uuid);
    }


    @Override
    public CompoundTag save(CompoundTag p_77763_) {
        ListTag listTag = new ListTag();
networkData.forEach((uuid, data) -> {
            Tag compoundTag =NetworkData.RECORD_CODEC.encodeStart(NbtOps.INSTANCE, data).get().orThrow();
            listTag.add(compoundTag);
        });
        p_77763_.put("networkData", listTag);
        return p_77763_;
    }

    public Map<UUID, NetworkData> networks() {
        return networkData;
    }
}

