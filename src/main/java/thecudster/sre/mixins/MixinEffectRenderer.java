/*
 * SkyblockReinvented - Hypixel Skyblock Improvement Modification for Minecraft
 *  Copyright (C) 2021 theCudster
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package thecudster.sre.mixins;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.gui.GuiManager;
import thecudster.sre.util.sbutil.Utils;

@Mixin(EffectRenderer.class)
public abstract class MixinEffectRenderer {
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
        if (!Utils.inSkyblock || !Utils.inDungeons) { return; }
        if (effect instanceof EntityFishWakeFX) {
            if (SkyblockReinvented.config.fishWarning) {
                EntityFishWakeFX fish = (EntityFishWakeFX) effect;
                Utils.sendMsg("Red: " + fish.getRedColorF());
                Utils.sendMsg("Blue: " + fish.getBlueColorF());
                Utils.sendMsg("Green: " + fish.getGreenColorF());
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
        else if (effect instanceof EntityBlockDustFX || effect instanceof EntityFootStepFX) {
            if (SkyblockReinvented.config.renderPlayers) {
                ci.cancel();
                return;
            }
        }
    }
}
