package thecudster.sre.core.gui;

import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.config.GuiCheckBox;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.core.gui.colours.CommonColors;
import thecudster.sre.features.impl.filter.CustomPlayersFilter;
import thecudster.sre.util.Utils;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RenderPlayersGUI extends GuiScreen {
    private SimpleButton add;
    private SimpleButton close;

    private File customPlayerFiltersJson = new File(SkyblockReinvented.modDir, "customPlayersFilter.json");

    private GuiTextField input;

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    @Override
    public void initGui() {
        super.initGui();
        try {
            if (!customPlayerFiltersJson.exists()) {
                customPlayerFiltersJson.createNewFile();
            }
        } catch (Exception ex) {
        }
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int height = sr.getScaledHeight();
        int width = sr.getScaledWidth();
        add = new SimpleButton(2, width / 2 - 100, (int) (height * 0.3), "Add Whitelisted Player");
        close = new SimpleButton(1, width / 2 - 100, (int) (height * 0.4), "Close");

        input = new GuiTextField(0, this.fontRendererObj, width / 2 - 98, (int) (height * 0.35), 196, 20);
        input.setVisible(true);
        input.setEnabled(true);
        this.customPlayersFilter.clear();
        readConfig();
        refresh();
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        input.drawTextBox();
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
        ScreenRenderer.fontRenderer.drawString("Player Whitelist", width / 2, (int) (height * 0.2), CommonColors.LIGHT_GRAY, SmartFontRenderer.TextAlignment.MIDDLE, SmartFontRenderer.TextShadow.NORMAL);
        GlStateManager.popMatrix();
        ScreenRenderer.fontRenderer.drawString("by theCudster", width / 2, (int) (height * 0.47), CommonColors.LIGHT_GRAY, SmartFontRenderer.TextAlignment.MIDDLE, SmartFontRenderer.TextShadow.NORMAL);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        readConfig();
        refresh();
    }

    private void refresh() {
        this.buttonList.clear();
        this.buttonList.add(add);
        this.buttonList.add(close);
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int y = sr.getScaledHeight() / 5;
        int x = sr.getScaledHeight() / 30;
        int longest = 0;
        readConfig();
        for (Map.Entry<String, CustomPlayersFilter> filter : this.customPlayersFilter.entrySet()) {
            if (filter.getValue().getPlayerName().length() > 0) {
                String s = filter.getValue().getPlayerName();
                if (s.length() > 19) {
                    s = s.substring(0, 17) + "...";
                }
                if (y > sr.getScaledHeight() - sr.getScaledHeight() / 4) {
                    y = sr.getScaledHeight() / 5;
                    x += longest + 1 + sr.getScaledWidth() / 20;
                    longest = 0;
                }
                if (x > sr.getScaledWidth() / 3.5 && x < (sr.getScaledWidth() / 3.5 + sr.getScaledWidth() / 3)) {
                    x += sr.getScaledWidth() / 3;
                }
                GlStateManager.pushMatrix();
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                ScreenRenderer.fontRenderer.drawString(s, x + sr.getScaledWidth() / 18, y, CommonColors.LIGHT_GRAY, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                GlStateManager.popMatrix();
                SimpleButton thisButton = new SimpleButton(-1 + x + y, x, y - (int) (ScreenRenderer.screen.getScaledHeight() * 0.012), 50, 20, "Remove", filter.getKey());
                if (!buttons.contains(thisButton) && (this.customPlayersFilter.containsKey(thisButton.filterName))) {
                    this.buttonList.add(thisButton);
                }
                y += 0.05f * ScreenRenderer.screen.getScaledHeight();
                if (mc.fontRendererObj.getStringWidth(s) > longest) {
                    longest = mc.fontRendererObj.getStringWidth(s);
                }
            }
        }
        for (SimpleButton button : buttons) {
            this.buttonList.add(button);
        }
    }
    private ArrayList<SimpleButton> buttons = new ArrayList<>();
    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id > 5 && button instanceof SimpleButton) {
            SimpleButton simpleButton = (SimpleButton) button;
            if (simpleButton.filterName != null) {
                if (this.customPlayersFilter.containsKey(((SimpleButton) button).filterName)) {
                    this.customPlayersFilter.remove(((SimpleButton) button).filterName);
                    this.buttons.remove(button);
                    this.buttonList.remove(button);
                    writeRemoveConfig(simpleButton.filterName);
                }
            }
        }
        if (button == add) {
            if (this.input.getText() != null) {
                SkyblockReinvented.config.listToRender.add(new CustomPlayersFilter(this.input.getText()));
                this.customPlayersFilter.put("customPlayersFilter" + this.customPlayersFilter.size(), new CustomPlayersFilter(this.input.getText()));
                writeConfig();
                input.setText("");
                input.setMaxStringLength(100);
            }
        } else if (button == close) {
            Minecraft.getMinecraft().displayGuiScreen(new MainGUI());
            return;
        }
        refresh();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        input.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        input.textboxKeyTyped(typedChar, keyCode);
    }
    private HashMap<String, CustomPlayersFilter> customPlayersFilter = new HashMap<>();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void readConfig() {
        JsonObject file;
        SkyblockReinvented.config.listToRender.clear();
        try (FileReader in = new FileReader(new File(SkyblockReinvented.modDir, "customPlayersFilter.json"))) {
            file = gson.fromJson(in, JsonObject.class);
            for (int i = 0; i < file.entrySet().size(); i++) {
                if (file.get("customPlayersFilter" + i) != null) {
                    String name = file.get("customPlayersFilter" + i).getAsJsonObject().get("name").getAsString();
                    SkyblockReinvented.config.listToRender.add(new CustomPlayersFilter(name));
                    this.customPlayersFilter.put("customPlayersFilter" + i, new CustomPlayersFilter(name));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeConfig() {
        try (FileWriter writer = new FileWriter(new File(SkyblockReinvented.modDir, "customPlayersFilter.json"))) {
            gson.toJson(customPlayersFilter, writer);
        } catch (Exception ex) {

        }
        readConfig();
    }
    public void writeRemoveConfig(String id) {
        if (this.customPlayersFilter.containsKey(id)) {
            SkyblockReinvented.config.listToRender.remove(customPlayersFilter.get(id));
            customPlayersFilter.remove(customPlayersFilter.get(id));
        }
        try (FileWriter writer = new FileWriter(new File(SkyblockReinvented.modDir, "customPlayersFilter.json"))) {
            gson.toJson(customPlayersFilter, writer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
