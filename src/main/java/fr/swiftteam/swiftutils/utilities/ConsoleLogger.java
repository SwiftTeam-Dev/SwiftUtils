package fr.swiftteam.swiftutils.utilities;

import org.bukkit.Bukkit;

public class ConsoleLogger {


	public static void console(String infoMessage) {
		Bukkit.getConsoleSender().sendMessage("§8[§b§lSwift§6§lUtils§8] §7" + infoMessage);
	}


	public static void info(String infoMessage) {
		Bukkit.getLogger().info("[SwiftUtils] " + infoMessage);
	}


	public static void warning(String infoMessage) {
		Bukkit.getLogger().warning("[SwiftUtils] " + infoMessage);
	}


	public static void error(String infoMessage) {
		Bukkit.getLogger().severe("[SwiftUtils] " + infoMessage);
	}
}
