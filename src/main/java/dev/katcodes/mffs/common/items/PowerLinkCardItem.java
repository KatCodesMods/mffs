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

import dev.katcodes.mffs.api.ILinkable;
import dev.katcodes.mffs.common.blocks.entities.CapacitorBlockEntity;
import dev.katcodes.mffs.common.misc.ModTranslations;
import dev.katcodes.mffs.common.world.NetworkWorldData;
import dev.katcodes.mffs.common.world.data.NetworkData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class PowerLinkCardItem extends CardItem {
    public PowerLinkCardItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(context.getPlayer().isShiftKeyDown()) {
            if(!context.getLevel().isClientSide) {
                BlockEntity entity = context.getLevel().getBlockEntity(context.getClickedPos());
                if(entity instanceof ILinkable) {
                    boolean succeeded = ((ILinkable)entity).linkCard(context.getItemInHand());
                    if(succeeded) {
                        context.getPlayer().displayClientMessage(Component.translatable(ModTranslations.NETWORK_SET), true);
                        return InteractionResult.CONSUME;
                    }
                    context.getPlayer().displayClientMessage(Component.translatable(ModTranslations.NETWORK_LINK_FAIL), true);
                }
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(player.isShiftKeyDown()) {
            if(!level.isClientSide) {
                if (CardItem.getNetworkID(player.getItemInHand(hand)).isPresent()) {
                    player.displayClientMessage(Component.translatable(ModTranslations.NETWORK_CLEARED),true);
                    CardItem.clearNetworkID(player.getItemInHand(hand));
                }
            }
        }
        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
        if(CardItem.getNetworkID(p_41421_).isPresent()) {
            p_41423_.add(Component.translatable(ModTranslations.POWER_LINK_TOOLTIP, NetworkWorldData.getClientInstance().getNetwork(CardItem.getNetworkID(p_41421_).get())));
        }
        else {
            p_41423_.add(Component.translatable(ModTranslations.POWER_LINK_EMPTY));
        }
    }
}
