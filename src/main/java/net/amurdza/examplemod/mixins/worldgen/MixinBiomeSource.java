//package net.amurdza.examplemod.mixins.worldgen;
//
//import com.google.common.collect.ImmutableList;
//import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
//import net.minecraft.core.Holder;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.biome.BiomeResolver;
//import net.minecraft.world.level.biome.BiomeSource;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import terrablender.worldgen.IExtendedBiomeSource;
//
//import java.util.List;
//import java.util.Set;
//import java.util.function.Supplier;
//
//@Mixin(value = BiomeSource.class,priority = 1001)
//public abstract class MixinBiomeSource implements BiomeResolver, IExtendedBiomeSource {
//    @Shadow
//    public Supplier<Set<Holder<Biome>>> possibleBiomes;
//    private boolean hasAppended = false;
//
//    public MixinBiomeSource() {
//    }
//
//    public void appendDeferredBiomesList(List<Holder<Biome>> biomesToAppend) {
//        if (!this.hasAppended) {
//            ImmutableList.Builder<Holder<Biome>> builder = ImmutableList.builder();
//            builder.addAll((Iterable)this.possibleBiomes.get());
//            builder.addAll(biomesToAppend);
//            ImmutableList<Holder<Biome>> biomeList = (ImmutableList)builder.build().stream().distinct().collect(ImmutableList.toImmutableList());
//            this.possibleBiomes = () -> {
//                return new ObjectLinkedOpenHashSet(biomeList);
//            };
//            this.hasAppended = true;
//        }
//    }
//}
