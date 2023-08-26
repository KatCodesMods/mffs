package dev.katcodes.mffs.common.items.modules;

import dev.katcodes.mffs.api.ForcefieldTypes;
import dev.katcodes.mffs.api.IModularProjector;
import net.minecraft.core.GlobalPos;

import java.util.Set;

public abstract class Module3DBase extends ModuleBase{
    public Module3DBase(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public ForcefieldTypes getForceFieldType() {
        //if(this instanceof Containment)
        //  return ForcefieldTypes.Containment;
        return ForcefieldTypes.Area;
    }

    public abstract void calculateField(IModularProjector projector,
                                        Set<GlobalPos> fieldPoints, Set<GlobalPos> interiorPoints);
}
