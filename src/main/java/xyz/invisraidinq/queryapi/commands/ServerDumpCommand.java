package xyz.invisraidinq.queryapi.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.invisraidinq.queryapi.server.ServerManager;
import xyz.invisraidinq.queryapi.utils.CC;
import xyz.invisraidinq.queryapi.utils.command.SimpleCommand;

import java.util.Arrays;

public class ServerDumpCommand extends SimpleCommand {

    private final ServerManager serverManager;

    public ServerDumpCommand(ServerManager serverManager) {
        super("serverdump");

        this.setDescription("Dump information about all servers on the network");
        this.setPermission("queryapi.command.serverdump");
        this.setAliases(Arrays.asList("serverinfodump", "infodump"));

        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.serverManager.getServerMap().values().forEach(server -> {
            sender.sendMessage(new String[] {
                    CC.colour("&e&m-------------------------------------"),
                    CC.colour("&eServer Name: &f" + server.getServerName()),
                    CC.colour("&eOnline Players: &f" + server.getOnlinePlayers()),
                    CC.colour("&eMax Players: &f" + server.getMaxPlayers()),
                    CC.colour("&eMOTD: &f" + server.getMotd()),
                    CC.colour("&eServer Status: " + server.getServerStatus().getFormat()),
                    CC.colour("&eBase Version: &f" + server.getBaseServerVersion()),
                    CC.colour("&eLast Update: &f" + (System.currentTimeMillis() - server.getLastUpdate()) + "ms ago"),
                    CC.colour("&e&m-------------------------------------")
            });
        });
        return false;
    }
}
