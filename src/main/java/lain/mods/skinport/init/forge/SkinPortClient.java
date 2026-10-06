package lain.mods.skinport.init.forge;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import lain.mods.skinport.impl.forge.network.SkinPortPackets;
import moddedmite.rustedironcore.api.event.Handlers;
import moddedmite.rustedironcore.api.event.events.PlayerLoggedOutEvent;
import moddedmite.rustedironcore.api.event.listener.IInitializationListener;
import moddedmite.rustedironcore.api.event.listener.IPlayerEventListener;
import moddedmite.rustedironcore.api.event.listener.ITickListener;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.Minecraft;
import net.xiaoyu233.fml.ModResourceManager;

import java.util.UUID;

public class SkinPortClient implements ClientModInitializer, ITickListener, IPlayerEventListener, IInitializationListener
{
    public static MinecraftSessionService sessionService = null;

    @Override
    public void onInitializeClient()
    {
        ForgeSkinPort.proxy = new ClientProxy();
        ForgeSkinPort.init();
        SkinPortPackets.registerClientReaders();
        Handlers.Tick.register(this);
        Handlers.PlayerEvent.register(this);
        Handlers.Initialization.register(this);
        ModResourceManager.addResourcePackDomain("skinport");
    }

    @Override
    public void onClientTick(Minecraft client)
    {
        ((ClientProxy) ForgeSkinPort.proxy).handleClientTicks();
    }

    @Override
    public void onPlayerLoggedOut(PlayerLoggedOutEvent event)
    {
        ((ClientProxy) ForgeSkinPort.proxy).onDisconnect();
    }

    @Override
    public void onClientStarted(Minecraft client) {
        this.sessionService = (new YggdrasilAuthenticationService(Minecraft.getMinecraft().getProxy(), UUID.randomUUID().toString())).createMinecraftSessionService();
    }
}
