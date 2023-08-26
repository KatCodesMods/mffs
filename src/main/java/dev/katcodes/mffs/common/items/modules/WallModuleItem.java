package dev.katcodes.mffs.common.items.modules;

import dev.katcodes.mffs.api.IModularProjector;
import dev.katcodes.mffs.common.items.ModItem;
import dev.katcodes.mffs.common.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.item.Item;

import java.util.Set;

public class WallModuleItem extends ModuleBase{
    public WallModuleItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public boolean supportsDistance() {
        return true;
    }

    @Override
    public boolean supportsStrength() {
        return true;
    }

    @Override
    public boolean supportsMatrix() {
        return true;
    }

    @Override
    public void calculateField(IModularProjector projector, Set<GlobalPos> fieldPoints) {
        int tpx = 0;
        int tpy = 0;
        int tpz = 0;
        for (int x1 = 0 - projector.countItemsInSlot(IModularProjector.ProjectorSlots.FocusLeft); x1 < projector
                .countItemsInSlot(IModularProjector.ProjectorSlots.FocusRight) + 1; x1++) {
            for (int z1 = 0 - projector.countItemsInSlot(IModularProjector.ProjectorSlots.FocusDown); z1 < projector
                    .countItemsInSlot(IModularProjector.ProjectorSlots.FocusUp) + 1; z1++) {
                for (int y1 = 1; y1 < projector
                        .countItemsInSlot(IModularProjector.ProjectorSlots.Strength) + 1 + 1; y1++) {
                    if (projector.getSide() == Direction.DOWN) {
                        tpy = y1 - y1 - y1
                                - projector.countItemsInSlot(IModularProjector.ProjectorSlots.Distance);
                        tpx = x1;
                        tpz = z1 - z1 - z1;
                    }

                    if (projector.getSide() == Direction.UP) {
                        tpy = y1 + projector.countItemsInSlot(IModularProjector.ProjectorSlots.Distance);
                        tpx = x1;
                        tpz = z1 - z1 - z1;
                    }

                    if (projector.getSide() == Direction.NORTH) {
                        tpz = y1 - y1 - y1
                                - projector.countItemsInSlot(IModularProjector.ProjectorSlots.Distance);
                        tpx = x1 - x1 - x1;
                        tpy = z1;
                    }

                    if (projector.getSide() == Direction.EAST) {
                        tpz = y1 + projector.countItemsInSlot(IModularProjector.ProjectorSlots.Distance);
                        tpx = x1;
                        tpy = z1;
                    }

                    if (projector.getSide() == Direction.SOUTH) {
                        tpx = y1 - y1 - y1
                                - projector.countItemsInSlot(IModularProjector.ProjectorSlots.Distance);
                        tpz = x1;
                        tpy = z1;
                    }
                    if (projector.getSide() == Direction.WEST) {
                        tpx = y1 + projector.countItemsInSlot(IModularProjector.ProjectorSlots.Distance);
                        tpz = x1 - x1 - x1;
                        tpy = z1;
                    }

                    if ((projector.getSide() == Direction.UP || projector.getSide() == Direction.DOWN)
                            && ((tpx == 0 && tpz != 0)
                            || (tpz == 0 && tpx != 0) || (tpz == 0 && tpx == 0))
                            || (projector.getSide() == Direction.NORTH || projector.getSide() == Direction.SOUTH)
                            && ((tpx == 0 && tpy != 0)
                            || (tpy == 0 && tpx != 0) || (tpy == 0 && tpx == 0))
                            || (projector.getSide() == Direction.EAST || projector.getSide() == Direction.WEST)
                            && ((tpz == 0 && tpy != 0)
                            || (tpy == 0 && tpz != 0) || (tpy == 0 && tpz == 0)))

                    {
                        fieldPoints.add(GlobalPos.of(projector.getLevel().dimension(), new BlockPos(tpx,tpy,tpz)));
                    }
                }
            }
        }
    }

    @Override
    public boolean supportsOption(Item item) {

        if(item == ModItems.OptionItems.BLOCK_BREAKER.get())
            return true;
        if(item == ModItems.OptionItems.CAMOUFLAGE.get())
            return true;

        return item == ModItems.OptionItems.TOUCH_DAMAGE.get();
    }

    @Override
    public ProjectorModule getModuleEnum() {
        return ProjectorModule.WALL_MODULE;
    }
}
