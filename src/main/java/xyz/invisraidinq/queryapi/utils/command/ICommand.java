package xyz.invisraidinq.queryapi.utils.command;

import java.util.List;

public interface ICommand {

    String getName();

    String getDescription();
    void setDescription(String description);

    List<String> getAliases();
    void setAliases(List<String> aliases);

    String getPermission();
    void setPermission(String permission);


}
