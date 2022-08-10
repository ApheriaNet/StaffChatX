package xyz.deliable;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.deliable.commands.StaffChat;
import xyz.deliable.commands.StaffChatAdmin;

import java.io.File;
import java.io.IOException;

public final class StaffChatX extends JavaPlugin implements Listener {

    public static YamlDocument config;
    public static YamlDocument langEN;
    static StaffChatX instance;

    @Override
    public void onEnable() {
        try {
            File lang = new File(this.getDataFolder(), "lang");
            if (!lang.exists()) {
                lang.mkdirs();
            }
            /*config = YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.DEFAULT);*/
            langEN = YamlDocument.create(new File(lang, "en.yml"), getResource("en.yml"),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getCommand("staffchat").setExecutor(new StaffChat());
        getCommand("staffchatadmin").setExecutor(new StaffChatAdmin());
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        instance = this;
    }

    public static Plugin getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {

    }

    public static void sendStaffMessage(String message, String playerName, String mode) {
        for (Player p: Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("staffchat.getmessage")) {
                if (mode == "command") {
                    p.sendMessage(Tools.color(langEN.getString("format.online").replace("%message%", message).replace("%player%", playerName).replace("%mode%", langEN.getString("modes.command"))));
                } else if (mode == "toggle") {
                    p.sendMessage(Tools.color(langEN.getString("format.online").replace("%message%", message).replace("%player%", playerName).replace("%mode%", langEN.getString("modes.toggle"))));
                }
            }
        }
    }

    @EventHandler
    public void on(AsyncPlayerChatEvent e) {
        StaffChat.setPUUID(e.getPlayer().getUniqueId().toString());
        if (StaffChat.file.getBoolean("toggled")) {
          e.setCancelled(true);
          sendStaffMessage(e.getMessage(), e.getPlayer().getName(), "toggle");
        }
    }
}
