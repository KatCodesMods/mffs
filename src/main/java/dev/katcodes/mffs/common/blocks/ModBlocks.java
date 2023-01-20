package dev.katcodes.mffs.common.blocks;

import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.common.blocks.entities.GeneratorEntity;
import dev.katcodes.mffs.common.tags.ModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.Tags;

public class ModBlocks {

    public static void registerSimpleActivatableMachine(Block block, String blockname, RegistrateBlockstateProvider provider) {
        ModelFile off = provider.models().orientable(blockname+"_off", provider.modLoc("block/"+blockname+"/side_off"), provider.modLoc("block/"+blockname+"/front_off"),provider.modLoc("block/"+blockname+"/side_off"));
        ModelFile on = provider.models().orientable(blockname+"_on", provider.modLoc("block/"+blockname+"/side_on"), provider.modLoc("block/"+blockname+"/front_on"),provider.modLoc("block/"+blockname+"/side_on"));
        provider.horizontalBlock(block, state -> state.getValue(AbstractMachineBlock.LIT) ? on : off);
    }
    public static final RegistryEntry<MonazitOreBlock> MONAZIT_ORE = MFFSMod.REGISTRATE.get().object("monazit_ore")
            .block(MonazitOreBlock::new)
            .defaultBlockstate()
            .blockstate((ctx, provider) -> provider.simpleBlock(ctx.get()) )
            .defaultLang()
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .tag(Tags.Blocks.ORES)
            .tag(Tags.Blocks.ORES_IN_GROUND_STONE)
            .tag(Tags.Blocks.ORE_RATES_DENSE)
            .tag(BlockTags.NEEDS_STONE_TOOL)
            .tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
            .tag(ModTags.BlockTags.MONAZIT_ORE)
            .item()
            .tag(Tags.Items.ORES)
            .tag(Tags.Items.ORES_IN_GROUND_STONE)
            .tag(Tags.Items.ORE_RATES_DENSE)
            .tag(Tags.Items.RAW_MATERIALS)
            .tag(ModTags.ItemTags.MONAZIT_ORE)
            .build()

            .register();

    public static final RegistryEntry<GeneratorBlock> GENERATOR = MFFSMod.REGISTRATE.get().object("generator")
            .block(GeneratorBlock::new)
            .defaultBlockstate()
            .blockstate(
                    (ctx, provider) ->
                            registerSimpleActivatableMachine(ctx.get(), "generator", provider))
            .defaultLang()
            .item()
            .model((ctx, provider) -> provider.withExistingParent(ctx.getName(), provider.modLoc("block/generator_off")))
            .build()
            .blockEntity(GeneratorEntity::new)
            .build()
            .register();

    public static void initialize() {

    }
}
