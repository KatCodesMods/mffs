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

import dev.katcodes.mffs.api.MachineType;
import dev.katcodes.mffs.common.blocks.AbstractActivatableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class SwitchableBlockEntities extends NetworkedBlockEntities {

    private int currentMode = getDefaultMode();

    protected int tickCount = 0;
    protected boolean initialized = false;


    public int getDefaultMode() {
        return 0;
    }

    public boolean getDefaultActive() {
        return true;
    }


    public int getCurrentMode() {
        return currentMode;
    }
    public void setCurrentMode(int currentMode) {
        this.currentMode = currentMode;
        this.setChanged();
    }

    public int[] getValidModes() {
        return new int[]{0,1,2,3,4};
    }

    public SwitchableBlockEntities(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("currentMode",currentMode);
        pTag.putInt("tickCount", tickCount);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if(pTag.contains("currentMode"))
            currentMode = pTag.getInt("currentMode");
        if(pTag.contains("tickCount"))
            tickCount=pTag.getInt("tickCount");
    }

    public void toggleMode() {
        int[] validModes = getValidModes();
        int currentMode = getCurrentMode();
        int nextMode = currentMode + 1;
        if(nextMode >= validModes.length)
            nextMode = 0;
        setCurrentMode(nextMode);
    }

    protected abstract void checkSlots();

    protected boolean firstInit() {
        return false;
    }
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        this.tickCount++;
        if(tickCount%20 ==0) {
            checkSlots();
            checkRedstone();
        }

        if(!this.initialized && this.tickCount >= 20 && this.getNetworkId()!=null){
            initialized = firstInit();
        }



    }

    protected void checkRedstone() {
        boolean redstone = level.hasNeighborSignal(getBlockPos());
        boolean activatedState = !getDefaultActive();
        if(currentMode==1) {
            if(redstone && getBlockState().getValue(AbstractActivatableBlock.ACTIVATED) == activatedState)
            {
                level.setBlock(worldPosition,getBlockState().cycle(AbstractActivatableBlock.ACTIVATED),2);
            } else if(!redstone && getBlockState().getValue(AbstractActivatableBlock.ACTIVATED) != activatedState){
                level.setBlock(worldPosition,getBlockState().cycle(AbstractActivatableBlock.ACTIVATED),2);
            }
        }
    }

}
