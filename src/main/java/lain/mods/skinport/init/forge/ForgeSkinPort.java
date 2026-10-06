package lain.mods.skinport.init.forge;

import lain.mods.skinport.impl.forge.SkinCustomization;
import lain.mods.skins.api.SkinProviderAPI;
import lain.mods.skins.api.interfaces.IPlayerProfile;
import lain.mods.skins.api.interfaces.ISkin;
import lain.mods.skins.api.interfaces.ISkinProvider;
import lain.mods.skins.impl.LegacyConversion;
import lain.mods.skins.impl.SkinData;
import lain.mods.skins.providers.CrafatarCapeProvider;
import lain.mods.skins.providers.CrafatarSkinProvider;
import lain.mods.skins.providers.CustomServerCapeProvider;
import lain.mods.skins.providers.CustomServerCapeProvider2;
import lain.mods.skins.providers.CustomServerSkinProvider;
import lain.mods.skins.providers.CustomServerSkinProvider2;
import lain.mods.skins.providers.MojangCapeProvider;
import lain.mods.skins.providers.MojangSkinProvider;
import lain.mods.skins.providers.UserManagedCapeProvider;
import lain.mods.skins.providers.UserManagedSkinProvider;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.common.Configuration;
import net.xiaoyu233.fml.FishModLoader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.UUID;

public class ForgeSkinPort implements ModInitializer
{
    
    @Override
    public void onInitialize()
    {
        ForgeSkinPort.proxy.register();
    }
    
    private static class DefaultSkinProvider implements ISkinProvider
    {

        ISkin DefaultSteve;
        ISkin DefaultAlex;

        DefaultSkinProvider()
        {
            try
            {
                byte[] data;
                ((SkinData) (DefaultSteve = new SkinData())).put(data = IOUtils.toByteArray(DefaultSkinProvider.class.getResource("/DefaultSteve.png")), SkinData.judgeSkinType(data));
                ((SkinData) (DefaultAlex = new SkinData())).put(data = IOUtils.toByteArray(DefaultSkinProvider.class.getResource("/DefaultAlex.png")), SkinData.judgeSkinType(data));
            }
            catch (IOException e)
            {
                DefaultSteve = null;
                DefaultAlex = null;
            }
        }

        @Override
        public ISkin getSkin(IPlayerProfile profile)
        {
            UUID uuid;
            if ((uuid = profile.getPlayerID()) != null && (uuid.hashCode() & 0x1) == 1)
                return DefaultAlex;
            return DefaultSteve;
        }

    }

    public static CommonProxy proxy = new CommonProxy();

    public static void loadOptions()
    {
        try
        {
            for (String line : FileUtils.readLines(Paths.get(FishModLoader.CONFIG_DIR.getPath(), "options_skinport.txt").toFile(), StandardCharsets.UTF_8))
            {
                String[] as = line.split(":", 2);
                if (as.length != 2 || as[0].startsWith("#"))
                    continue;
                if ("clientFlags".equals(as[0]))
                    SkinCustomization.ClientFlags = Integer.parseInt(as[1]);
            }
        }
        catch (FileNotFoundException | NumberFormatException e)
        {
            saveOptions();
        }
        catch (IOException e)
        {
            System.err.printf("Error loading options: %s%n", e.getMessage());
        }
    }

    public static void saveOptions()
    {
        try
        {
            FileUtils.write(Paths.get(FishModLoader.CONFIG_DIR.getPath(), "options_skinport.txt").toFile(), String.format("clientFlags:%d", SkinCustomization.ClientFlags), StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            System.err.printf("Error saving options: %s%n", e.getMessage());
        }
    }

    public static void init()
    {
        loadOptions();
        
        Configuration config = new Configuration(new File(FishModLoader.CONFIG_DIR, "options_skinport.txt"));
        boolean useMojang = config.get("useMojang", "client", true, "").getBoolean(true);
        boolean useCrafatar = config.get("useCrafatar", "client", true, "").getBoolean(true);
        boolean useCustomServer = config.get("useCustomServer", "client", false, "").getBoolean(false);
        String hostCustomServer = config.get("hostCustomServer", "client", "http://example.com", "/skins/(uuid|username) and /capes/(uuid|username) will be queried for respective resources").getString();
        boolean useCustomServer2 = config.get("useCustomServer2", "client", false, "").getBoolean(false);
        String hostCustomServer2Skin = config.get("hostCustomServer2Skin", "client", "http://example.com/skins/%auto%", "%name% will be replaced by username, %uuid% will be replaced by uuid, %auto% will be replaced by username or uuid accordingly").getString();
        String hostCustomServer2Cape = config.get("hostCustomServer2Cape", "client", "http://example.com/capes/%auto%", "%name% will be replaced by username, %uuid% will be replaced by uuid, %auto% will be replaced by username or uuid accordingly").getString();
        if (config.hasChanged())
            config.save();
        
        SkinProviderAPI.SKIN.clearProviders();
        SkinProviderAPI.SKIN.registerProvider(new UserManagedSkinProvider(Paths.get(".", "cachedImages")).withFilter(LegacyConversion.createFilter()));
        if (useCustomServer)
            SkinProviderAPI.SKIN.registerProvider(new CustomServerSkinProvider().setHost(hostCustomServer).withFilter(LegacyConversion.createFilter()));
        if (useCustomServer2)
            SkinProviderAPI.SKIN.registerProvider(new CustomServerSkinProvider2().setHost(hostCustomServer2Skin).withFilter(LegacyConversion.createFilter()));
        if (useMojang)
            SkinProviderAPI.SKIN.registerProvider(new MojangSkinProvider().withFilter(LegacyConversion.createFilter()));
        if (useCrafatar)
            SkinProviderAPI.SKIN.registerProvider(new CrafatarSkinProvider().withFilter(LegacyConversion.createFilter()));
        SkinProviderAPI.SKIN.registerProvider(new DefaultSkinProvider());
        
        SkinProviderAPI.CAPE.clearProviders();
        SkinProviderAPI.CAPE.registerProvider(new UserManagedCapeProvider(Paths.get(".", "cachedImages")));
        if (useCustomServer)
            SkinProviderAPI.CAPE.registerProvider(new CustomServerCapeProvider().setHost(hostCustomServer));
        if (useCustomServer2)
            SkinProviderAPI.CAPE.registerProvider(new CustomServerCapeProvider2().setHost(hostCustomServer2Cape));
        if (useMojang)
            SkinProviderAPI.CAPE.registerProvider(new MojangCapeProvider());
        if (useCrafatar)
            SkinProviderAPI.CAPE.registerProvider(new CrafatarCapeProvider());
    }
}
