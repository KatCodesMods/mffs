package dev.katcodes.mffs.common.items.modules;

import dev.katcodes.mffs.api.ForcefieldTypes;
import dev.katcodes.mffs.api.IModularProjector;
import dev.katcodes.mffs.common.items.ModItem;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract  class ModuleBase extends ModItem {
    public ModuleBase(Properties p_41383_) {
        super(p_41383_);
        instances.add(this);
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    private static List<ModuleBase> instances = new ArrayList<ModuleBase>();

    public static List<ModuleBase> get_instances() {
        return instances;
    }
    public abstract boolean supportsDistance();

    public abstract boolean supportsStrength();

    public abstract boolean supportsMatrix();

    public abstract void calculateField(IModularProjector projector,
                                        Set<GlobalPos> fieldPoints);

    public ForcefieldTypes getForceFieldType() {
        return ForcefieldTypes.Default;
    }

    public abstract boolean supportsOption(Item item);

    public abstract ProjectorModule getModuleEnum();


}
