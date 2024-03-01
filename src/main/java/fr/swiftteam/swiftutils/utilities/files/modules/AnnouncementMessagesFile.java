package fr.swiftteam.swiftutils.utilities.files.modules;

import org.bukkit.configuration.Configuration;

import java.util.List;

public class AnnouncementMessagesFile {


	public enum AnnouncementType {
		TITLE,
		ACTIONBAR,
		BROADCAST,
		MESSAGE
	}


	private final Configuration yamlFile;


	public AnnouncementMessagesFile(Configuration yamlFile) {
		this.yamlFile = yamlFile;
	}


	public List<String> getEnabledAnnouncements() {
		return yamlFile.getStringList("enabledAnnouncements");
	}


	public AnnouncementType getAnnouncementType(String announcementName) {
		String typeName = yamlFile.getString("announcements." + announcementName + ".type").toUpperCase();

		return switch (typeName) {
			case "TITLE" -> AnnouncementType.TITLE;
			case "ACTIONBAR" -> AnnouncementType.ACTIONBAR;
			case "BROADCAST" -> AnnouncementType.BROADCAST;
			case "MESSAGE" -> AnnouncementType.MESSAGE;
			default -> null;
		};
	}


	public int getFrequency(String announcementName) {
		return yamlFile.getInt("announcements." + announcementName + ".frequency");
	}


	public int getDuration(String announcementName) {
		return yamlFile.getInt("announcements." + announcementName + ".duration");
	}


	public String getPermissionNeeded(String announcementName) {
		return yamlFile.getString("announcements." + announcementName + ".permissionNeeded");
	}


	public String getMessage(String announcementName) {
		return yamlFile.getString("announcements." + announcementName + ".message");
	}


	public String getTitle(String announcementName) {
		return yamlFile.getString("announcements." + announcementName + ".title");
	}


	public String getSubtitle(String announcementName) {
		return yamlFile.getString("announcements." + announcementName + ".subtitle");
	}
}
