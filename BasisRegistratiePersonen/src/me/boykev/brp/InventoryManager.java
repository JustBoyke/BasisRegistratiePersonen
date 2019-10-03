package me.boykev.brp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class InventoryManager implements Listener{
	
	public InventoryManager(Main main) {
		this.instance = main;
	}
	
	private Main instance;
	private static String agename = ChatColor.translateAlternateColorCodes('&', "&8&lKies je leeftijd");
	private static String provname = ChatColor.translateAlternateColorCodes('&', "&8&lKies je Provintie");
	private UserManager um;
	
	
	public static Inventory setAge(Player player) {
		Inventory setage = Bukkit.createInventory(player, 27, agename);
		return setage;
	}
	
	public static Inventory setProv(Player player) {
		Inventory setage = Bukkit.createInventory(player, 18, provname);
		return setage;
	}
	
	public static ItemStack makeItem(String name, String jaar) {
		ItemStack item = new ItemStack(Material.STRUCTURE_VOID, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(name);
		ArrayList<String> ilore = new ArrayList<String>();
		ilore.add("Geboren in:");
		ilore.add(jaar);
		itemmeta.setLore(ilore);
		itemmeta.setDisplayName(name);
		item.setItemMeta(itemmeta);
		
		return item;
		
	}
	
	public static ItemStack makeProvintie(String name) {
		ItemStack item = new ItemStack(Material.STRUCTURE_VOID, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(name);
		itemmeta.setDisplayName(name);
		item.setItemMeta(itemmeta);
		
		return item;
		
	}
	
	public static ItemStack barrier(String name) {
		ItemStack item = new ItemStack(Material.BARRIER, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(name);
		item.setItemMeta(itemmeta);
		
		return item;
		
	}
	
	public static Inventory PrepareInv(Player p, String s) {
		Inventory inv = setAge(p);
		
		inv.setItem(0, barrier(ChatColor.RED + "Jonger dan 13"));
		inv.setItem(1, makeItem("13 Jaar", "2006"));
		inv.setItem(2, makeItem("14 Jaar", "2005"));
		inv.setItem(3, makeItem("15 Jaar", "2004"));
		inv.setItem(4, makeItem("16 Jaar", "2003"));
		inv.setItem(5, makeItem("17 Jaar", "2002"));
		inv.setItem(6, makeItem("18 Jaar", "2001"));
		inv.setItem(7, makeItem("19 Jaar", "2000"));
		inv.setItem(8, makeItem("20 Jaar", "1999"));
		inv.setItem(9, makeItem("21 Jaar", "1998"));
		inv.setItem(10, makeItem("22 Jaar", "1997"));
		inv.setItem(11, makeItem("23 Jaar", "1996"));
		inv.setItem(12, makeItem("24 Jaar", "1995"));
		inv.setItem(13, makeItem("25 Jaar", "1994"));
		inv.setItem(14, makeItem("26 Jaar", "1993"));
		inv.setItem(15, makeItem("27 Jaar", "1992"));
		inv.setItem(16, makeItem("28 Jaar", "1991"));
		inv.setItem(17, makeItem("29 Jaar", "1990"));
		inv.setItem(18, makeItem("30 Jaar", "1989"));
		inv.setItem(19, makeItem("31 Jaar", "1988"));
		inv.setItem(20, makeItem("32 Jaar", "1987"));
		inv.setItem(21, makeItem("33 Jaar", "1986"));
		inv.setItem(22, makeItem("34 Jaar", "1985"));
		inv.setItem(23, makeItem("35 Jaar", "1984"));
		inv.setItem(24, makeItem("36 Jaar", "1983"));
		inv.setItem(25, makeItem("37 Jaar", "1982"));
		inv.setItem(26, makeItem("38 Jaar", "1981"));
		return inv;
	}
	
	public static Inventory prepareProvintie(Player p, String s) {
		Inventory inv = setProv(p);
		
		inv.setItem(0, barrier(ChatColor.RED + "Staat er niet tussen"));
		inv.setItem(1, makeProvintie("België"));
		inv.setItem(2, makeProvintie("Ander land"));
		inv.setItem(3, makeProvintie("Groningen"));
		inv.setItem(4, makeProvintie("Fryslân"));
		inv.setItem(5, makeProvintie("Drenthe"));
		inv.setItem(6, makeProvintie("Overijssel"));
		inv.setItem(7, makeProvintie("Flevoland"));
		inv.setItem(8, makeProvintie("Gelderland"));
		inv.setItem(9, makeProvintie("Utrecht"));
		inv.setItem(10, makeProvintie("Noord-Holland"));
		inv.setItem(11, makeProvintie("Zuid-Holland"));
		inv.setItem(12, makeProvintie("Zeeland"));
		inv.setItem(13, makeProvintie("Noord-Brabant"));
		inv.setItem(14, makeProvintie("Limburg"));
		return inv;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		InventoryView inv = e.getView();
		Inventory inv2 = e.getInventory();
		um = new UserManager(instance,p);
		
		if(inv.getTitle().equals(InventoryManager.agename)) {
			if(e.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || e.getAction() == InventoryAction.HOTBAR_SWAP || e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
				e.setCancelled(true);
				return;
			}
			if(item == null) {
				return;
			}
			if(item.getType() == Material.BARRIER) {
				if(item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Jonger dan 13")) {
					p.kickPlayer(ChatColor.RED + "Je moet minimaal 13 zijn om Minetopia te mogen spelen!");
//					new Punishment(p.getName(), p.getUniqueId().toString(), "Underagged Speler", "Age-Ristrictor", PunishmentType.BAN, TimeManager.getTime(), -1, null, -1).create();
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " &cUnderagged Speler -s");
					inv2.clear();	
				}
			}
			
			if(item.getType() == Material.STRUCTURE_VOID) {
				inv.close();
				if(um.getConfig().getString("Provintie") == null) {
					p.openInventory(InventoryManager.prepareProvintie(p, "String"));
				}
				String age = item.getItemMeta().getDisplayName();
				List<String> ilore = item.getItemMeta().getLore();
				um.editConfig().set("Leeftijd", age);
				um.editConfig().set("geboortejaar", ilore.get(1));
				um.save();
				return;
			}
			
		}	
		
		if(inv.getTitle().equals(InventoryManager.provname)) {
			if(e.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || e.getAction() == InventoryAction.HOTBAR_SWAP || e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || e.getAction() == InventoryAction.PICKUP_ONE) {
				e.setCancelled(true);
				return;
			}
			if(item.getType() == Material.BARRIER) {
				if(item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Staat er niet tussen")) {
					inv.close();
					inv2.clear();	
				}
			}
			
			if(item.getType() == Material.STRUCTURE_VOID) {
				inv.close();
				String prov = item.getItemMeta().getDisplayName();
				um.editConfig().set("Provintie", prov);
				um.save();
				return;
			}
			
		}
	}
	
	@EventHandler
	public void invExit(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		InventoryView inv = e.getView();
		um = new UserManager(instance,p);
		
		if(inv.getTitle().equals(InventoryManager.agename)) {
			if(um.getConfig().getString("age") == null) {
				InventoryManager.PrepareInv(p, "randromstring");
				return;
			}
		}
		
		
	}
	
	
}
