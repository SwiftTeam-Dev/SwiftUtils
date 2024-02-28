package fr.swiftteam.swiftutils.utilities.files;

import fr.swiftteam.swiftutils.Main;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilesManager {

	protected ConfigurationFile configurationFile;
	protected MessagesFile messagesFile;


	public ConfigurationFile getConfigurationFile() {
		return configurationFile;
	}


	public MessagesFile getMessagesFile() {
		return messagesFile;
	}


	public void loadConfigurationFile() {
		loadYAMLFile("configuration");
	}


	public void loadMessagesFile() {
		loadYAMLFile("messages");
	}


	public boolean saveConfigurationFile() {
		return createBackupYAMLFile("configuration");
	}


	public boolean saveMessagesFile() {
		return createBackupYAMLFile("messages");
	}


	private void loadYAMLFile(String fileName) {

		File yamlFile = new File(Main.getInstance().getDataFolder(), fileName + ".yml");

		if (!yamlFile.exists()) {
			yamlFile.getParentFile().mkdirs();
			Main.getInstance().saveResource(fileName + ".yml", false);
		}

		YamlConfiguration.loadConfiguration(yamlFile);
		Configuration yamlConfiguration =
				YamlConfiguration.loadConfiguration(new File(Main.getInstance().getDataFolder(), fileName + ".yml"));

		if (fileName.equals("configuration")) {
			configurationFile = new ConfigurationFile(yamlConfiguration);

		} else if (fileName.equals("messages")) {
			messagesFile = new MessagesFile(yamlConfiguration);
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
}
