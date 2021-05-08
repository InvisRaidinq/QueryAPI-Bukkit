package xyz.invisraidinq.queryapi.utils.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class CommandHandler {

    private final JavaPlugin plugin;
    private String noPermissionMessage;

    private CommandMap commandMap;

    public CommandHandler(JavaPlugin plugin) {
        this.plugin = plugin;

        try {
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);

            this. commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (commandMap == null) {
            plugin.getLogger().warning("Failed to initialise the command register for " + plugin.getName());
        }
    }

    public PluginCommand getCommand(String name, Plugin owner) {
        PluginCommand command = null;

        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            command = constructor.newInstance(name, owner);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return command;
    }

    public void registerAdvancedCommand(AdvancedCommand advancedCommand) {
        PluginCommand command = this.getCommand(advancedCommand.getName(), this.plugin);

        command.setPermissionMessage(ChatColor.translateAlternateColorCodes('&', this.noPermissionMessage));

        if (advancedCommand.getPermission() != null) {
            command.setPermission(advancedCommand.getPermission().toLowerCase());
        }

        command.setDescription(advancedCommand.getDescription() != null ? advancedCommand.getDescription() : "This is the default description!");

        command.setAliases(advancedCommand.getAliases());

        command.setExecutor(advancedCommand);
        command.setTabCompleter(advancedCommand);

        if (!this.commandMap.register(advancedCommand.getName(), command)) {
            command.unregister(this.commandMap);
            this.commandMap.register(advancedCommand.getName(), command);
        }
    }

    public void registerSimpleCommand(SimpleCommand simpleCommand) {
        PluginCommand command = this.getCommand(simpleCommand.getName(), this.plugin);

        command.setPermissionMessage(ChatColor.translateAlternateColorCodes('&', this.noPermissionMessage));

        if (simpleCommand.getPermission() != null) {
            command.setPermission(simpleCommand.getPermission().toLowerCase());
        }

        command.setDescription(simpleCommand.getDescription() != null ? simpleCommand.getDescription() : "This is the default description!");

        command.setAliases(simpleCommand.getAliases());

        command.setExecutor(simpleCommand);
        command.setTabCompleter(simpleCommand);

        if (!this.commandMap.register(simpleCommand.getName(), command)) {
            command.unregister(this.commandMap);
            this.commandMap.register(simpleCommand.getName(), command);
        }
    }

    public void setNoPermissionMessage(String noPermissionMessage) {
        this.noPermissionMessage = noPermissionMessage;
    }
}
