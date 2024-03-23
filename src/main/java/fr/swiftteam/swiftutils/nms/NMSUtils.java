package fr.swiftteam.swiftutils.nms;

import org.bukkit.entity.Player;

public interface NMSUtils {

	void sendPlayerTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut);

	void sendPlayerActionBar(Player player, String message, int stay);
}
