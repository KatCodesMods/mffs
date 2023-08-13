package dev.katcodes.mffs.common.blocks.entities;

import dev.katcodes.mffs.api.IDebugStickOutput;
import dev.katcodes.mffs.api.ILinkable;
import dev.katcodes.mffs.api.MachineType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ExtractorBlockEntity extends SwitchableBlockEntities implements ILinkable, IDebugStickOutput, MenuProvider {

    public ExtractorBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Override
    public String getDebugStickOutput() {
        return "";
    }

    @Override
    public boolean linkCard(ItemStack card) {
        return false;
    }

    @Override
    public UUID getNetworkId() {
        return null;
    }

    @Override
    public MachineType getMachineType() {
        return null;
    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }
}
