package lain.mods.skinport.impl.forge;

import net.minecraft.AbstractClientPlayer;
import net.minecraft.RenderManager;

public interface SpecialRenderer
{

    void onGetRenderer(RenderManager manager, AbstractClientPlayer player);

}
