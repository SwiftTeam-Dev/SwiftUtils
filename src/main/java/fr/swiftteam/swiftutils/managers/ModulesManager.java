package fr.swiftteam.swiftutils.managers;

import fr.swiftteam.swiftutils.Main;
import fr.swiftteam.swiftutils.modules.announcementMessages.ModuleManagerAM;
import fr.swiftteam.swiftutils.modules.playerSpawn.ModuleManagerPS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModulesManager {

	private final ModuleManagerAM moduleManagerAM = new ModuleManagerAM();
	private final ModuleManagerPS moduleManagerPS = new ModuleManagerPS();

	private Map<String, Boolean> enabledModules = new HashMap<>();


	public static List<String> getModules() {
		return List.of(
				"announcementMessages",
				"playerSpawn");
	}


	public void enableModules() {

		Main.getConsoleLogger().console("");
		Main.getConsoleLogger().console("§7The modules are now §6enabling§7...");

		// LOAD ANNOUNCEMENT MESSAGES MODULE

		if (Main.getFilesManager().getConfigurationFile().isModuleEnabled("announcementMessages")) {
			if (getAM().loadAnnouncementMessagesModule()) {
				Main.getConsoleLogger().console("§8- §fAnnouncementMessages§7: §aLoaded successfully!");

			} else {
				Main.getConsoleLogger().console("§8- §fAnnouncementMessages§7: §cError during loading!");
			}

		} else {
			Main.getConsoleLogger().console("§8- §fAnnouncementMessages§7: §6Disabled in §econfiguration.yml§6.");
		}

		// LOAD PLAYER SPAWN MODULE

		if (Main.getFilesManager().getConfigurationFile().isModuleEnabled("playerSpawn")) {
			if (getPS().loadPlayerSpawnModule()) {
				Main.getConsoleLogger().console("§8- §fPlayerSpawn§7: §aLoaded successfully!");

			} else {
				Main.getConsoleLogger().console("§8- §fPlayerSpawn§7: §cError during loading!");
			}

		} else {
			Main.getConsoleLogger().console("§8- §fPlayerSpawn§7: §6Disabled in §econfiguration.yml§6.");
		}

		// END LOADING

		Main.getConsoleLogger().console("§7The modules are now §aenabled§7!");
	}


	public void disableModules() {

		getAM().unloadAnnouncementMessagesModule();
		getPS().unloadPlayerSpawnModule();
	}


	public void setModuleEnable(String moduleName) {
		enabledModules.put(moduleName, true);
	}


	public void setModuleDisable(String moduleName) {
		enabledModules.put(moduleName, false);
	}


	public boolean isEnabled(String moduleName) {
		return enabledModules.get(moduleName);
	}


	public ModuleManagerAM getAM() {
		return moduleManagerAM;
	}

	public ModuleManagerPS getPS() {
		return moduleManagerPS;
	}
}
