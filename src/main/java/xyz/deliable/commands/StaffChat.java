package xyz.deliable.commands;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.deliable.StaffChatX;
import xyz.deliable.Tools;

import java.io.File;
import java.io.IOException;

public class StaffChat implements CommandExecutor {

    public static YamlDocument file;
    public static String pUUID;

    public static void setPUUID(String uuid) {
        pUUID = uuid;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (sender.hasPermission("staffchat.use")) {
            if (args.length == 0) {
                File data = new File(StaffChatX.getInstance().getDataFolder(), "data");
                if (!data.exists()) {
                    data.mkdirs();
                }
                try {
                    setPUUID(p.getUniqueId().toString());
                    file = YamlDocument.create(new File(data, pUUID + ".yml"), StaffChatX.getInstance().getResource("playerData.yml"),
                            GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!file.getFile().exists()) {
                    Bukkit.getLogger().info("Creating data file for " + p.getName());
                    try {
                        file.save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bukkit.getLogger().info("Created data file for " + p.getName());
                } else {
                    if (file.getBoolean("toggled")) {
                        file.set("toggled", false);
                        try {
                            file.save();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sender.sendMessage(Tools.color(StaffChatX.langEN.getString("command.succesfulToggle").replace("%status%", StaffChatX.langEN.getString("status.off"))));
                    } else {
                        file.set("toggled", true);
                        try {
                            file.save();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sender.sendMessage(Tools.color(StaffChatX.langEN.getString("command.succesfulToggle").replace("%status%", StaffChatX.langEN.getString("status.on"))));
                    }
                }
            } else if (args.length >= 1) {
                sender.sendMessage(Tools.color(StaffChatX.langEN.getString("command.succesfulMessage")));
                StaffChatX.sendStaffMessage(String.join(" ", args), sender.getName(), "command");
            }
        } else {
            sender.sendMessage(Tools.color(StaffChatX.langEN.getString("command.noPermission")));
        }
        return true;
    }
}
