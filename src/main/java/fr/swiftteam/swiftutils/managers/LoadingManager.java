package fr.swiftteam.swiftutils.managers;

import fr.swiftteam.swiftutils.Main;
import fr.swiftteam.swiftutils.commands.admin.CommandSwiftUtils;
import org.bukkit.Bukkit;

public class LoadingManager {


	public boolean loadPlugin() {

		// INITIALIZE NMS
		Main.getConsoleLogger().console("");
		Main.getConsoleLogger().console("§7Implementing NMS...");

		if (Main.initializeNMSUtils()) {
			Main.getConsoleLogger().console("§7Implementation §acomplete §7for §eNMS " + getServerVersion());

		} else {
			Main.getConsoleLogger().console("§cAn error occurred during implementation for §6" + getServerVersion());
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
		Main.getModulesManager().enableModules();

		return true;
	}


	public boolean reloadPlugin() {

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
		Main.getModulesManager().enableModules();

		return reloadSuccess;
	}


	private void registerCommands() {

		Main.getConsoleLogger().console("");
		Main.getConsoleLogger().console("§7Commands registration...");

		Main.getInstance().getCommand("swiftUtils").setExecutor(new CommandSwiftUtils());

		Main.getConsoleLogger().console("§7Commands registration §acomplete§7!");

	}


	private boolean checkConfigurationFileVersion() {

		Main.getConsoleLogger().console("");
		Main.getConsoleLogger().console("§7Checking for configuration file version...");
		if (Main.getFilesManager().getConfigurationFile().getConfigVersion().equals(Main.getConfigurationFileVersion())) {
			Main.getConsoleLogger().console("§7Your configuration file is §aup to date§7.");
			return true;

		} else {
			Main.getConsoleLogger().console("§7A new version is §aavailable §7for your configuration file.");
			Main.getConsoleLogger().console("§6Updating of the configuration file...");

			if (Main.getFilesManager().saveConfigurationFile()) {
				Main.getConsoleLogger().console("§aYour configuration file has been updated.");
				return true;
			} else {
				Main.getConsoleLogger().console("§cAn error has occurred while saving your current configuration file.");
				Main.getConsoleLogger().console("§7- §ePlease save your current configuration file, delete it,");
				Main.getConsoleLogger().console("§7- §eand finally restart the plugin to create a new one.");
				return false;
			}
		}
	}


	private boolean checkMessagesFileVersion() {

		Main.getConsoleLogger().console("");
		Main.getConsoleLogger().console("§7Checking for message file version...");
		if (Main.getFilesManager().getMessagesFile().getMessagesVersion().equals(Main.getMessagesFileVersion())) {
			Main.getConsoleLogger().console("§7Your messages file is §aup to date§7.");
			return true;

		} else {
			Main.getConsoleLogger().console("§7A new version is §aavailable §7for your configuration file.");
			Main.getConsoleLogger().console("§6Updating of the configuration file...");

			if (Main.getFilesManager().saveMessagesFile()) {
				Main.getConsoleLogger().console("§aYour message file has been updated.");
				return true;
			} else {
				Main.getConsoleLogger().console("§cAn error has occurred while saving your current message file.");
				Main.getConsoleLogger().console("§7- §ePlease save your current message file, delete it,");
				Main.getConsoleLogger().console("§7- §eand finally restart the plugin to create a new one.");
				return false;
			}
		}
	}


	public String getServerVersion() {
		try {
			return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		} catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
			return null;
		}
	}
}
