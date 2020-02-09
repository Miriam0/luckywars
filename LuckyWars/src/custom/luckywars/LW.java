package custom.luckywars;

import org.bukkit.plugin.java.JavaPlugin;

import custom.mgapi.Game;
import custom.mgapi.managers.ArenaManager;
import custom.mgapi.managers.TeamManager;
import custom.mgapi.npc.ApiNpc;

public class LW {
	public static Game game;
	public static ArenaManager worldRestorer = new ArenaManager();
	public static JavaPlugin plugin;
}
