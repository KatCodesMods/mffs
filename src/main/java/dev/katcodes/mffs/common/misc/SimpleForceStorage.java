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

package dev.katcodes.mffs.common.misc;

import dev.katcodes.mffs.api.IForceEnergyCapability;

import java.util.UUID;

public class SimpleForceStorage  implements IForceEnergyCapability {

    private int maxFEStored;
    private int FEStored;
    private int maxReceive;
    private int maxExtract;
    private UUID networkID;

    private SimpleForceStorage(int maxFEStored, int FEStored, int maxReceive, int maxExtract, UUID networkID) {
        this.maxFEStored = maxFEStored;
        this.FEStored = FEStored;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.networkID = networkID;
    }
    @Override
    public UUID getNetworkID() {
        return networkID;
    }

    @Override
    public int receiveFE(int maxReceive, boolean simulate) {
        int change = 0;
        int freeSpace = maxFEStored - FEStored;
        if(maxReceive <= freeSpace)
        {
            if(!simulate)
                FEStored+=maxReceive;
        } else {
            change = maxReceive - freeSpace;
            if(!simulate)
                FEStored=maxFEStored;
        }
        return change;
    }

    @Override
    public int extractFE(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getFEStored() {
        return FEStored;
    }

    @Override
    public void setFEStored(int amount) {
        FEStored = amount;
    }

    @Override
    public int getMaxFEStored() {
        return maxFEStored;
    }

    @Override
    public void setMaxFEStored(int maxFEStored) {
        this.maxFEStored = maxFEStored;
    }

    @Override
    public boolean canExtract() {
        return FEStored > 0;
    }

    @Override
    public boolean canReceive() {
        return FEStored < maxFEStored;
    }
}
