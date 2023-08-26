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

import dev.katcodes.mffs.api.IForceEnergyCapability;
import dev.katcodes.mffs.api.MachineType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;

import java.util.UUID;

public abstract class NetworkedBlockEntities extends BlockEntity {

    public abstract UUID getNetworkId();

    public abstract MachineType getMachineType();

    public NetworkedBlockEntities(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    public void serverTick(Level level, BlockPos pos, BlockState state) {

    }
    public void clientTick(Level level, BlockPos pos, BlockState state) {

    }
    public static void tick(Level level, BlockPos pos, BlockState state, NetworkedBlockEntities blockEntity) {
        if(level.isClientSide)
            blockEntity.clientTick(level,pos,state);
        else
            blockEntity.serverTick(level,pos,state);
    }


}
