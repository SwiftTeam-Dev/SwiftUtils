package fr.swiftteam.swiftutils.managers;

import fr.swiftteam.swiftutils.Main;
import fr.swiftteam.swiftutils.modules.AnnouncementMessages;
import fr.swiftteam.swiftutils.utilities.ConsoleLogger;

public class ModulesManager {


	public static void enableModules() {

		ConsoleLogger.console("");
		ConsoleLogger.console("§7The modules are now §6enabling§7...");

		if (Main.getConfigurationFile().isModuleEnabled("announcementMessages")) {
			if (AnnouncementMessages.loadAnnouncementMessagesModule()) {
				ConsoleLogger.console("§8- §fAnnouncementMessages§7: §aLoaded successfully!");

			} else {
				ConsoleLogger.console("§8- §fAnnouncementMessages§7: §cError during loading!");
			}

		} else {
			ConsoleLogger.console("§8- §fAnnouncementMessages§7: §6Disabled in config.");
		}

		ConsoleLogger.console("§7The modules are now §aenabled§7!");
	}
}
