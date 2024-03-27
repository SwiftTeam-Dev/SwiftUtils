package fr.swiftteam.swiftutils.utilities.serializers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationSerializer {


	public static Location deserialize(String serializedLocation) {

		String[] locationParts = serializedLocation.split("/");

		World world = Bukkit.getWorld(locationParts[0]);
		double x = Double.parseDouble(locationParts[1]);
		double y = Double.parseDouble(locationParts[2]);
		double z = Double.parseDouble(locationParts[3]);
		float yaw = Float.parseFloat(locationParts[4]);
		float pitch = Float.parseFloat(locationParts[5]);

		return new Location(world, x, y, z, yaw, pitch);

	}


	public static String serialize(Location location) {

		String world = location.getWorld().getName();
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		float yaw = location.getYaw();
		float pitch = location.getPitch();

		return world + "/" + x + "/" + y + "/" + z + "/" + yaw + "/" + pitch;
	}
}
