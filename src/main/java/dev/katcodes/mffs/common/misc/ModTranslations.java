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
    public static final String MFFS_TAB="mffs";
    public static final String GUAGE_TOOLTIP="tabs.modid.mffs_tab.tooltip";
    public static final String GENERATOR_CONTAINER="container.mffs.generator";

    public static final String POWER_LINK_TOOLTIP="tooltip.mffs.power_link_card.network_name";
    public static final String POWER_LINK_EMPTY="tooltip.mffs.power_link_card.empty";

    public static final String NETWORK_SET="mffs.network.linked";
    public static final String NETWORK_CLEARED="mffs.network.cleared";
    public static final String NETWORK_LINK_FAIL = "mffs.network.link_failed";

    public static final String NETWORK_ALREADY_CONNECTED = "mffs.network.already_connected";

    public static final String UPGRADE_HOLD_SHIFT="mffs.upgrade.hold_shift";

    public static void initialize() {

        //MFFSMod.REGISTRATE.get().addRawLang(MFFS_TAB, "MFFS");
        MFFSMod.REGISTRATE.get().addRawLang(GUAGE_TOOLTIP, "Energy: %s/%s RF");
        MFFSMod.REGISTRATE.get().addRawLang(GENERATOR_CONTAINER, "Generator");
        MFFSMod.REGISTRATE.get().addRawLang(POWER_LINK_TOOLTIP, "Network Name: %s");
        MFFSMod.REGISTRATE.get().addRawLang(POWER_LINK_EMPTY, "Not Connected");
        MFFSMod.REGISTRATE.get().addRawLang(NETWORK_SET,"Network Linked");
        MFFSMod.REGISTRATE.get().addRawLang(NETWORK_CLEARED,"Cleared Network");
        MFFSMod.REGISTRATE.get().addRawLang(NETWORK_LINK_FAIL,"Failed to link Network");
        MFFSMod.REGISTRATE.get().addRawLang(NETWORK_ALREADY_CONNECTED, "Card is already linked. Clear before re-linking");
        MFFSMod.REGISTRATE.get().addRawLang("mffs.upgrade.range.description", "§7Range increase §ex%s§7 (Stack total §ex%s§7)");
        MFFSMod.REGISTRATE.get().addRawLang("mffs.upgrade.capacity.description", "§7Capacity increase §e+%s§7 FE (Stack total §e+%s§7 FE)");
        MFFSMod.REGISTRATE.get().addRawLang(UPGRADE_HOLD_SHIFT, "Hold shift to see how much upgraded by item stack");

    }
}
