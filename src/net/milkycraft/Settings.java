package net.milkycraft;

/**
 * The Class Settings.
 */
public class Settings extends ConfigLoader{
	
	/** The aroolp. */
	public static boolean aroolp;
	
	/**
	 * Instantiates a new settings.
	 *
	 * @param plugin the plugin
	 */
	public Settings(EasyRank plugin) {
		super(plugin, "config.yml");
		plugin = EasyRank.main;
		config = plugin.getConfig();
		saveIfNotExist();
	}
	
	/* (non-Javadoc)
	 * @see net.milkycraft.ConfigLoader#loadKeys()
	 */
	@Override
	protected void loadKeys() {
		aroolp = config.getBoolean("Easyrank.Allow.Ranking-Offline-Players");
	}
	
}
