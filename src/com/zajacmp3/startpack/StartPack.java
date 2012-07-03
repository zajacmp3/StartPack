package com.zajacmp3.startpack;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class StartPack extends JavaPlugin implements Listener{
	static int helmet;
	static int armor;
	static int legs;
	static int boots;
	static int additionalItems;
	public void onDisable(){
		PluginDescriptionFile description = this.getDescription();
		System.out.println("StartPack" + description.getVersion()+" is stopping...");
	}
	public void onEnable(){
		load();
		PluginDescriptionFile description = this.getDescription();
		System.out.println("StartPack" + description.getVersion()+" is starting...");
		EventListener(this);
	}
	public Plugin plugin;
	public void EventListener(Plugin instance) {
	plugin = instance;
	Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	private void load() {
		boolean exist = (new File("plugins/StartPack/config.yml")).exists();
		if(exist==false){
			createDefaultConfig();
		}
		else{
			FileConfiguration config = this.getConfig();
			helmet = config.getInt("Configuration.Helmet");
			armor = config.getInt("Configuration.Armor");
			legs = config.getInt("Configuration.Legs");
			boots = config.getInt("Configuration.Boots");
			additionalItems = config.getInt("Configuration.AdditionalItems");
		}
		
	}
	private void createDefaultConfig() {
		System.out.println("config.yml not found! Creating default config!");
		FileConfiguration config = this.getConfig();
		config.addDefault("Configuration.Helmet",298);
		config.addDefault("Configuration.Armor",299);
		config.addDefault("Configuration.Legs",300);
		config.addDefault("Configuration.Boots",301);
		config.addDefault("Configuration.AdditionalItems",1);
		config.addDefault("Configuration.AdditionalItem - 1.ID", 1);
		config.addDefault("Configuration.AdditionalItem - 1.Amount", 1);
		config.options().copyDefaults(true);
		saveConfig();
		load();
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onLogin(final PlayerJoinEvent event){
		Player logging = event.getPlayer();
		boolean exist = (new File("world/players/"+logging.getName()+".dat")).exists();
		if(exist==false){
			logging.getInventory().setChestplate(new ItemStack(armor, 1));
			logging.getInventory().setHelmet(new ItemStack(helmet,1));
			logging.getInventory().setBoots(new ItemStack(boots,1));
			logging.getInventory().setLeggings(new ItemStack(legs,1));
			for(int i=1;i<=additionalItems;i++){
				FileConfiguration configuration = this.getConfig();
				logging.getInventory().addItem(new ItemStack(configuration.getInt("Configuration.AdditionalItem - "+i+".ID"),configuration.getInt("Configuration.AdditionalItem - "+i+".Amount")));
			}
		}
	}
}
