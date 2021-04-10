package xyz.invisraidinq.queryapi;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.invisraidinq.queryapi.redis.RedisManager;
import xyz.invisraidinq.queryapi.server.ServerManager;
import xyz.invisraidinq.queryapi.utils.CC;
import xyz.invisraidinq.queryapi.utils.ConfigFile;

public class QueryPlugin extends JavaPlugin {

    private ConfigFile settingsFile;
    private RedisManager redisManager;
    private ServerManager serverManager;

    @Override
    public void onEnable() {
        CC.log("Starting plugin");

        long start = System.currentTimeMillis();

        this.settingsFile = new ConfigFile(this, "settings.yml");

        this.serverManager = new ServerManager(this);

        this.redisManager = new RedisManager(this.serverManager, this.settingsFile);

        CC.log("Plugin enabled in " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public void onDisable() {

    }
}
