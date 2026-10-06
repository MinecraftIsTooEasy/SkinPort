package lain.mods.skinport.impl.forge.network;

import lain.mods.skinport.impl.forge.network.packet.PacketGet0;
import lain.mods.skinport.impl.forge.network.packet.PacketGet1;
import lain.mods.skinport.impl.forge.network.packet.PacketPut0;
import lain.mods.skinport.impl.forge.network.packet.PacketPut1;
import moddedmite.rustedironcore.network.PacketReader;
import net.minecraft.ResourceLocation;

public class SkinPortPackets
{

    public static final ResourceLocation Get0 = new ResourceLocation("skinport", "get0");
    public static final ResourceLocation Get1 = new ResourceLocation("skinport", "get1");
    public static final ResourceLocation Put0 = new ResourceLocation("skinport", "put0");
    public static final ResourceLocation Put1 = new ResourceLocation("skinport", "put1");

    public static void registerClientReaders()
    {
        PacketReader.registerClientPacketReader(Get0, packetByteBuf -> new PacketGet0());
        PacketReader.registerClientPacketReader(Put1, PacketPut1::new);
    }

    public static void registerServerReaders()
    {
        PacketReader.registerServerPacketReader(Put0, PacketPut0::new);
        PacketReader.registerServerPacketReader(Get1, PacketGet1::new);
    }

}
