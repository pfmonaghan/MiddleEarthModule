package com.pfmonaghan.middleearth.worldgen.features;

import com.pfmonaghan.middleearth.MiddleEarth;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> ITHILIEN_TREE_PLACED_KEY = registerKey("ithilien_tree_placed");
    public static final ResourceKey<PlacedFeature> DEAD_MARSHES_PLACED_KEY = registerKey("dead_marshes_placed");
    public static final ResourceKey<PlacedFeature> DEAD_MARSHES_CORPSE_CANDLE_PLACED_KEY = registerKey("dead_marshes_corpse_candle_placed");
    public static final ResourceKey<PlacedFeature> ROCK_FEATURE_PLACED_KEY = registerKey("rock_feature_placed_key");


    public static void bootstrap(BootstrapContext<PlacedFeature> context)
    {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, ITHILIEN_TREE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ITHILIEN_TREE_KEY),
         VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, .1f, 2), Blocks.SPRUCE_SAPLING));
//        register(context, DEAD_MARSHES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DEAD_MARSHES_GRASS_1_KEY),
//                VegetationPlacements.worldSurfaceSquaredWithCount(5));
//        register(context, DEAD_MARSHES_CORPSE_CANDLE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DEAD_MARSHES_CORPSE_CANDLES_KEY),
//                VegetationPlacements.worldSurfaceSquaredWithCount(5));
        register(context, ROCK_FEATURE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ROCK_FEATURE_KEY),
                VegetationPlacements.worldSurfaceSquaredWithCount(5));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 PlacementModifier... modifiers) {
        context.register(key, new PlacedFeature(configuration, List.of(modifiers)));
    }

}
