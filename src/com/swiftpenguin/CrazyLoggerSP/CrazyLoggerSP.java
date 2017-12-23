package com.swiftpenguin.CrazyLoggerSP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CrazyLoggerSP extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new Logger(this), this);
        registerConfig();

    } 

    private void registerConfig() {
        saveDefaultConfig();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("crazylogger") && sender instanceof Player) {
            if (!sender.hasPermission("crazylogger.lookup")) {

            } else {

                if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + "Please give a IGN");
                    return true;

                } else if (args[0] != null) {

                    if (!sender.hasPermission("crazylogger.lookup")) {

                    } else {
                        String p = args[0];

                        if (getConfig().getInt("Data." + p + "." + "total") == 0) {
                            sender.sendMessage(ChatColor.RED + "Could not find that person in the data... Names are cap sensitive...");
                            return true;

                        } else {

                            sender.sendMessage(ChatColor.BLUE +p+"'s"+ " Data printed out in console...");

                            ConfigurationSection configSection = getConfig().getConfigurationSection("Data." + p + "." + "info");
                            for (String key : configSection.getKeys(false)) {
                                System.out.println(configSection.getString(key));

                            }

                            int total = getConfig().getInt("Data." + p + "." + "total");
                            System.out.println(ChatColor.RED + p + "'s" + " Total " + total);

                        }
                    }

                    return true;
                }


            }

        }
        return true;
    }
    }