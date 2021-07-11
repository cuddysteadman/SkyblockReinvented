package thecudster.sre.mixins;

import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.entity.item.EntityItemFrame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.Utils;
import thecudster.sre.util.sbutil.CurrentLoc;

@Mixin(RenderItemFrame.class)
public abstract class MixinRenderItemFrame {
    @Inject(method = "renderName", at = @At("HEAD"))
    private void onRenderName(EntityItemFrame entity, double x, double y, double z, CallbackInfo ci) {
        if (!Utils.inSkyblock) {
            return;
        }
        if (CurrentLoc.currentLoc != "Your Island") {
            return;
        }
        if (!SkyblockReinvented.config.itemFrameNames) {
            return;
        }
        ci.cancel();
    }
}
