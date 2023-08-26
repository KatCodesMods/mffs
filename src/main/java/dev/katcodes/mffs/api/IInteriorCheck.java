package dev.katcodes.mffs.api;

import dev.katcodes.mffs.api.IModularProjector;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.level.Level;

public interface IInteriorCheck {

    public void checkInteriorBlock(GlobalPos png, Level level,
                                   IModularProjector projector);
}
