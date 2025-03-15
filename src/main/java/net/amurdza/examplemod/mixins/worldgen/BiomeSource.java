package net.amurdza.examplemod.mixins.worldgen;

import com.mojang.datafixers.util.Pair;
import net.amurdza.examplemod.mixins.access.WorldGenValuesAccessor;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = MultiNoiseBiomeSource.class,priority = 1001)
public abstract class BiomeSource {
    @Shadow protected abstract Climate.ParameterList<Holder<Biome>> parameters();

    @Unique
    private Holder<Biome> september2024PartialMod$getNoiseBiomeHelper(int pX, int pY, int pZ, Climate.Sampler pSampler){
        List<Pair<Climate.ParameterPoint, Holder<Biome>>> pairs = ((WorldGenValuesAccessor)parameters()).getValues();
        double continentalness=pSampler.continentalness().compute(new DensityFunction.SinglePointContext(pX, pY, pZ));
        List<Float> floats=List.of(-0.38F,-0.1F,0.1F,0.38F);
        List<Integer> ys=List.of(-34,-5,24,53);
        for(int i=0;i<floats.size();i++){
            if(continentalness<floats.get(i)){
                return pairs.get(i).getSecond();
            }
        }
        for(int i=floats.size();i<floats.size()+ys.size();i++){
            if(pY<=ys.get(i-floats.size())){
                return pairs.get(i).getSecond();
            }
        }
        return pairs.get(pairs.size()-1).getSecond();
    }
    @Inject(
            method = {"getNoiseBiome(IIILnet/minecraft/world/level/biome/Climate$Sampler;)Lnet/minecraft/core/Holder;"},
            at = {@At("RETURN")},
            cancellable = true)
    public void getNoiseBiome(int x, int y, int z, Climate.Sampler sampler, CallbackInfoReturnable<Holder<Biome>> cir) {
        cir.setReturnValue(september2024PartialMod$getNoiseBiomeHelper(x,y,z,sampler));
    }
}
