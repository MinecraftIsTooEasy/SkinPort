package lain.mods.skinport.init.forge;

import com.mojang.authlib.GameProfile;
import lain.mods.skinport.impl.forge.SkinCustomization;
import lain.mods.skinport.impl.forge.SkinPortGuiCustomizeSkin;
import lain.mods.skinport.impl.forge.SkinPortModelHumanoidHead;
import lain.mods.skinport.impl.forge.SkinPortRenderPlayer;
import lain.mods.skinport.impl.forge.SkinPortTextureData;
import lain.mods.skinport.impl.forge.SpecialModel;
import lain.mods.skinport.impl.forge.SpecialRenderer;
import lain.mods.skins.api.SkinProviderAPI;
import lain.mods.skins.api.interfaces.ISkin;
import lain.mods.skins.impl.PlayerProfile;
import lain.mods.skins.impl.forge.CustomSkinTexture;
import net.minecraft.AbstractClientPlayer;
import net.minecraft.EntityPlayer;
import net.minecraft.GuiButton;
import net.minecraft.GuiOptions;
import net.minecraft.I18n;
import net.minecraft.Minecraft;
import net.minecraft.ModelBiped;
import net.minecraft.ModelSkeletonHead;
import net.minecraft.Render;
import net.minecraft.RenderManager;
import net.minecraft.ResourceLocation;
import net.minecraft.TextureObject;
import net.minecraft.TextureUtil;
import net.minecraft.ThreadDownloadImageData;
import net.minecraft.World;
import net.xiaoyu233.fml.FishModLoader;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class ClientProxy extends CommonProxy
{

    private static final Map<String, Render> renderers = new HashMap<>();
    private static final Map<ByteBuffer, CustomSkinTexture> textures = new WeakHashMap<>();
    private static final SkinPortModelHumanoidHead modelHumanoidHead = new SkinPortModelHumanoidHead();
    private static final SkinPortTextureData readyTextureData = new SkinPortTextureData(true);

    public static ResourceLocation bindTexture(GameProfile profile, ResourceLocation result)
    {
        if (profile != null)
        {
            ISkin skin = SkinProviderAPI.SKIN.getSkin(PlayerProfile.wrapGameProfile(profile));
            if (skin != null && skin.isDataReady())
                return ClientProxy.getOrCreateTexture(skin.getData(), skin).getLocation();
        }
        return null;
    }

    public static ResourceLocation generateRandomLocation()
    {
        return new ResourceLocation("skinport", String.format("textures/generated/%s", UUID.randomUUID().toString()));
    }

    public static ModelSkeletonHead getHumanoidHead(ResourceLocation location, ModelSkeletonHead result)
    {
        TextureObject texture = Minecraft.getMinecraft().getTextureManager().getTexture(location);
        if (texture instanceof CustomSkinTexture)
            return modelHumanoidHead;
        return null;
    }

    public static ResourceLocation getLocationCape(AbstractClientPlayer player, ResourceLocation result)
    {
        ISkin skin = SkinProviderAPI.CAPE.getSkin(PlayerProfile.wrapPlayer(player));
        if (skin != null && skin.isDataReady())
            return ClientProxy.getOrCreateTexture(skin.getData(), skin).getLocation();
        return null;
    }

    public static ResourceLocation getLocationSkin(AbstractClientPlayer player, ResourceLocation result)
    {
        ISkin skin = SkinProviderAPI.SKIN.getSkin(PlayerProfile.wrapPlayer(player));
        if (skin != null && skin.isDataReady())
            return ClientProxy.getOrCreateTexture(skin.getData(), skin).getLocation();
        return null;
    }

    public static ThreadDownloadImageData getTextureSkin(AbstractClientPlayer player, ThreadDownloadImageData result)
    {
        return ClientProxy.getLocationSkin(player, null) != null ? readyTextureData : result;
    }

    public static ThreadDownloadImageData getTextureCape(AbstractClientPlayer player, ThreadDownloadImageData result)
    {
        return ClientProxy.getLocationCape(player, null) != null ? readyTextureData : result;
    }

    public static CustomSkinTexture getOrCreateTexture(ByteBuffer data, ISkin skin)
    {
        if (!textures.containsKey(data))
        {
            CustomSkinTexture texture = new CustomSkinTexture(generateRandomLocation(), data);
            Minecraft.getMinecraft().getTextureManager().loadTexture(texture.getLocation(), texture);
            textures.put(data, texture);

            if (skin != null)
            {
                skin.setRemovalListener(s -> {
                    if (data == s.getData())
                    {
                        Minecraft.getMinecraft().getTextureManager().loadTexture(texture.getLocation(), TextureUtil.missingTexture);
                        GL11.glDeleteTextures(texture.getGlTextureId());
                        textures.remove(data);
                    }
                });
            }
        }
        return textures.get(data);
    }

    public static Render getPlayerRenderer(RenderManager manager, AbstractClientPlayer player, Render result)
    {
        if (renderers.isEmpty())
            setupRenderers(manager);
        result = renderers.getOrDefault(getSkinType(player), result);
        if (result instanceof SpecialRenderer)
            ((SpecialRenderer) result).onGetRenderer(manager, player);
        return result;
    }

    public static String getSkinType(AbstractClientPlayer player)
    {
        ResourceLocation location = getLocationSkin(player, null);
        if (location != null)
        {
            ISkin skin = SkinProviderAPI.SKIN.getSkin(PlayerProfile.wrapPlayer(player));
            if (skin != null && skin.isDataReady())
                return skin.getSkinType();
        }
        return "default";
    }

    public static boolean hasCape(AbstractClientPlayer player, boolean result)
    {
        return player.getLocationCape() != null;
    }

    public static boolean hasSkin(AbstractClientPlayer player, boolean result)
    {
        return player.getLocationSkin() != null;
    }

    public static int initHeight(ModelBiped model, int textureHeight)
    {
        if (model instanceof SpecialModel)
            return ((SpecialModel) model).initHeight();
        return textureHeight;
    }

    public static int initWidth(ModelBiped model, int textureWidth)
    {
        if (model instanceof SpecialModel)
            return ((SpecialModel) model).initWidth();
        return textureWidth;
    }

    public static void onButtonAction(GuiOptions gui, GuiButton button)
    {
        if (!button.enabled || button.id != 110)
            return;
        Minecraft.getMinecraft().gameSettings.saveOptions();
        Minecraft.getMinecraft().displayGuiScreen(new SkinPortGuiCustomizeSkin(gui));
    }

    public static void setupButton(GuiOptions gui, List<GuiButton> buttonList)
    {
        if (FishModLoader.hasMod("better_game_setting"))
        {
            buttonList.add(new GuiButton(110, gui.width / 2 - 152, gui.height / 6 + 96 - 30, 150, 20, I18n.getString("options.skinCustomisation")));
        }
        else
        {
            buttonList.add(new GuiButton(110, gui.width / 2 - 255, gui.height / 6 + 168, 150, 20, I18n.getString("options.skinCustomisation")));
        }
    }

    public static void setupRenderers(RenderManager manager)
    {
        renderers.put("default", new SkinPortRenderPlayer(manager, false));
        renderers.put("slim", new SkinPortRenderPlayer(manager, true));
    }

    public void handleClientTicks()
    {
        World world = Minecraft.getMinecraft().theWorld;
        if (world != null)
        {
            for (Object obj : world.playerEntities)
            {
                EntityPlayer player = (EntityPlayer) obj;
                SkinProviderAPI.SKIN.getSkin(PlayerProfile.wrapPlayer(player));
                SkinProviderAPI.CAPE.getSkin(PlayerProfile.wrapPlayer(player));
            }
        }
    }

    public void onDisconnect()
    {
        SkinCustomization.Flags.clear(MixinEnvironment.Side.CLIENT);
    }

}
