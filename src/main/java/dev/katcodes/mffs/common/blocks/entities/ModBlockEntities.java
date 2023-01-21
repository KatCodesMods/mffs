package dev.katcodes.mffs.common.blocks.entities;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.katcodes.mffs.common.blocks.GeneratorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.ForgeRegistries;

import static dev.katcodes.mffs.common.blocks.ModBlocks.GENERATOR;

public class ModBlockEntities {

    public static final BlockEntityEntry<GeneratorEntity> GENERATOR_ENTITY = BlockEntityEntry.cast(GENERATOR.getSibling(ForgeRegistries.BLOCK_ENTITY_TYPES));

    public static void initialize() {

    }
}
