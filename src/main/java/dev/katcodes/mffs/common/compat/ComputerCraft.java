package dev.katcodes.mffs.common.compat;

import dan200.computercraft.api.peripheral.IPeripheral;
import dev.katcodes.mffs.common.blocks.entities.CapacitorBlockEntity;
import dev.katcodes.mffs.common.compat.peripherals.CapacitorPeripheral;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.Capability;
public class ComputerCraft {


    public static Capability<IPeripheral> CAPABILITY_PERIPHERAL;

    public static void initialize() {
        CAPABILITY_PERIPHERAL = CapabilityManager.get(new CapabilityToken<IPeripheral>(){

        });
    }
    public static <T> LazyOptional<T> getCapacitorPeripheral(CapacitorBlockEntity entity)
    {
        return LazyOptional.of(() -> new CapacitorPeripheral(entity)).cast();
    }
}
