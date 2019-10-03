package me.boykev.brp;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventManager implements Listener{
	
	public EventManager(Main main) {
		this.instance = main;
	}
	
	private Main instance;
	private UserManager um;
	private SqlHandler sql;
	
	@EventHandler
	public void FirstJoin(PlayerJoinEvent e) {
		sql = new SqlHandler(instance);
		Player p = (Player) e.getPlayer();
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
			p.openInventory(InventoryManager.PrepareInv(p, "string"));
			return;
		}
		if(um.getConfig().getString("Leeftijd") == null) {
			p.openInventory(InventoryManager.PrepareInv(p, "string"));
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
