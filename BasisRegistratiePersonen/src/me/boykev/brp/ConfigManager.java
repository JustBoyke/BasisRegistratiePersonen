package me.boykev.brp;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager
{
  private File configFile;
  private FileConfiguration config;
  
  public ConfigManager(Main basement)
  {
    this.configFile = new File(basement.getDataFolder(), "config.yml");
    this.config = YamlConfiguration.loadConfiguration(this.configFile);
  }
  
  public void save()
  {
    try
    {
      this.config.save(this.configFile);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public FileConfiguration getConfig()
  {
    return this.config;
  }

  public boolean reloadConfig() {
      try {
          config = YamlConfiguration.loadConfiguration(configFile);
          return true;
      } catch (Exception erorr) {
          return false;
      }
  }
public void LoadDefaults() {
    config.addDefault("version", "1");
    config.addDefault("database.host", "-");
    config.addDefault("database.user", "-");
    config.addDefault("database.password", "-");
    config.addDefault("database.database", "-");
    config.addDefault("database.table", "-");
    config.options().copyDefaults(true);
    save();
	
}

public FileConfiguration editConfig() {
	return config;
}
}