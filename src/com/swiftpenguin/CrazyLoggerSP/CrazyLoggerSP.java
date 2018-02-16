package com.swiftpenguin.CrazyLoggerSP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

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

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if (event.getInventory().getName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "CrazyLogger")){

            event.setCancelled(true);
        }
    }

    private int counter;

    @EventHandler
    public void inventoryClick(InventoryClickEvent e){
        if (e.getInventory().getName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "CrazyLogger")){
            Bukkit.getServer().broadcastMessage("click event worked");

            if (e.getCurrentItem() != null){
                if (e.getCurrentItem().getType().equals(Material.COAL)){
                    Bukkit.getServer().broadcastMessage("coal worked");
                    e.getWhoClicked().closeInventory();

                    Inventory gui2 = getServer().createInventory(null, 54, ChatColor.LIGHT_PURPLE + "CrazyLogger Page 2");
                    ItemStack coal = new ItemStack(Material.COAL, 1);
                    gui2.addItem(coal);
                    e.getWhoClicked().openInventory(gui2);

                    counter=0;
                    ConfigurationSection configSection = getConfig().getConfigurationSection("Data." + p + "." + "info"); //?

                    for (String key : configSection.getKeys(false)) {

                        counter++;
                        System.out.println(counter);

                        if (counter > 64){

                            System.out.println(configSection.getString(key));
                            String format1 = configSection.getString(key);
                            String format2 = format1.replace("Crate:", "C:");
                            String format3 = format2.replace("Reward:", "R:");

                            ItemStack book = new ItemStack(Material.BOOK, 1);
                            ItemMeta itemM = book.getItemMeta();
                            itemM.setLore(Arrays.asList(format3));
                            itemM.setDisplayName(key);

                            book.setItemMeta(itemM);
                            gui2.addItem(book);
                        }

                    }

                }
            }
        }

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
                        return true;
                    } else {
                        String p = args[0];
                        if (getConfig().getInt("Data." + p + "." + "total") == 0) {
                            sender.sendMessage(ChatColor.RED + "Could not find that person in the data... Names are cap sensitive...");
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.BLUE + p + "'s" + " Full Data printed out in console...");
                            ConfigurationSection configSection = getConfig().getConfigurationSection("Data." + p + "." + "info");
                            Inventory gui = getServer().createInventory(null, 54, ChatColor.LIGHT_PURPLE + "CrazyLogger");

                            counter = 0;

                            for (String key : configSection.getKeys(false)) {

                                    System.out.println(configSection.getString(key));
                                    String format1 = configSection.getString(key).replace(p, "");
                                    String format2 = format1.replace("Crate:", "C:");
                                    String format3 = format2.replace("Reward:", "R:");

                                    ItemStack book = new ItemStack(Material.BOOK, 1);
                                    ItemMeta itemM = book.getItemMeta();
                                    itemM.setLore(Arrays.asList(format3));
                                    itemM.setDisplayName(key);

                                    book.setItemMeta(itemM);
                                    gui.addItem(book);
                                    counter++;

                            }

                                int total = getConfig().getInt("Data." + p + "." + "total");
                                System.out.println(ChatColor.RED + p + "'s" + " Total " + total);
                                ((Player) sender).getPlayer().openInventory(gui);

                            }
                        }
                    }
                    return true;
                }
        }
        return true;
    }
}

/*

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
                        return true;
                    } else {
                        String p = args[0];
                        if (getConfig().getInt("Data." + p + "." + "total") == 0) {
                            sender.sendMessage(ChatColor.RED + "Could not find that person in the data... Names are cap sensitive...");
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.BLUE +p+"'s"+ " Full Data printed out in console...");
                            ConfigurationSection configSection = getConfig().getConfigurationSection("Data." + p + "." + "info");
                            Inventory gui = getServer().createInventory(null, 54, ChatColor.LIGHT_PURPLE + "CrazyLogger");

                            for (String key : configSection.getKeys(false)) {

                                System.out.println(configSection.getString(key));
                                String format1 = configSection.getString(key).replace(p, "");
                                String format2 = format1.replace("Crate:", "C:");
                                String format3 = format2.replace("Reward:", "R:");
//                                sender.sendMessage(ChatColor.GREEN + format3);

                                ItemStack book = new ItemStack(Material.BOOK, 1);
                                ItemMeta itemM = book.getItemMeta();
                                itemM.setLore(Arrays.asList(format3));
                                itemM.setDisplayName(key);

                                book.setItemMeta(itemM);
                                gui.addItem(book);

                                //if private int > 63, add to different inventory and save to hashmmap maybe?
                            }

                            int total = getConfig().getInt("Data." + p + "." + "total");
                            System.out.println(ChatColor.RED + p + "'s" + " Total " + total);
                          ((Player) sender).getPlayer().openInventory(gui);

                        }
                    }
                    return true;
                }
            }
        }
        return true;
    }
}
 */