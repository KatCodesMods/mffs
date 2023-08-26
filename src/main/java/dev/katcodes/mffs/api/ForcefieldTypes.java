package dev.katcodes.mffs.api;

import net.minecraft.util.StringRepresentable;

public enum ForcefieldTypes implements StringRepresentable {
    Default,
    Area,
    Containment,
    Zapper,
    Camo;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
