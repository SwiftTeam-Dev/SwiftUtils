package fr.swiftteam.swiftutils.modules.AnnouncementMessages;

import fr.swiftteam.swiftutils.utilities.files.modules.AnnouncementMessagesFile;

public class AnnouncementMessage {


	protected String name;
	protected AnnouncementMessagesFile.AnnouncementType type;

	protected int frequency;
	protected int duration;

	protected String permissionNeeded;

	protected String message;
	protected String title;
	protected String subtitle;


	protected AnnouncementMessage(String name, AnnouncementMessagesFile.AnnouncementType type,
	                              int frequency, int duration,
	                              String permissionNeeded,
	                              String message, String title, String subtitle) {

		this.name = name;
		this.type = type;

		this.frequency = frequency;
		this.duration = duration;

		this.permissionNeeded = permissionNeeded;

		this.message = message;
		this.title = title;
		this.subtitle = subtitle;
	}


	protected String getName() {
		return name;
	}


	protected AnnouncementMessagesFile.AnnouncementType getType() {
		return type;
	}


	protected int getFrequency() {
		return frequency;
	}


	protected int getDuration() {
		return duration;
	}


	protected String getPermissionNeeded() {
		return permissionNeeded;
	}


	protected String getMessage() {
		return message;
	}


	protected String getTitle() {
		return title;
	}


	protected String getSubtitle() {
		return subtitle;
	}
}
