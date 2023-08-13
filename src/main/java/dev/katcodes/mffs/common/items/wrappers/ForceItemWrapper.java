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

package dev.katcodes.mffs.common.items.wrappers;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ForceItemWrapper implements ICapabilityProvider {
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return null;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return ICapabilityProvider.super.getCapability(cap);
    }
}
