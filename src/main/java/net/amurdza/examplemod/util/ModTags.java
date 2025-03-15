package net.amurdza.examplemod.util;

import net.amurdza.examplemod.AOEMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModTags {
    public static class Biomes{
        public static final TagKey<Biome> tropicalBiomes=tag("tropical_biomes");

        private static TagKey<Biome> tag(String name){
            return TagKey.create(Registries.BIOME,new ResourceLocation(AOEMod.MOD_ID,name));
        }
    }
}
