package dev.katcodes.mffs.common.inventory;

import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.common.blocks.entities.GeneratorEntity;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public class GeneratorContainer extends AbstractContainerMenu {
    private Player playerEntity;
    protected  Level level;
    private Inventory playerInventory;


    public GeneratorContainer(@Nullable MenuType<?> p_38851_,int id, final Inventory playerInventory) {
        super(p_38851_,id);

        addSlots(GeneratorEntity.createFuelHandler(null), GeneratorEntity.createCrystalHandler(null),playerInventory);
    }

    public GeneratorContainer(int windowId, final Inventory playerInventory, final GeneratorEntity entity) {
        super(ModMenus.GENERATOR.get(), windowId);
            this.playerEntity = playerInventory.player;
            this.playerInventory = playerInventory;
            this.level = playerInventory.player.level;
            addSlots(entity.getCrystalHandler(),entity.getFuelHandler(),playerInventory);

    }
    private void addSlots(IItemHandler monazit, IItemHandler fuelItem, Inventory inventory) {
        addSlot(new SlotItemHandler(monazit, 0, 56, 17));
        addSlot(new SlotItemHandler(fuelItem, 0, 56, 53));
        layoutPlayerInventorySlots(8, 84,new InvWrapper(inventory));
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    protected void layoutPlayerInventorySlots(int leftCol, int topRow, IItemHandler inventory) {
        // Player inventory
        addSlotBox(inventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(inventory, 0, leftCol, topRow, 9, 18);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }
}
