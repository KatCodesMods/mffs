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

package dev.katcodes.mffs.common.inventory;

import com.tterrag.registrate.util.entry.MenuEntry;
import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.client.gui.screens.CapacitorScreen;
import dev.katcodes.mffs.client.gui.screens.GeneratorScreen;

public class ModMenus {

    public static final MenuEntry<GeneratorMenu> GENERATOR =
            MFFSMod.REGISTRATE.get().menu("generator", (type,windowId,inv)->new GeneratorMenu(type,windowId,inv), ()->GeneratorScreen::new)
            .register();
    public static final MenuEntry<CapacitorMenu> CAPACITOR =
            MFFSMod.REGISTRATE.get().menu("capacitor", (type,windowId,inv, buf)->new CapacitorMenu(type,windowId,inv), ()-> CapacitorScreen::new)
            .register();

    public static void initialize() {
    }
}
