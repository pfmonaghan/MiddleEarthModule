package com.pfmonaghan.middleearth.worldgen.features;

import com.pfmonaghan.middleearth.MiddleEarth;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;

import java.util.OptionalInt;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?,?>> ITHILIEN_TREE_KEY = registerKey("ithilien_tree_key");
//    public static final ResourceKey<ConfiguredFeature<?,?>> DEAD_MARSHES_GRASS_1_KEY = registerKey("dead_marshes_grass_1_key");
//    public static final ResourceKey<ConfiguredFeature<?,?>> DEAD_MARSHES_CORPSE_CANDLES_KEY = registerKey("dead_marshes_corpse_candles_key");
    public static final ResourceKey<ConfiguredFeature<?,?>> ROCK_FEATURE_KEY = registerKey("rock_feature_key");
    //public static final ResourceKey<ConfiguredFeature<?,?>> MORDOR_GRASS = registerKey("mordor_grass");


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?,?>> context)
    {
        register(context, ITHILIEN_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new ForkingTrunkPlacer(4, 4, 3),
                BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
                new SpruceFoliagePlacer(ConstantInt.of(2), ConstantInt.of(3), ConstantInt.of(3)),
                new ThreeLayersFeatureSize(1, 0, 3, 2,1, OptionalInt.of(2)))
                .build());


//        context.register(DEAD_MARSHES_GRASS_1_KEY, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
//        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
//                .add(ModBlocks.DEAD_MARSHES_GRASS_1.get().defaultBlockState(), 15)
//                .add(ModBlocks.DEAD_MARSHES_GRASS_2.get().defaultBlockState(), 10)
//        ))));
//
//        context.register(DEAD_MARSHES_CORPSE_CANDLES_KEY, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
//        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
//                .add(ModBlocks.DEAD_MARSHES_CORPSE_CANDLE.get().defaultBlockState(), 5)
//        ))));

        context.register(ROCK_FEATURE_KEY, new ConfiguredFeature<>(Feature.FOREST_ROCK, new BlockStateConfiguration(Blocks.STONE.defaultBlockState())));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}