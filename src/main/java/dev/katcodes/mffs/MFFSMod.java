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

package dev.katcodes.mffs;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.katcodes.mffs.common.blocks.ModBlocks;
import dev.katcodes.mffs.common.compat.Compats;
import dev.katcodes.mffs.common.configs.MFFSConfigs;
import dev.katcodes.mffs.common.inventory.ModMenus;
import dev.katcodes.mffs.common.items.ModItems;
import dev.katcodes.mffs.common.misc.ModTranslations;
import dev.katcodes.mffs.common.networking.MFFSPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.gametest.framework.GlobalTestReporter;
import net.minecraft.gametest.framework.JUnitLikeTestReporter;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MFFSMod.MODID)
public class MFFSMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "mffs";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    public static final NonNullSupplier<Registrate> REGISTRATE =
            NonNullSupplier.lazy(() -> Registrate
                    .create(MODID)
            );
//                    .creativeModeTab(ModTranslations.MFFS_TAB, c -> {
//                        c.icon(ModItems.MONAZIT.get()::getDefaultInstance);
//                        c.title(Component.translatable(ModTranslations.MFFS_TAB));
//                    }));

    public final static  RegistryEntry<CreativeModeTab> CREATIVE_TAB = REGISTRATE.get().object(ModTranslations.MFFS_TAB)
            .defaultCreativeTab(tab -> tab.withLabelColor(0xFF00AA00).withSearchBar())
            .register();

    public MFFSMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MFFSConfigs.GENERAL_SPEC);
        ModBlocks.initialize();
        ModItems.initialize();
        ModTranslations.initialize();
        ModMenus.initialize();
        MFFSPacketHandler.init();
        MinecraftForge.EVENT_BUS.register(new MFFSPacketHandler());

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("Is test env? "+System.getenv().getOrDefault("MFFS_TESTS","false"));
        if(System.getenv().getOrDefault("MFFS_TESTS","false").equalsIgnoreCase("true")) {
            try {
                LOGGER.info("Setting JUNIT Test reporter");
                GlobalTestReporter.replaceWith(new JUnitLikeTestReporter(new File("tests/tests.xml")));
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
        } else {
            LOGGER.info("Using Normal Test Reporter");
        }
        // Register the item to a creative tab
//        modEventBus.addListener(this::registerTabs);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
        Compats.initialize();
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
