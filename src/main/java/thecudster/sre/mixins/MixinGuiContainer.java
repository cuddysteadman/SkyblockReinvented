package thecudster.sre.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.common.MinecraftForge;
import thecudster.sre.events.GuiContainerEvent;

/**
 * Taken from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 */
@Mixin(GuiContainer.class)
public class MixinGuiContainer {

    @Shadow public Container inventorySlots;

    private final GuiContainer that = (GuiContainer) (Object) this;

    @Inject(method = "keyTyped", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;closeScreen()V", shift = At.Shift.BEFORE))
    private void closeWindowPressed(CallbackInfo ci) {
        try {
            MinecraftForge.EVENT_BUS.post(new GuiContainerEvent.CloseWindowEvent(that, inventorySlots));
        } catch (Throwable e) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("§cSkyblockReinvented caught and logged an exception at GuiContainerEvent.CloseWindowEvent. Please report this on the Discord server."));
            e.printStackTrace();
        }
    }

    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V", ordinal = 1))
    private void backgroundDrawn(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        try {
            MinecraftForge.EVENT_BUS.post(new GuiContainerEvent.BackgroundDrawnEvent(that, inventorySlots, mouseX, mouseY, partialTicks));
        } catch (Throwable e) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("§cSkyblockReinvented caught and logged an exception at GuiContainerEvent.BackgroundDrawnEvent. Please report this on the Discord server."));
            e.printStackTrace();
        }
    }

    @Inject(method = "drawSlot", at = @At("HEAD"), cancellable = true)
    private void onDrawSlot(Slot slot, CallbackInfo ci) {
        try {
            if (MinecraftForge.EVENT_BUS.post(new GuiContainerEvent.DrawSlotEvent.Pre(that, inventorySlots, slot))) ci.cancel();
        } catch (Throwable e) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("§cSkyblockReinvented caught and logged an exception at GuiContainerEvent.DrawSlotEvent.Pre. Please report this on the Discord server."));
            e.printStackTrace();
        }
    }

    @Inject(method = "drawSlot", at = @At("RETURN"), cancellable = true)
    private void onDrawSlotPost(Slot slot, CallbackInfo ci) {
        try {
            MinecraftForge.EVENT_BUS.post(new GuiContainerEvent.DrawSlotEvent.Post(that, inventorySlots, slot));
        } catch (Throwable e) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("§cSkyblockReinvented caught and logged an exception at GuiContainerEvent.DrawSlotEvent.Post. Please report this on the Discord server."));
            e.printStackTrace();
        }
    }


    @Inject(method = "handleMouseClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;windowClick(IIIILnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;"), cancellable = true)
    private void onMouseClick(Slot slot, int slotId, int clickedButton, int clickType, CallbackInfo ci) {
        try {
            if (MinecraftForge.EVENT_BUS.post(new GuiContainerEvent.SlotClickEvent(that, inventorySlots, slot, slotId, clickedButton, clickType))) ci.cancel();
        } catch (Throwable e) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("§cSkyblockReinvented caught and logged an exception at GuiContainerEvent.SlotClickEvent. Please report this on the Discord server."));
            e.printStackTrace();
        }
    }
}