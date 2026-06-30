package com.pfmonaghan.middleearth.worldgen.biome.surface;

import com.pfmonaghan.middleearth.worldgen.biome.ModBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
    private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
    private static final SurfaceRules.RuleSource ANDESITE = makeStateRule(Blocks.ANDESITE);
    private static final SurfaceRules.RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
    private static final SurfaceRules.RuleSource MUD = makeStateRule(Blocks.MUD);
    private static final SurfaceRules.RuleSource WATER = makeStateRule(Blocks.WATER);


    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource deepslateSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, DEEPSLATE), STONE);
        SurfaceRules.RuleSource gravelSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRAVEL), STONE);

        return SurfaceRules.sequence(
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.MORDOR_BIOME),deepslateSurface)),
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.MORDOR_OUTSKIRTS_BIOME),gravelSurface)),
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.DEAD_MARSHES_BIOME), SurfaceRules.sequence(
                        MUD,
                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, (double)0.0f), WATER)
                ))),
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
                // Default to a grass and dirt surface

        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}

//SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, DEEPSLATE)),
//        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, ANDESITE)