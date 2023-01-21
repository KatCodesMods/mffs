package dev.katcodes.mffs.common.inventory;

import dev.katcodes.mffs.common.blocks.entities.GeneratorEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public class GeneratorMenu extends AbstractContainerMenu {
    private Player playerEntity;
    protected  Level level;
    private Inventory playerInventory;
    private ContainerData data;


    public GeneratorMenu(@Nullable MenuType<?> p_38851_, int id, final Inventory playerInventory) {
        super(p_38851_,id);
        this.data=new SimpleContainerData(6);
        addSlots(GeneratorEntity.createFuelHandler(null), GeneratorEntity.createCrystalHandler(null),playerInventory,data);
    }

    public GeneratorMenu(int windowId, final Inventory playerInventory, final GeneratorEntity entity, ContainerData data) {
        super(ModMenus.GENERATOR.get(), windowId);
            this.playerEntity = playerInventory.player;
            this.playerInventory = playerInventory;
            this.level = playerInventory.player.level;
            this.data=data;
            addSlots(entity.getCrystalHandler(),entity.getFuelHandler(),playerInventory,data);

    }
    private void addSlots(IItemHandler monazit, IItemHandler fuelItem, Inventory inventory, ContainerData data) {
        addSlot(new SlotItemHandler(monazit, 0, 56, 17));
        addSlot(new SlotItemHandler(fuelItem, 0, 56, 53));
        layoutPlayerInventorySlots(8, 84,new InvWrapper(inventory));
        this.addDataSlots(data);

    }

    public int getMaxEnergy() {
        return data.get(1);
    }
    public int getEnergy() {
        return data.get(0);
    }

    public boolean is_burning() {
        return this.getBurnTime() > 0;
    }

    public int getBurnTime() {
        return data.get(2);
    }

    public int getCookTime() {
        return data.get(3);
    }

    public int getCookTimeTotal() {
        return data.get(4);
    }

    public int getRecipesUsed() {
        return data.get(5);
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

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled() {
        int i = this.getRecipesUsed();
        if (i == 0) {
            i = 200;
        }

        return this.getBurnTime() * 13 / i;
    }

    @OnlyIn(Dist.CLIENT)
    public int getCookProgressionScaled() {
        int i = this.getCookTime();
        int j = this.getCookTimeTotal();
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }
}
