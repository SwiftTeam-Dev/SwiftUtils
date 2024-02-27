package fr.swiftteam.swiftutils;

import fr.swiftteam.swiftutils.commands.admin.CommandSwiftUtils;
import fr.swiftteam.swiftutils.utilities.ConsoleLogger;
import fr.swiftteam.swiftutils.utilities.files.ConfigurationFile;
import fr.swiftteam.swiftutils.utilities.files.MessagesFile;
import fr.swiftteam.swiftutils.utilities.files.FilesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main instance;
	private static FilesManager filesManager;

	private static final String configurationFileVersion = "1.0";
	private static final String messagesFileVersion = "1.0";


	@Override
	public void onEnable() {

		instance = this;
		filesManager = new FilesManager();

		ConsoleLogger.console("§7The plugin is now §6loading§7...");

		filesManager.reloadConfigurationFile();
		filesManager.reloadMessagesFile();

		ConsoleLogger.console("");
		ConsoleLogger.console("§7Checking for configuration file version...");
		if (filesManager.getConfigurationFile().getConfigVersion().equals(configurationFileVersion)) {
			ConsoleLogger.console("§7Your configuration file is §aup to date§7.");

		} else {
			ConsoleLogger.console("§7A new version is §aavailable §7for your configuration file.");
			ConsoleLogger.console("§6Updating of the configuration file...");

			if (filesManager.saveConfigurationFile()) {
				ConsoleLogger.console("§aYour configuration file has been updated.");
			} else {
				ConsoleLogger.console("§cAn error has occurred while saving your current configuration file.");
				ConsoleLogger.console("§7- §ePlease save your current configuration file, delete it,");
				ConsoleLogger.console("§7- §eand finally restart the plugin to create a new one.");
			}
		}

		ConsoleLogger.console("");
		ConsoleLogger.console("§7Checking for message file version...");
		if (filesManager.getMessagesFile().getMessagesVersion().equals(messagesFileVersion)) {
			ConsoleLogger.console("§7Your messages file is §aup to date§7.");

		} else {
			ConsoleLogger.console("§7A new version is §aavailable §7for your configuration file.");
			ConsoleLogger.console("§6Updating of the configuration file...");

			if (filesManager.saveMessagesFile()) {
				ConsoleLogger.console("§aYour message file has been updated.");
			} else {
				ConsoleLogger.console("§cAn error has occurred while saving your current message file.");
				ConsoleLogger.console("§7- §ePlease save your current message file, delete it,");
				ConsoleLogger.console("§7- §eand finally restart the plugin to create a new one.");
			}
		}

		getCommand("swiftUtils").setExecutor(new CommandSwiftUtils());

		ConsoleLogger.console("");
		ConsoleLogger.console("§7The plugin has been §aloaded §7successfully!");
	}


	@Override
	public void onDisable() {

		ConsoleLogger.console("§7The plugin is now §6disabling§7...");

		ConsoleLogger.console("");
		ConsoleLogger.console("§7The plugin has been §adisabled §7successfully!");
	}


	public static Main getInstance() {
		return instance;
	}


	public static ConfigurationFile getConfigurationFile() {
		return filesManager.getConfigurationFile();
	}


	public static MessagesFile getMessagesFile() {
		return filesManager.getMessagesFile();
	}


	public static FilesManager getFilesManager() {
		return filesManager;
	}


	public static String getConfigurationFileVersion() {
		return configurationFileVersion;
	}


	public static String getMessagesFileVersion() {
		return messagesFileVersion;
	}
}