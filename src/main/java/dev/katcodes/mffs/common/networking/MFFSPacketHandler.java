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

package dev.katcodes.mffs.common.networking;

import dev.katcodes.mffs.common.networking.packets.NetworkNamesPacket;
import dev.katcodes.mffs.common.world.NetworkWorldData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MFFSPacketHandler {

    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("mffs", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );


    public static void init() {
        int id = 0;
        INSTANCE.registerMessage(id++, NetworkNamesPacket.class, NetworkNamesPacket::encoder, NetworkNamesPacket::decoder, NetworkNamesPacket::messageConsumer);
    }

    @SubscribeEvent
    public void handleClientLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if(!event.getEntity().level.isClientSide) {
            Map<UUID,String> stringMap= new HashMap<>();
            NetworkWorldData.get().networks().forEach((id, data) -> {
                stringMap.put(id,data.getName());
            });
            NetworkNamesPacket packet= new NetworkNamesPacket();
            INSTANCE.send(PacketDistributor.PLAYER.with(()->(ServerPlayer)event.getEntity()),packet );
        }

    }

}
