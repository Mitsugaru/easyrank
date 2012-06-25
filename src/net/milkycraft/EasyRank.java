package net.milkycraft;

import java.io.IOException;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.bananaco.bpermissions.api.ApiLayer;

import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * The Class EasyRank.
 */
public class EasyRank extends JavaPlugin{

	private Permission perms = null;
	private ErCommandExecutor ce;
	private PermissionsEx pex;
	private ApiLayer bperms;
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		setUpPermissions();
		setUpExecutors();	
		Settings config = new Settings(this);
		config.load();
		Metrics metrics = null;
		try {
			metrics = new Metrics(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		metrics.start();
		writeInfo("[EasyRank] Successfully enabled!");
	}

	/* (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	@Override
	public void onDisable() {
		writeInfo("[EasyRank] Successfully disabled!");
	}
	
	/**
	 * Sets up the executors.
	 */
	private void setUpExecutors() {
		ce = new ErCommandExecutor(this);
		getCommand("rank").setExecutor(ce);
	}
	
	 /**
 	 * Sets up the permissions.
 	 *
 	 * @return true, if successful
 	 */
 	private boolean setUpPermissions() {
	        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
	        perms = rsp.getProvider();
	        return perms != null;
	    }
 	
 	protected void setUpDependencies() {
 		try {
			setupPex();
		} catch (Exception e) {
			writeWarn("Failed to load PermissionsEx.");
			e.printStackTrace();
		}
		try {
			setupBperms();
		} catch (Exception e) {
			writeWarn("Failed to load bPermissions.");
			e.printStackTrace();
		}
 	}
 	protected void setupPex() {
 		final Plugin pex = getServer().getPluginManager().getPlugin("PermissionsEx");
 		if(pex == null) {
 		} else {
 			this.pex = (PermissionsEx) pex;
 			writeInfo("[EasyRank] Sucessfully hooked into PermissionsEx");
 		}
 	}
 	protected void setupBperms() {
 		final Plugin bperms = getServer().getPluginManager().getPlugin("bPermissions");
 		if(bperms == null) {		
 		} else {
 			this.bperms = (ApiLayer) bperms;
 			writeInfo("[EasyRank] Sucessfully hooked into bPermissions");
 		}
 		
 	}
 	
 	public Permission getVaultPerms()
 	{
 		return perms;
 	}
 	
 	public PermissionsEx getPEX()
 	{
 		return pex;
 	}
 	
 	public ApiLayer getbPerms()
 	{
 		return bperms;
 	}
	 
	 /**
 	 * Write info to console .
 	 *
 	 * @param info the info
 	 */
 	public static void writeInfo(String info) {
		 java.util.logging.Logger.getLogger("Minecraft").info(info);
	 }
	 
	 /**
 	 * Write warning to console .
 	 *
 	 * @param warn the warn
 	 */
 	public static void writeWarn(String warn) {
		 java.util.logging.Logger.getLogger("Minecraft").warning(warn);
	 }
}
