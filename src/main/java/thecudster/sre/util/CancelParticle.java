package thecudster.sre.util;

import net.minecraft.client.Minecraft;

public class CancelParticle {
	public static void cancel() {
		Minecraft.getMinecraft().theWorld.removeWorldAccess(Minecraft.getMinecraft().renderGlobal);
		Minecraft.getMinecraft().theWorld.addWorldAccess(new CancelParticleHelper());
	}
}
