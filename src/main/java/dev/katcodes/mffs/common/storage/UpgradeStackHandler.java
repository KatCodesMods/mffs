package dev.katcodes.mffs.common.storage;

import dev.katcodes.mffs.api.MachineType;
import dev.katcodes.mffs.common.items.UpgradeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class UpgradeStackHandler extends ItemStackHandler {
    private MachineType machineType;
    private BlockEntity thisEntity;
    public UpgradeStackHandler(MachineType machineType, BlockEntity thisEntity) {
        super(2);
        this.machineType=machineType;
        this.thisEntity=thisEntity;
    };
    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if(stack.getItem() instanceof UpgradeItem upgradeItem) {
            return upgradeItem.isAllowedMachineType(machineType);
        } else {
            return false;
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return 16;
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        if (thisEntity != null) thisEntity.setChanged();
    }
}
