package thecudster.sre.features.impl.dungeons;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockSkull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameData;
import thecudster.sre.util.DrawWaypoint;

public class SecretWaypoints {
    public void getChests() {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        for (int x = (int) player.posX - 32; x < player.posX + 32; x++){
            for (int y = (int) player.posY - 16; y < player.posY + 16; y++) {
                for (int z = (int) player.posZ - 32; x < player.posZ + 32; z++) {
                    Block blockClicked = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (blockClicked instanceof BlockChest || blockClicked instanceof BlockSkull) {
                        DrawWaypoint.drawWaypoint(0.05f, new BlockPos(x, y, z), "Secret");
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void getItems(LivingEvent.LivingUpdateEvent event) {
        if (event.entity instanceof EntityItem) {
            EntityItem item = (EntityItem) event.entity;
            String name = item.getEntityItem().getDisplayName();
            if (name.contains("Decoy") || name.contains("Training Weights") || name.contains("Inflatable Jerry") || name.contains("Trap") || name.contains("Healing VIII") || name.contains("Dungeon Chest Key") || name.contains("Treasure Talisman") || name.contains("Obsidian")) { // TODO: obsidian for testing
                DrawWaypoint.drawWaypoint(0.05f, event.entity.getPosition().down(), "Secret");
            }
        }
    }
}
