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
package thecudster.sre.features.impl.slayer;

import thecudster.sre.util.gui.GuiManager;
import thecudster.sre.util.sbutil.Utils;

public class SlayerReminder {
    public static void remindRevenant() throws InterruptedException {
        Thread.sleep(5000);
        Utils.playLoudSound("random.orb", 0.5);
        GuiManager.createTitle("§cStart a new Revenant slayer!", 20);
    }
    public static void remindTara() throws InterruptedException {
        Thread.sleep(5000);
        Utils.playLoudSound("random.orb", 0.5);
        GuiManager.createTitle("§cStart a new Tarantula slayer!", 20);
    }
    public static void remindSven() throws InterruptedException {
        Thread.sleep(5000);
        Utils.playLoudSound("random.orb", 0.5);
        GuiManager.createTitle("§cStart a new Sven slayer!", 20);
    }
}
