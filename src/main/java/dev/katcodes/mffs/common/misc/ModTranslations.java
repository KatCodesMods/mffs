package dev.katcodes.mffs.common.misc;

import dev.katcodes.mffs.MFFSMod;

public class ModTranslations {
    public static final String MFFS_TAB="tabs.modid.mffs_tab";
    public static final String GUAGE_TOOLTIP="tabs.modid.mffs_tab.tooltip";
    public static final String GENERATOR_CONTAINER="container.mffs.generator";

    public static void initialize() {

        MFFSMod.REGISTRATE.get().addRawLang(MFFS_TAB, "MFFS");
        MFFSMod.REGISTRATE.get().addRawLang(GUAGE_TOOLTIP, "Energy: %s/%s RF");
        MFFSMod.REGISTRATE.get().addRawLang(GENERATOR_CONTAINER, "Generator");
    }
}
