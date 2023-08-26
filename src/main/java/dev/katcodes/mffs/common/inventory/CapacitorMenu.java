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
import dev.katcodes.mffs.common.storage.UpgradeStackHandler;
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

    public CapacitorMenu(@Nullable MenuType<?> p_38851_, int id, final Inventory playerInventory) {
        super(p_38851_,id);
        this.data=new SimpleContainerData(6);
        addSlots(CapacitorBlockEntity.createUpgradeHandler(null),playerInventory);
    }

    public CapacitorMenu(int windowId, final Inventory playerInventory, final CapacitorBlockEntity entity, ContainerData data) {
        super(ModMenus.CAPACITOR.get(), windowId, playerInventory, data, entity);
        this.playerEntity = playerInventory.player;
        this.playerInventory = playerInventory;
        this.level = playerInventory.player.level();
        addSlots(((CapacitorBlockEntity)this.entity).getUpgradesHandler(), playerInventory);

    }
    private void addSlots(IItemHandler upgrades, Inventory inventory) {
        addSlot(new SlotItemHandler(upgrades, 0, 154, 47));
        addSlot(new SlotItemHandler(upgrades, 1, 154, 67));
        layoutPlayerInventorySlots(8, 125,new InvWrapper(inventory));

        this.addDataSlots(data);
    }


    @Override
    public boolean clickMenuButton(Player pPlayer, int pId) {
        if(pId==1) {
            ((CapacitorBlockEntity)entity).togglePowerMode();
            return true;
        }
        return super.clickMenuButton(pPlayer, pId);
    }





    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }

    public int getPowerMode() {
        return data.get(2);
    }

    public int getTransmitRange() {
        return data.get(3);
    }

    public int getCapacity() {
        return data.get(4);
    }

    public int getEnergy() {
        return data.get(5);
    }

    public int getPercentCapacity() {
        float capacity = (float) getEnergy() /(float) getCapacity() * 100.0f;
        if(capacity>100)
            capacity=100;
        return (int)capacity;
    }

}
