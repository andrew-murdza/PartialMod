package net.amurdza.examplemod.mixins.worldgen;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import terrablender.handler.InitializationHandler;

@Mixin(InitializationHandler.class)
public class InitializationHandlerMixin {
    @Redirect(method = "onServerAboutToStart",at= @At(value = "INVOKE", target = "Lterrablender/util/LevelUtils;initializeOnServerStart(Lnet/minecraft/server/MinecraftServer;)V"),remap = false)
    private static void hi(MinecraftServer stem){

    }
}
