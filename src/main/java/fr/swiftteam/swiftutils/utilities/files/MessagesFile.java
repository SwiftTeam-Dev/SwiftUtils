package fr.swiftteam.swiftutils.utilities.files;

import org.bukkit.configuration.file.YamlConfiguration;

public class MessagesFile {

	private final YamlConfiguration yamlConfiguration;


	public MessagesFile(YamlConfiguration yamlConfiguration) {
		this.yamlConfiguration = yamlConfiguration;
	}


	public String getMessagesVersion() {
		String version = yamlConfiguration.getString("messages-version");
		if (version != null) {
			return version;
		}
		return "0.0";
	}


	public String getPrefix() {
		return yamlConfiguration.getString("prefix").replace("&", "§");
	}


	public String getError(String errorName) {
		return yamlConfiguration.getString("errors." + errorName).replace("&", "§");
	}
}
