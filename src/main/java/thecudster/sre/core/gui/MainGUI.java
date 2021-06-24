package thecudster.sre.core.gui;

import gg.essential.vigilance.VigilanceConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.core.gui.colours.CommonColors;
import thecudster.sre.events.SecondPassedEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainGUI extends GuiScreen {

    private SimpleButton configButton;
    private SimpleButton locationEditButton;
    private SimpleButton vigilanceEditButton;
    private SimpleButton githubButton;
    private SimpleButton discordButton;
    private SimpleButton closeGUI;
    private SimpleButton filter;
    private SimpleButton playerFilter;

    private boolean hasOpenedDiscord = false;
    private boolean hasOpenedGithub = false;
    public MainGUI() {

    }
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        super.initGui();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int height = sr.getScaledHeight();
        int width = sr.getScaledWidth();
        configButton = new SimpleButton(0, width / 2 - 100, (int) (height * 0.3), "Config");
        filter = new SimpleButton(0, width / 2 - 100, (int) (height * 0.35), "Edit Filter");
        playerFilter = new SimpleButton(0, width / 2 - 100, (int) (height * 0.4), "Edit Player Whitelist");
        locationEditButton = new SimpleButton(2, width / 2 - 100, (int) (height * 0.45), "Edit Locations");
        vigilanceEditButton = new SimpleButton(3, width / 2 - 100, (int) (height * 0.5), "Edit Vigilance");
        githubButton = new SimpleButton(4, width / 2 + 5, (int) (height * 0.55), 95, 20, "GitHub");
        discordButton = new SimpleButton(5, width / 2 - 100, (int) (height * 0.55), 95, 20, "Discord");
        closeGUI = new SimpleButton(1, width / 2 - 100, (int) (height * 0.6), "Close");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.pushMatrix();
        GlStateManager.translate(width / -1f, height * -0.2, 0.0f); // other option: GlStateManager.translate(width / -2f, height * -0.2f, 0.0f); GlStateManager.scale(2.0f, 2.0f, 1.0f);
        GlStateManager.scale(3.0f, 3.0f, 1.0f);
        ScreenRenderer.fontRenderer.drawString("SkyblockReinvented", width / 2, (int) (height * 0.1), CommonColors.RAINBOW, SmartFontRenderer.TextAlignment.MIDDLE, SmartFontRenderer.TextShadow.NORMAL);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(width / -2f, height * -0.2, 0.0f); // other option: GlStateManager.translate(width / -2f, height * -0.2f, 0.0f); GlStateManager.scale(2.0f, 2.0f, 1.0f);
        GlStateManager.scale(2.0f, 2.0f, 1.0f);
        ScreenRenderer.fontRenderer.drawString("Main GUI", width / 2, (int) (height * 0.2), CommonColors.LIGHT_GRAY, SmartFontRenderer.TextAlignment.MIDDLE, SmartFontRenderer.TextShadow.NORMAL);
        GlStateManager.popMatrix();
        ScreenRenderer.fontRenderer.drawString("by theCudster", width / 2, (int) (height * 0.67), CommonColors.LIGHT_GRAY, SmartFontRenderer.TextAlignment.MIDDLE, SmartFontRenderer.TextShadow.NORMAL);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();

        this.buttonList.add(configButton);
        this.buttonList.add(closeGUI);
        this.buttonList.add(locationEditButton);
        this.buttonList.add(vigilanceEditButton);
        this.buttonList.add(githubButton);
        this.buttonList.add(discordButton);
        this.buttonList.add(filter);
        this.buttonList.add(playerFilter);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button instanceof SimpleButton) {
            ((SimpleButton) button).hasPlayedSound = false;
        }
        if (button == configButton) {
            SkyblockReinvented.currentGui = SkyblockReinvented.config.gui();
        } else if (button == discordButton && !hasOpenedDiscord) {
            try {
                Desktop.getDesktop().browse(new URI("https://discord.com/invite/xkeYgZrRbN"));
                hasOpenedDiscord = true;
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (button == githubButton && !hasOpenedGithub) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/theCudster/SkyblockReinvented"));
                hasOpenedGithub = true;
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (button == locationEditButton) {
            SkyblockReinvented.currentGui = new LocationEditGUI();
        } else if (button == vigilanceEditButton) {
            SkyblockReinvented.currentGui = VigilanceConfig.INSTANCE.gui();
        } else if (button == closeGUI) {
            Minecraft.getMinecraft().thePlayer.closeScreen();
        } else if (button == filter) {
            SkyblockReinvented.currentGui = new FilterGUI();
        } else if (button == playerFilter) {
            SkyblockReinvented.currentGui = new RenderPlayersGUI();
        }
    }
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }
}
