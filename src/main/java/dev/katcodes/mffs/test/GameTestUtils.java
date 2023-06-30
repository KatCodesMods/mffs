package dev.katcodes.mffs.test;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestAssertPosException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.items.IItemHandler;

import java.util.UUID;

public class GameTestUtils {

    public static void assertContainerEmptyAtTickCapacity(GameTestHelper helper, BlockPos pos, int ticks, java.util.function.Consumer<Boolean> callback) {
        helper.runAtTickTime(ticks, ()-> {
            BlockEntity entity = helper.getBlockEntity(pos);
            if(!(entity instanceof BlockEntity)) {
                throw new GameTestAssertPosException("Block Entity not found at position",pos, helper.absolutePos(pos),helper.getTick());
            }
            IItemHandler handler = entity.getCapability(ForgeCapabilities.ITEM_HANDLER,null).orElseThrow(()->new GameTestAssertException("Entity "+entity+" is not a container"));
            boolean notEmpty= false;
            for(int i=0;i<handler.getSlots();i++) {
                helper.assertTrue(handler.getStackInSlot(i).isEmpty(),"Slot is not empty: "+i+ " "+  entity.toString());
                if(!handler.getStackInSlot(i).isEmpty() && !notEmpty)
                    notEmpty=true;
            }

            callback.accept(!notEmpty);

        });
    }
    public static ServerPlayer makeMockServerPlayerInLevel(GameTestHelper helper) {
        ServerPlayer serverplayer = new FakePlayer(helper.getLevel(),  new GameProfile(UUID.randomUUID(), "test-mock-player")) {
            /**
             * Returns {@code true} if the player is in spectator mode.
             */
            public boolean isSpectator() {
                return false;
            }

            public boolean isCreative() {
                return true;
            }
        };
        helper.getLevel().getServer().getPlayerList().placeNewPlayer(new Connection(PacketFlow.SERVERBOUND), serverplayer);
        return serverplayer;
    }
}
