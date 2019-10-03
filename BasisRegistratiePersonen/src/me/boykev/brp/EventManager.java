package me.boykev.brp;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventManager implements Listener{
	
	public EventManager(Main main) {
		this.instance = main;
	}
	
	private Main instance;
	private UserManager um;
	
	@EventHandler
	public void FirstJoin(PlayerJoinEvent e) {
		Player p = (Player) e.getPlayer();
		um = new UserManager(instance, p);
		File configFile = new File(instance.getDataFolder() + File.separator + "users", p.getUniqueId().toString() + ".yml");
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
	
}
