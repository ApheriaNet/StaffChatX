package xyz.deliable;

import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Tools {
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /*public static YamlDocument lang() {
        switch(Coretta.config.getString("settings.language")) {
            case "tr":
                return Coretta.langTR;
            case "en":
                return Coretta.langEN;
        }
        return null;
    }*/

    public static YamlDocument config() {
        return StaffChatX.config;
    }

    public static void disable() {
        Bukkit.getPluginManager().disablePlugin(new StaffChatX());
    }


    /*public static boolean perm(CommandSender s, String perm) {
        switch (perm) {
            case "main":
                if (Tools.config().getBoolean("settings.permissions.userCommands.main.required")) {
                    if (s.hasPermission(Tools.config().getString("permissions.userCommands.main"))) {
                        return true;
                    }
                }
            case "reloadMain":
                if (Tools.config().getBoolean("settings.permissions.adminCommands.reload.main.required")) {
                    if (s.hasPermission(Tools.config().getString("permissions.adminCommands.reload.main"))) {
                        return true;
                    }
                }
        }
        return false;
    }*/
}