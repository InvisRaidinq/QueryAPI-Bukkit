package xyz.invisraidinq.queryapi.redis;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import redis.clients.jedis.JedisPubSub;
import xyz.invisraidinq.queryapi.server.Server;
import xyz.invisraidinq.queryapi.utils.CC;

public class JedisSubscriber extends JedisPubSub {

    private final JedisManager jedisManager;

    public JedisSubscriber(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }

    @Override
    public void onMessage(String channel, String message) {
        if (channel.equalsIgnoreCase(this.jedisManager.getJedisChannel())) {
            final String[] data = message.split(CC.MESSAGE_SPLITTER);
            final String command = data[0];

            CC.log("Received command " + command);

            if (command.equals("ServerUpdate")) {
                final JsonObject object = JsonParser.parseString(data[1]).getAsJsonObject();

                this.jedisManager.getServerManager().initOrUpdateServer(new Server(object));

                CC.log("Updated server " + object.get("serverName").getAsString());
            } else {
                CC.log("Couldn't recognize packet: " + command);
            }
        }
    }
}
