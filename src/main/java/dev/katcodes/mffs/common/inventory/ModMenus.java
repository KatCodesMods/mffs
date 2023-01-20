package dev.katcodes.mffs.common.inventory;

import com.tterrag.registrate.util.entry.MenuEntry;
import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.client.gui.screens.GeneratorScreen;
import net.minecraft.world.inventory.ChestMenu;

public class ModMenus {

    public static final MenuEntry<GeneratorContainer> GENERATOR = MFFSMod.REGISTRATE.get().menu("generator", GeneratorContainer::new, ()->GeneratorScreen::new)
            .register();

    public static void initialize() {
    }
}
