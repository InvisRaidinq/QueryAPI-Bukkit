package xyz.invisraidinq.queryapi.redis;

import redis.clients.jedis.Jedis;
import xyz.invisraidinq.queryapi.server.ServerManager;

public class JedisPublisher {

    private Jedis jedis;
    private final RedisManager redisManager;
    private final ServerManager serverManager;

    /**
     * Constructor to initialise the JedisPublisher class
     *
     * @param redisManager The {@link RedisManager} instance
     * @param serverManager The {@link ServerManager} instance
     */
    public JedisPublisher(RedisManager redisManager, ServerManager serverManager) {
        this.redisManager = redisManager;
        this.serverManager = serverManager;
    }

    public void publishData(String message) {
        try {
            this.jedis = this.redisManager.getJedisPool().getResource();
            if (this.redisManager.isJedisAuth()) {
                this.jedis.auth(this.redisManager.getJedisPassword());
            }

            this.jedis.publish(this.redisManager.getJedisChannel(), message);
        } finally {
            this.jedis.close();
        }
    }
}
