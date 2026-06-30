package com.pfmonaghan.middleearth.worldgen.biome;

import com.pfmonaghan.middleearth.MiddleEarth;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBiomes() {
        Regions.register(new ModOverworldRegion(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MODID, "overworld"), 5));

    }
}