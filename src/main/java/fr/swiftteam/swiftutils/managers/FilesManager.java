package fr.swiftteam.swiftutils.managers;

import fr.swiftteam.swiftutils.Main;
import fr.swiftteam.swiftutils.utilities.files.ConfigurationFile;
import fr.swiftteam.swiftutils.utilities.files.LocationsFile;
import fr.swiftteam.swiftutils.utilities.files.MessagesFile;
import fr.swiftteam.swiftutils.utilities.files.modules.AnnouncementMessagesFile;
import fr.swiftteam.swiftutils.utilities.files.modules.PlayerSpawnFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilesManager {

	protected ConfigurationFile configurationFile;
	protected MessagesFile messagesFile;

	protected AnnouncementMessagesFile announcementMessagesFile;
	protected PlayerSpawnFile playerSpawnFile;

	protected LocationsFile locationsFile;


	public void loadConfigurationFile() {
		loadYAMLFile("configuration");
	}


	public void loadMessagesFile() {
		loadYAMLFile("messages");
	}


	public void loadLocationsFile() {
		loadYAMLFile("data/locations");
	}


	public void loadAnnouncementMessagesFile() {
		loadYAMLFile("modules/announcementMessages");
	}


	public boolean saveConfigurationFile() {
		return createBackupYAMLFile("configuration");
	}


	public boolean saveMessagesFile() {
		return createBackupYAMLFile("messages");
	}


	private void loadYAMLFile(String fileName) {

		File file = new File(Main.getInstance().getDataFolder(), fileName + ".yml");

		if (!file.exists()) {
			file.getParentFile().mkdirs();
			Main.getInstance().saveResource(fileName + ".yml", false);
		}

		YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

		switch (fileName) {
			case "configuration":
				configurationFile = new ConfigurationFile(yamlConfiguration);
				break;

			case "messages":
				messagesFile = new MessagesFile(yamlConfiguration);
				break;

			case "data/locations":
				locationsFile = new LocationsFile(yamlConfiguration);
				break;

			case "modules/announcementMessages":
				announcementMessagesFile = new AnnouncementMessagesFile(yamlConfiguration);
				break;
		}
	}


	private boolean createBackupYAMLFile(String fileName) {
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String formattedDate = dateFormat.format(currentDate);

		File fileToBackup = new File(Main.getInstance().getDataFolder(), fileName + ".yml");
		if (fileToBackup.exists()) {
			File backupFile = new File(fileToBackup.getParent(), formattedDate + "_" + fileName + ".yml");

			if (!backupFile.exists()) {
				try {
					boolean success = fileToBackup.renameTo(backupFile);
					if (success) {
						loadYAMLFile(fileName);
						return true;
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}

		} else {
			loadYAMLFile(fileName);
		}
		return false;
	}


	public ConfigurationFile getConfigurationFile() {
		return configurationFile;
	}

	public MessagesFile getMessagesFile() {
		return messagesFile;
	}


	public LocationsFile getLocationsFile() {
		return locationsFile;
	}


	public AnnouncementMessagesFile getAnnouncementMessagesFile() {
		return announcementMessagesFile;
	}

	public PlayerSpawnFile getPlayerSpawnFile() {
		return playerSpawnFile;
	}
}
