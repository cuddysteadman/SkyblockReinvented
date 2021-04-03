package thecudster.sre.util.api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.time.ZonedDateTime;
import org.apache.http.util.EntityUtils;
public class HypixelAPI {
    public CompletableFuture<SkyBlockAuctionsReply> getSkyBlockAuctions(int page) {
        return get(SkyBlockAuctionsReply.class, "skyblock/auctions", "page", page);
    }
    private static final String BASE_URL = "https://api.hypixel.net/";

    private final UUID apiKey;

    private final ExecutorService executorService;
    private final HttpClient httpClient;
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(UUID.class, new UUIDTypeAdapter())
            .registerTypeAdapter(GameType.class, new GameTypeTypeAdapter())
            .registerTypeAdapter(ZonedDateTime.class, new DateTimeTypeAdapter())

            .registerTypeAdapterFactory(new BoostersTypeAdapterFactory<>(BoostersReply.Booster.class))

            .create();
    public HypixelAPI(UUID apiKey) {
        this.apiKey = apiKey;

        this.executorService = Executors.newCachedThreadPool();
        this.httpClient = HttpClientBuilder.create().build();
    }

    /**
     * Shuts down the internal executor service
     */
    public void shutdown() {
        executorService.shutdown();
    }

    /**
     * @return currently set API key
     */
    public UUID getApiKey() {
        return apiKey;
    }
    private <R extends AbstractReply> CompletableFuture<R> get(Class<R> clazz, String request, Object... params) {
        CompletableFuture<R> future = new CompletableFuture<>();
        try {
            if (params.length % 2 != 0)
                throw new IllegalArgumentException("Need both key and value for parameters");

            StringBuilder url = new StringBuilder(BASE_URL);

            url.append(request);
            url.append("?key=").append(apiKey);

            for (int i = 0; i < params.length - 1; i += 2) {
                url.append("&").append(params[i]).append("=").append(params[i + 1]);
            }

            executorService.submit(() -> {
                try {
                    R response = httpClient.execute(new HttpGet(url.toString()), obj -> {
                        String content = EntityUtils.toString(obj.getEntity(), "UTF-8");
                        if (clazz == ResourceReply.class) {
                            return (R) new ResourceReply(GSON.fromJson(content, JsonObject.class));
                        } else {
                            return GSON.fromJson(content, clazz);
                        }
                    });

                    checkReply(response);

                    future.complete(response);
                } catch (Throwable t) {
                    future.completeExceptionally(t);
                }
            });
        } catch (Throwable throwable) {
            future.completeExceptionally(throwable);
        }
        return future;
    }
    private <T extends AbstractReply> void checkReply(T reply) {
        if (reply != null) {
            if (reply.isThrottle()) {

            } else if (!reply.isSuccess()) {

            }
        }
    }
}