package dev.katcodes.mffs.common.inventory;

import dev.katcodes.mffs.common.blocks.entities.CapacitorBlockEntity;
import dev.katcodes.mffs.common.blocks.entities.ExtractorBlockEntity;
import dev.katcodes.mffs.common.blocks.entities.SwitchableBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public class ExtractorMenu extends AbstractSwitchableMachineMenu {
    protected ExtractorMenu(@Nullable MenuType<?> pMenuType, int pContainerId, final Inventory playerInventory) {
        super(pMenuType, pContainerId);
        this.data=new SimpleContainerData(6);
        addSlots(ExtractorBlockEntity.createLinkCardSlot(null),ExtractorBlockEntity.createUpgradeSlots(null),ExtractorBlockEntity.createForciumSlot(null),playerInventory);
    }

    public ExtractorMenu( int windowId, Inventory playerInventory, ContainerData data, SwitchableBlockEntities entity) {
        super(ModMenus.EXTRACTOR.get(), windowId, playerInventory, data, entity);
        this.playerEntity = playerInventory.player;
        this.playerInventory = playerInventory;
        this.level = playerInventory.player.level();
        addSlots(((ExtractorBlockEntity)this.entity).getLinkCardSlot(),((ExtractorBlockEntity)this.entity).getUpgradeSlots(),((ExtractorBlockEntity)this.entity).getForciumSlot() ,playerInventory);
    }

    private void addSlots(IItemHandlerModifiable linkCard, IItemHandlerModifiable upgrades,IItemHandlerModifiable forcium,Inventory inventory) {
        addSlot(new SlotItemHandler(linkCard,0,145,39));
        addSlot(new SlotItemHandler(upgrades,0,20,66));
        addSlot(new SlotItemHandler(upgrades,1,38,66));
        addSlot(new SlotItemHandler(forcium,0,82,26));
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

    public int getWorkDone() {
        return this.data.get(2);
    }
    public int getWorkCycle() {
        return this.data.get(3);
    }

    public int getForceEnergy() {
        return this.data.get(4);
    }

    public int getMaxForceEnergy() {
        return this.data.get(5);
    }
}
