package fr.swiftteam.swiftutils;

import fr.swiftteam.swiftutils.managers.LoadingManager;
import fr.swiftteam.swiftutils.modules.AnnouncementMessages.ModuleManagerAM;
import fr.swiftteam.swiftutils.nms.NMSUtils;
import fr.swiftteam.swiftutils.nms.NMS_v1_8_R3;
import fr.swiftteam.swiftutils.utilities.ConsoleLogger;
import fr.swiftteam.swiftutils.utilities.files.ConfigurationFile;
import fr.swiftteam.swiftutils.utilities.files.MessagesFile;
import fr.swiftteam.swiftutils.utilities.files.FilesManager;
import fr.swiftteam.swiftutils.utilities.files.modules.AnnouncementMessagesFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin {

	private static Main instance;
	private static FilesManager filesManager;
	private static BukkitScheduler bukkitScheduler;
	private static NMSUtils nmsUtils;

	private static final String configurationFileVersion = "1.1";
	private static final String messagesFileVersion = "1.0";


	@Override
	public void onEnable() {

		instance = this;
		filesManager = new FilesManager();
		bukkitScheduler = this.getServer().getScheduler();

		// PLUGIN NOW LOADING
		ConsoleLogger.console("§7The plugin is now §6loading§7...");

		if (LoadingManager.loadPlugin()) {
			// PLUGIN STARTED SUCCESSFULLY
			ConsoleLogger.console("");
			ConsoleLogger.console("§7The plugin has been §aloaded §7successfully!");

		} else {
			// PLUGIN NOT STARTED, AND WITH A PROBLEM
			this.onDisable();
		}
	}


	@Override
	public void onDisable() {

		ConsoleLogger.console("§7The plugin is now §6disabling§7...");

		ModuleManagerAM.stopLoadedAnnouncements();

		ConsoleLogger.console("");
		ConsoleLogger.console("§7The plugin has been §adisabled §7successfully!");
	}


	public static boolean initializeNMSUtils() {

		String serverVersion = LoadingManager.getServerVersion();
		if (serverVersion == null) {
			return false;
		}

		if (serverVersion.equals("v1_8_R3")) {
			nmsUtils = new NMS_v1_8_R3();
		}
		return nmsUtils != null;
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


	public static AnnouncementMessagesFile getAnnouncementMessagesFile() {
		return filesManager.getAnnouncementMessagesFile();
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


	public static BukkitScheduler getScheduler() {
		return bukkitScheduler;
	}


	public static NMSUtils getNmsUtils() {
		return nmsUtils;
	}
}