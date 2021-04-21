package thecudster.sre.events;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;
/**
 * Taken from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
public class GuiRenderItemEvent extends Event {
    public static class RenderOverlayEvent extends GuiRenderItemEvent {

        public FontRenderer fr;
        public ItemStack stack;
        public int x, y;
        public String text;

        public RenderOverlayEvent(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, String text) {
            this.fr = fr;
            this.stack = stack;
            this.x = xPosition;
            this.y = yPosition;
            this.text = text;
        }

        public static class Post extends RenderOverlayEvent {
            public Post(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, String text) {
                super(fr, stack, xPosition, yPosition, text);
            }
        }
    }
}
