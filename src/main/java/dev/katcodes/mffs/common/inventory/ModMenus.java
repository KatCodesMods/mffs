package dev.katcodes.mffs.common.inventory;

import com.tterrag.registrate.util.entry.MenuEntry;
import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.client.gui.screens.GeneratorScreen;

public class ModMenus {

    public static final MenuEntry<GeneratorMenu> GENERATOR = MFFSMod.REGISTRATE.get().menu("generator", (type,windowId,inv)->new GeneratorMenu(type,windowId,inv), ()->GeneratorScreen::new)
            .register();

    public static void initialize() {
    }
}
