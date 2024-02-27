package fr.swiftteam.swiftutils.utilities.files;

import org.bukkit.configuration.Configuration;

public class MessagesFile {

	private final Configuration yamlFile;


	public MessagesFile(Configuration yamlFile) {
		this.yamlFile = yamlFile;
	}


	public String getMessagesVersion() {
		return yamlFile.getString("messages-version");
	}


	public String getPrefix() {
		return yamlFile.getString("prefix").replace("&", "§");
	}


	public String getError(String errorName) {
		return yamlFile.getString("errors." + errorName).replace("&", "§");
	}
}
