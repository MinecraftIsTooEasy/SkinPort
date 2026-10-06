package lain.mods.skinport.impl.forge;

import lain.mods.skins.impl.PlayerProfile;
import net.minecraft.AbstractClientPlayer;
import net.minecraft.EntityPlayer;
import net.minecraft.Minecraft;
import net.minecraft.ModelBiped;
import net.minecraft.RenderManager;
import net.minecraft.RenderPlayer;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SkinPortRenderPlayer extends RenderPlayer
{

    public SkinPortModelPlayer modelPlayer;

    public SkinPortRenderPlayer(RenderManager manager, boolean smallArms)
    {
        super();

        setRenderManager(manager);
        mainModel = new SkinPortModelPlayer(0.0F, smallArms);
        modelBipedMain = (ModelBiped) mainModel;
        modelPlayer = (SkinPortModelPlayer) mainModel;

        try
        {
            Field field = RenderPlayer.class.getDeclaredField("models");
            field.setAccessible(true);
            ((List<ModelBiped>) field.get(this)).add(modelPlayer);
        }
        catch (Exception e)
        {
        }
    }

    @Override
    public void func_130009_a(AbstractClientPlayer p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        boolean smHeadwear = modelPlayer.bipedHeadwear.showModel;
        boolean smLeftLegwear = modelPlayer.bipedLeftLegwear.showModel;
        boolean smRightLegwear = modelPlayer.bipedRightLegwear.showModel;
        boolean smLeftArmwear = modelPlayer.bipedLeftArmwear.showModel;
        boolean smRightArmwear = modelPlayer.bipedRightArmwear.showModel;
        boolean smBodyWear = modelPlayer.bipedBodyWear.showModel;
        boolean smCloak = modelPlayer.bipedCloak.showModel;

        int flags = getFlags(p_76986_1_);
        if (modelPlayer.bipedHeadwear.showModel)
            modelPlayer.bipedHeadwear.showModel = SkinCustomization.contains(flags, SkinCustomization.hat);
        if (modelPlayer.bipedLeftLegwear.showModel)
            modelPlayer.bipedLeftLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_pants_leg);
        if (modelPlayer.bipedRightLegwear.showModel)
            modelPlayer.bipedRightLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_pants_leg);
        if (modelPlayer.bipedLeftArmwear.showModel)
            modelPlayer.bipedLeftArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_sleeve);
        if (modelPlayer.bipedRightArmwear.showModel)
            modelPlayer.bipedRightArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_sleeve);
        if (modelPlayer.bipedBodyWear.showModel)
            modelPlayer.bipedBodyWear.showModel = SkinCustomization.contains(flags, SkinCustomization.jacket);
        if (modelPlayer.bipedCloak.showModel)
            modelPlayer.bipedCloak.showModel = SkinCustomization.contains(flags, SkinCustomization.cape);

        super.func_130009_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);

        modelPlayer.bipedHeadwear.showModel = smHeadwear;
        modelPlayer.bipedLeftLegwear.showModel = smLeftLegwear;
        modelPlayer.bipedRightLegwear.showModel = smRightLegwear;
        modelPlayer.bipedLeftArmwear.showModel = smLeftArmwear;
        modelPlayer.bipedRightArmwear.showModel = smRightArmwear;
        modelPlayer.bipedBodyWear.showModel = smBodyWear;
        modelPlayer.bipedCloak.showModel = smCloak;
    }

    private int getFlags(EntityPlayer player)
    {
        if (player == Minecraft.getMinecraft().thePlayer)
            return SkinCustomization.ClientFlags;
        UUID uuid = player.getUniqueIDSilent();
        if (uuid == null)
            uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getCommandSenderName()).getBytes(java.nio.charset.StandardCharsets.UTF_8));
        Optional<UUID> uuid2 = Optional.ofNullable(PlayerProfile.wrapPlayer(player).getPlayerID());
        Integer flags = SkinCustomization.Flags.get(MixinEnvironment.Side.CLIENT, uuid, uuid2);
        if (flags == null)
        {
            SkinCustomization.Flags.put(MixinEnvironment.Side.CLIENT, uuid, uuid2, flags = SkinCustomization.getDefaultFlags());
        }
        return flags;
    }

    @Override
    protected void renderSpecials(AbstractClientPlayer p_77029_1_, float p_77029_2_)
    {
        boolean smHeadwear = modelPlayer.bipedHeadwear.showModel;
        boolean smLeftLegwear = modelPlayer.bipedLeftLegwear.showModel;
        boolean smRightLegwear = modelPlayer.bipedRightLegwear.showModel;
        boolean smLeftArmwear = modelPlayer.bipedLeftArmwear.showModel;
        boolean smRightArmwear = modelPlayer.bipedRightArmwear.showModel;
        boolean smBodyWear = modelPlayer.bipedBodyWear.showModel;
        boolean smCloak = modelPlayer.bipedCloak.showModel;

        int flags = getFlags(p_77029_1_);
        if (modelPlayer.bipedHeadwear.showModel)
            modelPlayer.bipedHeadwear.showModel = SkinCustomization.contains(flags, SkinCustomization.hat);
        if (modelPlayer.bipedLeftLegwear.showModel)
            modelPlayer.bipedLeftLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_pants_leg);
        if (modelPlayer.bipedRightLegwear.showModel)
            modelPlayer.bipedRightLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_pants_leg);
        if (modelPlayer.bipedLeftArmwear.showModel)
            modelPlayer.bipedLeftArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_sleeve);
        if (modelPlayer.bipedRightArmwear.showModel)
            modelPlayer.bipedRightArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_sleeve);
        if (modelPlayer.bipedBodyWear.showModel)
            modelPlayer.bipedBodyWear.showModel = SkinCustomization.contains(flags, SkinCustomization.jacket);
        if (modelPlayer.bipedCloak.showModel)
            modelPlayer.bipedCloak.showModel = SkinCustomization.contains(flags, SkinCustomization.cape);

        super.renderSpecials(p_77029_1_, p_77029_2_);

        modelPlayer.bipedHeadwear.showModel = smHeadwear;
        modelPlayer.bipedLeftLegwear.showModel = smLeftLegwear;
        modelPlayer.bipedRightLegwear.showModel = smRightLegwear;
        modelPlayer.bipedLeftArmwear.showModel = smLeftArmwear;
        modelPlayer.bipedRightArmwear.showModel = smRightArmwear;
        modelPlayer.bipedBodyWear.showModel = smBodyWear;
        modelPlayer.bipedCloak.showModel = smCloak;
    }

    @Override
    public void renderFirstPersonArm(EntityPlayer player)
    {
        boolean smHeadwear = modelPlayer.bipedHeadwear.showModel;
        boolean smLeftLegwear = modelPlayer.bipedLeftLegwear.showModel;
        boolean smRightLegwear = modelPlayer.bipedRightLegwear.showModel;
        boolean smLeftArmwear = modelPlayer.bipedLeftArmwear.showModel;
        boolean smRightArmwear = modelPlayer.bipedRightArmwear.showModel;
        boolean smBodyWear = modelPlayer.bipedBodyWear.showModel;
        boolean smCloak = modelPlayer.bipedCloak.showModel;

        int flags = getFlags(player);
        if (modelPlayer.bipedHeadwear.showModel)
            modelPlayer.bipedHeadwear.showModel = SkinCustomization.contains(flags, SkinCustomization.hat);
        if (modelPlayer.bipedLeftLegwear.showModel)
            modelPlayer.bipedLeftLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_pants_leg);
        if (modelPlayer.bipedRightLegwear.showModel)
            modelPlayer.bipedRightLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_pants_leg);
        if (modelPlayer.bipedLeftArmwear.showModel)
            modelPlayer.bipedLeftArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_sleeve);
        if (modelPlayer.bipedRightArmwear.showModel)
            modelPlayer.bipedRightArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_sleeve);
        if (modelPlayer.bipedBodyWear.showModel)
            modelPlayer.bipedBodyWear.showModel = SkinCustomization.contains(flags, SkinCustomization.jacket);
        if (modelPlayer.bipedCloak.showModel)
            modelPlayer.bipedCloak.showModel = SkinCustomization.contains(flags, SkinCustomization.cape);

        modelPlayer.isRiding = modelPlayer.isSneak = false;
        super.renderFirstPersonArm(player);

        modelPlayer.bipedHeadwear.showModel = smHeadwear;
        modelPlayer.bipedLeftLegwear.showModel = smLeftLegwear;
        modelPlayer.bipedRightLegwear.showModel = smRightLegwear;
        modelPlayer.bipedLeftArmwear.showModel = smLeftArmwear;
        modelPlayer.bipedRightArmwear.showModel = smRightArmwear;
        modelPlayer.bipedBodyWear.showModel = smBodyWear;
        modelPlayer.bipedCloak.showModel = smCloak;
    }

}
