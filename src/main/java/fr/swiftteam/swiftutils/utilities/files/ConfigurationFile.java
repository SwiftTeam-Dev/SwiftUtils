package fr.swiftteam.swiftutils.utilities.files;

import org.bukkit.configuration.Configuration;

public class ConfigurationFile {

	private final Configuration yamlFile;


	public ConfigurationFile(Configuration yamlFile) {
		this.yamlFile = yamlFile;
	}


	public String getConfigVersion() {
		return yamlFile.getString("config-version");
	}


	public String getCommandPermission(String commandName) {
		return yamlFile.getString("permissions.commands." + commandName);
	}


	public boolean isModuleEnabled(String moduleName) {
		return yamlFile.getBoolean("modules." + moduleName);
	}
}
