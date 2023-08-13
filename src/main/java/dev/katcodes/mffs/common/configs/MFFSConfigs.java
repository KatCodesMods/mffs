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
    public static ForgeConfigSpec.BooleanValue GENERATE_MONAZIT;

    public static ForgeConfigSpec.IntValue GENERATOR_GENERATE;

    public static ForgeConfigSpec.IntValue CAPACITOR_CAPACITY;
    public static ForgeConfigSpec.IntValue CAPACITOR_RANGE;
    public static ForgeConfigSpec.IntValue CAPACITOR_UPGRADE_CAPACITY;
    public static ForgeConfigSpec.IntValue CAPACITOR_UPGRADE_RANGE;


    static  {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }
    private static void setupConfig(ForgeConfigSpec.Builder configBuilder) {
        configBuilder.comment("General settings").push("general");
        GENERATE_MONAZIT = configBuilder.comment("Generate Monazit ore in world").define("generate_monazit", true);
        configBuilder.push("generator");
        GENERATOR_GENERATE = configBuilder.comment("RF generated per fuel item").defineInRange("generate", 200, 0, Integer.MAX_VALUE);
        configBuilder.pop();
        configBuilder.push("capacitor");
        CAPACITOR_CAPACITY = configBuilder.comment("FE capacity of capacitor").defineInRange("capacity", 10000000, 0, Integer.MAX_VALUE);
        CAPACITOR_UPGRADE_CAPACITY = configBuilder.comment("FE capacity of capacitor upgrade").defineInRange("upgrade_capacity", 2000000, 0, Integer.MAX_VALUE);
        CAPACITOR_RANGE = configBuilder.comment("Range of capacitor").defineInRange("range", 8, 0, Integer.MAX_VALUE);
        CAPACITOR_UPGRADE_RANGE = configBuilder.comment("Range of capacitor upgrade").defineInRange("upgrade_range", 1, 0, Integer.MAX_VALUE);

    }



}
