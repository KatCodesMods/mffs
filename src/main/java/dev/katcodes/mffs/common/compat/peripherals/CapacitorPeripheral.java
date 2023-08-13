package dev.katcodes.mffs.common.compat.peripherals;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IPeripheral;
import dev.katcodes.mffs.common.blocks.entities.CapacitorBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

public class CapacitorPeripheral extends GeneralPeripheral implements IPeripheral {

    private final CapacitorBlockEntity entity;

    public CapacitorPeripheral(CapacitorBlockEntity entity) {
        super(entity);
        this.entity=entity;
    }
    @Override
    public String getType() {
        return "mffs:capacitor";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return false;
    }



    @LuaFunction
    public boolean hasNetwork() {
        return entity.getNetworkData().isPresent();
    }

    @LuaFunction
    public int getTransmitRange() {
        return entity.getTransmitRange();
    }


    @LuaFunction
    public MethodResult getStoredForceEnergy() {
        AtomicInteger curEnergy= new AtomicInteger();
        AtomicInteger freeEnergy= new AtomicInteger();
        this.entity.getNetworkData().ifPresent(data -> {
            curEnergy.set(data.getFEStored());
            freeEnergy.set(data.getMaxFEStored() - curEnergy.get());
        });
        return MethodResult.of(curEnergy.get(),freeEnergy.get());
    }

    @LuaFunction(mainThread = true)
    public boolean setPowerLinkMode(int mode) throws LuaException {
        if(mode < 0 || mode > 4)
            throw new LuaException("mode must be within valid range");
        this.entity.setPowerMode(mode);
        return true;
    }




}
