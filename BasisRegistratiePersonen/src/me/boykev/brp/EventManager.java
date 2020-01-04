package me.boykev.brp;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.onecraft.clientstats.ClientStats;
import fr.onecraft.clientstats.ClientStatsAPI;
import net.md_5.bungee.api.ChatColor;

public class EventManager implements Listener{
	
	public EventManager(Main main) {
		this.instance = main;
	}
	
	private Main instance;
	private UserManager um;
	private SqlHandler sql;
	
	
	@EventHandler
	public void FirstJoin(PlayerJoinEvent e) {
		ClientStatsAPI cstats = ClientStats.getApi();
		sql = new SqlHandler(instance);
		Player p = (Player) e.getPlayer();
		@SuppressWarnings("unused")
		int version = cstats.getProtocol(p.getUniqueId());
		um = new UserManager(instance, p);
		File configFile = new File(instance.getDataFolder() + File.separator + "users", p.getUniqueId().toString() + ".yml");
		if(configFile.exists()) {
			sql.updateData(p);
		}
		if(!configFile.exists()) {
			um.LoadDefaults();
			um.editConfig().set("PlayerName", p.getName().toString());
			um.save();
			sql.insertUser(p);
			return;
		}
		if(um.getConfig().getString("Leeftijd") == null) {
			p.sendMessage(ChatColor.RED + "Je bent nog niet ingeschreven in de Basisregistratie. Om dit te doen doe /brpregister");
			return;
		}
	}
	
	@EventHandler
	public void leaveEvent(PlayerQuitEvent e) {
		sql = new SqlHandler(instance);
		Player p = (Player) e.getPlayer();
		sql.updateData(p);
	}
	
}
