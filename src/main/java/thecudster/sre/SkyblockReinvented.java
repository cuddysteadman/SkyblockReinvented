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

package thecudster.sre;


import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import thecudster.sre.commands.*;
import thecudster.sre.commands.dungeons.Catacombs;
import thecudster.sre.commands.dungeons.FragRun;
import thecudster.sre.commands.dungeons.MasterMode;
import thecudster.sre.events.*;
import thecudster.sre.features.impl.bestiary.BestiaryGUI;
import thecudster.sre.features.impl.bestiary.BestiaryProgress;
import thecudster.sre.features.impl.dragons.DragTracker;
import thecudster.sre.features.impl.dragons.DragonArrowHitbox;
import thecudster.sre.features.impl.dungeons.*;
import thecudster.sre.features.impl.filter.BlockPowerOrb;
import thecudster.sre.features.impl.filter.FilterHandler;
import thecudster.sre.features.impl.filter.RemoveRaffleTitles;
import thecudster.sre.features.impl.qol.*;
import thecudster.sre.features.impl.rendering.HyperionOverlay;
import thecudster.sre.features.impl.rendering.PlayerHider;
import thecudster.sre.features.impl.rendering.RemoveVillagers;
import thecudster.sre.features.impl.slayer.SlayerFeatures;
import thecudster.sre.features.impl.slayer.SlayerTracker;
import thecudster.sre.features.impl.sounds.MiscSoundBlocks;
import thecudster.sre.settings.Config;
import thecudster.sre.util.gui.GuiManager;
import thecudster.sre.util.gui.ScreenRenderer;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.LootTracker;

import java.io.File;

@Mod(modid = SkyblockReinvented.MODID, name = SkyblockReinvented.MOD_NAME, version = SkyblockReinvented.VERSION, acceptedMinecraftVersions = "[1.8.9]", clientSideOnly = true)
public class SkyblockReinvented {
	public static Config config = new Config();
	public static final String MODID = "sre";
	public static final String MOD_NAME = "SkyblockReinvented";
	public static final String VERSION = "0.0.7-pre1";
	public static KeyBinding[] keyBindings = new KeyBinding[1];
	public static File modDir = new File(new File(Minecraft.getMinecraft().mcDataDir, "config"), "SRE");
	public static DiscordRPC discordRPC;
	public static final Minecraft mc = Minecraft.getMinecraft();
	public static GuiManager GUIMANAGER;
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
        if (!modDir.exists()) modDir.mkdirs();
        GUIMANAGER = new GuiManager();
		discordRPC = new DiscordRPC();
		BestiaryProgress.placeItems();
	}
	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println("initialized");
		
		ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);
        config.preload();

		ClientCommandHandler.instance.registerCommand(new SRECommand());
		ClientCommandHandler.instance.registerCommand(new AddItem());
		ClientCommandHandler.instance.registerCommand(new Rendering());
		ClientCommandHandler.instance.registerCommand(new FragRun());
		ClientCommandHandler.instance.registerCommand(new DragCommand());
		ClientCommandHandler.instance.registerCommand(new SetStatus());
		ClientCommandHandler.instance.registerCommand(new MasterMode());
		ClientCommandHandler.instance.registerCommand(new Catacombs());

		MinecraftForge.EVENT_BUS.register(new RemoveRaffleTitles());
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new FilterHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerHider());
		MinecraftForge.EVENT_BUS.register(new GiftCompassWaypoints());
		MinecraftForge.EVENT_BUS.register(new BlockPowerOrb());
		MinecraftForge.EVENT_BUS.register(new WitherCloakHider());
		MinecraftForge.EVENT_BUS.register(new RemoveVillagers());
		MinecraftForge.EVENT_BUS.register(new HyperionOverlay());
		MinecraftForge.EVENT_BUS.register(new MiscSoundBlocks());
		MinecraftForge.EVENT_BUS.register(new Keybindings());
		MinecraftForge.EVENT_BUS.register(new MiscClickBlocks());
		MinecraftForge.EVENT_BUS.register(new MiscGUIs());
		MinecraftForge.EVENT_BUS.register(new GhostLoot());
		MinecraftForge.EVENT_BUS.register(new LootTracker());
		MinecraftForge.EVENT_BUS.register(new RemoveItemFrameNames());
		MinecraftForge.EVENT_BUS.register(GUIMANAGER);
		MinecraftForge.EVENT_BUS.register(new DragonArrowHitbox());
		MinecraftForge.EVENT_BUS.register(new BoxUnkilledMobs());
		MinecraftForge.EVENT_BUS.register(new SlayerTracker());
		MinecraftForge.EVENT_BUS.register(new BestiaryProgress());
		MinecraftForge.EVENT_BUS.register(new BestiaryGUI());
		MinecraftForge.EVENT_BUS.register(new JerrychineHider());
		MinecraftForge.EVENT_BUS.register(new CakeStackSize());
		MinecraftForge.EVENT_BUS.register(new DragTracker());
		MinecraftForge.EVENT_BUS.register(new Stash());
		MinecraftForge.EVENT_BUS.register(new CreeperSolver());
		MinecraftForge.EVENT_BUS.register(new WorldChangeEvent());
		MinecraftForge.EVENT_BUS.register(new JerryTimer());
		MinecraftForge.EVENT_BUS.register(new HideIncorrectLivids());
		MinecraftForge.EVENT_BUS.register(new TreasureLocs());
		MinecraftForge.EVENT_BUS.register(new DungeonFeatures());
		MinecraftForge.EVENT_BUS.register(new SlayerFeatures());

		if (Minecraft.getMinecraft().gameSettings.language != null) {
			ScreenRenderer.fontRenderer.setUnicodeFlag(Minecraft.getMinecraft().isUnicode());
			ScreenRenderer.fontRenderer.setBidiFlag(Minecraft.getMinecraft().getLanguageManager().isCurrentLanguageBidirectional());
		}
		TreasureLocs.init();
		IReloadableResourceManager mgr = (IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
		mgr.registerReloadListener(ScreenRenderer.fontRenderer);
		keyBindings[0] = new KeyBinding("Refresh Location", Keyboard.KEY_H, "SkyblockReinvented");
		for (KeyBinding keyBinding : keyBindings) {
			ClientRegistry.registerKeyBinding(keyBinding);
		}
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
	@EventHandler
	public void serverClose(FMLServerStoppingEvent event) {
	}
	@SubscribeEvent
	public void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
		MinecraftForge.EVENT_BUS.post(new LeaveSkyblockEvent());
	}
	public static int ticks = 0;
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.START) return;
		if (ticks % 20 == 0) {
			if (mc.thePlayer == null) { return; }
			CurrentLoc.checkLoc();
			ScreenRenderer.refresh();
			DragTracker.updateGui();
			MinecraftForge.EVENT_BUS.post(new SecondPassedEvent());
			ticks = 0;
		}

		ticks++;
	}
	@SubscribeEvent
	public void onLeave(LeaveSkyblockEvent event) {
		if (discordRPC.isActive()) {
			this.discordRPC.stop();
		}
	}
	@SubscribeEvent
	public void onJoin(JoinSkyblockEvent event) {

		if (config.discordRP) {
			this.discordRPC.start();
		} else if (discordRPC.isActive()) {
			this.discordRPC.stop();
		}
	}

	
}
