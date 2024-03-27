package fr.swiftteam.swiftutils.utilities.files;

import org.bukkit.configuration.file.YamlConfiguration;

public class LocationsFile {

	private final YamlConfiguration yamlConfiguration;


	public LocationsFile(YamlConfiguration yamlConfiguration) {
		this.yamlConfiguration = yamlConfiguration;
	}


	public String getSpawnLocation() {
		String version = yamlConfiguration.getString("spawnLocation");
		if (version != null) {
			return version;
		}
		return null;
	}


	public void setSpawnLocation(String spawnLocation) {
        yamlConfiguration.set("spawnLocation", spawnLocation);
    }
}
