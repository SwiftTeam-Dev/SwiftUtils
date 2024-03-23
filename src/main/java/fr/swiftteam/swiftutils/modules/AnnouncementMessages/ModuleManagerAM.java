package fr.swiftteam.swiftutils.modules.AnnouncementMessages;

import fr.swiftteam.swiftutils.Main;
import fr.swiftteam.swiftutils.utilities.files.modules.AnnouncementMessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManagerAM {

	private static Map<String, BukkitTask> activeTasks = new HashMap<>();
	private static Map<String, AnnouncementMessage> loadedAnnouncements = new HashMap<>();


	public boolean loadAnnouncementMessagesModule() {

		Main.getFilesManager().loadAnnouncementMessagesFile();

		cancelCurrentAnnouncementTasks();

		List<String> enabledAnnouncements = Main.getFilesManager().getAnnouncementMessagesFile().getEnabledAnnouncements();

		int errorsCount = 0;

		for (String announcementName : enabledAnnouncements) {
			if (isAnnouncementValid(announcementName)) {
				loadAnnouncementMessage(announcementName);
				Main.getConsoleLogger().console("§8- §fAnnouncementMessages§7: §2" + announcementName + " §ahas been loaded!");

			} else {
				Main.getConsoleLogger().console("§8- §fAnnouncementMessages§7: §4" + announcementName + " §cannouncement can't be loaded!");
				errorsCount += 1;
			}
		}

		Main.getModulesManager().setModuleEnable("announcementMessages");
		return errorsCount == 0;
	}


	public void unloadAnnouncementMessagesModule() {
		cancelCurrentAnnouncementTasks();
		Main.getModulesManager().setModuleDisable("announcementMessages");

	}


	protected void loadAnnouncementMessage(String announcementName) {

		AnnouncementMessagesFile announcementMessagesFile = Main.getFilesManager().getAnnouncementMessagesFile();

		String type = announcementMessagesFile.getType(announcementName);
		int frequency = announcementMessagesFile.getFrequency(announcementName);
		int duration = announcementMessagesFile.getDuration(announcementName);
		String permissionNeeded = announcementMessagesFile.getPermissionNeeded(announcementName);
		String message = announcementMessagesFile.getMessage(announcementName);
		String title = announcementMessagesFile.getTitle(announcementName);
		String subtitle = announcementMessagesFile.getSubtitle(announcementName);

		AnnouncementMessage announcementMessage = new AnnouncementMessage(
				announcementName, type,
				frequency, duration,
				permissionNeeded, message, title, subtitle);

		loadedAnnouncements.put(announcementName, announcementMessage);
		startAnnouncement(announcementName);
	}


	private void cancelCurrentAnnouncementTasks() {
		for (BukkitTask bukkitTask : activeTasks.values()) {
			bukkitTask.cancel();
		}
		loadedAnnouncements.clear();
	}


	public void startAnnouncement(String announcementName) {

		AnnouncementMessage announcementMessage = loadedAnnouncements.get(announcementName);
		String type = announcementMessage.getType();
		long frequency = announcementMessage.getFrequency();

		activeTasks.put(announcementName, Main.getScheduler().runTaskTimer(Main.getInstance(), () -> {

			switch (type) {
				case "TITLE":
					sendTitleAnnouncement(announcementMessage);
					break;
				case "ACTIONBAR":
					sendActionBarAnnouncement(announcementMessage);
					break;
				case "BROADCAST":
					sendBroadcastAnnouncement(announcementMessage);
					break;
			}

		}, frequency * 20, frequency * 20));


	}


	private void sendTitleAnnouncement(AnnouncementMessage announcementMessage) {

		int duration = announcementMessage.getDuration();
		String permissionNeeded = announcementMessage.getPermissionNeeded();
		String title = announcementMessage.getTitle().replace("&", "§");
		String subtitle = announcementMessage.getSubtitle().replace("&", "§");

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (permissionNeeded != null) {
				if (player.hasPermission(permissionNeeded)) {
					Main.getNmsUtils().sendPlayerTitle(player, title, subtitle, 1, duration, 1);
				}
			} else {
				Main.getNmsUtils().sendPlayerTitle(player, title, subtitle, 1, duration, 1);
			}
		}
	}


	private void sendActionBarAnnouncement(AnnouncementMessage announcementMessage) {

		int duration = announcementMessage.getDuration();
		String permissionNeeded = announcementMessage.getPermissionNeeded();
		String message = announcementMessage.getMessage().replace("&", "§");

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (permissionNeeded != null) {
				if (player.hasPermission(permissionNeeded)) {
					Main.getNmsUtils().sendPlayerActionBar(player, message, duration);
				}
			} else {
				Main.getNmsUtils().sendPlayerActionBar(player, message, duration);
			}
		}
	}


	private void sendBroadcastAnnouncement(AnnouncementMessage announcementMessage) {

		String permissionNeeded = announcementMessage.getPermissionNeeded();
		String message = announcementMessage.getMessage().replace("&", "§");

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (permissionNeeded != null) {
				if (player.hasPermission(permissionNeeded)) {
					player.sendMessage(message);
				}
			} else {
				player.sendMessage(message);
			}
		}
	}


	private boolean isAnnouncementValid(String announcementName) {

		AnnouncementMessagesFile announcementMessagesFile = Main.getFilesManager().getAnnouncementMessagesFile();

		String type = announcementMessagesFile.getType(announcementName);
		int frequency = announcementMessagesFile.getFrequency(announcementName);

		switch (type) {

			case "TITLE" -> {
				String title = announcementMessagesFile.getTitle(announcementName);
				String subtitle = announcementMessagesFile.getSubtitle(announcementName);
				int duration = announcementMessagesFile.getDuration(announcementName);
				return (frequency > 0 &&
						duration > 0 &&
						(!title.isEmpty() || !subtitle.isEmpty()));
			}

			case "ACTIONBAR" -> {
				String message = announcementMessagesFile.getMessage(announcementName);
				int duration = announcementMessagesFile.getDuration(announcementName);
				return (frequency > 0 &&
						duration > 0 &&
						!message.isEmpty());
			}

			case "BROADCAST" -> {
				String message = announcementMessagesFile.getMessage(announcementName);
				return (frequency > 0 &&
						!message.isEmpty());
			}
		}
		return false;
	}
}