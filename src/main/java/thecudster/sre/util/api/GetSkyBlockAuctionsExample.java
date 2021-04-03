package thecudster.sre.util.api;

import com.google.gson.JsonElement;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GetSkyBlockAuctionsExample {
    public static boolean stopChecking = false;
    public static void printAuctions(int pageNumber) {
        if (stopChecking) { return; }
        APIUtil.API.getSkyBlockAuctions(pageNumber).whenComplete((page0, throwable) -> {

            if (throwable != null) {
                throwable.printStackTrace();
                FMLCommonHandler.instance().exitJava(1, true);
                return;
            }
            for (JsonElement j : page0.getAuctions()) {
                if (j.getAsJsonObject().get("item_name").toString().contains("God Potion")) {
                    if (!j.getAsJsonObject().get("bin").isJsonNull()) {
                        if (j.getAsJsonObject().get("bin").getAsBoolean()) {
                            System.out.println("a");
                        }
                    }
                }
            }
            if (page0.hasNextPage()) {
                APIUtil.API.getSkyBlockAuctions(page0.getPage() + 1).whenComplete(APIUtil.getTestConsumer());
            } else {
                FMLCommonHandler.instance().exitJava(1, true);
            }

        });
        APIUtil.await();

    }
}
