package lain.mods.skinport.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import lain.mods.skinport.init.forge.asm.Hooks;
import net.minecraft.Entity;
import net.minecraft.Render;
import net.minecraft.RenderManager;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RenderManager.class)
public class RenderManagerMixin {

    @ModifyReturnValue(method = "getEntityRenderObject", at = @At("RETURN"))
    private Render getEntityRenderObject(Render original, Entity entity) {
        return Hooks.RenderManager_getEntityRenderObject(ReflectHelper.dyCast(this), entity, original);
    }
}
