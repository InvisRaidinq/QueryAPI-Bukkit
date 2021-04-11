package xyz.invisraidinq.queryapi.redis;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import xyz.invisraidinq.queryapi.server.Server;
import xyz.invisraidinq.queryapi.server.ServerManager;
import xyz.invisraidinq.queryapi.server.ServerStatus;
import xyz.invisraidinq.queryapi.utils.CC;
import xyz.invisraidinq.queryapi.utils.ConfigFile;

public class RedisManager {

    private final ServerManager serverManager;

    private final JedisPool jedisPool;
    private final Jedis jedis;

    private final String jedisChannel;
    private final boolean jedisAuth;
    private final String jedisPassword;

    /**
     * Constructor to initialise the RedisManager
     *
     * @param serverManager The {@link ServerManager} object
     * @param settingsFile The {@link ConfigFile} that stores the redis credentials
     */
    public RedisManager(ServerManager serverManager, ConfigFile settingsFile) {
        this.serverManager = serverManager;

        final String databasePath = "DATABASE.REDIS.";
        this.jedisChannel = settingsFile.getString(databasePath + "CHANNEL");
        this.jedisAuth = settingsFile.getBoolean(databasePath + "AUTH.ENABLED");
        this.jedisPassword = settingsFile.getString(databasePath + "AUTH.PASSWORD");

        this.jedisPool = new JedisPool(settingsFile.getString(databasePath + "ADDRESS"), settingsFile.getInt(databasePath + "PORT"));

        this.jedis = this.jedisPool.getResource();

        if (this.jedisAuth) {
            this.jedis.auth(this.jedisPassword);
        }

        new Thread(() -> this.jedis.subscribe(this.startPubSub(), this.jedisChannel)).start();
    }

    /**
     * Start the {@link JedisPubSub}
     *
     * @return {@link JedisPubSub} implementation
     */
    private JedisPubSub startPubSub() {
        return new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                if (channel.equalsIgnoreCase(jedisChannel)) {
                    String[] data = message.split("///");

                    CC.log("Received command " + data[0]);
                    switch (data[0]) {
                        case "ServerUpdate":
                            JsonObject object = JsonParser.parseString(data[1]).getAsJsonObject();
                            serverManager.initOrUpdateServer(new Server(object));
                            CC.log("Updated server " + object.get("serverName").getAsString());
                            break;
                        default:
                            break;
                    }
                }
            }
        };
    }

    /**
     * Getter for the {@link JedisPool} object
     *
     * @return The {@link JedisPool} object
     */
    public JedisPool getJedisPool() {
        return this.jedisPool;
    }

    /**
     * Getter for the jedis channel
     *
     * @return The jedis channel
     */
    public String getJedisChannel() {
        return this.jedisChannel;
    }

    /**
     * Getter for the jedis auth boolean
     *
     * @return true/false depending on if auth is enabled
     */
    public boolean isJedisAuth() {
        return this.jedisAuth;
    }

    /**
     * Getter for the jedis password
     *
     * @return The jedis password
     */
    public String getJedisPassword() {
        return this.jedisPassword;
    }
}
