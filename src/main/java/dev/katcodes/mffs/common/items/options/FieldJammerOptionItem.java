package dev.katcodes.mffs.common.items.options;

import dev.katcodes.mffs.api.IChecksOnAll;
import dev.katcodes.mffs.api.IModularProjector;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.level.Level;

public class FieldJammerOptionItem extends ProjectorOptionBaseItem implements IChecksOnAll {
    public FieldJammerOptionItem(Properties pProperties) {
        super(pProperties);
    }

    // TODO: Add jammer influence check function
    public boolean CheckJammerinfluence(GlobalPos png, Level level,
                                        IModularProjector Projector) {
    return false;
    }
}
