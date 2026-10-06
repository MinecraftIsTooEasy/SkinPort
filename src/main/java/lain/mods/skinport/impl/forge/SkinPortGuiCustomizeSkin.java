package lain.mods.skinport.impl.forge;

import lain.mods.skinport.init.forge.ForgeSkinPort;
import lain.mods.skinport.impl.forge.network.packet.PacketPut0;
import moddedmite.rustedironcore.network.Network;
import net.minecraft.GuiButton;
import net.minecraft.GuiScreen;
import net.minecraft.I18n;
import net.minecraft.Minecraft;

public class SkinPortGuiCustomizeSkin extends GuiScreen
{

    class ButtonPart extends GuiButton
    {

        private final SkinCustomization part;

        public ButtonPart(int arg0, int arg1, int arg2, int arg3, int arg4, SkinCustomization arg5)
        {
            super(arg0, arg1, arg2, arg3, arg4, printButtonText(arg5));
            part = arg5;
        }

    }

    private final GuiScreen parent;
    private String title;

    public SkinPortGuiCustomizeSkin(GuiScreen gui)
    {
        parent = gui;
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (!button.enabled)
            return;

        if (button.id == 200)
        {
            mc.displayGuiScreen(parent);
        }
        else if (button instanceof ButtonPart a)
        {
	        if (SkinCustomization.contains(SkinCustomization.ClientFlags, a.part))
                SkinCustomization.ClientFlags &= ~a.part.getFlag();
            else
                SkinCustomization.ClientFlags |= a.part.getFlag();
            ForgeSkinPort.saveOptions();
            if (Minecraft.getMinecraft().thePlayer != null)
                Network.sendToServer(new PacketPut0(SkinCustomization.ClientFlags));
            button.displayString = printButtonText(a.part);
        }
    }

    @Override
    public void drawScreen(int arg0, int arg1, float arg2)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, title, width / 2, 20, 16777215);

        super.drawScreen(arg0, arg1, arg2);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui()
    {
        title = I18n.getString("options.skinCustomisation.title");
        int i = 0;
        for (SkinCustomization part : SkinCustomization.values())
        {
            buttonList.add(new ButtonPart(i, width / 2 - 155 + i % 2 * 160, height / 6 + 24 * (i >> 1), 150, 20, part));
            i++;
        }
        if (i % 2 == 1)
            i++;
        buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 24 * (i >> 1), I18n.getString("gui.done")));
    }

    private String printButtonText(SkinCustomization part)
    {
        return part.getDisplayName().toStringWithFormatting(true) + ": " + (SkinCustomization.contains(SkinCustomization.ClientFlags, part) ? I18n.getString("options.on") : I18n.getString("options.off"));
    }

}
