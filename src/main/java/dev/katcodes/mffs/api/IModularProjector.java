package dev.katcodes.mffs.api;

import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.level.Level;

import java.util.Set;
import java.util.UUID;

public interface IModularProjector {
public enum ProjectorSlots {
    Card,
    Type,
    Option1,
    Option2,
    Option3,
    Distance,
    Strength,
    FocusUp,
    FocusDown,
    FocusLeft,
    FocusRight,
    Center,
    Security

}
    public Level getLevel();

    public int countItemsInSlot(ProjectorSlots slt);

    public UUID getNetworkId();

    public Set<GlobalPos> getInteriorPoints();

    public void setBurnedOut(boolean burnOut);

    public boolean isActive();

    // true / false is Projector Active

    public Direction getSide();
}
