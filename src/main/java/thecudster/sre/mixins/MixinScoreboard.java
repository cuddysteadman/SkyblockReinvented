package thecudster.sre.mixins;

import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Scoreboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thecudster.sre.util.Utils;

@Mixin(Scoreboard.class)
public abstract class MixinScoreboard {
    @Inject(method = "addScoreObjective", at = @At("HEAD"))
    private void onAddObj(String name, IScoreObjectiveCriteria scoreObjectiveCriteria, CallbackInfo ci) {
        Utils.sendMsg("Name: " + name);
        Utils.sendMsg("ScoreObjCrit: " + scoreObjectiveCriteria.getName());
    }
}
