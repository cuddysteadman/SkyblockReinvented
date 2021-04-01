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
import scala.actors.threadpool.TimeUnit;
import thecudster.sre.commands.ReCommand;
import thecudster.sre.commands.SBCommand;
import thecudster.sre.commands.SBToggle;
import thecudster.sre.commands.SRECommand;
import thecudster.sre.commands.dungfloors.Floor1;
import thecudster.sre.commands.dungfloors.Floor2;
import thecudster.sre.commands.dungfloors.Floor3;
import thecudster.sre.commands.dungfloors.Floor4;
import thecudster.sre.commands.dungfloors.Floor5;
import thecudster.sre.commands.dungfloors.Floor6;
import thecudster.sre.commands.dungfloors.Floor7;
import thecudster.sre.events.GuiContainerEvent;
import thecudster.sre.features.impl.dungeons.LockMort;
import thecudster.sre.features.impl.filter.FilterHandler;
import thecudster.sre.features.impl.qol.Keybindings;
import thecudster.sre.features.impl.rendering.*;
import thecudster.sre.features.impls.sounds.BlockCreeperSound;
import thecudster.sre.settings.Config;
import thecudster.sre.util.Reference;

import javax.swing.text.JTextComponent;

@Mod(modid = "sre", name = Reference.MOD_NAME, version = Reference.VERSION)
public class SkyblockReinvented {
	public static Config config = new Config();
	public static KeyBinding[] keyBindings = new KeyBinding[2];
	public static boolean creeperActivated;
	public static File modDir;
	public static boolean foundSB;
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		modDir = new File(event.getModConfigurationDirectory(), "sre");
        if (!modDir.exists()) modDir.mkdirs();
		
	}
	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println("initialized");
		
		ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);

        config.preload();
		ClientCommandHandler.instance.registerCommand(new SRECommand());
		ClientCommandHandler.instance.registerCommand(new ReCommand());
		ClientCommandHandler.instance.registerCommand(new SBCommand());
		ClientCommandHandler.instance.registerCommand(new Floor1());
		ClientCommandHandler.instance.registerCommand(new Floor2());
		ClientCommandHandler.instance.registerCommand(new Floor3());
		ClientCommandHandler.instance.registerCommand(new Floor4());
		ClientCommandHandler.instance.registerCommand(new Floor5());
		ClientCommandHandler.instance.registerCommand(new Floor6());
		ClientCommandHandler.instance.registerCommand(new Floor7());
		ClientCommandHandler.instance.registerCommand(new SBToggle());
		
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new FilterHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerHider());
		MinecraftForge.EVENT_BUS.register(new GiftCompassWaypoints());
		MinecraftForge.EVENT_BUS.register(new BlockPowerOrb());
		MinecraftForge.EVENT_BUS.register(new WitherCloakHider());
		MinecraftForge.EVENT_BUS.register(new RemoveVillagers());

		MinecraftForge.EVENT_BUS.register(new BlockCreeperSound());
		MinecraftForge.EVENT_BUS.register(new RemoveDumbNametags());
		MinecraftForge.EVENT_BUS.register(new Keybindings());
		// MinecraftForge.EVENT_BUS.register(new LockMort());
		keyBindings[0] = new KeyBinding("Open Bazaar", Keyboard.KEY_H, "SkyblockReinvented");
		keyBindings[1] = new KeyBinding("Open AH", Keyboard.KEY_B, "SkyblockReinvented");
		for (KeyBinding keyBinding : keyBindings) {
			ClientRegistry.registerKeyBinding(keyBinding);
		}
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
	@EventHandler
	public void serverInit(FMLServerStartingEvent event) {
		
	}
	@EventHandler
	public void serverClose(FMLServerStoppingEvent event) {
		
	}

	
}
