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

import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.common.blocks.ModBlocks;
import dev.katcodes.mffs.common.tags.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ModItems {

    // Simple Items
    public static final ItemEntry<Item> MONAZIT = MFFSMod.REGISTRATE.get().object("monazit")
            .item(Item::new)
            .defaultLang()
            .tag(ModTags.ItemTags.MONAZIT)
            .recipe((ctx, provider) ->
                    provider.smeltingAndBlasting(
                            DataIngredient.tag(ModTags.ItemTags.MONAZIT_ORE),
                            RecipeCategory.MISC,
                            ctx,
                            0.7f))
            .register();


    // Item Blocks

    public static ItemEntry<BlockItem> MONAZIT_ORE_ITEM = (ItemEntry<BlockItem>) ModBlocks.MONAZIT_ORE.<Item, BlockItem>getSibling(Registries.ITEM);
    public static ItemEntry<BlockItem> GENERATOR_ITEM = (ItemEntry<BlockItem>) ModBlocks.GENERATOR.<Item, BlockItem>getSibling(Registries.ITEM);


    public static void initialize() {

    }
}
