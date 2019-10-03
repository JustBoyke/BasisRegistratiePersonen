package me.boykev.brp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.entity.Player;

public class SqlHandler {
	private Main instance;
	public Connection con;
	private ConfigManager db;
	private String host, database, username, password;
	private int port;
	
    public SqlHandler(Main main) {
		this.instance = main;
	}
    
    private UserManager um;
    
    public void openConnection() throws SQLException, ClassNotFoundException {
    	db = new ConfigManager(instance);
    	host = db.getConfig().getString("database.host");
    	database = db.getConfig().getString("database.database");
    	port = db.getConfig().getInt("database.port");
    	username = db.getConfig().getString("database.user");
    	password = db.getConfig().getString("database.password");
    if (con != null && !con.isClosed()) {
        return;
    }
 
    synchronized (this) {
        if (con != null && !con.isClosed()) {
            return;
        }
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?useSSL=false", this.username, this.password);
    }
}
    
    public boolean checkData() {
    	db = new ConfigManager(instance);
    	if(db.getConfig().getString("database.host").equals("-")) {
    		System.out.print("Host is niet insteld voor SQL, Database wordt niet gebruikt!");
    		return false;
    	}
    	if(db.getConfig().getString("database.database").equals("-")) {
    		System.out.print("Databasenaam is niet insteld voor SQL, Database wordt niet gebruikt!");
    		return false;
    	}
    	if(db.getConfig().getString("database.user").equals("-")) {
    		System.out.print("Database-gebruiker is niet insteld voor SQL, Database wordt niet gebruikt!");
    		return false;
    	}
    	if(db.getConfig().getString("database.password").equals("-")) {
    		System.out.print("Database-Wachtwoord is niet insteld voor SQL, Database wordt niet gebruikt!");
    		return false;
    	}
    	if(db.getConfig().getString("database.tabel").equals("-")) {
    		System.out.print("Database-Table is niet insteld voor SQL, Database wordt niet gebruikt!");
    		return false;
    	}
    	return true;
    }
    
	public void updateData(Player player) {
		if(this.checkData() == false) {
			return;
		}
		String tabel = db.getConfig().getString("database.tabel");
		db = new ConfigManager(instance);
		um = new UserManager(instance, player);
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		if(this.checkUpdate(player.getUniqueId()) == format.format(now)) {
			return;
		}
		String pn = um.getConfig().getString("PlayerName");
		String age = um.getConfig().getString("Leeftijd");
		String prov = um.getConfig().getString("Provintie");
		String byear = um.getConfig().getString("geboortejaar");
        try {
            openConnection();
            Statement statement = con.createStatement();   
            statement.executeUpdate("UPDATE " + tabel + " SET `lastupdate`= '" + format.format(now) + "', `playername`= '" + pn + "', `age`= '" + age + "', `provintie`= '" + prov + "', `geboortejaar`= '" + byear + "' WHERE `playeruuid`= '" + player.getUniqueId().toString() + "';");
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
	}
	
    public String checkUpdate(UUID uuid) {
    	String tabel = db.getConfig().getString("database.tabel");
        try {     
        	openConnection();
            Statement statement = con.createStatement();   
            ResultSet result = statement.executeQuery("SELECT lastupdate FROM " + tabel + " WHERE playeruuid = '" + uuid + "'");
            while (result.next()) {
            	String type = result.getString("lastupdate");
            	
            	return type;
            }
            con.close();
            
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
		return null;
        
	}
    
	public void insertUser(Player p) {
		db = new ConfigManager(instance);
		um = new UserManager(instance, p);
		String tabel = db.getConfig().getString("database.tabel");
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            openConnection();
            Statement statement = con.createStatement();   
            statement.executeUpdate("INSERT INTO `" + tabel + "`(`lastupdate`, `playername`, `playeruuid`) VALUES ('" + format.format(now) + "', '" + p.getName().toString() + "', '" + p.getUniqueId().toString() + "');");
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
	}

	
	
}