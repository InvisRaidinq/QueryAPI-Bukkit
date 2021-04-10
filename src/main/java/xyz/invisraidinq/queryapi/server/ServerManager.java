package xyz.invisraidinq.queryapi.server;

import xyz.invisraidinq.queryapi.QueryPlugin;

import java.util.HashMap;
import java.util.Map;

public class ServerManager {

    private final Map<String, Server> serverMap = new HashMap<>();

    /**
     * Constructor to initialise an {@link ServerManager} instance
     *
     * @param plugin The JavaPlugin object
     */
    public ServerManager(QueryPlugin plugin) {

    }

    /**
     * Method to initialise or update an existing server
     *
     * @param server The server object to update
     */
    public void initOrUpdateServer(Server server) {
        if (this.serverMap.get(server.getServerName().toLowerCase()) != null) {
            this.serverMap.replace(server.getServerName().toLowerCase(), server);
            return;
        }

        this.serverMap.put(server.getServerName().toLowerCase(), server);
    }

    /**
     * Method to get a server by its name
     *
     * @param serverName The name of the server
     * @return The server object found. Default: null
     */
    public Server getServerByName(String serverName) {
        return this.serverMap.getOrDefault(serverName.toLowerCase(), null);
    }
}
