package dev.katcodes.mffs.common.misc;

import dev.katcodes.mffs.MFFSMod;

public class ModTranslations {
    public static final String MFFS_TAB="tabs.modid.mffs_tab";

    public static void initialize() {
        MFFSMod.REGISTRATE.get().addRawLang(MFFS_TAB, "MFFS");
    }
}
