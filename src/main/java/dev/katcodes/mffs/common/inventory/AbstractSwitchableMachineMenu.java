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
import dev.katcodes.mffs.common.blocks.entities.SwitchableBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSwitchableMachineMenu extends AbstractContainerMenu {
    protected Player playerEntity;
    protected Level level;
    protected Inventory playerInventory;
    protected ContainerData data;
    protected SwitchableBlockEntities entity;
    protected AbstractSwitchableMachineMenu(@Nullable MenuType<?> pMenuType, int pContainerId) {
        super(pMenuType, pContainerId);
    }

    public AbstractSwitchableMachineMenu(@Nullable MenuType<?> pMenuType, int windowId, final Inventory playerInventory,  ContainerData data, SwitchableBlockEntities entity) {
        super(pMenuType, windowId);
        this.playerEntity = playerInventory.player;
        this.playerInventory = playerInventory;
        this.level = playerInventory.player.level();
        this.data=data;
        this.entity=entity;
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
    public boolean clickMenuButton(Player pPlayer, int pId) {
        if(pId==0) {
            entity.toggleMode();
            return true;
        }
        return super.clickMenuButton(pPlayer, pId);
    }
    public int getMode() {
        return data.get(0);
    }


    public int getMaxMode() {
        return data.get(1);
    }
}
