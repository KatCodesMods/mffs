package dev.katcodes.mffs.test;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestAssertPosException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

public class GameTestUtils {

    public static void assertContainerEmptyAtTickCapacity(GameTestHelper helper, BlockPos pos, int ticks) {
        helper.runAtTickTime(ticks, ()-> {
            BlockEntity entity = helper.getBlockEntity(pos);
            if(!(entity instanceof BlockEntity)) {
                throw new GameTestAssertPosException("Block Entity not found at position",pos, helper.absolutePos(pos),helper.getTick());
            }
            IItemHandler handler = entity.getCapability(ForgeCapabilities.ITEM_HANDLER,null).orElseThrow(()->new GameTestAssertException("Entity is not a container"));
            for(int i=0;i<handler.getSlots();i++) {
                helper.assertTrue(handler.getStackInSlot(i).isEmpty(),"Slot is not empty: "+i);
            }
            helper.succeed();
        });
    }
}
