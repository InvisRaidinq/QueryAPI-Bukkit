package xyz.invisraidinq.queryapi;

import xyz.invisraidinq.queryapi.server.Server;
import xyz.invisraidinq.queryapi.server.ServerManager;

public class QueryAPI {

    private static QueryAPI apiInsatnce;

    private final ServerManager serverManager;

    public QueryAPI(ServerManager serverManager) {
        apiInsatnce = this;

        this.serverManager = serverManager;
    }

    public Server getServerByName(String name) {
        return this.serverManager.getServerByName(name.toLowerCase());
    }

    public static QueryAPI getInstance() {
        return apiInsatnce;
    }
}
