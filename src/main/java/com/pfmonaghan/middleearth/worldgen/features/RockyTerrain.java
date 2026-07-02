package com.pfmonaghan.middleearth.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RockyTerrain extends Feature<NoneFeatureConfiguration> {

    private static final BlockStatePredicate IS_STONE;

    public RockyTerrain(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
     BlockPos blockpos = context.origin();
     WorldGenLevel world = context.level();
     RandomSource random = context.random();

        for(blockpos = blockpos.above();
            world.isEmptyBlock(blockpos) && blockpos.getY() > world.getMinBuildHeight() + 2;
            blockpos = blockpos.below()) {
        }

        int treeHeight = 10;//random.nextInt(7, 10);

     if (!IS_STONE.test(world.getBlockState(blockpos)))
     {
         return false;
     }
     else {
         this.setBlock(world, blockpos, Blocks.SPRUCE_LOG.defaultBlockState());

         BlockPos start = blockpos.above(treeHeight);

         this.setBlock(world, start, Blocks.SPRUCE_LEAVES.defaultBlockState());
         this.setBlock(world, start.above(1), Blocks.SPRUCE_LEAVES.defaultBlockState());

         for(int i =0;i<10;++i)
         {
             this.setBlock(world, start.below(i), Blocks.SPRUCE_LOG.defaultBlockState());
         }

         createLayer(3,start.below(1), world);
         createLayer(5,start.below(3), world);
         createLayer(7,start.below(5), world);

         return true;
     }
    }
    static {
        IS_STONE = BlockStatePredicate.forBlock(Blocks.DEEPSLATE);
    }

    public void createLayer(int width, BlockPos center, WorldGenLevel world)
    {
        BlockPos start = center.north(width/2).west(width/2);
        for(int i=0;i<width;++i)
        {
            for(int j=0;j<width;++j)
            {
                if(i==width/2&&j==width/2)
                {
                    this.setBlock(world, start.east(j), Blocks.SPRUCE_LOG.defaultBlockState());
                }else {

                    this.setBlock(world, start.east(j), Blocks.SPRUCE_LEAVES.defaultBlockState());
                }
            }
            start = start.south(1);
        }
    }


}
