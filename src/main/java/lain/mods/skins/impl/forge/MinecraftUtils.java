package lain.mods.skins.impl.forge;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import lain.mods.skinport.init.forge.SkinPortClient;
import net.minecraft.Minecraft;

import java.net.Proxy;

public class MinecraftUtils
{
    public static Proxy getProxy()
    {
        return Minecraft.getMinecraft().getProxy();
    }

    public static MinecraftSessionService getSessionService()
    {
        return SkinPortClient.sessionService;
    }

}
