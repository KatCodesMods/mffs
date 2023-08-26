package dev.katcodes.mffs.common.items.options;

import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ProjectorOptionBaseItem extends Item {

    private static List<ProjectorOptionBaseItem> instances = new ArrayList<>();

    public static List<ProjectorOptionBaseItem> get_instances() {
        return instances;
    }

    public ProjectorOptionBaseItem(Properties pProperties) {
        super(pProperties);
        instances.add(this);
    }
}
