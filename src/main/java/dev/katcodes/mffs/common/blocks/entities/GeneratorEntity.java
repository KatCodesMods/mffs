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

package dev.katcodes.mffs.common.blocks.entities;

import dev.katcodes.mffs.api.MachineType;
import dev.katcodes.mffs.common.blocks.AbstractMachineBlock;
import dev.katcodes.mffs.common.configs.MFFSConfigs;
import dev.katcodes.mffs.common.inventory.GeneratorMenu;
import dev.katcodes.mffs.common.misc.ModTranslations;
import dev.katcodes.mffs.common.storage.MFFSEnergyStorage;
import dev.katcodes.mffs.common.tags.ModTags;
import dev.katcodes.mffs.common.world.NetworkWorldData;
import dev.katcodes.mffs.common.world.data.NetworkData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneratorEntity extends BlockEntity implements MenuProvider {

    private final IItemHandlerModifiable fuelHandler = createFuelHandler(this);
    private final LazyOptional<IItemHandler> fuelOptional = LazyOptional.of(() -> this.fuelHandler);
    private final IItemHandlerModifiable crystalHandler = createCrystalHandler(this);
    private final LazyOptional<IItemHandler> crystalOptional = LazyOptional.of(() -> this.crystalHandler);
    private final LazyOptional<IItemHandler> combinedOptional = LazyOptional.of(() -> new CombinedInvWrapper(this.fuelHandler, this.crystalHandler));
    private final IEnergyStorage energyStorage = createEnergyStorage(this);
    private final LazyOptional<IEnergyStorage> energyOptional = LazyOptional.of(() -> this.energyStorage);
    private int burnTime;
    private int cookTime;
    private int cookTimeTotal = 200;
    private int recipesUsed;
    public final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int p_39284_) {
            switch (p_39284_) {
                case 0:
                    return GeneratorEntity.this.energyStorage.getEnergyStored();
                case 1:
                    return GeneratorEntity.this.energyStorage.getMaxEnergyStored();
                case 2:
                    return GeneratorEntity.this.burnTime;
                case 3:
                    return GeneratorEntity.this.cookTime;
                case 4:
                    return GeneratorEntity.this.cookTimeTotal;
                case 5:
                    return GeneratorEntity.this.recipesUsed;
                default:
                    return 0;
            }
        }


        @Override
        public void set(int p_39285_, int p_39286_) {
            switch (p_39285_) {
                case 0:
                    ((MFFSEnergyStorage) GeneratorEntity.this.energyStorage).setEnergy(p_39286_);
                    break;
                case 1:
                    ((MFFSEnergyStorage) GeneratorEntity.this.energyStorage).setMaxEnergy(p_39286_);
                    break;
                case 2:
                    GeneratorEntity.this.burnTime = p_39286_;
                    break;
                case 3:
                    GeneratorEntity.this.cookTime = p_39286_;
                    break;
                case 4:
                    GeneratorEntity.this.cookTimeTotal = p_39286_;
                    break;
                case 5:
                    GeneratorEntity.this.recipesUsed = p_39286_;
            }

        }

        @Override
        public int getCount() {
            return 6;
        }
    };


    public GeneratorEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @NotNull
    public static IItemHandlerModifiable createFuelHandler(GeneratorEntity thisEntity) {
        return new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return AbstractFurnaceBlockEntity.isFuel(stack);
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if (!AbstractFurnaceBlockEntity.isFuel(stack)) {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if (thisEntity != null) thisEntity.setChanged();
            }
        };
    }


    public static boolean isCrystal(ItemStack stack) {
        return stack.getTags().anyMatch(s -> s.equals(ModTags.ItemTags.MONAZIT));
    }


    @NotNull
    public static ItemStackHandler createCrystalHandler(GeneratorEntity thisEntity) {
        return new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return isCrystal(stack);
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if (!isCrystal(stack)) {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if (thisEntity != null) thisEntity.setChanged();
            }
        };
    }

    public static void tick(Level level, BlockPos pos, BlockState state, GeneratorEntity blockEntity) {
        if (blockEntity.isBurning()) {
            --blockEntity.burnTime;
        }
        if (!level.isClientSide) {
            NetworkWorldData test = NetworkWorldData.get();
            if (test.getNetworkCount() < 5) {
                NetworkData temp = test.createNetwork(0, 100);
                temp.getNetworkMachines().put(MachineType.GENERATOR, new ArrayList<>());
                temp.getNetworkMachines().get(MachineType.GENERATOR).add(GlobalPos.of(level.dimension(), pos));
                System.out.println(temp.getUuid().toString());
                System.out.println(test.getNetworkCount());

            }

            blockEntity.energyOptional.ifPresent(iEnergyStorage -> {
                blockEntity.fuelOptional.ifPresent(iFuelHandler -> blockEntity.crystalOptional.ifPresent(iMonazitHandler -> {
                    boolean flag = blockEntity.isBurning();
                    boolean flag1 = false;
                    ItemStack itemstack = iFuelHandler.getStackInSlot(0);
                    if (blockEntity.isBurning() || !itemstack.isEmpty() && !iMonazitHandler.getStackInSlot(0).isEmpty()) {
                        if (!blockEntity.isBurning() && iMonazitHandler.getStackInSlot(0).getTags().anyMatch(s -> s.equals(ModTags.ItemTags.MONAZIT))) {
                            blockEntity.burnTime = ForgeHooks.getBurnTime(itemstack, RecipeType.SMELTING);
                            blockEntity.recipesUsed = blockEntity.burnTime;
                            if (blockEntity.isBurning()) {
                                flag1 = true;

                                if (itemstack.hasCraftingRemainingItem())
                                    ((ItemStackHandler) iFuelHandler).setStackInSlot(0, itemstack.getCraftingRemainingItem());
                                else if (!itemstack.isEmpty()) {
                                    Item item = itemstack.getItem();
                                    itemstack.shrink(1);
                                    if (itemstack.isEmpty()) {
                                        ((ItemStackHandler) iFuelHandler).setStackInSlot(0, itemstack.getCraftingRemainingItem());
                                    }
                                }

                            }
                        }
                        if (blockEntity.isBurning()) {
                            if (iMonazitHandler.getStackInSlot(0).getTags().anyMatch(s -> s.equals(ModTags.ItemTags.MONAZIT))) {
                                blockEntity.energyOptional.ifPresent(e -> {
                                    ((MFFSEnergyStorage) e).addEnergy(MFFSConfigs.GENERATOR_GENERATE.get() / 200);
                                    if (e.getEnergyStored() < e.getMaxEnergyStored()) ++blockEntity.cookTime;
                                });
                            }
                            if (blockEntity.cookTime == blockEntity.cookTimeTotal) {
                                blockEntity.cookTime = 0;
                                blockEntity.cookTimeTotal = 200;
                                flag1 = true;
                                ItemStack monazit = iMonazitHandler.getStackInSlot(0);
                                if (!monazit.isEmpty()) {
                                    monazit.shrink(1);
                                    if (monazit.isEmpty()) {
                                        ((ItemStackHandler) iMonazitHandler).setStackInSlot(0, itemstack.getCraftingRemainingItem());
                                    }
                                }
                            }
                        } else {
                            blockEntity.cookTime = 0;
                        }
                    } else if (!blockEntity.isBurning() && blockEntity.cookTime > 0) {
                        blockEntity.cookTime = Mth.clamp(blockEntity.cookTime - 2, 0, blockEntity.cookTimeTotal);
                    }

                    if (flag != blockEntity.isBurning()) {
                        flag1 = true;
                        level.setBlock(pos, state.setValue(AbstractMachineBlock.LIT, blockEntity.isBurning()), 3);
                        blockEntity.setChanged();
                    }
                }));
            });
            sendOutPower(blockEntity);
        }
    }

    private static void sendOutPower(GeneratorEntity blockEntity) {
        blockEntity.energyOptional.ifPresent(energy -> {
            AtomicInteger capacity = new AtomicInteger(energy.getEnergyStored());
            if (capacity.get() > 0) {
                for (Direction direction : Direction.values()) {
                    BlockEntity te = blockEntity.level.getBlockEntity(blockEntity.getPos().relative(direction, 1));
                    if (te != null) {
                        boolean doContinue = te.getCapability(ForgeCapabilities.ENERGY, direction).map(handler -> {
                            if (handler.canReceive()) {
                                int recieved = handler.receiveEnergy(Math.min(capacity.get(), 100), false);
                                capacity.addAndGet(-recieved);
                                ((MFFSEnergyStorage) energy).consumeEnergy(recieved);
                                blockEntity.setChanged();
                                return capacity.get() > 0;

                            } else {
                                return true;
                            }
                        }).orElse(true);
                        if (!doContinue) {
                            return;
                        }
                    }
                }
            }
        });
    }

    public IItemHandlerModifiable getFuelHandler() {
        return fuelHandler;
    }

    public IItemHandlerModifiable getCrystalHandler() {
        return crystalHandler;
    }

    public IEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    private IEnergyStorage createEnergyStorage(GeneratorEntity generatorEntity) {
        return new MFFSEnergyStorage(10000, 900);
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == Direction.UP) {
                return crystalOptional.cast();
            }
            if (side == Direction.NORTH || side == Direction.SOUTH || side == Direction.EAST || side == Direction.WEST) {
                return fuelOptional.cast();
            }
            if (side == null) {
                return combinedOptional.cast();
            }
        } else if (cap == ForgeCapabilities.ENERGY) {
            return energyOptional.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        fuelOptional.invalidate();
        crystalOptional.invalidate();
        combinedOptional.invalidate();
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    @Override
    protected void saveAdditional(CompoundTag p_187471_) {
        super.saveAdditional(p_187471_);
        p_187471_.put("fuel", ((ItemStackHandler) fuelHandler).serializeNBT());
        p_187471_.put("crystal", ((ItemStackHandler) crystalHandler).serializeNBT());
        p_187471_.put("energy", ((MFFSEnergyStorage) energyStorage).serializeNBT());
        p_187471_.putInt("burnTime", this.burnTime);
        p_187471_.putInt("cookTime", this.cookTime);
        p_187471_.putInt("cookTimeTotal", this.cookTimeTotal);
        p_187471_.putInt("recipesUsed", this.recipesUsed);

    }

    @Override
    public void load(CompoundTag p_155245_) {
        super.load(p_155245_);
        ((ItemStackHandler) fuelHandler).deserializeNBT(p_155245_.getCompound("fuel"));
        ((ItemStackHandler) crystalHandler).deserializeNBT(p_155245_.getCompound("crystal"));
        ((MFFSEnergyStorage) energyStorage).deserializeNBT(p_155245_.get("energy"));
        this.burnTime = p_155245_.getInt("burnTime");
        this.cookTime = p_155245_.getInt("cookTime");
        this.cookTimeTotal = p_155245_.getInt("cookTimeTotal");
        this.recipesUsed = p_155245_.getInt("recipesUsed");

    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(ModTranslations.GENERATOR_CONTAINER);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return new GeneratorMenu(p_39954_, p_39955_, this, this.containerData);
    }

    public BlockPos getPos() {
        return this.worldPosition;
    }
}
