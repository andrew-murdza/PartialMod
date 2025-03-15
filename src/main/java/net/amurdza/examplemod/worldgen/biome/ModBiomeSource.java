package net.amurdza.examplemod.worldgen.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.DensityFunction;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public class ModBiomeSource extends BiomeSource {
    public static final Codec<ModBiomeSource> CODEC=RecordCodecBuilder.create(t
            -> t.group(BiomeParameterPoint.CODEC.listOf()
                    .fieldOf("biome_data").forGetter(p -> p.points),Biome.CODEC.fieldOf("default_biome")
                    .forGetter(p->p.biome))
            .apply(t, ModBiomeSource::new));
    private final List<BiomeParameterPoint> points;
    private final Holder<Biome> biome;
    private ModBiomeSource(List<BiomeParameterPoint> points, Holder<Biome> biome) {
        this.points = points;
        this.biome=biome;
    }

    protected @NotNull Stream<Holder<Biome>> collectPossibleBiomes() {
        return this.points.stream().map(t->t.biome);
    }

    protected @NotNull Codec<? extends BiomeSource> codec() {
        return CODEC;
    }


    public @NotNull Holder<Biome> getNoiseBiome(int pX, int pY, int pZ, Climate.Sampler pSampler) {
        double continentalness=pSampler.continentalness().compute(new DensityFunction.SinglePointContext(pX, pY, pZ));
        for(BiomeParameterPoint point:points){
            if(pY>=point.minY&&pY<=point.maxY&&continentalness>point.minDepth&&continentalness>point.maxDepth){
                return point.biome;
            }
        }
        return biome;
    }
    public static class BiomeParameterPoint {
        float minDepth;
        float maxDepth;
        int minY;
        int maxY;
        Holder<Biome> biome;
        public static final Codec<BiomeParameterPoint> CODEC = RecordCodecBuilder.create(t ->
                t.group(Codec.FLOAT.fieldOf("min_depth").forGetter(p->p.minDepth),
                        Codec.FLOAT.fieldOf("max_depth").forGetter(p->p.maxDepth),
                        Codec.INT.fieldOf("minY").forGetter(p->p.minY),
                        Codec.INT.fieldOf("maxY").forGetter(p->p.maxY),
                        Biome.CODEC.fieldOf("biome").forGetter(p->p.biome)
            ).apply(t, BiomeParameterPoint::new));
        public BiomeParameterPoint(float minDepth, float maxDepth, int minY, int maxY, Holder<Biome> biome){
            this.minDepth=minDepth;
            this.maxDepth=maxDepth;
            this.minY=minY;
            this.maxY=maxY;
            this.biome=biome;
        }
    }
}
