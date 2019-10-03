package me.boykev.brp;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener{
	
	public ConfigManager cm;
	public String plversion = "1";
	public UserManager um;
	
	public void onEnable() {
		File configFile = new File(this.getDataFolder(), "config.yml");
		cm = new ConfigManager(this);
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(new InventoryManager(this), this);
		pm.registerEvents(new EventManager(this), this);
		getCommand("brpset").setExecutor(new CommandHandler(this));
		if(!configFile.exists()) {
			cm.LoadDefaults();
			cm.save();
			System.out.print(ChatColor.DARK_GREEN + "Config Gemaakt!");
		}
		
		System.out.println(ChatColor.GREEN + "BasisRegistratiePersonen is opgestart!");
	}
	
	public void onDisable() {
		System.out.println(ChatColor.RED + "BasisRegistratiePersonen is afgesloten!");
	}
	
	
	
	
}
