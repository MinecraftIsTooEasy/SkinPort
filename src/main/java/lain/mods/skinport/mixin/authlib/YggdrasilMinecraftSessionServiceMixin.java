package lain.mods.skinport.mixin.authlib;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(YggdrasilMinecraftSessionService.class)
public class YggdrasilMinecraftSessionServiceMixin {

    @WrapOperation(method = "<clinit>", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/LogManager;getLogger()Lorg/apache/logging/log4j/Logger;"))
    private static Logger wrapLogger(Operation<Logger> original) {
        return LogManager.getLogger(YggdrasilMinecraftSessionService.class);
    }
}
