package lain.mods.skinport.init.forge.asm;

import lain.mods.skinport.init.forge.ClientProxy;
import net.minecraft.AbstractClientPlayer;
import net.minecraft.Entity;
import net.minecraft.EntityClientPlayerMP;
import net.minecraft.EntityOtherPlayerMP;
import net.minecraft.Render;
import net.minecraft.RenderManager;
import net.minecraft.ResourceLocation;
import net.minecraft.ThreadDownloadImageData;

public class Hooks
{

    public static ResourceLocation getLocationCape(AbstractClientPlayer player, ResourceLocation result)
    {
        ResourceLocation loc = ClientProxy.getLocationCape(player, result);
        if (loc != null)
            return loc;
        return result;
    }

    public static ResourceLocation getLocationSkin(AbstractClientPlayer player, ResourceLocation result)
    {
        ResourceLocation loc = ClientProxy.getLocationSkin(player, result);
        if (loc != null)
            return loc;
        return result;
    }

    public static ThreadDownloadImageData getTextureSkin(AbstractClientPlayer player, ThreadDownloadImageData result)
    {
        return ClientProxy.getTextureSkin(player, result);
    }

    public static ThreadDownloadImageData getTextureCape(AbstractClientPlayer player, ThreadDownloadImageData result)
    {
        return ClientProxy.getTextureCape(player, result);
    }

    public static Render RenderManager_getEntityRenderObject(RenderManager manager, Entity entity, Render result)
    {
        if (entity instanceof EntityClientPlayerMP || entity instanceof EntityOtherPlayerMP)
        {
            Render renderer = ClientProxy.getPlayerRenderer(manager, (AbstractClientPlayer) entity, result);
            if (renderer != null)
                return renderer;
        }
        return result;
    }
}
