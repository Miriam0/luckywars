package custom.luckywars;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import custom.luckywars.lucky.LuckyBlock;
import custom.mgapi.Game;
import custom.mgapi.classes.SpawnPoint;
import custom.mgapi.classes.StartPoint;
import custom.mgapi.classes.SuperItem;
import custom.mgapi.enums.GameStatus;
import custom.mgapi.events.MinigameAttackEvent;
import custom.mgapi.events.MinigamePlayerDeathEvent;
import custom.mgapi.events.MinigameStartedEvent;
import custom.mgapi.events.MinigameStoppedEvent;
import custom.mgapi.managers.TeamManager;
import custom.mgapi.npc.ApiNpc;
import custom.mgapi.secondary.ItemStore;
import custom.mgapi.timers.GameProcessTimer;
import custom.mgapi.timers.GameStartTimer;
import custom.mgapi.utils.UtilStringFormat;

import me.tigerhix.lib.scoreboard.common.EntryBuilder;
import me.tigerhix.lib.scoreboard.common.animate.HighlightedString;
import me.tigerhix.lib.scoreboard.type.Entry;
import me.tigerhix.lib.scoreboard.type.ScoreboardHandler;

public class Source extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		LW.plugin = this;
		Bukkit.getPluginManager().registerEvents(new EventListener(), this);
		
		//Install player inGame start paints
		StartPoint startPointList = new StartPoint();
		startPointList.addStartPoint(new SpawnPoint(56.5, 68.5, 21.5));
		startPointList.addStartPoint(new SpawnPoint(25.5, 68.5, 52.5));
		startPointList.addStartPoint(new SpawnPoint(-18.5, 68.5, -53.5));
		startPointList.addStartPoint(new SpawnPoint(-52.5, 68.5, 25.5));
		startPointList.addStartPoint(new SpawnPoint(-54.5, 68.5, -20.5));
		startPointList.addStartPoint(new SpawnPoint(-22.5, 68.5, -55.5));
		startPointList.addStartPoint(new SpawnPoint(18.5, 68.5, -53.5));
		startPointList.addStartPoint(new SpawnPoint(52.5, 68.5, -24.5));
		
		//Install teams
		//TeamManager.setMaxPlayers(4);
		//TeamManager.createTeam("Красные", ChatColor.RED);
		//TeamManager.createTeam("Синие", ChatColor.BLUE);
		
		//Initialize game
		LW.game = new Game(this,
				new ScoreboardHandler() {
		            private final HighlightedString minigameTitle = new HighlightedString("LuckyWars", "&6", "&e");
		
		            @Override
		            public String getTitle(Player player) {
		                return minigameTitle.next();
		            }
		
		            @Override
		            public List<Entry> getEntries(Player player) {
		            	if(LW.game.getGameStatus().equals(GameStatus.Started)) {
		            		long totalSecs = GameProcessTimer.getTimeValue();
		            		long hours = totalSecs / 3600;
		            		long minutes = (totalSecs % 3600) / 60;
		            		long seconds = totalSecs % 60;
		            		String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		            		
			                return new EntryBuilder()
			                        .blank()
			                        .next("&aСтатус игры")
			                        .next(LW.game.getGameStatus().toString())
			                        .blank()
			                        .next("&aИгроки")
			                        .next(LW.game.getPlayersCount() + " из " + LW.game.getMaxPlayersCount())
			                        .blank()
			                        .next("&aДо конца")
			                        .next(timeString)
				                    .blank()
				                    .build();
		            	} else if(LW.game.getGameStatus().equals(GameStatus.Starting)) {
		            		return new EntryBuilder()
			                        .blank()
			                        .next("&aСтатус игры")
			                        .next(LW.game.getGameStatus().toString())
			                        .blank()
			                        .next("&aИгроки")
			                        .next(LW.game.getPlayersCount() + " из " + LW.game.getMaxPlayersCount())
			                        .blank()
			                        .next("&aСтарт через")
			                        .next(GameStartTimer.getTime() + " сек.")
			                        .blank()
				                    .build();
		            	} else {
		            		return new EntryBuilder()
			                        .blank()
			                        .next("&aСтатус игры")
			                        .next(LW.game.getGameStatus().toString())
			                        .blank()
			                        .next("&aИгроки")
			                        .next(LW.game.getPlayersCount() + " из " + LW.game.getMaxPlayersCount())
			                        .blank()
				                    .blank()
				                    .build();
		            	}
		            }
		        },
				"Super Smash Mobs", 
				true, true, true, true, true, true, true,//ingame configuration
				1, 8, 121,//players and ingame time configuration
				-572.4, 46.5, -1231.6, //lobby x y z
				80, 32, -84, //start point of arena
				-81, 107, 83, //end point of arena
				startPointList, 0);
		
		Bukkit.getPluginManager().registerEvents(LW.game, this);
		System.out.println("LuckyWars started.");
	}
}
