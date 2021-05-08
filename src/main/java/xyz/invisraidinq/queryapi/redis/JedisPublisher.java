package xyz.invisraidinq.queryapi.redis;

import redis.clients.jedis.Jedis;
import xyz.invisraidinq.queryapi.server.ServerManager;

public class JedisPublisher {

    private Jedis jedis;
    private final JedisManager jedisManager;

    /**
     * Constructor to initialise the JedisPublisher class
     *
     * @param jedisManager The {@link JedisManager} instance
     */
    public JedisPublisher(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }

    public void publishData(String message) {
        try {
            this.jedis = this.jedisManager.getJedisPool().getResource();

            if (this.jedisManager.isJedisAuth()) {
                this.jedis.auth(this.jedisManager.getJedisPassword());
            }

            this.jedis.publish(this.jedisManager.getJedisChannel(), message);
        } finally {
            this.jedis.close();
        }
    }
}
