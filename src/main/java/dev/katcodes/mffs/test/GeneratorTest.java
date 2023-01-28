package dev.katcodes.mffs.test;


import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.common.blocks.entities.GeneratorEntity;
import dev.katcodes.mffs.common.configs.MFFSConfigs;
import dev.katcodes.mffs.common.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(MFFSMod.MODID)
public class GeneratorTest {

    @GameTest(
            setupTicks = 20L, // The test spends 20 ticks to set up for execution
            required = false, // The failure is logged but does not affect the execution of the batch
            batch = "generator_tests" // The test is part of the batch "link_card_test"
    )
    public static void testTakesCorrectCrystals(GameTestHelper helper) {
        GameTestUtils.assertContainerEmptyAtTickCapacity(helper,new BlockPos(0,1,0),50,success -> {
            if(success)
                helper.succeed();
            else
                helper.fail("Assert Container Fail");
        });
    }

    @GameTest(
            setupTicks = 20L, // The test spends 20 ticks to set up for execution
            required = false, // The failure is logged but does not affect the execution of the batch
            batch = "generator_tests" // The test is part of the batch "link_card_test"
    )
    public static void testTakesCorrectFuel(GameTestHelper helper) {
        GameTestUtils.assertContainerEmptyAtTickCapacity(helper,new BlockPos(0,1,0),50,success -> {
            if(success)
                helper.succeed();
            else
                helper.fail("Assert Container Fail");
        });
    }

    @GameTest(
            setupTicks = 20L, // The test spends 20 ticks to set up for execution
            timeoutTicks = 300,
            required = false, // The failure is logged but does not affect the execution of the batch
            batch = "generator_tests" // The test is part of the batch "link_card_test"
    )
    public static void tickLength(GameTestHelper helper) {
        GameTestUtils.assertContainerEmptyAtTickCapacity(helper,new BlockPos(0,1,0),50,success -> {
            if(success)
            {
                BlockEntity entity = helper.getBlockEntity(new BlockPos(0,1,0));
                if(entity instanceof GeneratorEntity) {
                    GeneratorEntity generatorEntity = (GeneratorEntity) entity;
                    generatorEntity.getCrystalHandler().setStackInSlot(0,new ItemStack(ModItems.MONAZIT,1));
                    generatorEntity.getFuelHandler().setStackInSlot(0,new ItemStack(Items.COAL,1));
                    helper.runAtTickTime(100,() -> {
                        helper.assertTrue(generatorEntity.getFuelHandler().getStackInSlot(0).isEmpty(),"Generator did not use coal up" );
                            GameTestUtils.assertContainerEmptyAtTickCapacity(helper,new BlockPos(0,1,0),260, aSuccess -> {
                                if(aSuccess) {
                                    helper.assertTrue(generatorEntity.getEnergyStorage().getEnergyStored() == MFFSConfigs.GENERATOR_GENERATE.get(), "Generator did not produce enough power");
                                    helper.succeed();
                                }
                                else
                                    helper.fail("Generator did not use up items");
                            });
                    });
                }
            }
            else
                helper.fail("Assert Container Fail");
        });
    }




}
