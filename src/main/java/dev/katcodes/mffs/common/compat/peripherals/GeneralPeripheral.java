package dev.katcodes.mffs.common.compat.peripherals;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import dev.katcodes.mffs.common.blocks.entities.SwitchableBlockEntities;
import org.jetbrains.annotations.Nullable;

public abstract class GeneralPeripheral implements IPeripheral {


    protected SwitchableBlockEntities entity;
    public GeneralPeripheral(SwitchableBlockEntities entity) {
        this.entity=entity;
    }

    @LuaFunction
    public int getActive() {
        return entity.getCurrentMode();
    }

    @LuaFunction
    public boolean setActive(int value) {
        return false;
    }
}
