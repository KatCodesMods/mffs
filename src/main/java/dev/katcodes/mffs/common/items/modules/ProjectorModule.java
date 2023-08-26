package dev.katcodes.mffs.common.items.modules;

import net.minecraft.util.StringRepresentable;

public enum ProjectorModule  implements StringRepresentable {
    EMPTY,
    WALL_MODULE,
    DEFLECTOR_MODULE,
    TUBE_MODULE,
    CUBE_MODULE,
    SPHERE_MODULE,
    CONTAINMENT_MODULE,
    ADV_CUBE_MODULE,
    DIAGONAL_WALL_MODULE;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
