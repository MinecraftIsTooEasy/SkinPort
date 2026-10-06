package lain.mods.skinport.impl.forge.network.packet;

import lain.mods.skinport.impl.forge.SkinCustomization;
import lain.mods.skinport.impl.forge.network.SkinPortPackets;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.EntityPlayer;
import net.minecraft.ResourceLocation;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.util.UUID;

public class PacketPut1 implements Packet
{

    private UUID uuid;
    private int value;

    public PacketPut1()
    {
    }

    public PacketPut1(UUID uuid, int value)
    {
        this.uuid = uuid;
        this.value = value;
    }

    public PacketPut1(PacketByteBuf buf)
    {
        this.uuid = new UUID(buf.readLong(), buf.readLong());
        this.value = buf.readInt();
    }

    @Override
    public void write(PacketByteBuf buf)
    {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
        buf.writeInt(value);
    }

    @Override
    public void apply(EntityPlayer player)
    {
        SkinCustomization.Flags.put(MixinEnvironment.Side.CLIENT, uuid, value);
    }

    @Override
    public ResourceLocation getChannel()
    {
        return SkinPortPackets.Put1;
    }

}
