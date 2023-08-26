package dev.katcodes.mffs.common.inventory;

import dev.katcodes.mffs.common.blocks.entities.ProjectorBlockEntity;
import dev.katcodes.mffs.common.blocks.entities.SwitchableBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public class ProjectorMenu extends AbstractSwitchableMachineMenu {

    protected ProjectorMenu(@Nullable MenuType<?> pMenuType, int pContainerId, final Inventory playerInventory) {
        super(pMenuType, pContainerId);
        this.data=new SimpleContainerData(6);
        addSlots(ProjectorBlockEntity.createLinkCardSlot(null),ProjectorBlockEntity.createTypeSlot(null),ProjectorBlockEntity.createOptionsHandler(null),ProjectorBlockEntity.createDistanceHandler(null),ProjectorBlockEntity.createStrengthHandler(null),ProjectorBlockEntity.createFociHandler(null),ProjectorBlockEntity.createCenterHandler(null),ProjectorBlockEntity.createSecurityHandler(null),playerInventory);
    }

    public ProjectorMenu(int windowId, Inventory playerInventory, ContainerData data, SwitchableBlockEntities entity) {
        super(ModMenus.PROJECTOR.get(), windowId, playerInventory, data, entity);
        this.playerEntity = playerInventory.player;
        this.playerInventory = playerInventory;
        this.level = playerInventory.player.level();
        addSlots(((ProjectorBlockEntity)this.entity).getLinkCardSlot(),((ProjectorBlockEntity)this.entity).getModuleTypeHandler(),((ProjectorBlockEntity)this.entity).getOptionsHandler(),((ProjectorBlockEntity)this.entity).getDistanceHandler(),((ProjectorBlockEntity)this.entity).getStrengthHandler(),((ProjectorBlockEntity)this.entity).getFociHandler(),((ProjectorBlockEntity)this.entity).getCenterHandler(),((ProjectorBlockEntity)this.entity).getSecurityHandler(),playerInventory);
    }

    private void addSlots(IItemHandlerModifiable linkCard, IItemHandlerModifiable type, IItemHandlerModifiable options, IItemHandlerModifiable distance, IItemHandlerModifiable strength, IItemHandlerModifiable foci, IItemHandlerModifiable center, IItemHandlerModifiable security ,Inventory inventory) {
        addSlot(new SlotItemHandler(linkCard,0,11,61));
        addSlot(new SlotItemHandler(type,0,11,38));
        addSlot(new SlotItemHandler(options,0,120,82));
        addSlot(new SlotItemHandler(options,1,138,82));
        addSlot(new SlotItemHandler(options,2,156,82));
        addSlot(new SlotItemHandler(distance,0,119,64));
        addSlot(new SlotItemHandler(strength,0,155,64));

        addSlot(new SlotItemHandler(foci,0,137,28));
        addSlot(new SlotItemHandler(foci,1,137,62));
        addSlot(new SlotItemHandler(foci,3,120,45));
        addSlot(new SlotItemHandler(foci,2,154,45));

        addSlot(new SlotItemHandler(center,0,137,45));
        addSlot(new SlotItemHandler(security,0,92,38));
        layoutPlayerInventorySlots(8, 104,new InvWrapper(inventory));

        this.addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

}
