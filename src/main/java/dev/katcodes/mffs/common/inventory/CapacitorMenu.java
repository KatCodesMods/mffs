/*
 * This file is part of MFFS.
 * Copyright (c) 2014-2023 KatCodes.
 * <p>
 *  MFFS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  <p>
 *  MFFS is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  <p>
 * You should have received a copy of the GNU General Public License
 * along with MFFS.  If not, see <http://www.gnu.org/licenses/>.
 * </p>
 * </p>
 * </p>
 */

package dev.katcodes.mffs.common.inventory;

import dev.katcodes.mffs.common.blocks.entities.CapacitorBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public class CapacitorMenu extends AbstractSwitchableMachineMenu {

    private CapacitorBlockEntity entity;

    public CapacitorMenu(@Nullable MenuType<?> p_38851_, int id, final Inventory playerInventory) {
        super(p_38851_,id);
        this.data=new SimpleContainerData(2);
        addSlots(playerInventory);
    }

    public CapacitorMenu(int windowId, final Inventory playerInventory, final CapacitorBlockEntity entity, ContainerData data) {
        super(ModMenus.CAPACITOR.get(), windowId, playerInventory, data);
        this.playerEntity = playerInventory.player;
        this.playerInventory = playerInventory;
        this.level = playerInventory.player.level();
        //this.data=data;
        this.entity=entity;
        addSlots(playerInventory);

    }
    private void addSlots(Inventory inventory) {

        layoutPlayerInventorySlots(8, 125,new InvWrapper(inventory));

        this.addDataSlots(data);
    }


    @Override
    public boolean clickMenuButton(Player pPlayer, int pId) {
        if(pId==0) {
            entity.toggleMode();
            return true;
        }
        return super.clickMenuButton(pPlayer, pId);
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
