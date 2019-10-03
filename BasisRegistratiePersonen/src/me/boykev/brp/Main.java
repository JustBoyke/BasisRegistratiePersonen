package me.boykev.brp;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
	
	@EventHandler
	public void FirstJoin(PlayerJoinEvent e) {
		Player p = (Player) e.getPlayer();
		um = new UserManager(this, p);
		File configFile = new File(this.getDataFolder() + File.separator + "users", p.getUniqueId().toString() + ".yml");
		if(!configFile.exists()) {
			um.LoadDefaults();
			um.editConfig().set("PlayerName", p.getName().toString());
			um.save();
			p.openInventory(InventoryManager.PrepareInv(p, "string"));
			return;
		}
		if(um.getConfig().getString("Leeftijd") == null) {
			p.openInventory(InventoryManager.PrepareInv(p, "string"));
			return;
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("brpset")) {
			Player p = (Player) sender;
			if(args.length < 2) {
				p.sendMessage(ChatColor.RED + "Niet genoeg argumenten! /brpset [type] [player]");
				return false;
			}
			if(args[0].equalsIgnoreCase("setage")) {
				if(!p.hasPermission("brp.setage")) {
					p.sendMessage(ChatColor.RED + "Hier heb je geen permissies voor!");
					return false;
				}
				Player target = Bukkit.getPlayer(args[1]);
				if(target == null) {
					p.sendMessage(ChatColor.RED + "Dit is geen speler!");
					return false;
				}
				p.sendMessage(ChatColor.GREEN + "Leeftijd scherm geopend voor de speler!");
				target.sendMessage(ChatColor.RED + "Een administrator heeft het leeftijd scherm voor je geopend!");
				target.openInventory(InventoryManager.PrepareInv(target, "string"));
				return false;
			}
			if(args[0].equalsIgnoreCase("setprov")) {
				if(!p.hasPermission("brp.setprov")) {
					p.sendMessage(ChatColor.RED + "Hier heb je geen permissies voor!");
					return false;
				}
				Player target = Bukkit.getPlayer(args[1]);
				if(target == null) {
					p.sendMessage(ChatColor.RED + "Dit is geen speler!");
					return false;
				}
				p.sendMessage(ChatColor.GREEN + "provintie scherm geopend voor de speler!");
				target.sendMessage(ChatColor.RED + "Een administrator heeft het provintie scherm voor je geopend!");
				target.openInventory(InventoryManager.prepareProvintie(target, "string"));
				return false;
			}
		}
		return false;
	}
	
	
}
