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

package dev.katcodes.mffs.common.items;

import dev.katcodes.mffs.api.IDebugStickOutput;
import dev.katcodes.mffs.api.MFFSCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DebugStick extends ModItem {
    public DebugStick(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        String side=context.getLevel().isClientSide ? "Client": "Server";
        Player player=context.getPlayer();
        BlockPos pos=context.getClickedPos();
        Level level = context.getLevel();
        if(player!=null) {
            player.sendSystemMessage(Component.literal(side + ": " + level.getBlockState(pos).getBlock().getName().getString()));
            BlockEntity entity = level.getBlockEntity(pos);

            if (entity != null)
                player.sendSystemMessage(Component.literal(side + ": " + entity.toString()));
            else
                player.sendSystemMessage(Component.literal(side + ": " + "No block entity"));
            if (entity instanceof IDebugStickOutput) {
                player.sendSystemMessage(Component.literal(side + ": " + ((IDebugStickOutput) entity).getDebugStickOutput()));
            }
            if (entity != null && entity.getCapability(MFFSCapabilities.FORCE_ENERGY_CAPABILITY, null).isPresent()) {
                player.sendSystemMessage(Component.literal(side + ": has fe capability"));
            }
        }
        return InteractionResult.CONSUME;
    }
}
