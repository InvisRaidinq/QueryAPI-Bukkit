package xyz.invisraidinq.queryapi;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.invisraidinq.queryapi.commands.ServerDumpCommand;
import xyz.invisraidinq.queryapi.redis.JedisManager;
import xyz.invisraidinq.queryapi.server.ServerManager;
import xyz.invisraidinq.queryapi.task.ServerHeartbeatTask;
import xyz.invisraidinq.queryapi.thread.ServerUpdateThread;
import xyz.invisraidinq.queryapi.utils.CC;
import xyz.invisraidinq.queryapi.utils.ConfigFile;
import xyz.invisraidinq.queryapi.utils.command.CommandHandler;

public class QueryPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        CC.log("Starting plugin");

        long start = System.currentTimeMillis();

        ConfigFile settingsFile = new ConfigFile(this, "settings.yml");

        ServerManager serverManager = new ServerManager(this);

        JedisManager jedisManager = new JedisManager(serverManager, settingsFile);

        new ServerUpdateThread(serverManager, jedisManager, settingsFile).start();
        new ServerHeartbeatTask(serverManager, settingsFile).runTaskTimerAsynchronously(this, 0L, 200L);

        final CommandHandler commandHandler = new CommandHandler(this);
        commandHandler.setNoPermissionMessage("&cNo Permission");

        commandHandler.registerSimpleCommand(new ServerDumpCommand(serverManager));

        new QueryAPI(this, serverManager);

        CC.log("Plugin enabled in " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public void onDisable() {

    }
}
