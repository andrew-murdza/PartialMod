package net.amurdza.examplemod.mixins.access;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.biome.Climate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Climate.ParameterList.class)
public interface  WorldGenValuesAccessor {
    @Accessor
    <T> List<Pair<Climate.ParameterPoint, T>> getValues();
}
