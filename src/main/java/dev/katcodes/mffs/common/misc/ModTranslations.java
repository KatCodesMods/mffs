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
