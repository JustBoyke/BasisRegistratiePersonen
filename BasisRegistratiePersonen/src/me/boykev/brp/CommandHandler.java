package me.boykev.brp;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CommandHandler implements CommandExecutor{
	public CommandHandler(Main main) {
		this.instance = main;
	}
	
	@SuppressWarnings("unused")
	private Main instance;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("brpset")) {
			Player p = (Player) sender;
			if(args.length < 2) {
				p.sendMessage(ChatColor.RED + "Niet genoeg argumenten! /brpset [type] [player]");
				return false;
			}
			if(args[0].equalsIgnoreCase("age")) {
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
			if(args[0].equalsIgnoreCase("prov")) {
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
			p.sendMessage(ChatColor.RED + "Dit is geen bestaand subcommando!");
		}
		return false;
	}
}
