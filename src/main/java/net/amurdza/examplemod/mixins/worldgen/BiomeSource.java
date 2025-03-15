package net.amurdza.examplemod.mixins.worldgen;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.amurdza.examplemod.AOEMod;
import net.amurdza.examplemod.mixins.access.WorldGenValuesAccessor;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.levelgen.DensityFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(value = MultiNoiseBiomeSource.class,priority = 1001)
public abstract class BiomeSource {
    @Shadow @Final private Either<Climate.ParameterList<Holder<Biome>>, Holder<MultiNoiseBiomeSourceParameterList>> parameters;

    @Shadow protected abstract Climate.ParameterList<Holder<Biome>> parameters();

    @Unique
    public Holder<Biome> september2024PartialMod$getNoiseBiome(Climate.TargetPoint pTargetPoint) {
        return parameters().findValue(pTargetPoint);
    }
    @Unique
    private Holder<Biome> september2024PartialMod$getNoiseBiomeHelper(int pX, int pY, int pZ, Climate.Sampler pSampler){
//        boolean flag=new Random().nextInt(200000)==0;
//        if(flag){
//            AOEMod.LOGGER.info("this ran 1");
//        }
        if(parameters.right().isPresent()){
            List<Pair<Climate.ParameterPoint, Holder<Biome>>> pairs = ((WorldGenValuesAccessor)parameters.right().get().get().parameters()).getValues();
            double continentalness=pSampler.continentalness().compute(new DensityFunction.SinglePointContext(pX, pY, pZ))*10000;
            for(Pair<Climate.ParameterPoint, Holder<Biome>> pair:pairs){
                Climate.ParameterPoint point=pair.getFirst();
                float maxY=point.temperature().max();
                float minY=point.temperature().min();
                float minCont=point.continentalness().min();
                float maxCont=point.continentalness().max();
                float pYfloat= (float) (pY + 64) /384*10000;
//                if(flag){
//                    AOEMod.LOGGER.info("{} {} {} {} {} {}", pYfloat, maxY, minY, continentalness, minCont, maxCont);
//                }
                if(pYfloat >= minY&&pYfloat <= maxY&&continentalness > minCont&&continentalness > maxCont){
//                    if(flag){
//                        AOEMod.LOGGER.info("this ran 3");
//                    }
                    return pair.getSecond();
                }
            }
        }
        return september2024PartialMod$getNoiseBiome(pSampler.sample(pX,pY,pZ));
    }
    @Inject(
            method = {"getNoiseBiome(IIILnet/minecraft/world/level/biome/Climate$Sampler;)Lnet/minecraft/core/Holder;"},
            at = {@At("RETURN")},
            cancellable = true)
    public void getNoiseBiome(int x, int y, int z, Climate.Sampler sampler, CallbackInfoReturnable<Holder<Biome>> cir) {
        cir.setReturnValue(september2024PartialMod$getNoiseBiomeHelper(x,y,z,sampler));
    }
}
