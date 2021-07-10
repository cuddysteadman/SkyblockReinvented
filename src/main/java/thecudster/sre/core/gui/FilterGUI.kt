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
import thecudster.sre.features.impl.filter.Filter;
import thecudster.sre.features.impl.filter.FilterConcat;
import thecudster.sre.util.Utils;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FilterGUI extends GuiScreen {
    private SimpleButton type;
    private SimpleButton add;
    private SimpleButton needsDungeons;
    private SimpleButton close;

    private File customFitlersJson = new File(SkyblockReinvented.modDir, "customFilters.json");
    private File customConcatFiltersJson = new File(SkyblockReinvented.modDir, "customConcatFilters.json");

    private GuiTextField input;

    private boolean typeToggle;
    private boolean needsDungeonsToggle;

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    @Override
    public void initGui() {
        super.initGui();
        try {
            if (!customFitlersJson.exists()) {
                customFitlersJson.createNewFile();
            }
            if (!customConcatFiltersJson.exists()) {
                customConcatFiltersJson.createNewFile();
            }
        } catch (Exception ex) {

        }
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int height = sr.getScaledHeight();
        int width = sr.getScaledWidth();
        type = new SimpleButton(0, width / 2 -100, (int) (height * 0.3), "Current Type: " + getType());
        needsDungeons = new SimpleButton(1, width / 2 - 100, (int) (height * 0.35), "Needs Dungeons: " + getNeedsDugeons());
        input = new GuiTextField(0, this.fontRendererObj, width / 2 - 98, (int) (height * 0.4), 196, 20);
        add = new SimpleButton(2, width / 2 - 100, (int) (height * 0.45), "Add Filter");
        close = new SimpleButton(1, width / 2 - 100, (int) (height * 0.5), "Close");

        input.setVisible(true);
        input.setEnabled(true);
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
        ScreenRenderer.fontRenderer.drawString("Custom Filters", width / 2, (int) (height * 0.2), CommonColors.LIGHT_GRAY, SmartFontRenderer.TextAlignment.MIDDLE, SmartFontRenderer.TextShadow.NORMAL);
        GlStateManager.popMatrix();
        ScreenRenderer.fontRenderer.drawString("by theCudster", width / 2, (int) (height * 0.57), CommonColors.LIGHT_GRAY, SmartFontRenderer.TextAlignment.MIDDLE, SmartFontRenderer.TextShadow.NORMAL);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        readConfig();
        refresh();
    }
    private String getNeedsDugeons() {
        if (this.needsDungeonsToggle) {
            return EnumChatFormatting.GREEN + "YES";
        } else {
            return EnumChatFormatting.RED + "NO";
        }
    }
    private String getType() {
        if (!this.typeToggle) {
            return EnumChatFormatting.GREEN + "Simple";
        } else {
            return EnumChatFormatting.GREEN + "Concatenation";
        }
    }

    private void refresh() {
        this.buttonList.clear();
        type = new SimpleButton(0, width / 2 -100, (int) (height * 0.3), "Current Type: " + getType());
        needsDungeons = new SimpleButton(1, width / 2 - 100, (int) (height * 0.35), "Needs Dungeons: " + getNeedsDugeons());
        this.buttonList.add(add);
        this.buttonList.add(type);
        this.buttonList.add(needsDungeons);
        this.buttonList.add(close);
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int y = sr.getScaledHeight() / 5;
        int x = sr.getScaledHeight() / 30;
        int longest = 0;
        for (Map.Entry<String, Filter> filter : customFilters.entrySet()) {
            if (filter.getValue().getMessageArr().length > 0) {
                String s = filter.getValue().getMessageArr()[0];
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
                if (!buttons.contains(thisButton) && (this.customFilters.containsKey(thisButton.filterName) || this.customConcatFilters.containsKey(thisButton.filterName))) {
                    this.buttonList.add(thisButton);
                }
                y += 0.05f * ScreenRenderer.screen.getScaledHeight();
                if (mc.fontRendererObj.getStringWidth(s) > longest) {
                    longest = mc.fontRendererObj.getStringWidth(s);
                }
            }
        }
        for (Map.Entry<String, FilterConcat> filter : customConcatFilters.entrySet()) {
            if (filter.getValue().getConcatCheck().length > 0) {
                String s = filter.getValue().getConcatCheck().toString();
                if (s.length() > 19) {
                    s = s.substring(0, 17) + "...";
                }
                if (y > sr.getScaledHeight() - sr.getScaledHeight() / 4) {
                    y = sr.getScaledHeight() / 5;
                    x += longest + 1 + sr.getScaledWidth() / 20;
                    longest = 0;
                }
                if (x > sr.getScaledWidth() / 3.5 && x < (sr.getScaledWidth() / 3.5 + sr.getScaledWidth() / 3)) {
                    x += sr.getScaledWidth() / 4;
                }
                GlStateManager.pushMatrix();
                GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                ScreenRenderer.fontRenderer.drawString(s, x + sr.getScaledWidth() / 18, y, CommonColors.LIGHT_GRAY, SmartFontRenderer.TextAlignment.LEFT_RIGHT, SmartFontRenderer.TextShadow.NORMAL);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                GlStateManager.popMatrix();
                SimpleButton thisButton = new SimpleButton(-1 + y + x, x, y - (int)(sr.getScaledHeight() * 0.05f), 50, 20, "Remove", filter.getKey());
                if (!buttons.contains(thisButton) && (this.customFilters.containsKey(thisButton.filterName) || this.customConcatFilters.containsKey(thisButton.filterName))) {
                    buttons.add(thisButton);
                }
                y += 0.05f * ScreenRenderer.screen.getScaledHeight();
                if (mc.fontRendererObj.getStringWidth(s) > longest) {
                    longest = mc.fontRendererObj.getStringWidth(s);
                }
            }
        }
        for (SimpleButton button : buttons) {
            if (this.customConcatFilters.containsKey(button.filterName) || this.customFilters.containsKey(button.filterName)) {
                this.buttonList.add(button);
            }
        }
    }
    private ArrayList<SimpleButton> buttons = new ArrayList<>();
    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id > 5 && button instanceof SimpleButton) {
            SimpleButton simpleButton = (SimpleButton) button;
            if (simpleButton.filterName != null) {
                if (this.customFilters.containsKey(((SimpleButton) button).filterName)) {
                    this.customFilters.remove(((SimpleButton) button).filterName);
                    this.buttons.remove(button);
                    this.buttonList.remove(button);
                    writeRemoveConfig(simpleButton.filterName);
                } else if (this.customConcatFilters.containsKey(((SimpleButton) button).filterName)) {
                    this.customConcatFilters.remove(((SimpleButton) button).filterName);
                    this.buttons.remove(button);
                    this.buttonList.remove(button);
                    writeRemoveConfig(simpleButton.filterName);
                }
            }
        }
        if (button == add) {
            SkyblockReinvented.filter.add(this.typeToggle, add(this.input.getText()), true);
            writeConfig(this.typeToggle, add(this.input.getText()), this.needsDungeonsToggle);
            input.setText("");
            input.setMaxStringLength(100);
        } else if (button == type) {
            this.typeToggle = !this.typeToggle;
        } else if (button == needsDungeons) {
            this.needsDungeonsToggle = !this.needsDungeonsToggle;
        } else if (button == close) {
            Minecraft.getMinecraft().displayGuiScreen(new MainGUI());
            return;
        }
        refresh();
    }
    private String[] add(String total) {
        String tempTotal = total;
        ArrayList<String> temp = new ArrayList<>();
        if (tempTotal.length() != 0) {
            if (!tempTotal.contains(",")) {
                return new String[]{tempTotal};
            }
            while (tempTotal.contains(", ")) {
                temp.add(tempTotal.substring(0, tempTotal.indexOf(", ")));
                tempTotal = tempTotal.substring(tempTotal.indexOf(", ") + 2);
            }
            if (tempTotal.length() > 0) {
                temp.add(tempTotal);
            }
        }
        String[] toArray = new String[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            toArray[i] = temp.get(i);
        }
        return toArray;
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
    private HashMap<String, Filter> customFilters = new HashMap<>();
    private HashMap<String, FilterConcat> customConcatFilters = new HashMap<>();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void readConfig() {
        JsonObject file;
        try (FileReader in = new FileReader(new File(SkyblockReinvented.modDir, "customFilters.json"))) {
            file = gson.fromJson(in, JsonObject.class);
            for (int i = 0; i < file.entrySet().size(); i++) {
                if (file.get("customFilter" + i) != null) {
                    JsonArray obj = file.get("customFilter" + i).getAsJsonObject().get("messageArr").getAsJsonArray();
                    String[] filterVal = new String[obj.size()];
                    for (int j = 0; j < obj.size(); j++) {
                        filterVal[j] = obj.get(j).getAsString();
                    }
                    Filter myFilter = new Filter(filterVal, file.get("customFilter" + i).getAsJsonObject().get("shouldCancel").getAsBoolean(),
                            file.get("customFilter" + i).getAsJsonObject().get("needsDungeons").getAsBoolean());
                    SkyblockReinvented.filter.add(true, myFilter.getMessageArr(), myFilter.getShouldCancel(), myFilter.getNeedsDungeons());
                    this.customFilters.put("customFilter" + i, myFilter);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try (FileReader in = new FileReader(new File(SkyblockReinvented.modDir, "customConcatFilters.json"))) {
            file = gson.fromJson(in, JsonObject.class);
            for (int i = 0; i < file.entrySet().size(); i++) {
                if (file.get("customFilter" + i) != null) {
                    JsonArray obj = file.get("customFilter" + i).getAsJsonObject().get("concatCheck").getAsJsonArray();
                    String[] filterVal = new String[obj.size()];
                    for (int j = 0; j < obj.size(); j++) {
                        filterVal[j] = obj.get(j).getAsString();
                    }
                    FilterConcat myFilter = new FilterConcat(filterVal, file.get("customFilter" + i).getAsJsonObject().get("shouldCancel").getAsBoolean(),
                            file.get("customFilter" + i).getAsJsonObject().get("needsDungeons").getAsBoolean());
                    SkyblockReinvented.filter.add(true, myFilter.getConcatCheck(), myFilter.getShouldCancel(), myFilter.getNeedsDungeons());
                    this.customConcatFilters.put("customFilter" + i, myFilter);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeConfig(boolean typeToggle, String[] check, boolean needsDungeonsToggle) {
        readConfig();
        if (!typeToggle) {
            customFilters.put("customFilter" + customFilters.size(), new Filter(check, true, needsDungeonsToggle));
            SkyblockReinvented.filter.add(false, check, true, needsDungeonsToggle);
        } else {
            this.customConcatFilters.put("customFilter" + customConcatFilters.size(), new FilterConcat(check, true, needsDungeonsToggle));
            SkyblockReinvented.filter.add(true, check, true, needsDungeonsToggle);
        }
        try (FileWriter writer = new FileWriter(new File(SkyblockReinvented.modDir, "customFilters.json"))) {
            gson.toJson(customFilters, writer);
        } catch (Exception ex) {

        }
        try (FileWriter writer = new FileWriter(new File(SkyblockReinvented.modDir, "customConcatFilters.json"))) {
            gson.toJson(customConcatFilters, writer);
        } catch (Exception ex) {

        }
    }
    public void writeRemoveConfig(String id) {
        if (this.customFilters.containsKey(id)) {
            customFilters.remove(customFilters.get(id));
            SkyblockReinvented.filter.init();
            for (Map.Entry<String, Filter> myFilter : customFilters.entrySet()) {
                SkyblockReinvented.filter.getFilters().add(myFilter.getValue());
            }
        } else if (this.customConcatFilters.containsKey(id)) {
            customConcatFilters.remove(customConcatFilters.get(id));
            SkyblockReinvented.filter.init();
            for (Map.Entry<String, FilterConcat> myFilter : customConcatFilters.entrySet()) {
                SkyblockReinvented.filter.getFilterConcats().add(myFilter.getValue());
            }
        }
        try (FileWriter writer = new FileWriter(new File(SkyblockReinvented.modDir, "customFilters.json"))) {
            gson.toJson(customFilters, writer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
