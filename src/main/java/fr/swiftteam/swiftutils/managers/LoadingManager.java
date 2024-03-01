package fr.swiftteam.swiftutils.managers;

import fr.swiftteam.swiftutils.Main;
import fr.swiftteam.swiftutils.commands.admin.CommandSwiftUtils;
import fr.swiftteam.swiftutils.utilities.ConsoleLogger;
import org.bukkit.Bukkit;

public class LoadingManager {


	public static boolean loadPlugin() {

		// INITIALIZE NMS
		ConsoleLogger.console("");
		ConsoleLogger.console("§7Implementing NMS...");

		if (Main.initializeNMSUtils()) {
			ConsoleLogger.console("§7Implementation §acomplete §7for §eNMS " + getServerVersion());

		} else {
			ConsoleLogger.console("§cAn error occurred during implementation for §6" + getServerVersion());
		}

		// CONFIGURATION AND MESSAGES FILES LOADING
		Main.getFilesManager().loadConfigurationFile();
		Main.getFilesManager().loadMessagesFile();

		// CHECKING CONFIGURATION FILE VERSION
		checkConfigurationFileVersion();

		// CHECKING MESSAGES FILE VERSION
		checkMessagesFileVersion();

		// COMMANDS REGISTRATION
		registerCommands();

		// MODULES ENABLING
		ModulesManager.enableModules();

		return true;
	}


	public static boolean reloadPlugin() {

		boolean reloadSuccess = true;

		// CONFIGURATION AND MESSAGES FILES LOADING
		Main.getFilesManager().loadConfigurationFile();
		Main.getFilesManager().loadMessagesFile();

		// CHECKING CONFIGURATION FILE VERSION
		if (!checkConfigurationFileVersion()) {
			reloadSuccess = false;
		}

		// CHECKING MESSAGES FILE VERSION
		if (!checkMessagesFileVersion()) {
			reloadSuccess = false;
		}

		// MODULES ENABLING
		ModulesManager.enableModules();

		return reloadSuccess;
	}


	private static void registerCommands() {

		ConsoleLogger.console("");
		ConsoleLogger.console("§7Commands registration...");

		Main.getInstance().getCommand("swiftUtils").setExecutor(new CommandSwiftUtils());

		ConsoleLogger.console("§7Commands registration §acomplete§7!");

	}


	private static boolean checkConfigurationFileVersion() {

		ConsoleLogger.console("");
		ConsoleLogger.console("§7Checking for configuration file version...");
		if (Main.getFilesManager().getConfigurationFile().getConfigVersion().equals(Main.getConfigurationFileVersion())) {
			ConsoleLogger.console("§7Your configuration file is §aup to date§7.");
			return true;

		} else {
			ConsoleLogger.console("§7A new version is §aavailable §7for your configuration file.");
			ConsoleLogger.console("§6Updating of the configuration file...");

			if (Main.getFilesManager().saveConfigurationFile()) {
				ConsoleLogger.console("§aYour configuration file has been updated.");
				return true;
			} else {
				ConsoleLogger.console("§cAn error has occurred while saving your current configuration file.");
				ConsoleLogger.console("§7- §ePlease save your current configuration file, delete it,");
				ConsoleLogger.console("§7- §eand finally restart the plugin to create a new one.");
				return false;
			}
		}
	}


	private static boolean checkMessagesFileVersion() {

		ConsoleLogger.console("");
		ConsoleLogger.console("§7Checking for message file version...");
		if (Main.getFilesManager().getMessagesFile().getMessagesVersion().equals(Main.getMessagesFileVersion())) {
			ConsoleLogger.console("§7Your messages file is §aup to date§7.");
			return true;

		} else {
			ConsoleLogger.console("§7A new version is §aavailable §7for your configuration file.");
			ConsoleLogger.console("§6Updating of the configuration file...");

			if (Main.getFilesManager().saveMessagesFile()) {
				ConsoleLogger.console("§aYour message file has been updated.");
				return true;
			} else {
				ConsoleLogger.console("§cAn error has occurred while saving your current message file.");
				ConsoleLogger.console("§7- §ePlease save your current message file, delete it,");
				ConsoleLogger.console("§7- §eand finally restart the plugin to create a new one.");
				return false;
			}
		}
	}


	public static String getServerVersion() {
		try {
			return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		} catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
			return null;
		}
	}
}
