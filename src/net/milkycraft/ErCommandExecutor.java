package net.milkycraft;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ErCommandExecutor extends EasyRank implements CommandExecutor {
	private final EasyRank plugin;

	public ErCommandExecutor(EasyRank plugin) {
		this.plugin = plugin;
	}

	/* /rank player group */
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] split) {
		if (sender instanceof ConsoleCommandSender) {
			handleConsoleCommand(command, commandLabel, split);
			return true;
		}
		if(split.length == 1 && split[0].equalsIgnoreCase("write")) {
			if(sender.hasPermission("easyrank.write")) {
				Writer.writeNames();
				Writer.writeRanks();
				sender.sendMessage("Current arraylist written to file");
				return true;
			}
		}
		if (split.length == 2) {
			if(plugin.getServer().getPlayer(split[0]) == null) {
				rankOfflinePlayer(sender, split);
				return true;
			}
				final Player target = plugin.getServer().getPlayer(split[0]);
				final String PlayerName = sender.getName();
				final String carget = plugin.getServer().getPlayer(split[0])
						.getDisplayName();
				final String rank = Character.toUpperCase(split[1].charAt(0))
						+ split[1].substring(1);
				if(!sender.hasPermission("easyrank." + split[1].toLowerCase()) || sender.isOp()) {
					sender.sendMessage(ChatColor.RED + "You dont have permission to rank to " + rank); 
					return true;
				}
				if (EasyRank.perms.getPrimaryGroup(target).equalsIgnoreCase(
						rank)) {
					sender.sendMessage(ChatColor.RED + carget
							+ " is already a " + rank);
					return true;
				}
				removeOldRanks(target);
				EasyRank.perms.playerAddGroup(target, rank);
				sender.sendMessage(ChatColor.GRAY + "Sucessfully ranked "
						+ ChatColor.YELLOW + carget + ChatColor.GRAY + " to "
						+ ChatColor.GOLD + rank);
				target.sendMessage(ChatColor.GRAY
						+ plugin.getServer().getPlayer(PlayerName)
								.getDisplayName() + " ranked you to "
						+ ChatColor.YELLOW + rank);

		} else {
			sender.sendMessage(ChatColor.RED + "Use: /rank player group");
			return true;
		}
		return false;
	}

	protected static void removeOldRanks(Player player) {
		for (String r : EasyRank.perms.getPlayerGroups(player)) {
			EasyRank.perms.playerRemoveGroup(player, r);
		}

	}
	private void rankOfflinePlayer(CommandSender sender, String[] split) {
		final String carget = plugin.getServer()
				.getOfflinePlayer(split[0]).getName();
		final String rank = Character.toUpperCase(split[1].charAt(0))
				+ split[1].substring(1);
		addToArrayList(split);
			sender.sendMessage(ChatColor.GRAY + carget + " will be ranked to " + rank + " when they log in");		
	}

	private void addToArrayList(String[] split) {
		final String rankName = Character.toUpperCase(split[1].charAt(0))
				+ split[1].substring(1);
		if(EasyRank.rank == null) {
			rank = new ArrayList<String>();
		}
		if(EasyRank.name == null) {
			name = new ArrayList<String>();
		}
		EasyRank.rank.add(rankName);
		EasyRank.name.add(split[0]);
	}

	private boolean handleConsoleCommand(Command command, String commandLabel,
			String[] split) {
		if (split.length == 2) {
			try {
				final Player target = plugin.getServer().getPlayer(split[0]);
				final String carget = plugin.getServer().getPlayer(split[0])
						.getDisplayName();
				final String rank = Character.toUpperCase(split[1].charAt(0))
						+ split[1].substring(1);
				if (EasyRank.perms.getPrimaryGroup(target).equalsIgnoreCase(
						rank)) {
					writeInfo(carget + " is already a " + rank);
					return true;
				}
				removeOldRanks(target);
				EasyRank.perms.playerAddGroup(target, rank);

				if (EasyRank.perms.getPrimaryGroup(target).equalsIgnoreCase(
						rank)) {
					writeInfo("Sucessfully ranked " + carget + " to " + rank);
					target.sendMessage(ChatColor.GRAY
							+ "Console ranked you to " + ChatColor.YELLOW
							+ rank);
				}
			} catch (NullPointerException e) {
				final String carget = plugin.getServer()
						.getOfflinePlayer(split[0]).getName();
				final String rank = Character.toUpperCase(split[1].charAt(0))
						+ split[1].substring(1);
				addToArrayList(split);
					writeInfo(carget + " will be ranked to " + rank + " when they log in");		
			}

		} else {
			writeInfo("Use: /rank player group");
			return true;
		}
		return false;
	}
}
