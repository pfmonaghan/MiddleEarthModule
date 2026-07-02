package com.pfmonaghan.middleearth.worldgen.features;

import com.pfmonaghan.middleearth.MiddleEarth;
import com.pfmonaghan.middleearth.worldgen.features.RockyTerrain;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.BlockBlobFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModdedFeatures<FC extends FeatureConfiguration> {

    public static final Feature<BlockStateConfiguration> LARGE_STONE;

    static{
        LARGE_STONE = register("large_stone", ()->new BlockBlobFeature(BlockStateConfiguration.CODEC));
    }

    public static final DeferredRegister<Feature<?>> MOD_FEATURES =
            DeferredRegister.create(Registries.FEATURE, MiddleEarth.MODID);
//
 //    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> ROCK =
//            MOD_FEATURES.register("rock",
//                    ()-> new RockyTerrain(NoneFeatureConfiguration.CODEC));
//
    public static void register(IEventBus bus)
    {
        MOD_FEATURES.register(bus);
    }
//
//
private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String key, Supplier<F> value) {
    return (F)(MOD_FEATURES.register( key, value)).get();
}


}
