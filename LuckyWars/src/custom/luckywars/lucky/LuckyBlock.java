package custom.luckywars.lucky;

import org.bukkit.entity.Player;

import custom.luckywars.skull.Skull;

public class LuckyBlock {
	public static void giveLuckyBlocks(Player p) {
		p.getInventory().addItem(Skull.getCustomSkull("http://textures.minecraft.net/texture/519d28a8632fa4d87ca199bbc2e88cf368dedd55747017ae34843569f7a634c5", 20));
	}
}
