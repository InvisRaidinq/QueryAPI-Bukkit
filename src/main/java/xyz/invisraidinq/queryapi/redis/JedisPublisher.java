package xyz.invisraidinq.queryapi.redis;

import redis.clients.jedis.Jedis;
import xyz.invisraidinq.queryapi.server.ServerManager;

public class JedisPublisher {

    private Jedis jedis;
    private final RedisManager redisManager;

    /**
     * Constructor to initialise the JedisPublisher class
     *
     * @param redisManager The {@link RedisManager} instance
     */
    public JedisPublisher(RedisManager redisManager) {
        this.redisManager = redisManager;
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
