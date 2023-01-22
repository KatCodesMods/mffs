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

package dev.katcodes.mffs.common.blocks.entities;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraftforge.registries.ForgeRegistries;

import static dev.katcodes.mffs.common.blocks.ModBlocks.CAPACITOR;
import static dev.katcodes.mffs.common.blocks.ModBlocks.GENERATOR;


public class ModBlockEntities {

    public static final BlockEntityEntry<GeneratorEntity> GENERATOR_ENTITY = BlockEntityEntry.cast(GENERATOR.getSibling(ForgeRegistries.BLOCK_ENTITY_TYPES));
    public static final BlockEntityEntry<CapacitorBlockEntity> CAPACITOR_ENTITY = BlockEntityEntry.cast(CAPACITOR.getSibling(ForgeRegistries.BLOCK_ENTITY_TYPES));


    public static void initialize() {

    }
}
