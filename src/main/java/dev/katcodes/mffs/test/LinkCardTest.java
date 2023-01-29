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

package dev.katcodes.mffs;

import dev.katcodes.mffs.common.items.ModItems;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(MFFSMod.MODID)
public class LinkCardTest {

    @GameTest(
            setupTicks = 20L, // The test spends 20 ticks to set up for execution
            required = false, // The failure is logged but does not affect the execution of the batch
            batch = "link_card_test" // The test is part of the batch "link_card_test"
    )
    public static void emptyCard(GameTestHelper helper) {
        Player player=helper.makeMockPlayer();

        player.getInventory().setItem(0, new ItemStack(ModItems.POWER_LINK_CARD.get()));
        helper.assertTrue(player.getInventory().getItem(0).getTag()==null,"Card should be empty");
        helper.succeed();
    }
}
