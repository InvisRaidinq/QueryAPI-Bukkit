package xyz.invisraidinq.queryapi.server;

public enum ServerStatus {

    ONLINE("&aOnline"),
    OFFLINE("&cOffline"),
    WHITELISTED("&dWhitelisted");

    private final String format;

    /**
     * Enum constructor
     *
     * @param format The format of the status
     */
    ServerStatus(String format) {
        this.format = format;
    }

    /**
     * Get the format of the server status
     *
     * @return The server status format
     */
    public String getFormat() {
        return this.format;
    }

}
