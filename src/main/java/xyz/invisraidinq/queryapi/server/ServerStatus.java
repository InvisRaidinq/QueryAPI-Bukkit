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

    public String getFormat() {
        return this.format;
    }

}
