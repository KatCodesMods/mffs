package dev.katcodes.mffs.common.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class MFFSConfigs {
    public static final ForgeConfigSpec GENERAL_SPEC;
    public static ForgeConfigSpec.BooleanValue GENERATE_MONAZIT;
    public static ForgeConfigSpec.BooleanValue ENABLE_GENERATOR;

    public static ForgeConfigSpec.IntValue GENERATOR_GENERATE;

    static  {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }
    private static void setupConfig(ForgeConfigSpec.Builder configBuilder) {
        configBuilder.comment("General settings").push("general");
        GENERATE_MONAZIT = configBuilder.comment("Generate Monazit ore in world").define("generate_monazit", true);
        ENABLE_GENERATOR = configBuilder.comment("Enable the RF Generator").define("enable_generator", true);
        configBuilder.push("generator");
        GENERATOR_GENERATE = configBuilder.comment("RF generated per fuel item").defineInRange("generate", 200, 0, Integer.MAX_VALUE);
        configBuilder.pop();
    }


}
