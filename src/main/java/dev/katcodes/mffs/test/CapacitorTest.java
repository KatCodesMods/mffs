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

package dev.katcodes.mffs.test;

import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.common.items.ModItems;
import dev.katcodes.mffs.common.world.NetworkWorldData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(MFFSMod.MODID)
public class CapacitorTest {

    @GameTest(
            setupTicks = 20L, // The test spends 20 ticks to set up for execution
            required = false, // The failure is logged but does not affect the execution of the batch
            batch = "capacitor_tests" // The test is part of the batch "link_card_test"
    )
    public static void newNetwork(GameTestHelper helper) {
        Player player = helper.makeMockSurvivalPlayer();
        player.getInventory().add(new ItemStack(ModItems.POWER_LINK_CARD.get()));
        player.setPose(Pose.CROUCHING);
        player.setShiftKeyDown(true);
        MFFSMod.LOGGER.info("There are networks: "+NetworkWorldData.get().networks().size());
        MFFSMod.LOGGER.info("Block is "+helper.getBlockState(new BlockPos(0,1,0)).getBlock());
        ItemStack stack=player.getItemInHand(InteractionHand.MAIN_HAND);
        stack.getItem().useOn(
                new UseOnContext(player.level(),player,InteractionHand.MAIN_HAND,stack, new BlockHitResult(new Vec3(0,1,0), Direction.UP,helper.absolutePos(new BlockPos(0,1,0)),false))
                        );
        MFFSMod.LOGGER.info("There are networks: "+NetworkWorldData.get().networks().size());
        helper.succeed();

    }

}
