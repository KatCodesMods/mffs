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

package dev.katcodes.mffs.api;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.UUID;

/**
 * Interface for the Force Energy Capability.
 * <p>
 * This is used to interact with the network's Force Energy.
 * </p>
 */
@AutoRegisterCapability
public interface IForceEnergyCapability {

    /**
     * Get the Network ID.
     *
     * @return The Network ID.
     */
    UUID getNetworkID();

    /**
     * Receive FE into the network.
     *
     * @param maxReceive The maximum amount of FE to receive.
     * @param simulate   If true, the receive will only be simulated.
     * @return Amount of FE that was (or would have been, if simulated) received.
     */
    int receiveFE(int maxReceive, boolean simulate);


    /**
     * Extract FE from the network.
     *
     * @param maxExtract The maximum amount of FE to extract.
     * @param simulate   If true, the extract will only be simulated.
     * @return Amount of FE that was (or would have been, if simulated) extracted.
     */
    int extractFE(int maxExtract, boolean simulate);


    /**
     * Get the amount of FE stored in the network.
     *
     * @return The amount of FE stored in the network.
     */
    int getFEStored();

    void setFEStored(int amount);


    /**
     * Get the maximum amount of FE that can be stored in the network.
     *
     * @return The maximum amount of FE that can be stored in the network.
     */
    int getMaxFEStored();

    /**
     * Set the maximum amount of FE that can be stored in the network.
     *
     * @param maxFEStored The maximum amount of FE that can be stored in the network.
     */
    void setMaxFEStored(int maxFEStored);


    /**
     * Can FE be extracted from the network?
     *
     * @return True if FE can be extracted from the network.
     */
    boolean canExtract();


    /**
     * Can FE be received by the network?
     *
     * @return True if FE can be received by the network.
     */
    boolean canReceive();


}
