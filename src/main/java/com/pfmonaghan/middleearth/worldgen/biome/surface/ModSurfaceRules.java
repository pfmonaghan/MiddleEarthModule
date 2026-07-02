package com.pfmonaghan.middleearth.worldgen.biome.surface;

import com.google.common.collect.ImmutableList;
import com.pfmonaghan.middleearth.worldgen.biome.ModBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
    private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
    private static final SurfaceRules.RuleSource ANDESITE = makeStateRule(Blocks.ANDESITE);
    private static final SurfaceRules.RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
    private static final SurfaceRules.RuleSource MUD = makeStateRule(Blocks.MUD);
    private static final SurfaceRules.RuleSource WATER = makeStateRule(Blocks.WATER);
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource SNOW = makeStateRule(Blocks.SNOW_BLOCK);


    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.ConditionSource surfacerules$conditionsource = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(97), 3);
        SurfaceRules.ConditionSource above66 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(65), 3);

        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource deepslateSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(0, 3), DEEPSLATE), STONE);
        SurfaceRules.RuleSource gravelSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRAVEL), STONE);

        SurfaceRules.RuleSource sf8 = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(
                        new SurfaceRules.RuleSource[]{
                                SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{ModBiomes.MORDOR_BIOME}), deepslateSurface),
                                SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{ModBiomes.MORDOR_OUTSKIRTS_BIOME}),
                                        SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, (double)0.0f), GRAVEL)})),
                                SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{ModBiomes.MORIA_BIOME}), SurfaceRules.ifTrue(surfacerules$conditionsource,SurfaceRules.ifTrue(SurfaceRules.steep(), SNOW))),
                                SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{ModBiomes.DEAD_MARSHES_BIOME}),
                                        SurfaceRules.sequence(new SurfaceRules.RuleSource[]{SurfaceRules.ifTrue(above66, SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, (double)0.0f, (double)1.3f), WATER)), SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, (double)0.0f), MUD)}))

                        })
                ),

                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
                // Default to a grass and dirt surface

        );
        SurfaceRules.RuleSource surfacerules$rulesource9 = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), sf8);

        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
        builder.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK));
        builder.add(SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), sf8));
        return SurfaceRules.sequence(
                (SurfaceRules.RuleSource[])builder.build().toArray((x$0) -> new SurfaceRules.RuleSource[x$0]
        ));
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}

//SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, DEEPSLATE)),
//        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, ANDESITE)