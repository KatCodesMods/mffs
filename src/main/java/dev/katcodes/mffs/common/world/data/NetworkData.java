package dev.katcodes.mffs.common.world.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.util.ExtraCodecs;

import java.util.UUID;

public class NetworkData {

    private UUID uuid;
    private int energy;
    private int capacity;

    public NetworkData(UUID uuid, int energy, int capacity) {
        this.uuid = uuid;
        this.energy = energy;
        this.capacity = capacity;
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

    public static final Codec<NetworkData> RECORD_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    UUIDUtil.CODEC.fieldOf("uuid").forGetter(NetworkData::getUuid),
                    Codec.INT.fieldOf("energy").forGetter(NetworkData::getEnergy),
                    Codec.INT.fieldOf("capacity").forGetter(NetworkData::getCapacity)
            ).apply(instance, NetworkData::new));

}
