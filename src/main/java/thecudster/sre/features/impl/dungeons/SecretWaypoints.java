package thecudster.sre.features.impl.dungeons;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockSkull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameData;
import thecudster.sre.util.DrawWaypoint;

import java.util.ArrayList;

public class SecretWaypoints {
    ArrayList<EntityItem> items = new ArrayList<EntityItem>();
    /*
    @SubscribeEvent
    public void getItems(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityItem) {
            // Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("aaaa"));
            EntityItem item = (EntityItem) event.entity;
            String name = item.getEntityItem().getDisplayName();
            if (name.contains("Decoy") || name.contains("Training Weights") || name.contains("Inflatable Jerry") || name.contains("Trap") || name.contains("Healing VIII") || name.contains("Dungeon Chest Key") || name.contains("Treasure Talisman") || name.contains("Obsidian")) { // TODO: obsidian for testing
                items.add(item);
            }
        }
    }*/
    @SubscribeEvent
    public void onRender(RenderWorldEvent event) {
        for (TileEntity o : Minecraft.getMinecraft().theWorld.loadedTileEntityList) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Found TileEntity"));
            if (o instanceof TileEntityChest) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("found chest"));
                DrawWaypoint.drawWaypoint(0.05f, ((TileEntityChest) o).getPos().down(), "Secret: Chest");
            }

        }
        for (EntityItem item : items) {
            DrawWaypoint.drawWaypoint(0.05f, item.getPosition().down(), "Secret: Item");
        }
    }
}
