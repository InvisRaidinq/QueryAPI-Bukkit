package xyz.invisraidinq.queryapi.server;

import org.bukkit.Bukkit;

public class Server {

    private final String serverName;
    private int onlinePlayers;
    private int maxPlayers;
    private final String motd;
    private ServerStatus serverStatus;
    private final String baseServerVersion;

    /**
     * Constructor to initialise an {@link Server} object
     *
     * @param serverName The name of the server to initialise
     */
    public Server(String serverName) {
        this.serverName = serverName;
        this.onlinePlayers = Bukkit.getOnlinePlayers().size();
        this.maxPlayers = Bukkit.getMaxPlayers();
        this.motd = Bukkit.getMotd();
        this.serverStatus = Bukkit.hasWhitelist() ? ServerStatus.WHITELISTED : ServerStatus.ONLINE;
        this.baseServerVersion = Bukkit.getVersion();
    }

    /**
     * Constructor to initialise an {@link Server} object with all params
     *
     * @param serverName The name of the server
     * @param onlinePlayers The player count
     * @param maxPlayers The max player count
     * @param motd The server MOTD
     * @param serverStatus The server status
     * @param baseServerVersion The base server version
     */
    public Server(String serverName, int onlinePlayers, int maxPlayers, String motd, ServerStatus serverStatus, String baseServerVersion) {
        this.serverName = serverName;
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
        this.motd = motd;
        this.serverStatus = serverStatus;
        this.baseServerVersion = baseServerVersion;
    }

    /**
     * Get the server name
     *
     * @return The cached name of the server
     */
    public String getServerName() {
        return this.serverName;
    }

    /**
     * Get the online player count
     *
     * @return The cached online player count
     */
    public int getOnlinePlayers() {
        return this.onlinePlayers;
    }

    /**
     * Get the maximum player count
     *
     * @return The maximum player count
     */
    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    /**
     * Get the server MOTD
     *
     * @return The cached server MOTD
     */
    public String getMotd() {
        return this.motd;
    }


    /**
     * Get the server status
     *
     * @return The cached server status
     */
    public ServerStatus getServerStatus() {
        return this.serverStatus;
    }

    /**
     * Get the base server version
     *
     * @return The cached base server version
     */
    public String getBaseServerVersion() {
        return this.baseServerVersion;
    }
}
