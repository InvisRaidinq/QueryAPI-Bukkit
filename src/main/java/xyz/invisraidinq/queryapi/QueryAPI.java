package xyz.invisraidinq.queryapi;

import org.bukkit.scheduler.BukkitRunnable;
import xyz.invisraidinq.queryapi.server.Server;
import xyz.invisraidinq.queryapi.server.ServerManager;

public class QueryAPI {

    private static QueryAPI apiInstance;

    private final ServerManager serverManager;

    private int onlinePlayers;

    /**
     * Constructor to initialise the developer API
     *
     * @param plugin The {@link org.bukkit.plugin.java.JavaPlugin} object
     * @param serverManager The {@link ServerManager} instance
     */
    public QueryAPI(QueryPlugin plugin, ServerManager serverManager) {
        apiInstance = this;

        this.serverManager = serverManager;

        new BukkitRunnable() {
            public void run() {
                int online = 0;

                for (Server server : serverManager.getServerMap().values()) {
                    online = online + server.getOnlinePlayers();
                }

                onlinePlayers = online;
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 40L);
    }

    /**
     * Get an {@link Server} by its name
     *
     * @param name The name of the server
     * @return The {@link Server} object. default: null
     */
    public Server getServerByName(String name) {
        return this.serverManager.getServerByName(name.toLowerCase());
    }

    /**
     * Get the total online player count
     *
     * @return The online player count
     */
    public int getOnlinePlayers() {
        return this.onlinePlayers;
    }

    /**
     * Get the {@link QueryAPI} instance
     *
     * @return The {@link QueryAPI} instance
     */
    public static QueryAPI getInstance() {
        return apiInstance;
    }
}
