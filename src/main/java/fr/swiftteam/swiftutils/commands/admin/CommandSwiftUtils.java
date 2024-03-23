package fr.swiftteam.swiftutils.commands.admin;

import fr.swiftteam.swiftutils.Main;
import fr.swiftteam.swiftutils.managers.ModulesManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandSwiftUtils implements CommandExecutor, TabCompleter {


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		final String prefix = Main.getFilesManager().getMessagesFile().getPrefix();

		if (!(sender instanceof Player player)) {
			sendAboutMessageToConsole(sender);

		} else {

			// SEND ABOUT MESSAGE TO PLAYER WITHOUT PERMISSION
			if (!player.hasPermission(Main.getFilesManager().getConfigurationFile().getCommandPermission("swiftutils"))) {
				sendAboutMessageToPlayer(player);

			} else {

				// COMMAND WITHOUT ANY ARGUMENT
				if (args.length < 1) {
					sendHelpMessage(player, 1);

				} else {

					String firstArgument = args[0];

					// HELP MESSAGE
					if (firstArgument.equalsIgnoreCase("help")) {

						if (args.length < 2) {
							sendHelpMessage(player, 1);

						} else {
							String secondArgument = args[1];
							try {
								int pageNumber = Integer.parseInt(secondArgument);
								sendHelpMessage(player, pageNumber);

							} catch (NumberFormatException e) {
								player.sendMessage(prefix + Main.getFilesManager().getMessagesFile().getError("argumentMustBeNumber"));
							}
						}

						// ABOUT MESSAGE
					} else if (firstArgument.equalsIgnoreCase("about")) {
						sendAboutMessageToPlayer(player);

						// VERSIONS MESSAGE
					} else if (firstArgument.equalsIgnoreCase("versions")) {
						sendVersionMessage(player);

						// RELOAD FILES
					} else if (firstArgument.equalsIgnoreCase("reload")) {
						reloadPlugin(player);
					}
				}
			 }
		}
		return false;
	}


	private void sendAboutMessageToConsole(CommandSender sender) {
		sender.sendMessage("");
		sender.sendMessage("§8» §6Sorry, but the console can't use this command. Please join the server");
		sender.sendMessage("  §6with a suffisant permission in order to use this command.");
		sender.sendMessage("");
		sender.sendMessage("§8» §7This plugin was developed by the §b§lSwift§6§lTeam §8(§7§ohttps://github.com/SwiftTeam-Dev§8)§7.");
		sender.sendMessage("  §7Version: §e" + Main.getInstance().getDescription().getVersion());
		sender.sendMessage("  §7Source code: §6§ohttps://github.com/SwiftTeam-Dev/SwiftUtils");
		sender.sendMessage("");
	}


	private void sendAboutMessageToPlayer(CommandSender sender) {

		Player player = (Player) sender;

		TextComponent part1 = new TextComponent("§8» §7This plugin was developed by the ");
		TextComponent partClickable = new TextComponent("§b§lSwift§6§lTeam");
		TextComponent part2 = new TextComponent("§7.");

		ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/SwiftTeam-Dev");
		partClickable.setClickEvent(clickEvent);

		HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[] {
				new TextComponent("§f§lGitHub Profile")
		});
		partClickable.setHoverEvent(hoverEvent);

		sender.sendMessage("");
		player.spigot().sendMessage(part1, partClickable, part2);
		sender.sendMessage("   §7Version: §e" + Main.getInstance().getDescription().getVersion());
		sender.sendMessage("   §7Source code: §6§ohttps://github.com/SwiftTeam-Dev/SwiftUtils");
		sender.sendMessage("");
	}


	private void sendHelpMessage(CommandSender sender, int pageNumber) {

		final String prefix = Main.getFilesManager().getMessagesFile().getPrefix();

		if (pageNumber == 1) {
			sender.sendMessage("§8§m-----------------------------------");
			sender.sendMessage("");
			sender.sendMessage(" §8» §b§lSwift§6§lUtils §8- §7Current page: §e1§8/§61");
			sender.sendMessage("");
			sender.sendMessage("    §e/su help §6 §6[page] §8- §7See the help message.");
			sender.sendMessage("    §e/su about §8- §7See the about message.");
			sender.sendMessage("    §e/su versions §8- §7See different versions of files and plugin.");
			sender.sendMessage("    §e/su reload §8- §7Reload configuration and messages files.");
			sender.sendMessage("");
			sender.sendMessage("§8§m-----------------------------------");

		} else {
			sender.sendMessage(prefix + Main.getFilesManager().getMessagesFile().getError("helpPageNotFound"));
		}
	}


	private void sendVersionMessage(CommandSender sender) {

		final String prefix = Main.getFilesManager().getMessagesFile().getPrefix();

		sender.sendMessage(prefix + "§7This plugin is running §3v" + Main.getInstance().getDescription().getVersion());
		sender.sendMessage(prefix + "§7Configuration File's Version: §ev" + Main.getConfigurationFileVersion());
		sender.sendMessage(prefix + "§7Messages File's Version: §6v" + Main.getMessagesFileVersion());
	}


	private void reloadPlugin(CommandSender sender) {

		final String prefix = Main.getFilesManager().getMessagesFile().getPrefix();

		Instant reloadingStartInstant = Instant.now();
		sender.sendMessage(prefix + "§7Reloading SwiftUtils...");

		boolean reloadSuccess = Main.getLoadingManager().reloadPlugin();

		Instant reloadingEndInstant = Instant.now();
		long milliseconds = Duration.between(reloadingStartInstant, reloadingEndInstant).toMillis();
		sender.sendMessage(prefix + "§7Reloading SwiftUtils §acomplete§7! §8(§e" + milliseconds + "ms§8)");

		if (!reloadSuccess) {
			sender.sendMessage(prefix + "§cUnfortunately, a problem occurred during reloading. Please check the console.");
		}
	}


	// TAB COMPLETER
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

		List<String> options = new ArrayList<>();

		List<String> firstArguments = Arrays.asList("help", "about", "versions", "reload");

		if (args.length == 1) {
			String input = args[0].toLowerCase();

			for (String option : firstArguments) {
				if (option.startsWith(input)) {
					options.add(option);
				}
			}

		} else if (args.length == 2) {
			String firstArg = args[0];

			if (firstArg.equalsIgnoreCase("help")) {
				options.add("1");
			}
		}
		return options;
	}
}
