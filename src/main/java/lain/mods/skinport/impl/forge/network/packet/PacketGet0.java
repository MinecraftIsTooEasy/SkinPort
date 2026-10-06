package lain.mods.skinport.impl.forge.network.packet;

import lain.mods.skinport.impl.forge.SkinCustomization;
import lain.mods.skinport.impl.forge.network.SkinPortPackets;
import moddedmite.rustedironcore.network.Network;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.EntityPlayer;
import net.minecraft.ResourceLocation;

public class PacketGet0 implements Packet
{

    public PacketGet0()
    {
    }

    @Override
    public void write(PacketByteBuf buf)
    {
    }

    @Override
    public void apply(EntityPlayer player)
    {
        Network.sendToServer(new PacketPut0(SkinCustomization.ClientFlags));
    }

    @Override
    public ResourceLocation getChannel()
    {
        return SkinPortPackets.Get0;
    }

}
