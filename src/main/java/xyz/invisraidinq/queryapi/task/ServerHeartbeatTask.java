package xyz.invisraidinq.queryapi.task;

import org.bukkit.scheduler.BukkitRunnable;
import xyz.invisraidinq.queryapi.server.Server;
import xyz.invisraidinq.queryapi.server.ServerManager;
import xyz.invisraidinq.queryapi.utils.CC;
import xyz.invisraidinq.queryapi.utils.ConfigFile;

public class ServerHeartbeatTask extends BukkitRunnable {

    private final ServerManager serverManager;
    private final long updateInterval;

    /**
     * Constructor to initialise the {@link ServerHeartbeatTask}
     *
     * @param serverManager The {@link ServerManager} instance
     */
    public ServerHeartbeatTask(ServerManager serverManager, ConfigFile settingsFile) {
        this.serverManager = serverManager;
        this.updateInterval = settingsFile.getLong("SERVER.UPDATE-INTERVAL");
    }

    @Override
    public void run() {
        for (Server server : this.serverManager.getServerMap().values()) {
            if (server.getLastUpdate() + (this.updateInterval * 2) < System.currentTimeMillis()) {
                CC.log("Server " + server.getServerName() + " hasn't had a heartbeat for 30s, setting server state as offline");
                this.serverManager.getServerMap().remove(server.getServerName().toLowerCase());
            }
        }
    }
}
