package lain.mods.skinport.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import lain.mods.skinport.init.forge.asm.Hooks;
import net.minecraft.AbstractClientPlayer;
import net.minecraft.ResourceLocation;
import net.minecraft.ThreadDownloadImageData;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    @ModifyReturnValue(method = "getLocationSkin*", at = @At("RETURN"))
    private ResourceLocation getLocationSkin(ResourceLocation original) {
        return Hooks.getLocationSkin(ReflectHelper.dyCast(this), original);
    }

    @ModifyReturnValue(method = "getLocationCape*", at = @At("RETURN"))
    private ResourceLocation getLocationCape(ResourceLocation original) {
        return Hooks.getLocationCape(ReflectHelper.dyCast(this), original);
    }

    @ModifyReturnValue(method = "getTextureSkin", at = @At("RETURN"))
    private ThreadDownloadImageData getTextureSkin(ThreadDownloadImageData original) {
        return Hooks.getTextureSkin(ReflectHelper.dyCast(this), original);
    }

    @ModifyReturnValue(method = "getTextureCape", at = @At("RETURN"))
    private ThreadDownloadImageData getTextureCape(ThreadDownloadImageData original) {
        return Hooks.getTextureCape(ReflectHelper.dyCast(this), original);
    }
}
