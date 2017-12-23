package com.swiftpenguin.CrazyLoggerSP;

import me.badbones69.crazycrates.api.events.PlayerPrizeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger implements Listener {

    private CrazyLoggerSP plugin;

    public Logger(CrazyLoggerSP plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void prizeLogger(PlayerPrizeEvent e) {

        String p = e.getPlayer().getName();
        String crate = e.getCrateName();
        String prize = e.getPrize().getDisplayItem().getItemMeta().getDisplayName();

        if (!plugin.getConfig().getBoolean("ignore-ops")){
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                int counter = plugin.getConfig().getInt("Data." + p + ".total") + 1;
                plugin.getConfig().set("Data." + p + ".total", counter);
                plugin.getConfig().set("Data." + p + ".info." + counter, dateFormat.format(date) + " " + p + " Crate: " + crate + " Reward: " + prize);
                plugin.saveConfig();

            } else  if (e.getPlayer().isOp() || e.getPlayer().hasPermission("crazylogger.exempt")) {
                return;
            }
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                int counter = plugin.getConfig().getInt("Data." + p + ".total") + 1;
                plugin.getConfig().set("Data." + p + ".total", counter);
                plugin.getConfig().set("Data." + p + ".info." + counter, dateFormat.format(date) + " " + p + " Crate: " + crate + " Reward: " + prize);
                plugin.saveConfig();

        }
}

