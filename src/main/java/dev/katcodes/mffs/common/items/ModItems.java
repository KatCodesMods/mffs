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
import dev.katcodes.mffs.common.libs.LibItems;
import dev.katcodes.mffs.common.tags.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ModItems {

    // Simple Items
    public static final ItemEntry<Item> MONAZIT = MFFSMod.REGISTRATE.get().object(LibItems.MONAZIT)
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


    // Cards
    public static final ItemEntry<PowerLinkCardItem> POWER_LINK_CARD = MFFSMod.REGISTRATE.get().object(LibItems.POWER_LINK_CARD)
            .item(PowerLinkCardItem::new)
            .defaultLang()
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                    .texture("layer0", prov.modLoc("item/cards/" + ctx.getName())))
            .register();

    // Item Blocks

    public static ItemEntry<BlockItem> MONAZIT_ORE_ITEM = (ItemEntry<BlockItem>) ModBlocks.MONAZIT_ORE.<Item, BlockItem>getSibling(Registries.ITEM);
    public static ItemEntry<BlockItem> GENERATOR_ITEM = (ItemEntry<BlockItem>) ModBlocks.GENERATOR.<Item, BlockItem>getSibling(Registries.ITEM);

    public static ItemEntry<BlockItem> CAPACITOR_ITEM = (ItemEntry<BlockItem>) ModBlocks.CAPACITOR.<Item, BlockItem>getSibling(Registries.ITEM);
    public static ItemEntry<BlockItem> EXTRACTOR_ITEM = (ItemEntry<BlockItem>) ModBlocks.EXTRACTOR.<Item, BlockItem>getSibling(Registries.ITEM);

    public static void initialize() {

    }
}
