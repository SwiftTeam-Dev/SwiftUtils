package fr.swiftteam.swiftutils.utilities.files;

import fr.swiftteam.swiftutils.Main;
import fr.swiftteam.swiftutils.utilities.files.modules.AnnouncementMessagesFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilesManager {

	protected ConfigurationFile configurationFile;
	protected MessagesFile messagesFile;
	protected AnnouncementMessagesFile announcementMessagesFile;


	public void loadConfigurationFile() {
		loadYAMLFile("configuration");
	}


	public void loadMessagesFile() {
		loadYAMLFile("messages");
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

		if (fileName.equals("configuration")) {
			configurationFile = new ConfigurationFile(yamlConfiguration);

		} else if (fileName.equals("messages")) {
			messagesFile = new MessagesFile(yamlConfiguration);

		} else if (fileName.equals("modules/announcementMessages")) {
			announcementMessagesFile = new AnnouncementMessagesFile(yamlConfiguration);
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


	public AnnouncementMessagesFile getAnnouncementMessagesFile() {
		return announcementMessagesFile;
	}
}
