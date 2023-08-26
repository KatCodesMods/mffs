package dev.katcodes.mffs.common.items.options;

import dev.katcodes.mffs.api.IInteriorCheck;
import dev.katcodes.mffs.api.IModularProjector;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.level.Level;

public class FieldFusionOptionItem  extends ProjectorOptionBaseItem implements IInteriorCheck {
    public FieldFusionOptionItem(Properties pProperties) {
        super(pProperties);
    }

    // TODO: Fill in once link grid made
    public boolean checkFieldFusioninfluence(GlobalPos png, Level level,
                                             IModularProjector projector) {
        return false;
    }
    @Override
    public void checkInteriorBlock(GlobalPos png, Level level, IModularProjector projector) {

    }
}
