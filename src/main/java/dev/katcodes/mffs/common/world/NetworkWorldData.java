package dev.katcodes.mffs.common.world;

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
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NetworkWorldData extends SavedData {


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

    public NetworkData createNetwork(int energy, int capacity) {
        UUID uuid = UUID.randomUUID();
        NetworkData data = new NetworkData(uuid, energy, capacity);
        networkData.put(uuid, data);
        this.setDirty();
        return data;
    }

    public NetworkData getNetwork(UUID uuid) {
        return networkData.get(uuid);
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
}

