package fr.swiftteam.swiftutils.nms;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMS_v1_8_R3 implements NMSUtils {


	public void sendPlayerTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		CraftPlayer craftPlayer = (CraftPlayer) player;
		IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
		IChatBaseComponent subtitleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");

		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(
				PacketPlayOutTitle.EnumTitleAction.TITLE,
				titleComponent,
				fadeIn,
				stay,
				fadeOut
		);

		PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(
				PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
				subtitleComponent,
				fadeIn,
				stay,
				fadeOut
		);

		craftPlayer.getHandle().playerConnection.sendPacket(titlePacket);
		craftPlayer.getHandle().playerConnection.sendPacket(subtitlePacket);
	}
}
