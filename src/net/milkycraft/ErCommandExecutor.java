package net.milkycraft;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import ru.tehkode.permissions.bukkit.commands.PermissionsCommand;

/**
 * The Class ErCommandExecutor.
 */
public class ErCommandExecutor extends PermissionsCommand implements CommandExecutor {
	
	/** The plugin. */
	private static EasyRank plugin;
	
	/**
	 * Instantiates a new er command executor.
	 *
	 * @param plugin the plugin
	 */
	public ErCommandExecutor(EasyRank plugin) {
		ErCommandExecutor.plugin = plugin;
	}

	/* /rank player group */
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] split) {
		if (sender instanceof ConsoleCommandSender) {
			handleConsoleCommand(command, commandLabel, split);
			return true;
		}
		if (split.length == 2) {
			if ((plugin.getServer().getPlayer(split[0]) == null)
					&& (Settings.aroolp)) {
				rankOfflinePlayer(sender, split);
				return true;
			}
			final Player target = plugin.getServer().getPlayer(split[0]);
			final String PlayerName = sender.getName();
			final String carget = plugin.getServer().getPlayer(split[0])
					.getDisplayName();
			final String rank = Character.toUpperCase(split[1].charAt(0))
					+ split[1].substring(1);
			if (!sender.hasPermission("easyrank." + split[1].toLowerCase())
					|| !sender.isOp()) {
				sender.sendMessage(ChatColor.RED
						+ "You dont have permission to rank to " + rank);
				return true;
			}
			if (plugin.getVaultPerms().getPrimaryGroup(target).equalsIgnoreCase(rank)) {
				sender.sendMessage(ChatColor.RED + carget + " is already a "
						+ rank);
				return true;
			}
			removeOldRanks(target);
			plugin.getVaultPerms().playerAddGroup(target, rank);
			sender.sendMessage(ChatColor.GRAY + "Sucessfully ranked "
					+ ChatColor.YELLOW + carget + ChatColor.GRAY + " to "
					+ ChatColor.GOLD + rank);
			target.sendMessage(ChatColor.GRAY
					+ plugin.getServer().getPlayer(PlayerName).getDisplayName()
					+ " ranked you to " + ChatColor.YELLOW + rank);

		} else {
			sender.sendMessage(ChatColor.RED + "Use: /rank player group");
			return true;
		}
		return false;
	}

	/**
	 * Removes the old ranks.
	 *
	 * @param player the player
	 */
	protected static void removeOldRanks(Player player) {
		for (String r : plugin.getVaultPerms().getPlayerGroups(player)) {
			plugin.getVaultPerms().playerRemoveGroup(player, r);
		}

	}

	/**
	 * Rank offline player.
	 *
	 * @param sender the sender
	 * @param split the split
	 */
	@SuppressWarnings("unused")
	private void rankOfflinePlayer(CommandSender sender, String[] split) {
		final Player target = plugin.getServer().getOfflinePlayer(split[0])
				.getPlayer();
		final String carget = plugin.getServer().getOfflinePlayer(split[0])
				.getName();
		final String rank = Character.toUpperCase(split[1].charAt(0))
				+ split[1].substring(1);
		removeOldRanks(target);
		if(plugin.getPEX() != null) {
			groupUsersAdd(plugin, sender, split);
		} else {
			/*bPermissions rank*/
		}		
	}
	public void groupUsersAdd(Plugin plugin, CommandSender sender, String[] args) {
		String groupName = this.autoCompleteGroupName(args[1]);
		String worldName = this.autoCompleteWorldName(((Player) sender).getWorld().getName());
			String userName = this.autoCompletePlayerName(args[0]);
			PermissionUser user = PermissionsEx.getPermissionManager().getUser(userName);

			if (user == null) {
				sender.sendMessage(ChatColor.RED + "User does not exist");
				return;
			}
			user.addGroup(groupName, worldName);
			sender.sendMessage(ChatColor.WHITE + "User " + user.getName() + " added to " + groupName + " !");
			this.informPlayer(plugin, userName, "You are assigned to \"" + groupName + "\" group");
	}
	/**
	 * Handle console command.
	 *
	 * @param command the command
	 * @param commandLabel the command label
	 * @param split the split
	 * @return true, if successful
	 */
	private boolean handleConsoleCommand(Command command, String commandLabel,
			String[] split) {
		if (split.length == 2) {
			try {
				final Player target = plugin.getServer().getPlayer(split[0]);
				final String carget = plugin.getServer().getPlayer(split[0])
						.getDisplayName();
				final String rank = Character.toUpperCase(split[1].charAt(0))
						+ split[1].substring(1);
				if (plugin.getVaultPerms().getPrimaryGroup(target).equalsIgnoreCase(rank)) {
					EasyRank.writeInfo(carget + " is already a " + rank);
					return true;
				}
				removeOldRanks(target);
				plugin.getVaultPerms().playerAddGroup(target, rank);

				if (plugin.getVaultPerms().getPrimaryGroup(target).equalsIgnoreCase(rank)) {
					EasyRank.writeInfo("Sucessfully ranked " + carget + " to " + rank);
					target.sendMessage(ChatColor.GRAY
							+ "Console ranked you to " + ChatColor.YELLOW
							+ rank);
				}
			} catch (NullPointerException e) {
				if (Settings.aroolp) {
					final Player target = plugin.getServer()
							.getOfflinePlayer(split[0]).getPlayer();
					final String carget = plugin.getServer()
							.getOfflinePlayer(split[0]).getName();
					final String rank = Character.toUpperCase(split[1]
							.charAt(0)) + split[1].substring(1);
					removeOldRanks(target);
					plugin.getVaultPerms().playerAddGroup(target, rank);
					EasyRank.writeInfo("Ranked " + carget + " to " + rank);
				}
			}

		} else {
			EasyRank.writeInfo("Use: /rank player group");
			return true;
		}
		return false;
	}
}
