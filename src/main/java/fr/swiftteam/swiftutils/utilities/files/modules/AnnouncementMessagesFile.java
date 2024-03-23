package fr.swiftteam.swiftutils.utilities.files.modules;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class AnnouncementMessagesFile {

	private final YamlConfiguration yamlConfiguration;


	public AnnouncementMessagesFile(YamlConfiguration yamlConfiguration) {
		this.yamlConfiguration = yamlConfiguration;
	}


	public List<String> getEnabledAnnouncements() {
		return yamlConfiguration.getStringList("enabledAnnouncements");
	}


	public String getType(String announcementName) {
		return yamlConfiguration.getString("announcements." + announcementName + ".type").toUpperCase();
	}


	public Integer getFrequency(String announcementName) {
		return yamlConfiguration.getInt("announcements." + announcementName + ".frequency");
	}


	public Integer getDuration(String announcementName) {
		return yamlConfiguration.getInt("announcements." + announcementName + ".duration");
	}


	public String getPermissionNeeded(String announcementName) {
		return yamlConfiguration.getString("announcements." + announcementName + ".permissionNeeded");
	}


	public String getMessage(String announcementName) {
		return yamlConfiguration.getString("announcements." + announcementName + ".message");
	}


	public String getTitle(String announcementName) {
		return yamlConfiguration.getString("announcements." + announcementName + ".title");
	}


	public String getSubtitle(String announcementName) {
		return yamlConfiguration.getString("announcements." + announcementName + ".subtitle");
	}
}
