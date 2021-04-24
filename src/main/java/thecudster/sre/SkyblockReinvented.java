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
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import thecudster.sre.commands.*;
import thecudster.sre.commands.dungeons.Catacombs;
import thecudster.sre.commands.dungeons.MasterMode;
import thecudster.sre.events.JoinSkyblockEvent;
import thecudster.sre.events.Keybindings;
import thecudster.sre.events.LeaveSkyblockEvent;
import thecudster.sre.features.impl.bestiary.BestiaryProgress;
import thecudster.sre.features.impl.discord.DiscordRPC;
import thecudster.sre.features.impl.dragons.DragTracker;
import thecudster.sre.features.impl.dragons.DragonArrowHitbox;
import thecudster.sre.features.impl.dungeons.*;
import thecudster.sre.features.impl.filter.BlockPowerOrb;
import thecudster.sre.features.impl.filter.FilterHandler;
import thecudster.sre.features.impl.filter.RemoveRaffleTitles;
import thecudster.sre.features.impl.qol.*;
import thecudster.sre.features.impl.rendering.*;
import thecudster.sre.features.impl.slayer.SlayerTracker;
import thecudster.sre.features.impl.sounds.MiscSoundBlocks;
import thecudster.sre.settings.Config;
import thecudster.sre.util.gui.GuiManager;
import thecudster.sre.util.gui.ScreenRenderer;
import thecudster.sre.util.sbutil.CurrentLoc;
import thecudster.sre.util.sbutil.LootTracker;
import thecudster.sre.util.sbutil.Utils;

import java.io.File;

@Mod(modid = SkyblockReinvented.MODID, name = SkyblockReinvented.MOD_NAME, version = SkyblockReinvented.VERSION, acceptedMinecraftVersions = "[1.8.9]", clientSideOnly = true)
public class SkyblockReinvented {
	public static Config config = new Config();
	public static final String MODID = "sre";
	public static final String MOD_NAME = "SkyblockReinvented";
	public static final String VERSION = "0.0.6-pre1";
	public static KeyBinding[] keyBindings = new KeyBinding[13];
	public static boolean creeperActivated;
	public static File modDir = new File(new File(Minecraft.getMinecraft().mcDataDir, "config"), "SRE");
	public static boolean foundSB;
	public static DiscordRPC discordRPC;
	public static final Minecraft mc = Minecraft.getMinecraft();
	public static GuiManager GUIMANAGER;
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
        if (!modDir.exists()) modDir.mkdirs();
        GUIMANAGER = new GuiManager();
		discordRPC = new DiscordRPC();
		BestiaryProgress.placeItems();
		BestiaryProgress.getThings();
	}
	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println("initialized");
		
		ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);
        config.preload();

		ClientCommandHandler.instance.registerCommand(new SRECommand());
		ClientCommandHandler.instance.registerCommand(new SBCommand());
		ClientCommandHandler.instance.registerCommand(new AddItem());
		ClientCommandHandler.instance.registerCommand(new Rendering());
		ClientCommandHandler.instance.registerCommand(new JoinDung());
		ClientCommandHandler.instance.registerCommand(new FragRun());
		ClientCommandHandler.instance.registerCommand(new DragCommand());
		MinecraftForge.EVENT_BUS.register(new RemoveRaffleTitles());
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new FilterHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerHider());
		MinecraftForge.EVENT_BUS.register(new GiftCompassWaypoints());
		MinecraftForge.EVENT_BUS.register(new BlockPowerOrb());
		MinecraftForge.EVENT_BUS.register(new WitherCloakHider());
		MinecraftForge.EVENT_BUS.register(new RemoveVillagers());
		MinecraftForge.EVENT_BUS.register(new DeleteOwnSpiritBats());
		MinecraftForge.EVENT_BUS.register(new HyperionOverlay());
		MinecraftForge.EVENT_BUS.register(new MiscSoundBlocks());
		MinecraftForge.EVENT_BUS.register(new Keybindings());
		MinecraftForge.EVENT_BUS.register(new MiscClickBlocks());
		MinecraftForge.EVENT_BUS.register(new MiscGUIs());
		MinecraftForge.EVENT_BUS.register(new SkeletonMasterReminder());
		MinecraftForge.EVENT_BUS.register(new GhostLoot());
		MinecraftForge.EVENT_BUS.register(new LootTracker());
		MinecraftForge.EVENT_BUS.register(new MasterMode());
		MinecraftForge.EVENT_BUS.register(new Catacombs());
		MinecraftForge.EVENT_BUS.register(new RemoveItemFrameNames());
		MinecraftForge.EVENT_BUS.register(GUIMANAGER);
		MinecraftForge.EVENT_BUS.register(new DragonArrowHitbox());
		MinecraftForge.EVENT_BUS.register(new BoxUnkilledMobs());
		MinecraftForge.EVENT_BUS.register(new SlayerTracker());
		MinecraftForge.EVENT_BUS.register(new BestiaryProgress());
		MinecraftForge.EVENT_BUS.register(new JerrychineHider());
		MinecraftForge.EVENT_BUS.register(new CakeStackSize());
		MinecraftForge.EVENT_BUS.register(new DragTracker());
		MinecraftForge.EVENT_BUS.register(new Stash());

		keyBindings[0] = new KeyBinding("Open Bazaar", Keyboard.KEY_B, "SkyblockReinvented");
		keyBindings[1] = new KeyBinding("Open AH", Keyboard.KEY_H, "SkyblockReinvented");
		keyBindings[2] = new KeyBinding("Open PRTL", Keyboard.KEY_P, "SkyblockReinvented");
		keyBindings[3] = new KeyBinding("Open Pets", Keyboard.KEY_NONE, "SkyblockReinvented");
		keyBindings[4] = new KeyBinding("Open EChest", Keyboard.KEY_NONE, "SkyblockReinvented");
		keyBindings[5] = new KeyBinding("Open Wardrobe", Keyboard.KEY_NONE, "SkyblockReinvented");
		keyBindings[6] = new KeyBinding("Open Crafting Menu", Keyboard.KEY_NONE, "SkyblockReinvented");
		keyBindings[7] = new KeyBinding("Open SB Menu", Keyboard.KEY_NONE, "SkyblockReinvented");
		keyBindings[8] = new KeyBinding("Open Skills", Keyboard.KEY_NONE, "SkyblockReinvented");
		keyBindings[9] = new KeyBinding("Open HOTM", Keyboard.KEY_NONE, "SkyblockReinvented");
		keyBindings[10] = new KeyBinding("Warp Hub", Keyboard.KEY_NONE, "SkyblockReinvented");
		keyBindings[11] = new KeyBinding("Warp Dungeon Hub", Keyboard.KEY_NONE, "SkyblockReinvented");
		keyBindings[12] = new KeyBinding("Pickup Stash", Keyboard.KEY_P, "SkyblockReinvented");
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
	public static int ticks = 0;
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.START) return;
		ScreenRenderer.refresh();
		if (ticks % 5 == 0) {
			if (mc.thePlayer != null) {
			}
		}
		if (ticks % 20 == 0) {
			if (mc.thePlayer == null) { return; }
			ScreenRenderer.refresh();
			Utils.checkForDungeons();
			CurrentLoc.checkLoc();
			DragTracker.updateGui();
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
