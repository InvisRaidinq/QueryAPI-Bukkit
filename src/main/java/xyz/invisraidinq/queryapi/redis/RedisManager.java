package xyz.invisraidinq.queryapi.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import xyz.invisraidinq.queryapi.server.ServerManager;
import xyz.invisraidinq.queryapi.utils.CC;
import xyz.invisraidinq.queryapi.utils.ConfigFile;

public class RedisManager {

    private final ServerManager serverManager;

    private final JedisPool jedisPool;
    private final Jedis jedis;

    private final String jedisChannel;
    private final String jedisPassword;

    private final boolean jedisAuth;

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

        this.authenticate();
        this.tryToConnect();
    }

    public void authenticate() {
        if (this.jedisAuth) {
            this.jedis.auth(this.jedisPassword);
        }
    }

    public void tryToConnect() {
        new Thread(() -> {
            try {
                this.jedis.subscribe(new JedisSubscriber(this), this.jedisChannel);

                CC.log("Subscribed to jedis channel: " + this.jedisChannel);
            } catch (Exception exception) {
                exception.printStackTrace();

                CC.log("Could not subscribe to jedis channel: " + this.jedisChannel + " for " + exception.getMessage());
            }
        }).start();
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

    /**
     * Getter for the server manager
     *
     * @return The server manager
     */
    public ServerManager getServerManager() {
        return this.serverManager;
    }
}
