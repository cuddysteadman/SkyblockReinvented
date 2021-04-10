package thecudster.sre.util.api;
import net.minecraftforge.fml.common.FMLCommonHandler;
import thecudster.sre.util.api.HypixelAPI;
import thecudster.sre.util.api.AbstractReply;
import thecudster.sre.SkyblockReinvented;

import java.util.UUID;
import java.util.function.BiConsumer;
    /*
    * Taken from PublicAPI.
    * @author HypixelDev
     */


public class APIUtil {

    public static final HypixelAPI API = new HypixelAPI(UUID.fromString("asdf")); // arbitrary key, replace with your own to test

    public static final UUID HYPIXEL = UUID.fromString("f7c77d99-9f15-4a66-a87d-c4a51ef30d19");

    /**
     * Keep the program alive till we explicitly exit.
     */
    public static void await() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T extends AbstractReply> BiConsumer<T, Throwable> getTestConsumer() {
        return (result, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
                FMLCommonHandler.instance().exitJava(1, true);
                return;
            }

            System.out.println(result);
            FMLCommonHandler.instance().exitJava(1, true);

        };
    }
}

