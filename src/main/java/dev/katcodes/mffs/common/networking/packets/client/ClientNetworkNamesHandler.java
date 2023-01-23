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

package dev.katcodes.mffs.common.networking.packets.client;

import dev.katcodes.mffs.common.networking.packets.NetworkNamesPacket;
import dev.katcodes.mffs.common.world.NetworkWorldData;
import dev.katcodes.mffs.common.world.data.NetworkData;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class ClientNetworkNamesHandler {
    public static void handlePacket(NetworkNamesPacket msg, Supplier<NetworkEvent.Context> ctx) {
        Map<UUID, NetworkData> dataMap=new HashMap<>();
        System.out.println("Received network names");

        msg.getNetworkNames().forEach((id, name) -> {
            NetworkData data = new NetworkData(id, name);
            dataMap.put(id,data);
        });
        dataMap.forEach((id, data) -> {
            System.out.println("ID: " + id + " Name: " + data.getName());
        });
        NetworkWorldData.CLIENT_INSTANCE.clear();
        NetworkWorldData.CLIENT_INSTANCE.addAll(dataMap);
    }
}
