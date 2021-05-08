package xyz.invisraidinq.queryapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.invisraidinq.queryapi.QueryPlugin;

import java.util.ArrayList;
import java.util.List;

public class CC {

    public static final String MESSAGE_SPLITTER = "///";

    /**
     * @param text the text to colorize
     * @return A coloured string
     */
    public static String colour(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * @param list The array to translate
     * @return A translated array
     */
    public static List<String> colour(List<String> list) {
        List<String> translated = new ArrayList<>();
        for (String string : list) {
            translated.add(colour(string));
        }
        return translated;
    }

    /**
     * @param text The text to output
     */
    public static void log(String text) {
        Bukkit.getConsoleSender().sendMessage(colour("[QueryAPI-Bukkit] " + text));
    }
}
