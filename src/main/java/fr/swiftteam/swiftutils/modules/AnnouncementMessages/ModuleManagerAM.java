package fr.swiftteam.swiftutils.modules.AnnouncementMessages;

import fr.swiftteam.swiftutils.Main;
import fr.swiftteam.swiftutils.utilities.ConsoleLogger;
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


	public static Map<String, AnnouncementMessage> getLoadedAnnouncements() {
		return loadedAnnouncements;
	}


	public static boolean loadAnnouncementMessagesModule() {

		Main.getFilesManager().loadAnnouncementMessagesFile();

		stopLoadedAnnouncements();

		List<String> enabledAnnouncements = Main.getAnnouncementMessagesFile().getEnabledAnnouncements();

		int errorsCount = 0;

		for (String announcementName : enabledAnnouncements) {
			if (isAnnouncementValid(announcementName)) {
				loadAnnouncementMessage(announcementName);
				ConsoleLogger.console("§8- §fAnnouncementMessages§7: §2" + announcementName + " §ahas been loaded!");

			} else {
				ConsoleLogger.console("§8- §fAnnouncementMessages§7: §4" + announcementName + " §cannouncement can't be loaded!");
				errorsCount += 1;
			}
		}
		return errorsCount == 0;
	}


	protected static void loadAnnouncementMessage(String announcementName) {

		AnnouncementMessagesFile.AnnouncementType type = Main.getAnnouncementMessagesFile().getAnnouncementType(announcementName);
		int frequency = Main.getAnnouncementMessagesFile().getFrequency(announcementName);
		int duration = Main.getAnnouncementMessagesFile().getDuration(announcementName);
		String permissionNeeded = Main.getAnnouncementMessagesFile().getPermissionNeeded(announcementName);
		String message = Main.getAnnouncementMessagesFile().getMessage(announcementName);
		String title = Main.getAnnouncementMessagesFile().getTitle(announcementName);
		String subtitle = Main.getAnnouncementMessagesFile().getSubtitle(announcementName);

		AnnouncementMessage announcementMessage = new AnnouncementMessage(
				announcementName, type,
				frequency, duration,
				permissionNeeded, message, title, subtitle);

		loadedAnnouncements.put(announcementName, announcementMessage);
		startAnnouncement(announcementName);
	}


	public static void startAnnouncement(String announcementName) {

		AnnouncementMessage announcementMessage = getLoadedAnnouncements().get(announcementName);

		AnnouncementMessagesFile.AnnouncementType type = announcementMessage.getType();

		String permissionNeeded = announcementMessage.getPermissionNeeded();
		long frequency = announcementMessage.getFrequency();

		activeTasks.put(announcementName, Main.getScheduler().runTaskTimer(Main.getInstance(), () -> {

			if (type == AnnouncementMessagesFile.AnnouncementType.TITLE) {

				int duration = announcementMessage.getDuration();
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

		}, frequency * 20, frequency * 20));


	}


	public static void stopLoadedAnnouncements() {
		for (BukkitTask bukkitTask : activeTasks.values()) {
			bukkitTask.cancel();
		}

		loadedAnnouncements.clear();
	}


	private static boolean isAnnouncementValid(String announcementName) {
		AnnouncementMessagesFile.AnnouncementType type = Main.getAnnouncementMessagesFile().getAnnouncementType(announcementName);
		int frequency = Main.getAnnouncementMessagesFile().getFrequency(announcementName);

		if (type == AnnouncementMessagesFile.AnnouncementType.TITLE) {
			String title = Main.getAnnouncementMessagesFile().getTitle(announcementName);
			String subtitle = Main.getAnnouncementMessagesFile().getSubtitle(announcementName);
			int duration = Main.getAnnouncementMessagesFile().getDuration(announcementName);
			return (frequency > 0 &&
					duration > 0 &&
					(!title.isEmpty() || !subtitle.isEmpty()));
		}

		if (type == AnnouncementMessagesFile.AnnouncementType.ACTIONBAR) {
			String message = Main.getAnnouncementMessagesFile().getMessage(announcementName);
			int duration = Main.getAnnouncementMessagesFile().getDuration(announcementName);
			return (frequency > 0 &&
					duration > 0 &&
					!message.isEmpty());
		}

		if (type == AnnouncementMessagesFile.AnnouncementType.BROADCAST || type == AnnouncementMessagesFile.AnnouncementType.MESSAGE) {
			String message = Main.getAnnouncementMessagesFile().getMessage(announcementName);
			return (frequency > 0 &&
					!message.isEmpty());
		}
		return false;
	}


	private static List<String> getTitleAndSubtitle(String text) {
		String[] textSplit = text.split("_SUBTITLE:_");
		String title = textSplit[0].replace("_TITLE:_", "").replace("&", "§");
		String subtitle = textSplit[0].replace("_SUBTITLE:_", "").replace("&", "§");
		return List.of(title, subtitle);
	}
}