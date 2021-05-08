package xyz.invisraidinq.queryapi.utils.command;

import com.google.common.base.Preconditions;
import org.bukkit.ChatColor;
import org.bukkit.command.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdvancedCommand implements CommandExecutor, TabCompleter, ICommand {

    private final String name;
    private String description;
    private List<String> aliases;
    private String permission;

    private List<SubCommand> subCommands = new ArrayList<>();
    private BaseCommand baseCommand;

    public AdvancedCommand(String name) {
        this.name = name;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            if (this.baseCommand != null) {
                this.baseCommand.onCommand(sender, command, label, args);
                return true;
            }
            
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&cInvalid Usage! Available sub-commands for &7" + label + " &care &7" +
                            this.subCommands.stream().map(SubCommand::getName).collect(Collectors.joining("&7, "))));
            return false;
        }

        SubCommand argument = this.getSubCommand(args[0]);

        if (argument == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSub-command &7" + args[0] + " &cdoes not exist in command &7" + this.name));
            return false;
        }

        argument.onCommand(sender, command, label, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){
        List<String> results = new ArrayList<>();

        if(args.length < 2) {
            for (SubCommand subCommand : this.subCommands) {
                results.add(subCommand.getName());
            }

            if (results.isEmpty()) {
                return null;
            }

        } else {
            SubCommand argument = this.getSubCommand(args[0]);

            if (argument == null) {
                return results;
            }

            results = argument.onTabComplete(sender, command, label, args);

            if (results == null) {
                return null;
            }
        }

        return this.getCompletions(args, results);
    }

    public SubCommand getSubCommand(String name) {
        for (SubCommand subCommand : this.subCommands) {
            if (subCommand.getName().equalsIgnoreCase(name) || subCommand.getAliases().contains(name.toLowerCase())) {
                return subCommand;
            }
        }

        return null;
    }

    private List<String> getCompletions(String[] arguments, List<String> input) {
        Preconditions.checkNotNull(arguments);
        Preconditions.checkArgument(arguments.length != 0);
        String argument = arguments[arguments.length - 1];
        return input.stream().filter(string -> string.regionMatches(true, 0, argument, 0, argument.length())).limit(80).collect(Collectors.toList());
    }

    public void addSubCommands(List<SubCommand> subCommands) {
        this.subCommands.addAll(subCommands);
    }

    public void setBaseCommand(BaseCommand baseCommand) {
        this.baseCommand = baseCommand;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public List<String> getAliases() {
        return this.aliases;
    }

    @Override
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    @Override
    public String getPermission() {
        return this.permission;
    }

    @Override
    public void setPermission(String permission) {
        this.permission = permission;
    }
}

