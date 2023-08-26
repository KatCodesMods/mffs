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
import dev.katcodes.mffs.client.renderers.ForcefieldRenderer;
import dev.katcodes.mffs.common.blocks.entities.*;
import dev.katcodes.mffs.common.items.ModItems;
import dev.katcodes.mffs.common.items.modules.ProjectorModule;
import dev.katcodes.mffs.common.libs.LibBlocks;
import dev.katcodes.mffs.common.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.Tags;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ModBlocks {


    //region Register special blocks
    public static void registerSimpleFurnaceMachine(Block block, String blockname, RegistrateBlockstateProvider provider) {
        ModelFile off = provider.models().orientable(blockname+"_off", provider.modLoc("block/"+blockname+"/side_off"), provider.modLoc("block/"+blockname+"/side_off"),provider.modLoc("block/"+blockname+"/front_off"));
        ModelFile on = provider.models().orientable(blockname+"_on", provider.modLoc("block/"+blockname+"/side_on"), provider.modLoc("block/"+blockname+"/side_on"),provider.modLoc("block/"+blockname+"/front_on"));

        provider.directionalBlock(block, state -> state.getValue(GeneratorBlock.LIT) ? on : off);
    }

    public static void registerSimpleActivatableMachine(Block block, String blockname, RegistrateBlockstateProvider provider) {
        ModelFile off = provider.models().orientable(blockname+"_off", provider.modLoc("block/"+blockname+"/side_off"), provider.modLoc("block/"+blockname+"/side_off"),provider.modLoc("block/"+blockname+"/front_off"));
        ModelFile on = provider.models().orientable(blockname+"_on", provider.modLoc("block/"+blockname+"/side_on"), provider.modLoc("block/"+blockname+"/side_on"),provider.modLoc("block/"+blockname+"/front_on"));
        provider.directionalBlock(block, state -> state.getValue(AbstractActivatableBlock.ACTIVATED) ? on : off);
    }

    public static void registerProjectorMachine(Block block, String blockname, RegistrateBlockstateProvider provider) {


//        provider.getVariantBuilder(block).forAllStates(state -> {
//                    return ConfiguredModel.builder()
//                            .modelFile(
//                                    provider
//                                            .models()
//                                            .cubeAll(LibBlocks.FORCE_FIELD+"_"+state.getValue(ForceFieldBlock.BLOCK_TYPE).getSerializedName(), provider.modLoc("block/force_field/" + state.getValue(ForceFieldBlock.BLOCK_TYPE).getSerializedName()))
//                                            .renderType(provider.mcLoc("cutout")))
//                            .build();
//                }
//        );

        Map<ProjectorModule, Map<Integer,ModelFile>> models = new HashMap<>();
        for (ProjectorModule module: ProjectorModule.values()) {
            ModelFile off = provider.models().orientable(blockname+"_"+module.getSerializedName()+"_off", provider.modLoc("block/"+blockname+"/"+module.getSerializedName()+"/side_off"), provider.modLoc("block/"+blockname+"/"+module.getSerializedName()+"/side_off"),provider.modLoc("block/"+blockname+"/"+module.getSerializedName()+"/front_off"));
            ModelFile on = provider.models().orientable(blockname+"_"+module.getSerializedName()+"_on", provider.modLoc("block/"+blockname+"/"+module.getSerializedName()+"/side_on"), provider.modLoc("block/"+blockname+"/"+module.getSerializedName()+"/side_on"),provider.modLoc("block/"+blockname+"/"+module.getSerializedName()+"/front_on"));
            ModelFile vertical_off = provider.models().orientableVertical(blockname+"_"+module.getSerializedName()+"_vertical_off", provider.modLoc("block/"+blockname+"/"+module.getSerializedName()+"/side_off"), provider.modLoc("block/"+blockname+"/"+module.getSerializedName()+"/front_off"));
            ModelFile vertical_on = provider.models().orientableVertical(blockname+"_"+module.getSerializedName()+"_vertical_on", provider.modLoc("block/"+blockname+"/"+module.getSerializedName()+"/side_on"), provider.modLoc("block/"+blockname+"/"+module.getSerializedName()+"/front_on"));
            Map<Integer,ModelFile> local_models=new HashMap<>();
            local_models.put(0,off);
            local_models.put(1,on);
            local_models.put(2,vertical_off);
            local_models.put(3,vertical_on);
            models.put(module,local_models);
        }

        provider.directionalBlock(block, state ->
                models.get(
                        state.getValue(ProjectorBlock.MODULE))
                        .get(state.getValue(ProjectorBlock.ACTIVATED)
                                ?
                                (state.getValue(ProjectorBlock.FACING).getAxis().isVertical()? 1:3)
                                : (state.getValue(ProjectorBlock.FACING).getAxis().isVertical()? 2:0) )
        );
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
            .properties(properties -> properties.strength(3.0f,3.0f))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
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
                            registerSimpleFurnaceMachine(ctx.get(), LibBlocks.GENERATOR, provider))
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
            .properties(properties -> properties.strength(3.0f,3.0f))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
            .item()
            .model((ctx, provider) -> provider.withExistingParent(ctx.getName(), provider.modLoc("block/generator_off")))
            .build()
            .blockEntity(GeneratorEntity::new)
            .build()

            .register();

            public static final RegistryEntry<Block> MONAZIT_BLOCK = MFFSMod.REGISTRATE.get()
                    .object(LibBlocks.MONAZIT_BLOCK)
                    .block(Block::new)
                    .defaultBlockstate()
                    .defaultLang()
                    .defaultLoot()
                    .recipe((ctx,prov) ->
                            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.getEntry())
                                    .requires(Ingredient.of(ModTags.ItemTags.MONAZIT),9)
                                    .unlockedBy("has_monazit", RegistrateRecipeProvider.has(ModTags.ItemTags.MONAZIT))
                                    .save(prov)
                    )
                    .item()
                    .build()
                    .register();
            public static final RegistryEntry<CapacitorBlock> CAPACITOR = MFFSMod.REGISTRATE.get()
                    .object(LibBlocks.CAPACITOR)
                    .block(CapacitorBlock::new)
                    .blockstate(
                            (ctx, provider) ->
                                    registerSimpleActivatableMachine(ctx.get(), LibBlocks.CAPACITOR, provider))
                    .defaultLang()

                    .properties(properties -> properties.strength(3.0f,3.0f))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .recipe((ctx, prov) -> {
                        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.getEntry())
                                .pattern("III")
                                .pattern("IBI")
                                .pattern("ICI")
                                .define('I', Tags.Items.INGOTS_IRON)
                                .define('B', ModBlocks.MONAZIT_BLOCK.get())
                                .define('C',ModItems.NETWORK_CONNECTOR.get())

                                .unlockedBy("has_monazit", RegistrateRecipeProvider.has(ModTags.ItemTags.MONAZIT))
                                .save(prov);

                    })
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
                    .properties(properties -> properties.strength(3.0f,3.0f))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .recipe((ctx, prov) -> {
                        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.getEntry())
                                .pattern("III")
                                .pattern("IGI")
                                .pattern("ICI")
                                .define('I', Tags.Items.INGOTS_IRON)
                                .define('G', Blocks.FURNACE)
                                .define('C',ModItems.NETWORK_CONNECTOR.get())

                                .unlockedBy("has_monazit", RegistrateRecipeProvider.has(ModTags.ItemTags.MONAZIT))
                                .save(prov);

                    })
                    .item()
                    .model((ctx, provider) -> provider.withExistingParent(ctx.getName(), provider.modLoc("block/extractor_off")))
                    .build()
                    .blockEntity(ExtractorBlockEntity::new)
                    .build()
                    .register();

    public static final RegistryEntry<ProjectorBlock> PROJECTOR = MFFSMod.REGISTRATE.get()
            .object(LibBlocks.PROJECTOR)
            .block(ProjectorBlock::new)
            .blockstate(
                    (ctx, provider) ->
                            registerProjectorMachine(ctx.get(), LibBlocks.PROJECTOR, provider))
            .defaultLang()
            .properties(properties -> properties.strength(3.0f,3.0f))
            .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)

            .item()
            .model((ctx, provider) -> provider.withExistingParent(ctx.getName(), provider.modLoc("block/projector_empty_off")))
            .build()
            .blockEntity(ProjectorBlockEntity::new)
            .build()
            .register();

            public static final RegistryEntry<ForceFieldBlock> FORCE_FIELD = MFFSMod.REGISTRATE.get()
                    .object(LibBlocks.FORCE_FIELD)
                    .block(ForceFieldBlock::new)
                    .defaultBlockstate()
                    .defaultLang()
                    .blockstate(
                            (ctx,provider) ->

                                provider.getVariantBuilder(ctx.get()).forAllStates(state -> {
                                    return ConfiguredModel.builder()
                                            .modelFile(
                                                    provider
                                                            .models()
                                                            .cubeAll(LibBlocks.FORCE_FIELD+"_"+state.getValue(ForceFieldBlock.BLOCK_TYPE).getSerializedName(), provider.modLoc("block/force_field/" + state.getValue(ForceFieldBlock.BLOCK_TYPE).getSerializedName()))
                                                            .renderType(provider.mcLoc("cutout")))
                                            .build();
                                        }
                                )

                    )
                    //.properties(properties -> properties.strength(-1.0F, 3600000.0F))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.isViewBlocking(ModBlocks::never))
                    .blockEntity(ForceFieldBlockEntity::new)
                    .renderer(() ->  context -> new ForcefieldRenderer(context.getBlockEntityRenderDispatcher(),context.getBlockRenderDispatcher()))
                    .build()
                    .register();


    private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }
    //endregion
    public static void initialize() {

    }
}
