/*
 * Copyright 2018 Benjamin Martin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.lapsimc.lapisinventories;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventoriesCommand implements CommandExecutor {

    private LapisInventories plugin;

    InventoriesCommand(LapisInventories p) {
        plugin = p;
        p.getCommand("LapisInventories").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("LapisInventories")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("inspect")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(plugin.invConfigs.getMessage("Error.MustBePlayer"));
                    return true;
                }
                Player p = (Player) sender;
                if (!p.hasPermission("LapisInventories.checkBlocks")) {
                    p.sendMessage(plugin.invConfigs.getColoredMessage("Error.NotPermitted"));
                    return true;
                }
                if (plugin.inspectingPlayers.contains(p.getUniqueId())) {
                    plugin.inspectingPlayers.remove(p.getUniqueId());
                    p.sendMessage(plugin.invConfigs.getColoredMessage("CheckBlock.Disabled"));
                } else {
                    plugin.inspectingPlayers.add(p.getUniqueId());
                    p.sendMessage(plugin.invConfigs.getColoredMessage("CheckBlock.Enabled"));
                }
            } else if (args.length == 1 & args[0].equalsIgnoreCase("update")) {
                boolean permitted;
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    permitted = p.hasPermission("LapisInventories.canUpdate");
                } else {
                    permitted = true;
                }
                if (!permitted) {
                    sender.sendMessage(plugin.invConfigs.getColoredMessage("Error.NotPermitted"));
                    return true;
                }
                if (plugin.updater.checkUpdate()) {
                    sender.sendMessage("An update is available, it is being downloaded now and will be installed when the server restarts");
                    plugin.updater.downloadUpdate();
                } else {
                    sender.sendMessage("No update available");
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
                sendHelp(sender);
            } else {
                pluginInfo(sender);
            }
            return true;
        }
        return false;
    }


    private void sendHelp(CommandSender sender) {
        String primary = ChatColor.BLUE + "";
        String secondary = ChatColor.AQUA + "";
        String bars = secondary + "-----------";
        sender.sendMessage(bars + primary + " Lapis Inventories Commands " + bars);
        sender.sendMessage(primary + "/lapisinventories: " + secondary + "Displays plugin information");
        sender.sendMessage(primary + "/lapisinventories help: " + secondary + "Displays this information");
        sender.sendMessage(primary + "/lapisinventories inspect: " + secondary + "Enables creative block inspection");
        sender.sendMessage(primary + "/lapisinventories update: " + secondary + "Checks for and installs updates");
        sender.sendMessage(bars + bars + bars);

    }

    private void pluginInfo(CommandSender sender) {
        String primary = ChatColor.BLUE + "";
        String secondary = ChatColor.AQUA + "";
        String bars = secondary + "-----------";
        sender.sendMessage(bars + primary + " Lapis Inventories " + bars);
        sender.sendMessage(primary + "Version: " + secondary + plugin.getDescription().getVersion());
        sender.sendMessage(primary + "Author: " + secondary + plugin.getDescription().getAuthors().get(0));
        //TODO: get spigot link when setup
        //sender.sendMessage(primary + "Spigot: " + secondary + "https://goo.gl/6edyJA");
        sender.sendMessage(primary + "If you need help use " + secondary + "/lapisinventories help");
        sender.sendMessage(bars + bars + bars);
    }

}
