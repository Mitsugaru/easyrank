package net.milkycraft;

import java.io.IOException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class Ranker extends EasyRank implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onLogin(PlayerLoginEvent e) {
		try {
			for(String n : Writer.getNames()) {
				if(e.getPlayer().getName() == n) {
					rank(n, e, Writer.getLineNumName(n));
					handleFiles(n, Writer.getRanks().get(Writer.getLineNumName(n)));
				}
			}
		} catch (Exception e1) {
			/*Dont warn because if they dont rank anyone offline this block gets called */
		}
	}
	private void rank(String nam, PlayerLoginEvent e, Integer index) {
		try {
			ErCommandExecutor.removeOldRanks(e.getPlayer());
			EasyRank.perms.playerAddGroup(e.getPlayer(), Writer.getRanks().get(index));
		} catch (IOException ex) {
			
		}
	}
	private void handleFiles(String name, String rank) {
		removeName(name);
		removeRank(rank);
	}
	private void removeName(String name) {
		try {
			Writer.removeIndexName(name);
		} catch(Exception ex) {			
		}
	}
	private void removeRank(String rank) {
		try {
			Writer.removeIndexName(rank);
		} catch(Exception ex) {			
		}
	}
}
