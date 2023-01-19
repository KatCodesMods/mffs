package dev.katcodes.mffs.blocks;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.katcodes.mffs.MFFSMod;
import net.minecraft.tags.BlockTags;

public class ModBlocks {

    public static final RegistryEntry<MonazitOreBlock> MONAZIT_ORE = MFFSMod.REGISTRATE.get().object("monazit_ore")
            .block(MonazitOreBlock::new)
            .defaultBlockstate()
            .tag(BlockTags.STONE_ORE_REPLACEABLES)
            .blockstate((ctx, provider) -> provider.simpleBlock(ctx.get()) )
            .lang("Test Block")
            .simpleItem()
            .register();

    public static void initialize() {

    }
}
