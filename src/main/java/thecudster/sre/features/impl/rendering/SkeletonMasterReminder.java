package thecudster.sre.features.impl.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.GuiManager;
import thecudster.sre.util.ItemUtil;
import thecudster.sre.util.TextRenderUtils;
import thecudster.sre.util.Utils;

import java.util.Objects;

/**
 * Modified from NotEnoughUpdates under Creative Commons Attribution-NonCommercial 3.0
 * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
 * @author Moulberry
 */
public class SkeletonMasterReminder {
    int stopDestroyingMyFuckingEars = 600;
    @SubscribeEvent
    public void onRenderLivingPre(RenderLivingEvent.Pre event) {
        if (event.entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) <= SkyblockReinvented.config.skeletonRange && Utils.inDungeons && event.entity instanceof EntitySkeleton && SkyblockReinvented.config.warnSkeletonMasters && !event.entity.isInvisible() && Objects.equals(ItemUtil.getSkyBlockItemID(event.entity.getCurrentArmor(0)), "SKELETON_MASTER_BOOTS")) {
            if (stopDestroyingMyFuckingEars > 300) {
                GuiManager.createTitle("Skeleton Master Nearby!", 20);
                stopDestroyingMyFuckingEars = 0;
            }
            else {
                stopDestroyingMyFuckingEars++;
            }
        }
        if (event.entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) <= SkyblockReinvented.config.batRange && SkyblockReinvented.config.warnBatSecrets && Utils.inDungeons && event.entity instanceof EntityBat && event.entity.getMaxHealth() == 100) {
            if (stopDestroyingMyFuckingEars > 300) {
                GuiManager.createTitle("Bat Secret Nearby!", 20);
                stopDestroyingMyFuckingEars = 0;
            }
            else {
                stopDestroyingMyFuckingEars++;
            }
        }
    }
}
