package thecudster.sre;


import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.lwjgl.input.Keyboard;
import scala.collection.parallel.ParIterableLike;
import thecudster.sre.commands.*;
import thecudster.sre.events.CheckSkyblockTick;
import thecudster.sre.features.impl.bestiary.BestiaryHelper;
import thecudster.sre.features.impl.discord.DiscordRPC;
import thecudster.sre.features.impl.dragons.DragTracker;
import thecudster.sre.features.impl.dungeons.BoxUnkilledMobs;
import thecudster.sre.features.impl.dungeons.MiscClickBlocks;
import thecudster.sre.features.impl.filter.FilterHandler;
import thecudster.sre.events.Keybindings;
import thecudster.sre.features.impl.bestiary.BestiaryProgress;
import thecudster.sre.features.impl.qol.CakeStackSize;
import thecudster.sre.features.impl.qol.GhostLoot;
import thecudster.sre.features.impl.qol.MiscGUIs;
import thecudster.sre.features.impl.rendering.*;
import thecudster.sre.commands.dungeons.Catacombs;
import thecudster.sre.commands.dungeons.MasterMode;
import thecudster.sre.features.impl.sounds.MiscSoundBlocks;
import thecudster.sre.settings.Config;
import thecudster.sre.util.gui.GuiManager;
import thecudster.sre.util.sbutil.LootTracker;
import thecudster.sre.features.impl.slayer.SlayerTracker;

@Mod(modid = SkyblockReinvented.MODID, name = SkyblockReinvented.MOD_NAME, version = SkyblockReinvented.VERSION, acceptedMinecraftVersions = "[1.8.9]", clientSideOnly = true)
public class SkyblockReinvented {
	public static Config config = new Config();
	public static final String MODID = "sre";
	public static final String MOD_NAME = "SkyblockReinvented";
	public static final String VERSION = "0.0.6-pre1";
	public static KeyBinding[] keyBindings = new KeyBinding[12];
	public static boolean creeperActivated;
	public static File modDir = new File(new File(Minecraft.getMinecraft().mcDataDir, "config"), "SRE");
	public static boolean foundSB;
	public static DiscordRPC discordRPC;
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
		ClientCommandHandler.instance.registerCommand(new SBToggle());
		ClientCommandHandler.instance.registerCommand(new AddItem());
		ClientCommandHandler.instance.registerCommand(new Rendering());
		ClientCommandHandler.instance.registerCommand(new JoinDung());
		ClientCommandHandler.instance.registerCommand(new FragRun());

		MinecraftForge.EVENT_BUS.register(new RemoveRaffleTitles());
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new FilterHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerHider());
		MinecraftForge.EVENT_BUS.register(new MiscWaypoints());
		MinecraftForge.EVENT_BUS.register(new BlockPowerOrb());
		MinecraftForge.EVENT_BUS.register(new WitherCloakHider());
		MinecraftForge.EVENT_BUS.register(new RemoveVillagers());
		MinecraftForge.EVENT_BUS.register(new DeleteOwnSpiritBats());
		MinecraftForge.EVENT_BUS.register(new HyperionOverlay());
		MinecraftForge.EVENT_BUS.register(new MiscSoundBlocks());
		MinecraftForge.EVENT_BUS.register(new Keybindings());
		MinecraftForge.EVENT_BUS.register(new MiscClickBlocks());
		MinecraftForge.EVENT_BUS.register(new SlayerOverlays());
		MinecraftForge.EVENT_BUS.register(new MiscGUIs());
		MinecraftForge.EVENT_BUS.register(new SkeletonMasterReminder());
		MinecraftForge.EVENT_BUS.register(new GhostLoot());
		MinecraftForge.EVENT_BUS.register(new LootTracker());
		MinecraftForge.EVENT_BUS.register(new MasterMode());
		MinecraftForge.EVENT_BUS.register(new Catacombs());
		MinecraftForge.EVENT_BUS.register(new RemoveItemFrameNames());
		MinecraftForge.EVENT_BUS.register(new CheckSkyblockTick());
		MinecraftForge.EVENT_BUS.register(GUIMANAGER);
		MinecraftForge.EVENT_BUS.register(new DragonArrowHitbox());
		MinecraftForge.EVENT_BUS.register(new BoxUnkilledMobs());
		MinecraftForge.EVENT_BUS.register(new SlayerTracker());
		MinecraftForge.EVENT_BUS.register(new BestiaryProgress());
		MinecraftForge.EVENT_BUS.register(new JerrychineHider());
		MinecraftForge.EVENT_BUS.register(new CakeStackSize());
		MinecraftForge.EVENT_BUS.register(new DragTracker());

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
		for (KeyBinding keyBinding : keyBindings) {
			ClientRegistry.registerKeyBinding(keyBinding);
		}
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (config.discordRP) {
			discordRPC.start();
		}
	}
	@EventHandler
	public void serverInit(FMLServerStartingEvent event) {
		BestiaryProgress.getThings();
	}
	@EventHandler
	public void serverClose(FMLServerStoppingEvent event) {
	}

	
}
