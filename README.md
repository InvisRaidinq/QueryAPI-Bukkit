# QueryAPI-Bukkit
``NOTE: This plugin is untested, it's open source hence and is super easy to work with. Please report and issues to me``

# Compiling
First, clone the repository using ``git clone https://github.com/InvisRaidinq/QueryAPI-Bukkit.git``, and then compile using ``cd QueryAPI-Bukkit && mvn clean install``. A ``target/`` directory will be created, with the compiled JAR file.

# Implementing the API into a plugin

### Getting an instance of the API

There are two possible ways to get an instance of the API. You can declare it a global variable in your Main class, and then create a getter for it:

```java
public class ExamplePlugin extends JavaPlugin {
    
    private final QueryAPI queryAPI = QueryAPI.getInstance();

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("QueryAPI-Bukkit") == null) {
            this.getLogger().warning("Oh no, looks like you forgot to set the plugin as a dependency!");
            this.getServer().shutdown();
        }
    }
    
    public QueryAPI getQueryAPI() {
        return this.queryAPI;
    }
}
```

Or you can grab the instance in a class:

```java
public class ExampleClass {
    
    private final QueryAPI queryAPI = QueryAPI.getInstance();
    
    public void broadcastOnlinePlayers() {
        Bukkit.broadcastMessage(ChatColor.GREEN + "There are currently " + queryAPI.getOnlinePlayers() + " players on this network");
    }
}
```

# Pull Requests and Changes
I want this to be a community focused project: if there are any bugs or you've got a suggestion, feel free to fork the repository and make a pull request, or let me know your ideas and I'll look into implementing them!

# Using this in your plugins
You're free to use this API in any of your plugins, including premium plugins that you want to re-sell. Please, however, give me credit for the work I've done.
