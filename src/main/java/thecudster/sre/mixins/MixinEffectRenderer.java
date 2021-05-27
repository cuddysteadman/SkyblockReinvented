package thecudster.sre.mixins;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thecudster.sre.SkyblockReinvented;

@Mixin(EffectRenderer.class)
public abstract class MixinEffectRenderer {
    @Inject(method = "addBlockHitEffects", at = @At("HEAD"), cancellable = true)
    private void addBlockHitEffects(BlockPos pos, EnumFacing directionFacing, CallbackInfo ci) {
        if (!SkyblockReinvented.config.disableFarmParticles) {
            return;
        }
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        if (block instanceof BlockCarrot || block instanceof BlockNetherWart || block instanceof BlockPumpkin || block instanceof BlockMelon ||
                block instanceof BlockCocoa || block instanceof BlockCactus || block instanceof BlockMushroom || block instanceof BlockReed || block instanceof BlockCrops) {
            ci.cancel();
            return;
        }
    }

    @Inject(method = "addBlockDestroyEffects", at = @At("HEAD"), cancellable = true)
    private void addBlockDestroyEffects(BlockPos pos, IBlockState state, CallbackInfo ci) {
        if (!SkyblockReinvented.config.disableFarmParticles) {
            return;
        }
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        if (block instanceof BlockCarrot || block instanceof BlockNetherWart || block instanceof BlockPumpkin || block instanceof BlockMelon ||
                block instanceof BlockCocoa || block instanceof BlockCactus || block instanceof BlockMushroom || block instanceof BlockReed || block instanceof BlockCrops) {
            ci.cancel();
            return;
        }
    }
}
