package xyz.invisraidinq.queryapi.thread;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import xyz.invisraidinq.queryapi.redis.JedisPublisher;
import xyz.invisraidinq.queryapi.redis.RedisManager;
import xyz.invisraidinq.queryapi.server.Server;
import xyz.invisraidinq.queryapi.server.ServerManager;
import xyz.invisraidinq.queryapi.server.ServerStatus;
import xyz.invisraidinq.queryapi.utils.ConfigFile;

public class ServerUpdateThread extends Thread {

    private final ServerManager serverManager;
    private final RedisManager redisManager;

    private final String serverName;

    /**
     * Constructor to initialise the {@link ServerUpdateThread} instance
     *
     * @param serverManager The {@link ServerManager} instance
     * @param redisManager The {@link RedisManager} instance
     * @param settingsFile The {@link ConfigFile} storing the server name
     */
    public ServerUpdateThread(ServerManager serverManager, RedisManager redisManager, ConfigFile settingsFile) {
        this.setName("QueryPlugin-Bukkit-ServerUpdate");

        this.serverManager = serverManager;
        this.redisManager = redisManager;

        this.serverName = settingsFile.getString("SERVER.NAME");
    }

    @Override
    public void run() {
        while(true) {
            JsonObject object = new JsonObject();
            Server server = this.serverManager.getServerByName(this.serverName.toLowerCase());

            if (server == null) {
                Server serverToUpdate = new Server(this.serverName);

                object.addProperty("serverName", serverToUpdate.getServerName());
                object.addProperty("onlinePlayers", serverToUpdate.getOnlinePlayers());
                object.addProperty("maxPlayers", serverToUpdate.getMaxPlayers());
                object.addProperty("motd", serverToUpdate.getMotd());
                object.addProperty("serverStatus", ServerStatus.BOOTING.toString());
                object.addProperty("baseServerVersion", serverToUpdate.getBaseServerVersion());
            } else {
                object.addProperty("serverName", server.getServerName());
                object.addProperty("onlinePlayers", Bukkit.getOnlinePlayers().size());
                object.addProperty("maxPlayers", Bukkit.getMaxPlayers());
                object.addProperty("motd", Bukkit.getMotd());
                object.addProperty("serverStatus", Bukkit.hasWhitelist() ? ServerStatus.WHITELISTED.toString() : ServerStatus.ONLINE.toString());
                object.addProperty("baseServerVersion", server.getBaseServerVersion());
            }

            new JedisPublisher(this.redisManager, this.serverManager).publishData("ServerUpdate///" + object.toString());

            try {
                Thread.sleep(10000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
