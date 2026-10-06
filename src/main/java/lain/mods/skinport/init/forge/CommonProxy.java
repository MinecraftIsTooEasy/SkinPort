package lain.mods.skinport.init.forge;

import lain.mods.skinport.impl.forge.SkinCustomization;
import lain.mods.skinport.impl.forge.network.SkinPortPackets;
import lain.mods.skinport.impl.forge.network.packet.PacketGet0;
import moddedmite.rustedironcore.api.event.Handlers;
import moddedmite.rustedironcore.api.event.events.PlayerLoggedInEvent;
import moddedmite.rustedironcore.api.event.events.PlayerLoggedOutEvent;
import moddedmite.rustedironcore.api.event.listener.IPlayerEventListener;
import moddedmite.rustedironcore.network.Network;
import org.spongepowered.asm.mixin.MixinEnvironment;

public class CommonProxy
{
    public void register()
    {
        SkinPortPackets.registerServerReaders();
        Handlers.PlayerEvent.register(new IPlayerEventListener()
        {
            @Override
            public void onPlayerLoggedIn(PlayerLoggedInEvent event)
            {
                Network.sendToClient(event.player(), new PacketGet0());
            }
            
            @Override
            public void onPlayerLoggedOut(PlayerLoggedOutEvent event)
            {
                SkinCustomization.Flags.remove(MixinEnvironment.Side.SERVER, event.player().getUniqueID());
            }
        });
    }
}
