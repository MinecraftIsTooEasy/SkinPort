package lain.mods.skinport.impl.forge.network.packet;

import lain.mods.skinport.impl.forge.SkinCustomization;
import lain.mods.skinport.impl.forge.network.SkinPortPackets;
import moddedmite.rustedironcore.network.Network;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.EntityPlayer;
import net.minecraft.ResourceLocation;
import net.minecraft.ServerPlayer;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.util.UUID;

public class PacketGet1 implements Packet
{

    private UUID uuid;

    public PacketGet1()
    {
    }

    public PacketGet1(UUID uuid)
    {
        this.uuid = uuid;
    }

    public PacketGet1(PacketByteBuf buf)
    {
        this.uuid = new UUID(buf.readLong(), buf.readLong());
    }

    @Override
    public void write(PacketByteBuf buf)
    {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }

    @Override
    public void apply(EntityPlayer player)
    {
        Integer flags = SkinCustomization.Flags.get(MixinEnvironment.Side.SERVER, uuid);
        if (flags == null)
        {
            SkinCustomization.Flags.put(MixinEnvironment.Side.SERVER, uuid, flags = SkinCustomization.getDefaultFlags());
            for (Object obj : MinecraftServer.getServer().getConfigurationManager().playerEntityList)
            {
                ServerPlayer otherplayer = (ServerPlayer) obj;
                if (!otherplayer.getUniqueID().equals(uuid))
                    continue;
                Network.sendToClient(otherplayer, new PacketGet0());
            }
        }
        Network.sendToClient((ServerPlayer) player, new PacketPut1(uuid, flags));
    }

    @Override
    public ResourceLocation getChannel()
    {
        return SkinPortPackets.Get1;
    }

}
