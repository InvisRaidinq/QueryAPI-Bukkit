package xyz.invisraidinq.queryapi.task;

import org.bukkit.scheduler.BukkitRunnable;
import xyz.invisraidinq.queryapi.server.Server;
import xyz.invisraidinq.queryapi.server.ServerManager;
import xyz.invisraidinq.queryapi.utils.CC;

public class ServerHeartbeatTask extends BukkitRunnable {

    private final ServerManager serverManager;

    public ServerHeartbeatTask(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void run() {
        for (Server server : this.serverManager.getServerMap().values()) {
            if (server.getLastUpdate() + 30000 < System.currentTimeMillis()) {
                CC.log("Server " + server.getServerName() + " hasn't had a heartbeat for 30s, setting server state as offline");
                this.serverManager.getServerMap().remove(server.getServerName().toLowerCase());
            }
        }
    }
}
