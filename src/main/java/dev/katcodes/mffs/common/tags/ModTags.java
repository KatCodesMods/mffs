package dev.katcodes.mffs.common.tags;

import dev.katcodes.mffs.MFFSMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModTags {

    public static class BlockTags extends BlockTagsProvider {
        public static final TagKey<Block> MONAZIT_ORE = net.minecraft.tags.BlockTags.create(new ResourceLocation("forge","ores/monazit"));

        public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, modId, existingFileHelper);
        }


        @Override
        protected void addTags(HolderLookup.Provider p_256380_) {
            this.tag(MONAZIT_ORE);
        }
    }
    public class ItemTags extends ItemTagsProvider {
        public static TagKey<Item> MONAZIT = net.minecraft.tags.ItemTags.create(new ResourceLocation(MFFSMod.MODID, "monazit"));
        public static TagKey<Item> MONAZIT_ORE = net.minecraft.tags.ItemTags.create(new ResourceLocation("forge","ores/monazit"));
        public ItemTags(PackOutput p_255871_, CompletableFuture<HolderLookup.Provider> p_256035_, TagsProvider<Block> p_256467_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
            super(p_255871_, p_256035_, p_256467_, modId, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider p_256380_) {
            this.tag(MONAZIT);
        }
    }
}
