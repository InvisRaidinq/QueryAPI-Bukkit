package xyz.invisraidinq.queryapi;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.invisraidinq.queryapi.redis.JedisManager;
import xyz.invisraidinq.queryapi.server.ServerManager;
import xyz.invisraidinq.queryapi.task.ServerHeartbeatTask;
import xyz.invisraidinq.queryapi.thread.ServerUpdateThread;
import xyz.invisraidinq.queryapi.utils.CC;
import xyz.invisraidinq.queryapi.utils.ConfigFile;

public class QueryPlugin extends JavaPlugin {

    private ConfigFile settingsFile;
    private JedisManager jedisManager;
    private ServerManager serverManager;

    @Override
    public void onEnable() {
        CC.log("Starting plugin");

        long start = System.currentTimeMillis();

        this.settingsFile = new ConfigFile(this, "settings.yml");

        this.serverManager = new ServerManager(this);

        this.jedisManager = new JedisManager(this.serverManager, this.settingsFile);

        new ServerUpdateThread(this.serverManager, this.jedisManager, this.settingsFile).start();
        new ServerHeartbeatTask(this.serverManager, this.settingsFile).runTaskTimerAsynchronously(this, 0L, 200L);

        new QueryAPI(this, this.serverManager);

        CC.log("Plugin enabled in " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public void onDisable() {

    }
}
