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

package dev.katcodes.mffs.common.blocks;

import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.common.blocks.entities.CapacitorBlockEntity;
import dev.katcodes.mffs.common.blocks.entities.GeneratorEntity;
import dev.katcodes.mffs.common.libs.LibBlocks;
import dev.katcodes.mffs.common.tags.ModTags;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;

public class ModBlocks {


    //region Register special blocks
    public static void registerSimpleActivatableMachine(Block block, String blockname, RegistrateBlockstateProvider provider) {
        ModelFile off = provider.models().orientable(blockname+"_off", provider.modLoc("block/"+blockname+"/side_off"), provider.modLoc("block/"+blockname+"/front_off"),provider.modLoc("block/"+blockname+"/side_off"));
        ModelFile on = provider.models().orientable(blockname+"_on", provider.modLoc("block/"+blockname+"/side_on"), provider.modLoc("block/"+blockname+"/front_on"),provider.modLoc("block/"+blockname+"/side_on"));
        provider.horizontalBlock(block, state -> state.getValue(AbstractMachineBlock.LIT) ? on : off);
    }

    //endregion

    //region Simple Blocks
    public static final RegistryEntry<MonazitOreBlock> MONAZIT_ORE = MFFSMod.REGISTRATE.get().object(LibBlocks.MONAZIT_ORE)
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

    //endregion


    //region Basic Machines

    public static final RegistryEntry<GeneratorBlock> GENERATOR = MFFSMod.REGISTRATE.get().object(LibBlocks.GENERATOR)
            .block(GeneratorBlock::new)
            .blockstate(
                    (ctx, provider) ->
                            registerSimpleActivatableMachine(ctx.get(), LibBlocks.GENERATOR, provider))
            .defaultLang()
            .recipe((ctx, prov) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.getEntry())
                        .pattern("III")
                        .pattern("IMI")
                        .pattern("III")
                        .define('I', Tags.Items.INGOTS_IRON)
                        .define('M', ModTags.ItemTags.MONAZIT)

                        .unlockedBy("has_monazit", RegistrateRecipeProvider.has(ModTags.ItemTags.MONAZIT))
                        .save(prov);

            })
            .item()
            .model((ctx, provider) -> provider.withExistingParent(ctx.getName(), provider.modLoc("block/generator_off")))
            .build()
            .blockEntity(GeneratorEntity::new)
            .build()

            .register();

            public static final RegistryEntry<CapacitorBlock> CAPACITOR = MFFSMod.REGISTRATE.get()
                    .object(LibBlocks.CAPACITOR)
                    .block(CapacitorBlock::new)
                    .blockstate(
                            (ctx, provider) ->
                                    registerSimpleActivatableMachine(ctx.get(), LibBlocks.CAPACITOR, provider))
                    .defaultLang()
                    .item()
                    .model((ctx, provider) -> provider.withExistingParent(ctx.getName(), provider.modLoc("block/capacitor_off")))
                    .build()
                    .blockEntity(CapacitorBlockEntity::new)
                    .build()
                    .register();


            public static final RegistryEntry<ExtractorBlock> EXTRACTOR = MFFSMod.REGISTRATE.get()
                    .object(LibBlocks.EXTRACTOR)
                    .block(ExtractorBlock::new)
                    .blockstate(
                            (ctx, provider) ->
                                    registerSimpleActivatableMachine(ctx.get(), LibBlocks.EXTRACTOR, provider))
                    .defaultLang()
                    .item()
                    .model((ctx, provider) -> provider.withExistingParent(ctx.getName(), provider.modLoc("block/extractor_off")))
                    .build()
                    .register();


    //endregion
    public static void initialize() {

    }
}
