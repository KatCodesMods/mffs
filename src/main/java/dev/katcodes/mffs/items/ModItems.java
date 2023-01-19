package dev.katcodes.mffs.items;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.katcodes.mffs.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ModItems {

    public static ItemEntry<BlockItem> MONAZIT_ORE_ITEM = (ItemEntry<BlockItem>) ModBlocks.MONAZIT_ORE.<Item, BlockItem>getSibling(Registries.ITEM);
    public static void initialize() {

    }
}
