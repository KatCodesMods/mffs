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

package dev.katcodes.mffs.common.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class CardItem extends ModItem{
    public CardItem(Properties p_41383_) {
        super(p_41383_);
    }


    public static UUID getNetworkID(ItemStack card) {
        if(card.hasTag() && card.getTag().contains("networkID")) {
            return UUID.fromString(card.getTag().getString("networkID"));
        }
        return null;
    }

    public static void setNetworkID(ItemStack card, UUID networkID) {
        if(!card.hasTag()) {
            card.setTag(new CompoundTag());
        }
        card.getTag().putString("networkID", networkID.toString());
    }
}
