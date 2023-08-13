package dev.katcodes.mffs.common.items;

import dev.katcodes.mffs.api.MachineType;
import dev.katcodes.mffs.api.UpgradeTypes;
import dev.katcodes.mffs.common.configs.MFFSConfigs;
import dev.katcodes.mffs.common.misc.ModTranslations;
import dev.katcodes.mffs.common.misc.PlayerHelpers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UpgradeItem extends ModItem{
    private UpgradeTypes type;
    public UpgradeItem(Properties p_41383_, UpgradeTypes type) {
        super(p_41383_);
        this.type=type;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 16;
    }

    public UpgradeTypes getType() {
        return type;
    }

    public MachineType[] getAllowedMachineTypes() {
        return type.getAllowedMachineTypes();
    }

    public boolean isAllowedMachineType(MachineType type) {
        for(MachineType allowedType : getAllowedMachineTypes()) {
            if(allowedType==type) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        int value;
        switch(((UpgradeItem)pStack.getItem()).getType()) {
            case Range -> value= MFFSConfigs.CAPACITOR_UPGRADE_RANGE.get();
            case Capacity -> value= MFFSConfigs.CAPACITOR_UPGRADE_CAPACITY.get() ;
            default -> value=0;
        }
        pTooltipComponents.add(Component.translatable("mffs.upgrade."+type.toString().toLowerCase()+".description",value,value*pStack.getCount()));

    }
}
