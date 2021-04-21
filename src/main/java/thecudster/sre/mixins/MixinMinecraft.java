package thecudster.sre.mixins;
/**
 * Taken from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thecudster.sre.util.gui.ScreenRenderer;
import thecudster.sre.util.sbutil.ItemUtil;
import thecudster.sre.util.sbutil.Utils;

import java.util.Objects;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Shadow
    private EntityPlayerSP thePlayer;

    private final Minecraft that = (Minecraft) (Object) this;

    @Inject(method = "clickMouse()V", at = @At(value = "INVOKE", target = "net/minecraft/client/entity/EntityPlayerSP.swingItem()V", shift = At.Shift.AFTER))
    private void clickMouse(CallbackInfo info) {
        if (!Utils.isOnHypixel() || !Utils.inSkyblock) return;

        ItemStack item = thePlayer.getHeldItem();
        if (item != null) {
            NBTTagCompound extraAttr = ItemUtil.getExtraAttributes(item);
            String itemId = ItemUtil.getSkyBlockItemID(extraAttr);

            if (Objects.equals(itemId, "BLOCK_ZAPPER")) {

            }
        }
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/IReloadableResourceManager;registerReloadListener(Lnet/minecraft/client/resources/IResourceManagerReloadListener;)V", shift = At.Shift.AFTER))
    private void initializeSmartFontRenderer(CallbackInfo ci) {
        ScreenRenderer.refresh();
    }

}