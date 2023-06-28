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

package dev.katcodes.mffs.common.networking.packets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.katcodes.mffs.common.networking.packets.client.ClientNetworkNamesHandler;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class NetworkNamesPacket {
    public static final Codec<NetworkNamesPacket> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(UUIDUtil.CODEC, Codec.STRING).fieldOf("networkNames").forGetter(NetworkNamesPacket::getNetworkNames)
    ).apply(instance, NetworkNamesPacket::new));


    private static Map<UUID,String> networkNames = new HashMap<>();

    public Map<UUID, String> getNetworkNames() {
        return networkNames;
    }
    public NetworkNamesPacket() {

    }
    public NetworkNamesPacket(Map<UUID,String> networkNames) {
        this.networkNames = networkNames;
    }

    // A class MessagePacket
    public void encoder(FriendlyByteBuf buffer) {
        // Write to buffer
        buffer.writeMap(networkNames, FriendlyByteBuf::writeUUID, FriendlyByteBuf::writeUtf);
    }
    public static NetworkNamesPacket decoder(FriendlyByteBuf buffer) {
        // Create packet from buffer data
        Map<UUID, String> networkNames;
        networkNames = buffer.readMap(FriendlyByteBuf::readUUID, FriendlyByteBuf::readUtf);
        Map<UUID,String> newMAp=new HashMap<>(networkNames);
        return new NetworkNamesPacket(newMAp);
    }

    public static void messageConsumer(NetworkNamesPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT,() -> () -> ClientNetworkNamesHandler.handlePacket(packet, ctx))
        );
        ctx.get().setPacketHandled(true);
    }



}
