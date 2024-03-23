package fr.swiftteam.swiftutils.utilities.files;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigurationFile {

	private final YamlConfiguration yamlConfiguration;


	public ConfigurationFile(YamlConfiguration yamlConfiguration) {
		this.yamlConfiguration = yamlConfiguration;
	}


	public String getConfigVersion() {
		String version = yamlConfiguration.getString("config-version");
		if (version != null) {
			return version;
		}
		return "0.0";
	}


	public String getCommandPermission(String commandName) {
		return yamlConfiguration.getString("permissions.commands." + commandName);
	}


	public boolean isModuleEnabled(String moduleName) {
		return yamlConfiguration.getBoolean("modules." + moduleName);
	}
}
