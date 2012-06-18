package net.milkycraft;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class EasyRank extends JavaPlugin{
	public static Permission perms = null;
	public static EasyRank main;
	public static String maindirectory;
	private ErCommandExecutor ce;
	public static ArrayList<String> rank;
	public static ArrayList<String> name;	
	@Override
	public void onEnable() {
		main = this;
		setUpPermissions();
		setUpExecutors();	
		Settings config = new Settings(this);
		config.load();
		Metrics metrics = null;
		maindirectory = getDataFolder().getPath() + File.separator;
		getServer().getPluginManager().registerEvents(new Ranker(),
				this);
		try {
			metrics = new Metrics(main);
		} catch (IOException e) {
			e.printStackTrace();
		}
		metrics.start();
		writeInfo("[EasyRank] Successfully enabled!");
	}

	@Override
	public void onDisable() {
		writeInfo("[EasyRank] Successfully disabled!");
	}
	
	private void setUpExecutors() {
		ce = new ErCommandExecutor(this);
		getCommand("rank").setExecutor(ce);
	}
	
	 private boolean setUpPermissions() {
	        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
	        perms = rsp.getProvider();
	        return perms != null;
	    }
	 
	 public static void writeInfo(String info) {
		 java.util.logging.Logger.getLogger("Minecraft").info(info);
	 }
	 
	 public static void writeWarn(String warn) {
		 java.util.logging.Logger.getLogger("Minecraft").warning(warn);
	 }
	 
	 public static final EasyRank getMain() {
		 return main;
	 }
}
