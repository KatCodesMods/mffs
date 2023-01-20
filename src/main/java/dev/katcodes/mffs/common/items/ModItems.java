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
