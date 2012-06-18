package net.milkycraft;

public class Settings extends ConfigLoader{
	public static boolean aroolp;
	public Settings(EasyRank plugin) {
		super(plugin, "config.yml");
		plugin = EasyRank.main;
		config = plugin.getConfig();
		saveIfNotExist();
	}
	
	@Override
	protected void loadKeys() {
		aroolp = config.getBoolean("Easyrank.Allow.Ranking-Offline-Players");
	}
	
}
