package dev.katcodes.mffs.common.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class MFFSEnergyStorage extends EnergyStorage  {

    public MFFSEnergyStorage(int capacity,int maxTransfer) {
        super(capacity,maxTransfer);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.capacity = maxEnergy;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
        if (this.energy > getMaxEnergyStored()) {
            this.energy = getMaxEnergyStored();
        }
    }

    public void consumeEnergy(int energy)
    {
        this.energy-=energy;
        if(this.energy<0)
            this.energy=0;
    }

    @Override
    public Tag serializeNBT() {
        return super.serializeNBT();
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        super.deserializeNBT(nbt);
    }
}
