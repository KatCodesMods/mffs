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

package dev.katcodes.mffs.common.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class MFFSConfigs {
    public static final ForgeConfigSpec GENERAL_SPEC;

    public static ForgeConfigSpec.BooleanValue ADVENTURE_MAP;
    public static ForgeConfigSpec.BooleanValue GENERATE_MONAZIT;

    public static ForgeConfigSpec.IntValue GENERATOR_GENERATE;

    public static ForgeConfigSpec.IntValue CAPACITOR_CAPACITY;
    public static ForgeConfigSpec.IntValue CAPACITOR_RANGE;
    public static ForgeConfigSpec.IntValue CAPACITOR_UPGRADE_CAPACITY;
    public static ForgeConfigSpec.IntValue CAPACITOR_UPGRADE_RANGE;


    public static ForgeConfigSpec.IntValue EXTRACTOR_BUFFER;
    public static ForgeConfigSpec.IntValue MONAZIT_WORK_CYCLE;
    public static ForgeConfigSpec.IntValue MONAZIT_CELL_WORK_CYCLE;
    public static ForgeConfigSpec.IntValue EXTRACTOR_FE_GENERATED;
    public static ForgeConfigSpec.IntValue PROJECTOR_BLOCK_COST;
    public static ForgeConfigSpec.IntValue PROJECTOR_BLOCK_CREATE;
    public static ForgeConfigSpec.IntValue PROJECTOR_BLOCK_ZAPPER;

    public static ForgeConfigSpec.IntValue PROJECTOR_MAX_TICK;


    static  {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }
    private static void setupConfig(ForgeConfigSpec.Builder configBuilder) {
        configBuilder.comment("General settings").push("general");
        GENERATE_MONAZIT = configBuilder.comment("Generate Monazit ore in world").define("generate_monazit", true);
        ADVENTURE_MAP = configBuilder.comment("Set MFFS to AdventureMap Mode Extractor need no Forcium and ForceField have no click damage").define("adventuremap",false);
        configBuilder.pop();
        configBuilder.comment("Generator settings").push("generator");
        GENERATOR_GENERATE = configBuilder.comment("RF generated per fuel item").defineInRange("generate", 200, 0, Integer.MAX_VALUE);
        configBuilder.pop();
        configBuilder.push("capacitor");
        CAPACITOR_CAPACITY = configBuilder.comment("FE capacity of capacitor").defineInRange("capacity", 10000000, 0, Integer.MAX_VALUE);
        CAPACITOR_UPGRADE_CAPACITY = configBuilder.comment("FE capacity of capacitor upgrade").defineInRange("upgrade_capacity", 2000000, 0, Integer.MAX_VALUE);
        CAPACITOR_RANGE = configBuilder.comment("Range of capacitor").defineInRange("range", 8, 0, Integer.MAX_VALUE);
        CAPACITOR_UPGRADE_RANGE = configBuilder.comment("Range of capacitor upgrade").defineInRange("upgrade_range", 1, 0, Integer.MAX_VALUE);
        configBuilder.pop();
        configBuilder.push("extractor");
        EXTRACTOR_BUFFER = configBuilder.comment("Forge Energy buffer size for the extractor").defineInRange("extractor_buffer",4000,0,Integer.MAX_VALUE);
        MONAZIT_WORK_CYCLE = configBuilder.comment("Work cycle of a Monazit crystal inside the Extractor").defineInRange("monazit_work_cycle",250,0,Integer.MAX_VALUE);
        MONAZIT_CELL_WORK_CYCLE = configBuilder.comment("Work cycle of a Monazit cell inside the Extractor").defineInRange("monazit_cell_work_cycle",230,0,Integer.MAX_VALUE);
        EXTRACTOR_FE_GENERATED = configBuilder.comment("How many ForceEnergy generate per WorkCycle").defineInRange("extractor_fe_generated",12000,0,Integer.MAX_VALUE);
        configBuilder.pop();
        configBuilder.push("projector");
        PROJECTOR_BLOCK_COST = configBuilder.comment("How much upkeep FE cost a default Force Field block per second").defineInRange("block_cost",1,0,Integer.MAX_VALUE);
        PROJECTOR_BLOCK_CREATE = configBuilder.comment("Energy multiple needed to create a Force Field block (eg Cost * Create)").defineInRange("create_multiple",10,0,Integer.MAX_VALUE);
        PROJECTOR_BLOCK_ZAPPER = configBuilder.comment("Energy need multiplier used when the damage option is installed").defineInRange("damage_modifier",2,0,Integer.MAX_VALUE);
        PROJECTOR_MAX_TICK = configBuilder.comment("How many field blocks can be generated per tick?").defineInRange("blocks_per_tick",5000,0,Integer.MAX_VALUE);
    }



}
