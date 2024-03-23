package fr.swiftteam.swiftutils.utilities;

import org.bukkit.Bukkit;

public class ConsoleLogger {


	public void console(String infoMessage) {
		Bukkit.getConsoleSender().sendMessage("§8[§b§lSwift§6§lUtils§8] §7" + infoMessage);
	}


	public void info(String infoMessage) {
		Bukkit.getLogger().info("[SwiftUtils] " + infoMessage);
	}


	public void warning(String infoMessage) {
		Bukkit.getLogger().warning("[SwiftUtils] " + infoMessage);
	}


	public void error(String infoMessage) {
		Bukkit.getLogger().severe("[SwiftUtils] " + infoMessage);
	}
}
