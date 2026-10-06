package lain.mods.skinport.impl.forge;

import net.minecraft.ResourceManager;
import net.minecraft.ThreadDownloadImageData;

public class SkinPortTextureData extends ThreadDownloadImageData
{

    private final boolean uploaded;

    public SkinPortTextureData(boolean uploaded)
    {
        super(null, null, null);
        this.uploaded = uploaded;
    }

    @Override
    public void loadTexture(ResourceManager resourceManager)
    {
    }

    @Override
    public boolean isTextureUploaded()
    {
        return uploaded;
    }

}
