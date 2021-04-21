package thecudster.sre.mixins;

import jdk.nashorn.internal.codegen.CompilerConstants;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityBlockDustFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import scala.collection.parallel.ParIterableLike;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.GuiManager;
import thecudster.sre.util.sbutil.Utils;
import net.minecraft.client.particle.EntityFishWakeFX;
@Mixin(EffectRenderer.class)
public class MixinEffectRenderer {
    @Inject(method = "addBlockHitEffects", at = @At("HEAD"), cancellable = true)
    private void addBlockHitEffects(BlockPos pos, EnumFacing directionFacing, CallbackInfo ci) {
        if (!SkyblockReinvented.config.disableFarmParticles) { return; }
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        if (block instanceof BlockCarrot || block instanceof BlockNetherWart || block instanceof BlockPumpkin || block instanceof BlockMelon ||
            block instanceof BlockCocoa || block instanceof BlockCactus || block instanceof BlockMushroom || block instanceof BlockReed || block instanceof BlockCrops) {
            ci.cancel();
            return;
        }
    }
    @Inject(method = "addBlockDestroyEffects", at = @At("HEAD"), cancellable = true)
    private void addBlockDestroyEffects(BlockPos pos, IBlockState state, CallbackInfo ci) {
        if (!SkyblockReinvented.config.disableFarmParticles) { return; }
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        if (block instanceof BlockCarrot || block instanceof BlockNetherWart || block instanceof BlockPumpkin || block instanceof BlockMelon ||
                block instanceof BlockCocoa || block instanceof BlockCactus || block instanceof BlockMushroom || block instanceof BlockReed || block instanceof BlockCrops) {
            ci.cancel();
            return;
        }
    }
    int stopDestroyingMyFuckingEars = 60;
    @Inject(method = "addEffect", at = @At("HEAD"), cancellable = true)
    private void addEffect(EntityFX effect, CallbackInfo ci) {
        if (effect instanceof EntityFishWakeFX) {
            if (SkyblockReinvented.config.fishWarning) {
                EntityFishWakeFX fish = (EntityFishWakeFX) effect;
                boolean found = false;
                for (Entity e : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                    if (e instanceof EntityFishHook) {
                        EntityFishHook fishHook = (EntityFishHook) e;
                        if (fishHook.angler instanceof EntityOtherPlayerMP && Minecraft.getMinecraft().thePlayer.getDistanceToEntity(fishHook) > 20) {
                            // ci.cancel();
                        }
                        else if (fishHook.angler instanceof EntityPlayerSP) {
                            found = true;
                        }
                    }
                }
                if (!found) { return; }
                float scale = SkyblockReinvented.config.fishScale / 100f;
                fish.multipleParticleScaleBy(scale);
                if (fish.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) < 7) {
                    if (stopDestroyingMyFuckingEars > 120) {
                        GuiManager.createTitle("Fish near Hook!", 20);
                        stopDestroyingMyFuckingEars = 0;
                    } else {
                        stopDestroyingMyFuckingEars++;
                    }
                }
            }
        }
        else if (effect instanceof EntityBlockDustFX) {
            if (SkyblockReinvented.config.renderPlayers) {
                ci.cancel();
                return;
            }
        }
    }
}
