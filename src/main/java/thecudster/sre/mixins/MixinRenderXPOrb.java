package thecudster.sre.mixins;

import net.minecraft.client.renderer.entity.RenderXPOrb;
import net.minecraft.entity.item.EntityXPOrb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.Utils;
import thecudster.sre.util.sbutil.ArrStorage;

import java.nio.channels.SelectionKey;

@Mixin(RenderXPOrb.class)
public abstract class MixinRenderXPOrb {
    @Inject(method = "doRender", at=@At("HEAD"), cancellable = true)
    private void doRender(EntityXPOrb entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (Utils.inLoc(ArrStorage.dwarvenLocs) && SkyblockReinvented.config.stopRenderXP) {
            ci.cancel();
        }
    }
}