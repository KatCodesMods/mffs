package dev.katcodes.mffs.common.items.modules;

import dev.katcodes.mffs.api.IModularProjector;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.item.Item;

import java.util.Set;

public class CubeModuleItem extends Module3DBase {
    public CubeModuleItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void calculateField(IModularProjector projector, Set<GlobalPos> fieldPoints, Set<GlobalPos> interiorPoints) {

    }

    @Override
    public boolean supportsDistance() {
        return false;
    }

    @Override
    public boolean supportsStrength() {
        return false;
    }

    @Override
    public boolean supportsMatrix() {
        return false;
    }

    @Override
    public void calculateField(IModularProjector projector, Set<GlobalPos> fieldPoints) {

    }

    @Override
    public boolean supportsOption(Item item) {
        return false;
    }

    @Override
    public ProjectorModule getModuleEnum() {
        return ProjectorModule.CUBE_MODULE;
    }
}
