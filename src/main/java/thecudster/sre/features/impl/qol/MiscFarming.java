/*
 * SkyblockReinvented - Hypixel Skyblock Improvement Modification for Minecraft
 * Copyright (C) 2021 theCudster
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package thecudster.sre.features.impl.qol;
/*
 * Modified from Skytils under GNU Affero General Public License.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 * @author Angry-Pineapple3121
 * @author AzuredBlue
 */
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.events.DamageBlockEvent;
import thecudster.sre.util.sbutil.Utils;

public class MiscFarming {
    private static final Minecraft mc = Minecraft.getMinecraft();
    @SubscribeEvent
    public void onBreak(DamageBlockEvent event) {
        if (!Utils.inSkyblock || mc.thePlayer == null || mc.theWorld == null) return;
        EntityPlayerSP p = mc.thePlayer;
        Block block = mc.theWorld.getBlockState(event.pos).getBlock();
        if (block instanceof BlockCarrot || block instanceof BlockNetherWart || block instanceof BlockPumpkin || block instanceof BlockMelon ||
        block instanceof BlockCocoa || block instanceof BlockCactus || block instanceof BlockMushroom || block instanceof BlockReed || block instanceof BlockCrops) {
        }
    }
}
