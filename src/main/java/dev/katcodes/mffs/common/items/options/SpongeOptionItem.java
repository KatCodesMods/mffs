package dev.katcodes.mffs.common.items.options;

import dev.katcodes.mffs.api.IInteriorCheck;
import dev.katcodes.mffs.api.IModularProjector;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.level.Level;

public class SpongeOptionItem extends ProjectorOptionBaseItem implements IInteriorCheck {
    public SpongeOptionItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void checkInteriorBlock(GlobalPos png, Level level, IModularProjector projector) {

    }
}
