package xyz.deliable.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.deliable.StaffChatX;
import xyz.deliable.Tools;

import java.io.IOException;

public class StaffChatAdmin implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("staffchatx.admin")) {
            if (args.length == 0) {
                for (String s : StaffChatX.langEN.getStringList("command.help")) {
                    if (cmd.getName().equalsIgnoreCase("sca")) {
                        sender.sendMessage(Tools.color(s).replace("%command%", "sca"));
                    } else if (cmd.getName().equalsIgnoreCase("staffchatadmin")) {
                        sender.sendMessage(Tools.color(s).replace("%command%", "staffchatadmin"));
                    }
                }
            } else if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    try {
                        StaffChatX.langEN.reload();
                        sender.sendMessage(Tools.color(StaffChatX.langEN.getString("command.reload")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    sender.sendMessage(Tools.color(StaffChatX.langEN.getString("command.invalidArg")));
                }
            }
        } else {
            sender.sendMessage(Tools.color(StaffChatX.langEN.getString("command.noPermission")));
        }
        return true;
    }
}
