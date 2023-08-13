package dev.katcodes.mffs.common.compat;
import net.minecraftforge.fml.ModList;
public class Compats {
    public static boolean isCCLoaded;

    public static void initialize() {
        isCCLoaded = ModList.get().isLoaded("computercraft");

        if(isCCLoaded)
            ComputerCraft.initialize();

    }
}
