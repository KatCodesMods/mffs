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

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.api.UpgradeTypes;
import dev.katcodes.mffs.common.blocks.ModBlocks;
import dev.katcodes.mffs.common.items.modules.*;
import dev.katcodes.mffs.common.items.options.DefenseStationOptionItem;
import dev.katcodes.mffs.common.items.options.Options;
import dev.katcodes.mffs.common.items.options.ProjectorOptionBaseItem;
import dev.katcodes.mffs.common.libs.LibItems;
import dev.katcodes.mffs.common.tags.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.HashMap;
import java.util.Map;

public class ModItems {

    // Simple Items
    public static final ItemEntry<Item> MONAZIT = MFFSMod.REGISTRATE.get().object(LibItems.MONAZIT)
            .item(Item::new)
            .defaultLang()
            .tag(ModTags.ItemTags.MONAZIT)
            .recipe((ctx, provider) ->{
                    provider.smeltingAndBlasting(
                            DataIngredient.tag(ModTags.ItemTags.MONAZIT_ORE),
                            RecipeCategory.MISC,
                            ctx,
                            0.7f);
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ctx.get(),9)
                        .requires(Ingredient.of(ModBlocks.MONAZIT_BLOCK.get()))
                        .unlockedBy("has_monazit_block", RegistrateRecipeProvider.has(ModBlocks.MONAZIT_BLOCK.get()))
                        .save(provider);
            })

            .register();

    public static final ItemEntry<DebugStick> DEBUG_STICK = MFFSMod.REGISTRATE.get().object(LibItems.DEBUG_STICK)
            .item(DebugStick::new)
            .defaultLang()
            .model((ctx, prov) ->
                prov.withExistingParent(ctx.getName(),prov.mcLoc("item/handheld")).texture("layer0", prov.mcLoc("item/stick")))
            .register();

    public static final ItemEntry<Item> NETWORK_CONNECTOR = MFFSMod.REGISTRATE.get()
            .object(LibItems.NETWORK_CONNECTOR)
            .item(Item::new)
            .defaultLang()
            .recipe((ctx, provider) ->
                    ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ctx.get())
                            .pattern(" M ")
                            .pattern(" S ")
                            .pattern("CIC")
                            .define('M',ModTags.ItemTags.MONAZIT)
                            .define('C', Tags.Items.INGOTS_COPPER)
                            .define('I', Tags.Items.INGOTS_IRON)
                            .define('S',Tags.Items.RODS_WOODEN)
                            .unlockedBy("has_monazit",RegistrateRecipeProvider.has(ModTags.ItemTags.MONAZIT))
                            .unlockedBy("has_iron",RegistrateRecipeProvider.has(Tags.Items.INGOTS_IRON))
                            .unlockedBy("has_copper",RegistrateRecipeProvider.has(Tags.Items.INGOTS_COPPER))
                            .save(provider)

            )
            .register();

    // Cards
    public static final ItemEntry<PowerLinkCardItem> POWER_LINK_CARD = MFFSMod.REGISTRATE.get().object(LibItems.POWER_LINK_CARD)
            .item(PowerLinkCardItem::new)
            .defaultLang()
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                    .texture("layer0", prov.modLoc("item/cards/" + ctx.getName())))
            .register();

    public static final ItemEntry<Item> FOCUS_MATRIX=MFFSMod.REGISTRATE.get().object(LibItems.FOCUS_MATRIX)
            .item(Item::new)
            .defaultLang()
            .register();


    public static class OptionItems {
        public static void init() {}
        public static ItemBuilder<ProjectorOptionBaseItem, Registrate> registerOption(Options option) {
            return MFFSMod.REGISTRATE.get()
                    .object(option.getResourceName())
                    .item(option.getBase())
                    .lang(option.getEnglishName())
                    .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                            .texture("layer0", prov.modLoc("item/options/" + ctx.getName())));
        }
        public static ItemEntry<ProjectorOptionBaseItem> BLOCK_BREAKER =
                registerOption(Options.BlockBreaker).register();

        public static ItemEntry<ProjectorOptionBaseItem> CAMOUFLAGE = registerOption(Options.Camouflage).register();
        public static ItemEntry<ProjectorOptionBaseItem> DEFENSE_STATION = registerOption(Options.DefenseStation).register();
        public static ItemEntry<ProjectorOptionBaseItem> FIELD_FUSION = registerOption(Options.FieldFusion).register();
        public static ItemEntry<ProjectorOptionBaseItem> FIELD_MANIPULATOR = registerOption(Options.FieldManipulator).register();
        public static ItemEntry<ProjectorOptionBaseItem> FIELD_JAMMER = registerOption(Options.FieldJammer).register();
        public static ItemEntry<ProjectorOptionBaseItem> MOB_DEFENCE = registerOption(Options.MobDefence).register();
        public static ItemEntry<ProjectorOptionBaseItem> SPONGE = registerOption(Options.Sponge).register();
        public static ItemEntry<ProjectorOptionBaseItem> TOUCH_DAMAGE = registerOption(Options.TouchDamage).register();

        // TODO: Other options

    }

    // Item Blocks
    public static Map<UpgradeTypes,ItemEntry<UpgradeItem>> UPGRADE_CARDS=new HashMap<>();
    public static ItemEntry<BlockItem> MONAZIT_ORE_ITEM = (ItemEntry<BlockItem>) ModBlocks.MONAZIT_ORE.<Item, BlockItem>getSibling(Registries.ITEM);
    public static ItemEntry<BlockItem> GENERATOR_ITEM = (ItemEntry<BlockItem>) ModBlocks.GENERATOR.<Item, BlockItem>getSibling(Registries.ITEM);

    public static ItemEntry<BlockItem> CAPACITOR_ITEM = (ItemEntry<BlockItem>) ModBlocks.CAPACITOR.<Item, BlockItem>getSibling(Registries.ITEM);
    public static ItemEntry<BlockItem> EXTRACTOR_ITEM = (ItemEntry<BlockItem>) ModBlocks.EXTRACTOR.<Item, BlockItem>getSibling(Registries.ITEM);

    public static ItemEntry<BlockItem> PROJECTOR_ITEM = (ItemEntry<BlockItem>) ModBlocks.PROJECTOR.<Item, BlockItem>getSibling(Registries.ITEM);
    public static class Modules {
        public static void init(){}
        public static ItemEntry<WallModuleItem> WALL_MODULE = MFFSMod.REGISTRATE.get()
                .object(ProjectorModule.WALL_MODULE.getSerializedName())
                .item(WallModuleItem::new)
                .defaultLang()
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/modules/" + ctx.getName())))
                .register();
        public static ItemEntry<AdvCubeModuleItem> ADV_CUBE_MODULE = MFFSMod.REGISTRATE.get()
                .object(ProjectorModule.ADV_CUBE_MODULE.getSerializedName())
                .item(AdvCubeModuleItem::new)
                .defaultLang()
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/modules/" + ctx.getName())))
                .register();
        public static ItemEntry<ContainmentModuleItem> CONTAINMENT_MODULE = MFFSMod.REGISTRATE.get()
                .object(ProjectorModule.CONTAINMENT_MODULE.getSerializedName())
                .item(ContainmentModuleItem::new)
                .defaultLang()
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/modules/" + ctx.getName())))
                .register();
        public static ItemEntry<CubeModuleItem> CUBE_MODULE = MFFSMod.REGISTRATE.get()
                .object(ProjectorModule.CUBE_MODULE.getSerializedName())
                .item(CubeModuleItem::new)
                .defaultLang()
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/modules/" + ctx.getName())))
                .register();
        public static ItemEntry<DeflectorModuleItem> DEFLECTOR_MODULE = MFFSMod.REGISTRATE.get()
                .object(ProjectorModule.DEFLECTOR_MODULE.getSerializedName())
                .item(DeflectorModuleItem::new)
                .defaultLang()
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/modules/" + ctx.getName())))
                .register();
        public static ItemEntry<DiagonalWallModuleItem> DIAGONAL_WALL_MODULE = MFFSMod.REGISTRATE.get()
                .object(ProjectorModule.DIAGONAL_WALL_MODULE.getSerializedName())
                .item(DiagonalWallModuleItem::new)
                .defaultLang()
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/modules/" + ctx.getName())))
                .register();
        public static ItemEntry<SphereModuleItem> SPHERE_MODULE = MFFSMod.REGISTRATE.get()
                .object(ProjectorModule.SPHERE_MODULE.getSerializedName())
                .item(SphereModuleItem::new)
                .defaultLang()
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/modules/" + ctx.getName())))
                .register();
        public static ItemEntry<TubeModuleItem> TUBE_MODULE = MFFSMod.REGISTRATE.get()
                .object(ProjectorModule.TUBE_MODULE.getSerializedName())
                .item(TubeModuleItem::new)
                .defaultLang()
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/modules/" + ctx.getName())))
                .register();
    }

    public static void initialize() {
        Modules.init();
        OptionItems.init();
        for(UpgradeTypes type: UpgradeTypes.values()) {
            UPGRADE_CARDS.put(type, MFFSMod.REGISTRATE.get().object(type.toString().toLowerCase()+"_upgrade")
                    .item(properties -> new UpgradeItem(properties, type))
                    .defaultLang()
                    .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                            .texture("layer0", prov.modLoc("item/upgrades/" + type.toString().toLowerCase()+"_upgrade")))
                    .register());
        }
    }
}
