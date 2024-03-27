package fr.swiftteam.swiftutils;

import fr.swiftteam.swiftutils.managers.LoadingManager;
import fr.swiftteam.swiftutils.managers.ModulesManager;
import fr.swiftteam.swiftutils.nms.NMSUtils;
import fr.swiftteam.swiftutils.nms.NMS_v1_8_R3;
import fr.swiftteam.swiftutils.utilities.ConsoleLogger;
import fr.swiftteam.swiftutils.managers.FilesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin {

	private static Main instance;
	private static ConsoleLogger consoleLogger;

	private static FilesManager filesManager;
	private static ModulesManager modulesManager;
	private static LoadingManager loadingManager;

	private static BukkitScheduler bukkitScheduler;
	private static NMSUtils nmsUtils;

	private static final String latestConfigurationFileVersion = "1.0";
	private static final String latestMessagesFileVersion = "1.0";


	@Override
	public void onEnable() {

		instance = this;

		consoleLogger = new ConsoleLogger();

		filesManager = new FilesManager();
		modulesManager = new ModulesManager();
		loadingManager = new LoadingManager();

		bukkitScheduler = this.getServer().getScheduler();

		// PLUGIN NOW LOADING
		consoleLogger.console("§7The plugin is now §6loading§7...");
		consoleLogger.console("§7Version: §6" + getDescription().getVersion());

		if (loadingManager.loadPlugin()) {
			// PLUGIN STARTED SUCCESSFULLY
			consoleLogger.console("");
			consoleLogger.console("§7The plugin has been §aloaded §7successfully!");

		} else {
			// PLUGIN NOT STARTED, AND WITH A PROBLEM
			this.onDisable();
		}
	}


	@Override
	public void onDisable() {

		consoleLogger.console("§7The plugin is now §6disabling§7...");

		modulesManager.disableModules();

		consoleLogger.console("");
		consoleLogger.console("§7The plugin has been §adisabled §7successfully!");
	}


	public static boolean initializeNMSUtils() {

		String serverVersion = loadingManager.getServerVersion();
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


	public static ConsoleLogger getConsoleLogger() {
		return consoleLogger;
	}


	public static FilesManager getFilesManager() {
		return filesManager;
	}


	public static ModulesManager getModulesManager() {
		return modulesManager;
	}


	public static LoadingManager getLoadingManager() {
		return loadingManager;
	}


	public static String getConfigurationFileVersion() {
		return latestConfigurationFileVersion;
	}


	public static String getMessagesFileVersion() {
		return latestMessagesFileVersion;
	}


	public static BukkitScheduler getScheduler() {
		return bukkitScheduler;
	}


	public static NMSUtils getNmsUtils() {
		return nmsUtils;
	}
}