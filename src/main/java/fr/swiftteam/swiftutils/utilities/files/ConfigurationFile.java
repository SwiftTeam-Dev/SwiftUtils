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


	public String getPermission(String commandName) {
		return yamlFile.getString("commands." + commandName);
	}
}
