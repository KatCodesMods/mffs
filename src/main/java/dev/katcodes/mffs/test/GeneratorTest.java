package dev.katcodes.mffs.test;


import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.common.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(MFFSMod.MODID)
public class GeneratorTest {

    @GameTest(
            setupTicks = 20L, // The test spends 20 ticks to set up for execution
            required = false, // The failure is logged but does not affect the execution of the batch
            batch = "generator_tests" // The test is part of the batch "link_card_test"
    )
    public static void testTakesCorrectCrystals(GameTestHelper helper) {
        GameTestUtils.assertContainerEmptyAtTickCapacity(helper,new BlockPos(0,1,0),50);
    }

    @GameTest(
            setupTicks = 20L, // The test spends 20 ticks to set up for execution
            required = false, // The failure is logged but does not affect the execution of the batch
            batch = "generator_tests" // The test is part of the batch "link_card_test"
    )
    public static void testTakesCorrectFuel(GameTestHelper helper) {
        GameTestUtils.assertContainerEmptyAtTickCapacity(helper,new BlockPos(0,1,0),50);
    }

}
