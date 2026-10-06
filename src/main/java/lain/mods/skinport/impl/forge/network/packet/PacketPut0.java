package lain.mods.skinport.impl.forge.network.packet;

import lain.mods.skinport.impl.forge.SkinCustomization;
import lain.mods.skinport.impl.forge.network.SkinPortPackets;
import moddedmite.rustedironcore.network.Network;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.EntityPlayer;
import net.minecraft.ResourceLocation;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.util.UUID;

public class PacketPut0 implements Packet
{

    private int value;

    public PacketPut0()
    {
    }

    public PacketPut0(int value)
    {
        this.value = value;
    }

    public PacketPut0(PacketByteBuf buf)
    {
        this.value = buf.readInt();
    }

    @Override
    public void write(PacketByteBuf buf)
    {
        buf.writeInt(value);
    }

    @Override
    public void apply(EntityPlayer player)
    {
        UUID uuid = player.getUniqueID();
        SkinCustomization.Flags.put(MixinEnvironment.Side.SERVER, uuid, value);
        Network.sendToAllPlayers(new PacketPut1(uuid, value));
    }

    @Override
    public ResourceLocation getChannel()
    {
        return SkinPortPackets.Put0;
    }

}
