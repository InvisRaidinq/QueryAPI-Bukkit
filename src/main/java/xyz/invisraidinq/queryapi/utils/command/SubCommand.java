package xyz.invisraidinq.queryapi.utils.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand {

    private final String name;

    private String description;
    private List<String> aliases = new ArrayList<>();


    public SubCommand(String name) {
        this.name = name;
    }

    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAliases() {
        return this.aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }


}