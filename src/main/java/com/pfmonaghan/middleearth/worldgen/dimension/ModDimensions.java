package com.pfmonaghan.middleearth.worldgen.dimension;

import com.mojang.datafixers.util.Pair;
import com.pfmonaghan.middleearth.MiddleEarth;
import com.pfmonaghan.middleearth.worldgen.biome.ModBiomes;
import com.pfmonaghan.middleearth.worldgen.biome.surface.ModSurfaceRules;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;

import java.util.List;
import java.util.OptionalLong;

public class ModDimensions {
    public static final ResourceKey<LevelStem> MIDDLEEARTHDIM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(MiddleEarth.MODID, "middleearthdim"));
    public static final ResourceKey<Level> MIDDLEEARTHDIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(MiddleEarth.MODID, "middleearthdim"));
    public static final ResourceKey<DimensionType> MIDDLEEARTH_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(MiddleEarth.MODID, "middleearthdim_type"));
    //Holder<NoiseGeneratorSettings> noiseGeneratorSettingsHolder =


    public static void bootstrapType(BootstrapContext<DimensionType> context) {
        //SurfaceRuleData.overworld()
        context.register(MIDDLEEARTH_DIM_TYPE, new DimensionType(
                OptionalLong.empty(), // fixedTime
                true, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                false, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                416, // height
                416, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }

    public static void bootstrapStem(BootstrapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator wrappedChunkGenerator = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(ModBiomes.GONDOR_BIOME)),
                Holder.direct(new NoiseGeneratorSettings(NoiseSettings.create(-64, 416, 1, 2), Blocks.STONE.defaultBlockState(), Blocks.WATER.defaultBlockState(),

                        NoiseRouterData.overworld(context.lookup(Registries.DENSITY_FUNCTION), context.lookup(Registries.NOISE),
                                false,false),
                        SurfaceRuleData.overworld(), (new OverworldBiomeBuilder()).spawnTarget(),
                        63, false, true, true, false)
                ));

        NoiseBasedChunkGenerator noiseBasedChunkGenerator = new NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(
                        new Climate.ParameterList<>(List.of(
                                Pair.of(
                                        // GONDOR - Temperate, moderate humidity, inland, flat/rolling hills
                                        Climate.parameters(0.3F, 0.4F, 0.1F, 0.2F, 0.0F, 0.0F, 0.0F),
                                        biomeRegistry.getOrThrow(ModBiomes.GONDOR_BIOME)),

                                Pair.of(
                                        // OSGILIATH - Same as Gondor but slightly different weirdness for variation
                                        Climate.parameters(0.3F, 0.4F, 0.15F, 0.2F, 0.1F, 0.0F, 0.0F),
                                        biomeRegistry.getOrThrow(ModBiomes.OSGILIATH_BIOME)),

                                Pair.of(
                                        // ITHILIEN - Lush, temperate, near Gondor
                                        Climate.parameters(0.3F, 0.6F, 0.1F, 0.2F, 0.2F, 0.0F, 0.0F),
                                        biomeRegistry.getOrThrow(ModBiomes.ITHILIEN_BIOME)),

                                Pair.of(
                                        // ROHAN - Dry plains, warm, inland
                                        Climate.parameters(0.5F, 0.2F, 0.2F, 0.3F, 0.0F, 0.0F, 0.0F),
                                        biomeRegistry.getOrThrow(ModBiomes.ROHAN_BIOME)),

                                Pair.of(
                                        // FANGORN - Wet forest, moderate temp, hilly
                                        Climate.parameters(0.3F, 0.8F, 0.3F, 0.5F, 0.0F, 0.0F, 0.0F),
                                        biomeRegistry.getOrThrow(ModBiomes.FANGORN_BIOME)),

                                Pair.of(
                                        // MORIA - Cold, dry, mountainous, inland
                                        Climate.parameters(-0.2F, 0.2F, 0.3F, 0.7F, 0.0F, 0.0F, 0.0F),
                                        biomeRegistry.getOrThrow(ModBiomes.MORIA_BIOME)),

                                Pair.of(
                                        // DEAD MARSHES - Wet, moderate temp, flat, inland
                                        Climate.parameters(0.3F, 0.9F, 0.1F, 0.1F, 0.0F, 0.0F, 0.0F),
                                        biomeRegistry.getOrThrow(ModBiomes.DEAD_MARSHES_BIOME)),

                                Pair.of(
                                        // MORDOR OUTSKIRTS - Hot, dry, barren, inland
                                        Climate.parameters(0.7F, 0.2F, 0.2F, 0.3F, 0.0F, 0.0F, 0.0F),
                                        biomeRegistry.getOrThrow(ModBiomes.MORDOR_OUTSKIRTS_BIOME)),

                                Pair.of(
                                        // MORDOR - Very hot, very dry, inhospitable, inland
                                        Climate.parameters(0.9F, 0.1F, 0.4F, 0.2F, 0.0F, 0.0F, 0.1F),
                                        biomeRegistry.getOrThrow(ModBiomes.MORDOR_BIOME))


                        ))),
                Holder.direct(new NoiseGeneratorSettings(NoiseSettings.create(-64, 416, 1, 2), Blocks.STONE.defaultBlockState(), Blocks.WATER.defaultBlockState(),

                        NoiseRouterData.overworld(context.lookup(Registries.DENSITY_FUNCTION), context.lookup(Registries.NOISE),
                                false,false),
                        ModSurfaceRules.makeRules(), (new OverworldBiomeBuilder()).spawnTarget(),
                        63, false, true, true, false)
                ));

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(ModDimensions.MIDDLEEARTH_DIM_TYPE), noiseBasedChunkGenerator);

        context.register(MIDDLEEARTHDIM_KEY, stem);
    }
}

//noiseGenSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD))

//Pair.of(
//        Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(ModBiomes.GONDOR_BIOME)),
//        Pair.of(
//        Climate.parameters(0.1F, 0.2F, 0.0F, 0.2F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(ModBiomes.OSGILIATH_BIOME)),
//        Pair.of(
//        Climate.parameters(0.3F, 0.6F, 0.1F, 0.1F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(ModBiomes.ITHILIEN_BIOME)),
//        Pair.of(
//        Climate.parameters(0.4F, 0.3F, 0.2F, 0.1F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(ModBiomes.ROHAN_BIOME)),
//        Pair.of(Climate.parameters(0.4F, 0.3F, 0.2F, 0.1F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(ModBiomes.FANGORN_BIOME)),
//        Pair.of(Climate.parameters(0.4F, 0.3F, 0.2F, 0.1F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(ModBiomes.MORIA_BIOME)),
//        Pair.of(Climate.parameters(0.4F, 0.3F, 0.2F, 0.1F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(ModBiomes.DEAD_MARSHES_BIOME)),
//        Pair.of(Climate.parameters(0.4F, 0.3F, 0.2F, 0.1F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(ModBiomes.MORDOR_OUTSKIRTS_BIOME)),
//        Pair.of(Climate.parameters(0.4F, 0.3F, 0.2F, 0.1F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(ModBiomes.MORDOR_BIOME))
