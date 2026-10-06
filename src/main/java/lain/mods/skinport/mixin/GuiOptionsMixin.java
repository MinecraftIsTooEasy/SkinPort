package lain.mods.skinport.mixin;

import lain.mods.skinport.init.forge.ClientProxy;
import net.minecraft.GuiButton;
import net.minecraft.GuiOptions;
import net.minecraft.GuiScreen;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiOptions.class)
public class GuiOptionsMixin extends GuiScreen {
    @Inject(method = "initGui", at = @At("TAIL"))
    private void addButton(CallbackInfo ci) {
        ClientProxy.setupButton(ReflectHelper.dyCast(this), this.buttonList);
    }

    @Inject(method = "actionPerformed", at = @At("TAIL"))
    private void actionPerformed(GuiButton par1GuiButton, CallbackInfo ci) {
        ClientProxy.onButtonAction(ReflectHelper.dyCast(this), par1GuiButton);
    }
}
